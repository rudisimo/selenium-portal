package com.blim.qa.portal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public class App {
	public static void main(String[] args) throws IOException {
		Properties prop = new Properties();
		FileInputStream config = null;
		try {
			config = new FileInputStream("config.properties");
			prop.load(config);
			config.close();
		} catch (Exception e) {

		} finally {
			if (config != null) {
				config.close();
			}
		}

		OutputStream out = new FileOutputStream(new File("config.properties"));
		List<Option> propertiesOptsList = new ArrayList<Option>();

		String property = null;
		String flag = null;

		for (Iterator i = propertiesOptsList.iterator(); i.hasNext();) {
			Option opt = ((Option) i.next());
			property = opt.getOption();
			flag = opt.getFlag().toUpperCase();

			switch (flag) {
			case "-b":
				prop.setProperty("baseUrl", property);
				break;
			case "-u":
				prop.setProperty("username", property);
				break;
			case "-p":
				prop.setProperty("password", property);
				break;
			case "-chromeDriver":
				prop.setProperty("chromeDriver", property);
				break;
			default:
				System.out.println("Value " + property
						+ " is not a recognized option for properties list");
			}
		}
		prop.store(out, null);
		out.close();
	}

	private static class Option {
		String flag, opt;

		public Option(String flag, String opt) {
			this.flag = flag;
			this.opt = opt;
		}

		public String getFlag() {
			return this.flag;
		}

		public String getOption() {
			return this.opt;
		}
	}
}
