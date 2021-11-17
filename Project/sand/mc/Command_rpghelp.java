package rpg.sand.mc;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import rpg.sand.file.Config;

public class Command_rpghelp implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', Config.loadMsg("alerts").get(0)));
			return true;
		}

		Player player = (Player) sender;
	
		if (args.length == 0) {
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.loadMsg("help").get(0)));
			return true;
		}
		else if (args.length > 1) {
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.loadMsg("alerts").get(2)));
			return true;
		}
	
			switch(args[0].toLowerCase()) {
				case "general":
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.loadMsg("help").get(1)));
					break;
				case "races":
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.loadMsg("help").get(2)));
					break;
				case "dwarves":
				case "dwarfs":
				case "dwarf":
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.loadMsg("help").get(3)));
					break;
				case "elves":
				case "elf":
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.loadMsg("help").get(4)));
					break;
				case "goblins":
				case "goblin":
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.loadMsg("help").get(5)));
					break;
				case "humans":
				case "human":
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.loadMsg("help").get(6)));
					break;
				default:
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.loadMsg("alerts").get(3)).replaceFirst("%command%", args[0]));
			}
		return false;
	}

}
