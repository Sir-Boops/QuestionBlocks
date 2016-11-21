package me.boops.questionblocks.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import me.boops.questionblocks.config.Cache;
import me.boops.questionblocks.config.Config;

public class QB implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String string, String[] args) {

		Config config = new Config();
		Player player = (Player) sender;

		// Check if sender has permission
		if (sender.hasPermission("questionblocks.admin")) {

			// Check the args

			// if Create
			if (args[0].toLowerCase().equals("create")) {

				// Check if sender is a player
				if (sender instanceof Player) {
					// Check for a name
					if (!args[1].isEmpty()) {

						// Check if a block with that name is already
						// In use
						List<String> blocks = (List<String>) config.Get("blocks");
						if (blocks != null) {
							if (!blocks.contains(args[1]) && !Cache.creating.containsKey(player.getUniqueId())
									&& !Cache.creating.containsValue(args[1])) {

								// Create The Head
								ItemStack head = new ItemStack(Material.SKULL_ITEM, 1,
										(short) SkullType.PLAYER.ordinal());

								// Set the LORE
								List<String> lore = new ArrayList<String>();
								lore.add("Place the head where you");
								lore.add("wish to have your question block");

								// Set the Meta
								SkullMeta meta = (SkullMeta) head.getItemMeta();
								meta.setDisplayName(args[1]);
								meta.setLore(lore);
								meta.setOwner("MHF_Question");

								// Save the meta
								head.setItemMeta(meta);

								// Set the player into cache
								Cache.creating.put(player.getUniqueId(), args[1]);

								// Give the head to the player
								player.getInventory().addItem(head);

							} else {
								sender.sendMessage(
										"There is already a block with that name or you have not finished creating a block yet!");
							}
						} else {
							// Create The Head
							ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());

							// Set the LORE
							List<String> lore = new ArrayList<String>();
							lore.add("Place the head where you");
							lore.add("wish to have your question block");

							// Set the Meta
							SkullMeta meta = (SkullMeta) head.getItemMeta();
							meta.setDisplayName(args[1]);
							meta.setLore(lore);
							meta.setOwner("MHF_Question");

							// Save the meta
							head.setItemMeta(meta);

							// Set the player into cache
							Cache.creating.put(player.getUniqueId(), args[1].toLowerCase());

							// Give the head to the player
							player.getInventory().addItem(head);
						}

					} else {
						sender.sendMessage("/qb help");
					}
				} else {
					sender.sendMessage("This command must be run as a player!");
				}
			}

			// If Set
			if (args[0].toLowerCase().equals("set")) {

				// If Setting item
				if (args[2].toLowerCase().equals("item")) {

					// Check if block exists
					List<String> blocks = (List<String>) config.Get("blocks");
					if (blocks != null && blocks.contains(args[1])) {

						// Set the new item in the config
						config.Set(args[1].toLowerCase() + ".drops", args[3]);
						
						// Tell the player
						sender.sendMessage("item dropping for block " + args[1].toLowerCase() + " Set to " + args[3]);

					}
				}

				// If setting time
				if (args[2].toLowerCase().equals("time")) {
					// Check if block exists
					List<String> blocks = (List<String>) config.Get("blocks");
					if (blocks != null && blocks.contains(args[1])) {

						// Set the new item in the config
						config.Set(args[1].toLowerCase() + ".timer", Integer.valueOf(args[3]));

						// Tell the player
						sender.sendMessage("Time for block " + args[1].toLowerCase() + " Set to " + args[3]);

					}
				}

				// If setting drop
				if (args[2].toLowerCase().equals("drop")) {
					// Check if block exists
					List<String> blocks = (List<String>) config.Get("blocks");
					if (blocks != null && blocks.contains(args[1])) {

						// Set the new item in the config
						config.Set(args[1].toLowerCase() + ".drop", args[3]);

						// Tell the player
						sender.sendMessage("Drop status for block " + args[1].toLowerCase() + " Set to " + args[3]);

					}
				}
				
				// If setting drop
				if (args[2].toLowerCase().equals("amount")) {
					// Check if block exists
					List<String> blocks = (List<String>) config.Get("blocks");
					if (blocks != null && blocks.contains(args[1])) {

						// Set the new item in the config
						config.Set(args[1].toLowerCase() + ".amount", Integer.parseInt(args[3]));

						// Tell the player
						sender.sendMessage("Drop status for block " + args[1].toLowerCase() + " Set to " + args[3]);

					}
				}
			}
			
			if(args[0].toLowerCase().equals("del")){
				List<String> blocks = (List<String>) config.Get("blocks");
				if (blocks != null && blocks.contains(args[1])) {
					
					blocks.remove(args[1].toLowerCase());
					
					config.Set("blocks", blocks);
					
					// Tell the player
					sender.sendMessage("Removed the block " + args[1].toLowerCase());
					
				}
			}

			// If help
			if (args[0].toLowerCase().equals("help")) {
				sender.sendMessage("Question Block Help:");
				sender.sendMessage("/qb create <Block Name>");
				sender.sendMessage("/qb set <block name> <item/time/drop/amount> <itemID/time in sec/[inv/floor]/amount#>");
				sender.sendMessage("/qb del <block name>");
			}

		} else {
			sender.sendMessage("You don't have permission!");
		}
		return true;
	}

}
