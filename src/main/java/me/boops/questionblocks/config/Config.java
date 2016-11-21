package me.boops.questionblocks.config;

import java.io.File;

import me.boops.questionblocks.Main;

public class Config {
	
	//Create The Config
	public void Create() {

		// Create the folder if it's not
		// Already there
		if (!Main.plugin.getDataFolder().exists()) {
			Main.plugin.getDataFolder().mkdirs();
		}

		try {

			// Try and load the default config
			File file = new File(Main.plugin.getDataFolder(), "config.yml");

			if (!file.exists()) {
				Main.plugin.saveDefaultConfig();
			}

		} catch (Exception e) {
			e.printStackTrace();

		}

	}
	
	//Set Somthing Is The Config
	public void Set(String path, Object data){
		Main.plugin.getConfig().set(path, data);
		Main.plugin.saveConfig();
	}
	
	public Object Get(String path){
		return Main.plugin.getConfig().get(path);
	}
}
