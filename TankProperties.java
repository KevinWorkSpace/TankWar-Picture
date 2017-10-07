package TankWar;

import java.io.IOException;
import java.util.Properties;

public class TankProperties {
	static Properties props = new Properties();
	static {
		try {
			props.load(TankProperties.class.getClassLoader().getResourceAsStream("config/Tank.properties"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	public static String getProperty(String key) {
		return props.getProperty(key);
	}
	
}
