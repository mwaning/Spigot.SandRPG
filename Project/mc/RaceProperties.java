package rpg.sand.mc;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World.Environment;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import rpg.sand.file.Config;
import rpg.sand.file.Players;

public class RaceProperties {

	public String raceCheck(Player player, String target) {
		String playerName = player.getName();
		String race = "";
		
		if (target.length() > 3) {
			try { playerName = Bukkit.getPlayer(target).getName(); } catch (Exception e) { }
		}

		if (Players.get().getString("current.dwarf." + playerName) != null) {
			race = "dwarf";
			dwarf(player, playerName);
		}
		else if (Players.get().getString("current.elf." + playerName) != null) {
			race = "elf";
			elf(player, playerName);
		}
		else if (Players.get().getString("current.goblin." + playerName) != null) {
			race = "goblin";
			goblin(player, playerName);
		}
		else if (Players.get().getString("current.human." + playerName) != null) {
			race = "human";
			human(player, playerName);
		}
		else {
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.loadMsg("messages").get(0)).replace("%playername%", playerName));
			Players.playtimeSet(player, race, race, 0, true);		
			//Players.playtimeSet(player, "", "", 0, false);		

			human(player, playerName);
		}
		return race;
	}
	
	public void raceChange(Player player, String newRace) {
		String oldRace = raceCheck(player, "");
		
		for (PotionEffect effect : player.getActivePotionEffects()) {
			player.removePotionEffect(effect.getType());
		}
		
		Players.playtimeSet(player, newRace, oldRace, 0, true);
	}

	public void dwarf(Player player, String playerName) {
		player.setDisplayName(ChatColor.BLUE + playerName + ChatColor.WHITE);
		player.setPlayerListName(ChatColor.BLUE + playerName + ChatColor.WHITE);
		player.getLocation().getY();
		if (player.getLocation().getY() <= 62) {
			player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 0, true, false));
		}
		else {
			player.removePotionEffect(PotionEffectType.FAST_DIGGING);
		}
	}
	
	public void elf(Player player, String playerName) {
		player.setDisplayName(ChatColor.DARK_GREEN + playerName + ChatColor.WHITE);
		player.setPlayerListName(ChatColor.DARK_GREEN + playerName + ChatColor.WHITE);
		
		if (player.getLocation().getY() >= 62) {
			player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0, true, false));
		}
		else {
			player.removePotionEffect(PotionEffectType.SPEED);
		}
	}
	
	public void goblin(Player player, String playerName) {
		player.setDisplayName(ChatColor.DARK_AQUA + playerName + ChatColor.WHITE);
		player.setPlayerListName(ChatColor.DARK_AQUA + playerName + ChatColor.WHITE);
				
		if (!(player.getWorld().getEnvironment() == Environment.NETHER)) {
			player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, Integer.MAX_VALUE, 0, true, false));
			player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
		}
		else {
			player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 0, true, false));
			player.removePotionEffect(PotionEffectType.WEAKNESS);
		}
	}
	
	public void human(Player player, String playerName)  {
		player.setDisplayName(ChatColor.WHITE + playerName + ChatColor.WHITE);
		player.setPlayerListName(ChatColor.WHITE + playerName + ChatColor.WHITE);
	}
	
}

