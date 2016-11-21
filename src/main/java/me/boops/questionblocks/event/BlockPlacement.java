package me.boops.questionblocks.event;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import me.boops.questionblocks.config.Cache;
import me.boops.questionblocks.config.Config;

public final class BlockPlacement implements Listener {

	@EventHandler
	public void BlockPlacement(BlockPlaceEvent event) {

		Player player = event.getPlayer();
		Config config = new Config();
		Block block = event.getBlock();

		// Check if player is placing the skull
		if (event.getBlock().getType() == Material.SKULL) {

			// Check if the player is set to
			// Create a new Question block
			if (Cache.creating.containsKey(player.getUniqueId())) {

				// Add Block to the list
				List<String> blocks = (List<String>) config.Get("blocks");

				if (blocks != null) {
					blocks.add(Cache.creating.get(player.getUniqueId()));
					config.Set("blocks", blocks);
				} else {
					blocks = new ArrayList<String>();
					blocks.add(Cache.creating.get(player.getUniqueId()));
					config.Set("blocks", blocks);
				}

				// Write the cords & world to the config
				config.Set(Cache.creating.get(player.getUniqueId()) + ".world",
						block.getLocation().getWorld().getName());
				config.Set(Cache.creating.get(player.getUniqueId()) + ".x", block.getLocation().getBlockX());
				config.Set(Cache.creating.get(player.getUniqueId()) + ".y", block.getLocation().getBlockY());
				config.Set(Cache.creating.get(player.getUniqueId()) + ".z", block.getLocation().getBlockZ());

				// Set the block settings to default
				config.Set(Cache.creating.get(player.getUniqueId()) + ".drops", "DIRT");
				config.Set(Cache.creating.get(player.getUniqueId()) + ".timer", 0);
				config.Set(Cache.creating.get(player.getUniqueId()) + ".drop", "inv");
				config.Set(Cache.creating.get(player.getUniqueId()) + ".amount", 1);

				// Create The New question block
				player.sendMessage("You have created the Question Block " + Cache.creating.get(player.getUniqueId()));

				// Remove the player from the cache
				Cache.creating.remove(player.getUniqueId());

				player.getInventory().remove(player.getInventory().getItemInMainHand());

			}
		}
	}
}
