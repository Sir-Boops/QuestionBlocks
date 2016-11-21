package me.boops.questionblocks.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class QuestionBlock implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String string, String[] arr) {
		
		//Check if sender has permission
		if(sender.hasPermission("questionblocks.admin")){
			sender.sendMessage("Please use /qb help for help!");
			return true;	
		} else {
			sender.sendMessage("You don't have permission!");
		}
		
		return true;
	}
	
}
