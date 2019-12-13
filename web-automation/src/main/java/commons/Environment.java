package commons;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.ConfigFactory;

import static org.aeonbits.owner.Config.*;

@Sources({
        "classpath:environments/${env}.properties"
})
public interface Environment extends Config {
    Environment ENV = ConfigFactory.create(Environment.class, System.getenv(), System.getProperties());

    @Key("web.url")
    String getUrl();

    @Key("web.said")
    String getIDNumber();

    @Key("web.password")
    String getPassword();

    @Key("testPlanID")
    String getTestPlanID();

}

