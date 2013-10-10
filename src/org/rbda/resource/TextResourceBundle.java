package org.rbda.resource;

import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

import org.rbda.logging.Logger;

/**
 * System Text Resource Bundle
 * 
 * @author fujunwei
 * 
 */
public class TextResourceBundle {
	private static TextResourceBundle resourceBundle = null;

	private static Properties props = null;

	/**
	 * Get ResourceBundle instance
	 * 
	 * @return
	 */
	public static TextResourceBundle getInstance() {
		if (resourceBundle == null) {
			resourceBundle = new TextResourceBundle();
		}

		return resourceBundle;
	}

	private TextResourceBundle() {
		try {
			props = new Properties();

			Locale zhLoc = new Locale("zh", "CN");
			Locale enLoc = new Locale("en", "US");
			ResourceBundle bundleZh = ResourceBundle
					.getBundle("Message", zhLoc);
			ResourceBundle bundleEn = ResourceBundle
					.getBundle("Message", enLoc);

			// load all text resource into properties object
			Enumeration<String> keys = bundleEn.getKeys();
			while (keys.hasMoreElements()) {
				String key = keys.nextElement();
				props.put(key, bundleZh.getString(key));
			}
			keys = bundleZh.getKeys();
			while (keys.hasMoreElements()) {
				String key = keys.nextElement();
				props.put(key, bundleZh.getString(key));
			}
		} catch (MissingResourceException ex) {
			Logger.getInstance().warn("Could not find resource file:Message");
		}

	}

	/**
	 * Get Local Text String
	 * 
	 * @param name
	 *            text name
	 * @return
	 */
	public String getString(String name) {
		return props.getProperty(name, name);
	}
}
