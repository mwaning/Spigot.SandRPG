package rpg.sand.mc;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import rpg.sand.file.Config;

public class Command_rpgrace implements CommandExecutor {
	RaceProperties rp = new RaceProperties();

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {			
		if (!(sender instanceof Player)) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', Config.loadMsg("alerts").get(0)));
			return true;
		}
		
		Player player = (Player) sender;

		if (args.length < 2){
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.loadMsg("alerts").get(1)));
			return true;
		}
		else if (args.length > 2) {
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.loadMsg("alerts").get(2)));
			return true;
		}
			
		Player target = null;
		try {
			target = Bukkit.getPlayer(args[0]);
		} catch (Exception e) { }
		
		if (target == null) {
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.loadMsg("alerts").get(4).replace("%target%", args[0])));
			return true;
		}
		
		player.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.loadMsg("messages").get(1).replace("%target%", args[0]).replace("%race%", args[1])));
	
		switch(args[1].toLowerCase()) {
			case "dwarf":
			case "dwarfs":
			case "dwarves":
				rp.raceChange(target, "dwarf");
				break;
			case "elf":
			case "elves":
				rp.raceChange(target, "elf");
				break;
			case "goblin":
			case "goblins":
				rp.raceChange(target, "goblin");
				break;
			case "human":
			case "humans":
				rp.raceChange(target, "human");
				break;
			default:
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.loadMsg("alerts").get(5).replace("%target%", args[0]).replace("%race%", args[1])));
				return true;
		}
		
		player.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.loadMsg("messages").get(2).replace("%target%", args[0]).replace("%race%", args[1])));
		

		return false;
	}

}
