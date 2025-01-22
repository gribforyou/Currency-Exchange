package model;

import java.io.IOException;
import java.util.Properties;

final class PropertiesUtil {
    private static final Properties PROPERTIES = new Properties();

    static {
        try(var inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream("db.properties")){
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getProperty(String key) {
        return PROPERTIES.getProperty(key);
    }

    private PropertiesUtil() {}
}
