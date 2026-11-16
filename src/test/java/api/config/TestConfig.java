package api.config;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.LoadPolicy;
import org.aeonbits.owner.Config.LoadType;
import org.aeonbits.owner.Config.Sources;

@LoadPolicy(LoadType.MERGE)
@Sources({"system:properties", "classpath:config.properties"})
public interface TestConfig extends Config {
    @Key("base.api.url")
    String baseUrl();

    @Key("browser")
    @DefaultValue("chrome")
    String browser();
}