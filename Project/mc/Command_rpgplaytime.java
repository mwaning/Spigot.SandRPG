package rpg.sand.mc;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import rpg.sand.file.Config;
import rpg.sand.file.Players;

import org.bukkit.ChatColor;

public class Command_rpgplaytime implements CommandExecutor {
	RaceProperties rp = new RaceProperties();

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String race = "";
		Player player = (Player) sender;
		
		if (!(sender instanceof Player)) {
			Bukkit.getConsoleSender().sendMessage(Config.loadMsg("alerts").get(0));
		}

		if (args.length > 1) {
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.loadMsg("alerts").get(2)));
		}		
		else if (args.length == 0) {
			race = rp.raceCheck(player, "");
			Players.playtimeOnePlayer(player, null, race);
		}
		else if (args[0].toLowerCase().equals("rl")) {
			
		}
		else if (args[0].toLowerCase().equals("all")) {
			Players.playtimeAllPlayers(player);
		}
		else if (Players.get().getString("total.all." + args[0].toLowerCase()) != null) {
			race = rp.raceCheck(player, args[0]);
			Players.playtimeOnePlayer(player, args[0].toLowerCase(), race);
		}
		else {
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.loadMsg("alerts").get(6)).replaceFirst("%playername%", args[0]));
		
		}
		return false;
	}
}
