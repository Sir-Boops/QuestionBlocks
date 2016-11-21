package me.boops.questionblocks.event;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.boops.questionblocks.config.Config;

public class PlayerInteract implements Listener {
	
	@EventHandler
	public void PlayerInteract(PlayerInteractEvent event) {
		Block block = event.getClickedBlock();

		// Check if it's a skull
		if (block.getType() == Material.SKULL) {

			Player player = event.getPlayer();
			Config config = new Config();
			
			//Check if they have permission
			if(player.hasPermission("questionblocks.use")){
				
				// Get list of blocks
				List<String> block_list = (List<String>) config.Get("blocks");

				// Make sure the List is not null
				if (block_list != null) {
					
					//Loop though and try to find matching cords
					for(int i=0; block_list.size()>i; i++){
						if(config.Get(block_list.get(i)+".world").equals(block.getLocation().getWorld().getName())){
							if(config.Get(block_list.get(i)+".x").equals(block.getLocation().getBlockX())){
								if(config.Get(block_list.get(i)+".y").equals(block.getLocation().getBlockY())){
									if(config.Get(block_list.get(i)+".z").equals(block.getLocation().getBlockZ())){
										
										//We have a vaild block!
										//Now check and add the player to the config
										
										int time = (int) (System.currentTimeMillis() / 1000);
										
										if(config.Get("players." + player.getUniqueId() + "." + block_list.get(i)) == null){
											config.Set("players." + player.getUniqueId() + "." + block_list.get(i), 0);
										}
										
										int player_time = (int) config.Get("players." + player.getUniqueId() + "." + block_list.get(i));
										int block_time = (int) config.Get(block_list.get(i) + ".timer");
										
										//Check if the player is allowed to destroy a block
										if(config.Get(block_list.get(i) + ".timer").equals(0) ||
												config.Get("players." + player.getUniqueId() + "." + block_list.get(i)).equals(0) ||
												(player_time + block_time) < time){
											
											//Set last destory time
											config.Set("players." + player.getUniqueId() + "." + block_list.get(i), time);
											
											if(config.Get(block_list.get(i) + ".drop").equals("inv")){
												//Player gets the items
												player.getInventory().addItem(new ItemStack(Material.getMaterial(config.Get(block_list.get(i)+".drops").toString()), (int) config.Get(block_list.get(i) + ".amount")));
											}
											
											if(config.Get(block_list.get(i) + ".drop").equals("floor")){
												//Else drop the item
												block.getWorld().dropItem(block.getLocation().add(0.5, 1, 0.5), new ItemStack(Material.getMaterial(config.Get(block_list.get(i)+".drops").toString()), (int) config.Get(block_list.get(i) + ".amount")));	
											}
										} else {
											player.sendMessage("Sorry, You cannot use this block yet");
										}
										event.setCancelled(true);
										return;
									}
								}
							}
						}
					}
				}
			}
		}
	}

}
