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

		if (args.length < 0){
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.loadMsg("alerts").get(1)));
			return true;
		}
		else if (args.length > 1) {
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.loadMsg("alerts").get(2)));
			return true;
		}
		else if (args.length == 0) {
			String race = rp.raceCheck(player, "");
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.loadMsg("messages").get(1).replace("%race%", race)));
			return true;
		}
		
		player.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.loadMsg("messages").get(2).replace("%race%", args[0])));
		
		switch(args[0].toLowerCase()) {
			case "dwarf":
			case "dwarfs":
			case "dwarves":;
				rp.raceChange(player, "dwarf");
				break;
			case "elf":
			case "elves":
				rp.raceChange(player, "elf");
				break;
			case "goblin":
			case "goblins":
				rp.raceChange(player, "goblin");
				break;
			case "human":
			case "humans":
				rp.raceChange(player, "human");
				break;
			default:
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.loadMsg("alerts").get(7).replace("%race%", args[0])));
				return true;
		}
		

		return false;
	}

}
