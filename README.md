# Stellar Burgers Test Automation

Автоматизированные тесты для веб-приложения **Stellar Burgers** (конструктор бургеров): API-тесты, UI-тесты и unit-тесты на Java с отчётами Allure.

## Что покрыто тестами

Всего 41 тестовый метод в трёх наборах.

| Набор | Пакет | Что проверяется |
| --- | --- | --- |
| API | `api.tests` | Создание пользователя, авторизация, обновление данных пользователя, создание заказа, получение заказов |
| UI | `ui.tests` | Регистрация, вход (через разные точки входа), выход из аккаунта, переключение вкладок конструктора |
| Unit | `unit.tests` | Модели бургера: булки, ингредиенты, типы ингредиентов, добавление, перемещение, удаление ингредиентов, расчёт цены и чек |

### API-тесты

- **Создание пользователя:** уникальный пользователь, уже зарегистрированный пользователь, отсутствие имени, пароля или email.
- **Авторизация:** существующий пользователь, неверный пароль, неверный email.
- **Обновление данных:** с авторизацией и без неё.
- **Создание заказа:** с авторизацией и без, с ингредиентами и без, с невалидными хэшами ингредиентов.
- **Получение заказов:** авторизованным и неавторизованным пользователем.

### UI-тесты

- **Регистрация:** успешная регистрация, ошибка при пароле короче 6 символов.
- **Вход:** через кнопку «Войти в аккаунт», «Личный кабинет», форму регистрации и страницу восстановления пароля.
- **Личный кабинет:** переход со страницы восстановления пароля и выход из аккаунта.
- **Конструктор:** переключение вкладок «Булки», «Соусы» и «Начинки».

### Unit-тесты

Проверяют классы `Bun`, `Ingredient`, `IngredientType` и `Burger` с использованием моков (Mockito) и параметризованных тестов (JUnit 5).

## Технологии

| Назначение | Инструмент |
| --- | --- |
| Язык | Java 17 |
| Сборка | Maven |
| Тестовый раннер | JUnit 5 |
| UI-тесты | Selenide, Selenium WebDriver |
| API-тесты | REST Assured |
| Unit-тесты | JUnit 5, Mockito |
| Отчёты | Allure |
| Тестовые данные | DataFaker |
| Конфигурация | Owner |
| CI | Jenkins (в Docker) |

## Структура проекта

```text
src
├── main/java/models            # модель пользователя (User)
└── test
    ├── java
    │   ├── api
    │   │   ├── config          # конфигурация и пути API
    │   │   ├── tests           # API-тесты
    │   │   └── utils           # генератор тестовых пользователей
    │   ├── clients             # клиенты API (UserClient, OrderClient)
    │   ├── ui
    │   │   ├── config          # пути страниц приложения
    │   │   ├── pages           # Page Object классы
    │   │   └── tests           # UI-тесты
    │   └── unit
    │       ├── models          # проверяемые модели бургера
    │       └── tests           # unit-тесты
    └── resources
        ├── allure.properties
        ├── config.properties.example
        └── selenide.properties
```

## Подход к тестам

- **Page Object:** страницы приложения описаны отдельными классами, действия на страницах оформлены как шаги Allure (`@Step`).
- **Слой клиентов:** запросы к API вынесены в `UserClient` и `OrderClient`, пути собраны в константах `ApiRoutes`.
- **Изолированные данные:** для каждого теста генерируется новый пользователь, после теста он удаляется через API.
- **Подготовка через API:** в UI-тестах пользователь создаётся через API, а не через интерфейс, чтобы тесты были быстрее и стабильнее.
- **Конфигурация вне кода:** адрес API хранится в `config.properties`, настройки браузера и таймаутов в `selenide.properties`.

## Быстрый старт

1. Склонируйте репозиторий:

   ```bash
   git clone https://github.com/youngblader/stellar-burgers-automation.git
   cd stellar-burgers-automation
   ```

2. Создайте файл конфигурации из примера:

   ```bash
   cp src/test/resources/config.properties.example src/test/resources/config.properties
   ```

3. Откройте `src/test/resources/config.properties` и укажите адрес API:

   ```properties
   base.api.url=https://qa-stellarburgers.education-services.ru/api
   browser=chrome
   ```

   Файл `config.properties` добавлен в `.gitignore` и не попадает в репозиторий.

Адрес веб-приложения для UI-тестов задан в `src/test/resources/selenide.properties` (`selenide.baseUrl`).

## Запуск тестов

Все тесты:

```bash
mvn test
```

Отдельные наборы:

```bash
mvn test -Dtest='api.tests.*'
mvn test -Dtest='ui.tests.*'
mvn test -Dtest='unit.tests.*'
```

Один тестовый класс:

```bash
mvn test -Dtest=SignInTest
```

UI-тесты без открытия окна браузера:

```bash
mvn test -Dselenide.headless=true
```

Адрес API можно передать параметром вместо файла `config.properties` (параметр имеет приоритет над файлом):

```bash
mvn test -Dbase.api.url=<адрес API>
```

## Отчёты Allure

Результаты прогона сохраняются в `target/allure-results`.

Открыть отчёт в браузере:

```bash
mvn allure:serve
```

Собрать отчёт в папку `target/site/allure-maven-plugin`:

```bash
mvn allure:report
```

В отчёте у каждого теста есть название (`@DisplayName`), описание (`@Description`) и пошаговое выполнение (`@Step`). Скриншоты и исходный код страниц при падении UI-тестов Selenide сохраняет в `build/reports/selenide`.

## Запуск в Jenkins (Docker)

Тесты можно запускать на Jenkins, развёрнутом в Docker, и получать Allure-отчёт на странице каждой сборки.

### 1. Запуск Jenkins в Docker

Создайте `docker-compose.yml`:

```yaml
version: '3.7'
services: # описание сервисов, которые развернутся при запуске файла
  jenkins: # развернётся Jenkins
    image: jenkins/jenkins:lts # образ, который скачается для установки Jenkins
    privileged: true
    user: root
    ports: # порты, которые использует сервис
      - 8081:8080 # В контейнере Jenkins развернётся на порте 8080,
        # но на локальной машине этот порт часто занят,
        # поэтому порт 8080 контейнера связывается
        # с портом 8081 на локальной машине.
      # Получится адрес localhost:8081
    container_name: aqa-burgers-jenkins # имя контейнера: оно может быть любым
```

Запустите контейнер и получите пароль для первого входа:

```bash
docker compose up -d
docker exec aqa-burgers-jenkins cat /var/jenkins_home/secrets/initialAdminPassword
```

Jenkins откроется по адресу `http://localhost:8081`. При первом входе установите рекомендуемые плагины.

> Контейнер нужно останавливать командой `docker compose stop`. Команда `docker compose down` удаляет контейнер, а без тома `jenkins_home` ещё и все настройки.

### 2. Браузер для UI-тестов

В образе Jenkins нет браузера, поэтому установите Chromium и драйвер в контейнер:

```bash
docker exec -u root aqa-burgers-jenkins bash -c "apt-get update && apt-get install -y chromium chromium-driver"
```

При пересоздании контейнера команду нужно выполнить снова.

### 3. Плагины и инструменты

1. **Manage Jenkins → Plugins → Available plugins**: установите **Allure Jenkins Plugin**.
2. **Manage Jenkins → Tools** добавьте (везде с опцией Install automatically):
   - JDK 17;
   - Maven;
   - Allure Commandline.

### 4. Секрет с адресом API

Файл `config.properties` не хранится в репозитории, поэтому адрес API передаётся в тесты из Jenkins:

1. **Manage Jenkins → Credentials → System → Global credentials → Add Credentials**.
2. Тип **Secret text**, в поле Secret укажите адрес API, ID: `base-api-url`.

### 5. Настройка job

Создайте **Freestyle project** со следующими параметрами:

| Раздел | Настройка |
| --- | --- |
| Source Code Management | Git, URL репозитория, ветка `*/main` |
| Build Environment | **Use secret text(s) or file(s)** → Secret text, Variable `BASE_API_URL`, Credentials `base-api-url` |
| Build Steps | **Invoke top-level Maven targets** (выберите настроенный Maven), поле Goals — см. ниже |
| Post-build Actions | **Allure Report**, Path: `target/allure-results` |

Goals:

```text
clean test -Dbase.api.url=$BASE_API_URL -Dbrowser=chrome -Dselenide.browser=chrome -Dselenide.headless=true -Dselenide.browserBinary=/usr/bin/chromium -Dwebdriver.chrome.driver=/usr/bin/chromedriver -Dchromeoptions.args=--no-sandbox,--disable-dev-shm-usage
```

### 6. Запуск и результат

Нажмите **Build Now**. После сборки на странице появится ссылка **Allure Report**. Отчёт строится и для упавших сборок.

### Как конфигурация доходит до тестов

Класс `TestConfig` (Owner) читает настройки из системных свойств и из `config.properties`. Системные свойства (`-Dbase.api.url=...`) имеют приоритет, поэтому локально тесты работают с файлом, а в Jenkins значение передаётся параметром. Если файла `config.properties` нет, ошибки не возникает.

### Возможные проблемы

| Симптом | Причина и решение |
| --- | --- |
| `baseURI cannot be null` | Адрес API не передан: проверьте секрет, переменную `BASE_API_URL` и параметр `-Dbase.api.url` |
| `chromedriver must exist` | В контейнере не установлены Chromium и драйвер (шаг 2) |
| `Driver server process died prematurely, exit value: 127` | Драйвер не подходит под архитектуру (например, ARM на Apple Silicon): используйте `chromium-driver` из пакетов контейнера |
| Тесты падают из-за формата чисел | Проверки не должны зависеть от локали (например, использовать `String.format("%f", ...)` вместо жёстко заданной запятой) |

## Лицензия

Проект распространяется по лицензии MIT, подробности в файле [LICENSE](LICENSE).
