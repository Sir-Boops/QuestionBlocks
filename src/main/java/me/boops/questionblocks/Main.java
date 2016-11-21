package me.boops.questionblocks;

import org.bukkit.plugin.java.JavaPlugin;

import me.boops.questionblocks.commands.QB;
import me.boops.questionblocks.commands.QuestionBlock;
import me.boops.questionblocks.config.Config;
import me.boops.questionblocks.event.BlockBreak;
import me.boops.questionblocks.event.BlockPlacement;
import me.boops.questionblocks.event.PlayerInteract;
import me.boops.questionblocks.logging.Logger;

public class Main extends JavaPlugin {

	Logger log = new Logger();
	public static Main plugin;

	@Override
	public void onEnable() {
		
		//This is the plugin
		plugin = this;

		// Start Up
		log.Log("Hello Server!");

		// Register Commands
		this.getCommand("questionblock").setExecutor(new QuestionBlock());
		this.getCommand("qb").setExecutor(new QB());

		// Register Listeners
		this.getServer().getPluginManager().registerEvents(new BlockPlacement(), this);
		this.getServer().getPluginManager().registerEvents(new BlockBreak(), this);
		this.getServer().getPluginManager().registerEvents(new PlayerInteract(), this);
		
		//Register permissions
		

		// Load or create the config
		new Config().Create();

	}

	@Override
	public void onDisable() {
		
		//Disable the plugin
		plugin = null;
		
		//Save the config on shutdown
		this.saveConfig();
		
	}
}
