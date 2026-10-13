package api.config;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.DefaultValue;
import org.aeonbits.owner.Config.Key;
import org.aeonbits.owner.Config.Sources;

@Sources({"classpath:config.properties"})
public interface TestConfig extends Config {
    @Key("base.api.url")
    String baseUrl();

    @Key("browser")
    @DefaultValue("chrome")
    String browser();
}