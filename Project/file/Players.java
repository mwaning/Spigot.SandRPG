package rpg.sand.file;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.configuration.file.FileConfiguration;

public class Players {
    private static File players;
    private static FileConfiguration playersConf;
	private static ArrayList<String> list = new ArrayList<String>();
 
    public static void setup(){
    	players = new File(Bukkit.getServer().getPluginManager().getPlugin("SandRPG").getDataFolder(), "players.yml");
 
        if (!players.exists()){
            try{
            	players.createNewFile();
            }catch (IOException e){
				e.printStackTrace();
            }
        }
        playersConf = YamlConfiguration.loadConfiguration(players);
    }
 
    public static FileConfiguration get(){
        return playersConf;
    }
 
    public static void save(){
        try{
        	playersConf.save(players);
        }catch (IOException e){
            System.out.println("Couldn't save file");
        }
    }
 
    public static void reload(){
    	playersConf = YamlConfiguration.loadConfiguration(players);
    }
    
	public static ArrayList<String> playtimeSet(Player player, String newRace, String oldRace, int timeSet, boolean change) {
		String playerID = player.getUniqueId().toString();
		String playerName = player.getName().toString().toLowerCase();
		int totalTime = player.getStatistic(Statistic.PLAY_ONE_MINUTE);
		int currentTime; int raceTime; int previousTime;
		
		if (oldRace.equals("")) { oldRace = newRace; }
		else if (newRace.equals("")) { newRace = oldRace; }
		
		list.addAll(get().getStringList("current." + newRace ));
		if (!(list.contains(playerName))) { list.add(playerName); } 
		
		if (change) {
			for (int i = 0; i < list.size(); i++) {
				get().set("current." + oldRace + "." + playerName, null);
				get().set("current." + newRace + "." + playerName, "");
				//Bukkit.getConsoleSender().sendMessage(list.get(i));
			}

		}
		else {
			for (int i = 0; i < list.size(); i++) {
				
				currentTime = get().getInt("current." + oldRace + "." + list.get(i) + ".playtime");
				if (currentTime > 0) { currentTime = currentTime + 6000; }
				else { currentTime = currentTime + totalTime; }
				
				previousTime = get().getInt("current." + oldRace + "." + list.get(i) + ".playtime");
				previousTime = currentTime - previousTime;
				
				raceTime = get().getInt("total." + oldRace + "." + list.get(i) + ".playtime");
				raceTime = raceTime + previousTime;
				
				get().set("current." + oldRace + "." + list.get(i) + ".playtime", currentTime);
				get().set("total.all." + list.get(i) + ".playtime", totalTime);
				get().set("total.all." + list.get(i) + ".previous", currentTime);
				get().set("total.all." + list.get(i) + ".uuid", playerID);
				get().set("total." + oldRace + "." + list.get(i) + ".playtime", raceTime);
			}
		}
			
		save();
		return list;
	}
	
	public static void playtimeOnePlayer(Player player, String targetS, String race) {
		Player target = null;
		if (targetS == null) { targetS = player.getDisplayName(); }
		else { target = Bukkit.getServer().getPlayer(targetS); }
		//String playerID = target.getUniqueId().toString();
		//String playerName = target.getName().toString();
		
		try {
			target = Bukkit.getPlayer(targetS);
		} catch (Exception e) { }
	
		if (target == null) { target = player; }
		
		String raceColor = "&f";
		if (race.equals("dwarf")) { raceColor = "&9"; }
		else if (race.equals("elf")) { raceColor = "&2"; }
		else if (race.equals("goblin")) { raceColor = "&3"; }

		player.sendMessage(ChatColor.translateAlternateColorCodes('&', "-".repeat(17) + "\n" + raceColor + targetS + "&r's playtime:\n" + "-".repeat(17)));
		player.sendMessage(ChatColor.translateAlternateColorCodes('&', raceColor + "Current&r: " + get().getInt("current." + race + "." + targetS + ".playtime") / 20 / 60 / 60 + "H " + get().getInt("current." + race + "." + target + ".playtime") / 20 / 60 % 60 + "M"));
		player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9Dwarf&r: " + get().getInt("total.dwarf." + targetS + ".playtime") / 20 / 60 / 60 + "H " + get().getInt("total.dwarf." + targetS + ".playtime") / 20 / 60 % 60 + "M"));
		player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2Elf&r: " + get().getInt("total.elf." + targetS + ".playtime") / 20 / 60 / 60 + "H " + get().getInt("total.elf." + targetS + ".playtime") / 20 / 60 % 60 + "M"));
		player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3Goblin&r: " + get().getInt("total.goblin." + targetS + ".playtime") / 20 / 60 / 60 + "H " + get().getInt("total.goblin." + targetS + ".playtime") / 20 / 60 % 60 + "M"));
		player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&rHuman&r: " + get().getInt("total.human." + targetS + ".playtime") / 20 / 60 / 60 + "H " + get().getInt("total.human." + targetS + ".playtime") / 20 / 60 % 60 + "M"));
		player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&5Total&r: " + get().getInt("total.all." + targetS + ".playtime") / 20 / 60 / 60 + "H " + get().getInt("total.all." + targetS + ".playtime") / 20 / 60 % 60 + "M\n" + "-".repeat(17)));
	}
	
	public static void playtimeAllPlayers(Player player) {
		int timeDwarf = 0;
		String playersDwarf = "";
		//list.clear();
		list.addAll(get().getStringList("current.dwarf"));
		player.sendMessage(list.size() + "");

		for (int i = 0; i < list.size(); i++) {  
			timeDwarf = timeDwarf + get().getInt("total.dwarves." + list.get(i) + ".playtime"); 
			playersDwarf = playersDwarf + "&9" + get().getString("current.dwarves." + list.get(i).toString()) + "&r, ";
		}
		
		int timeElf = 0;
		String playersElf = "";
		list.clear();
		list.addAll(get().getStringList("current.elf"));
		for (int i = 0; i < list.size(); i++) {  
			timeElf = timeElf + get().getInt("total.elf." + list.get(i) + ".playtime"); 
			playersElf = playersElf + "&2" + get().getString("current.elves." + list.get(i).toString()) + "&r, ";
		}
		
		int timeGoblin = 0;
		String playersGoblin = "";
		list.clear();
		list.addAll(get().getStringList("current.goblin"));
		for (int i = 0; i < list.size(); i++) {  
			timeGoblin = timeGoblin + get().getInt("total.goblin." + list.get(i) + ".playtime"); 
			playersGoblin = playersGoblin + "&3" + get().getString("current.goblin." + list.get(i).toString()) + "&r, ";
		}
		
		int timeHuman = 0;
		String playersHuman = "";
		list.clear();
		list.addAll(get().getStringList("current.human"));
		//	list.add(get().getString("current.humans"));
		for (int i = 0; i < list.size(); i++) {  
			timeHuman = timeHuman + get().getInt("total.human." + list.get(i) + ".playtime"); 
			playersHuman = "&f" + get().getString("current.human." + list.get(i)) + "&r, ";
		}

		list.clear();
		int timeTotal = timeDwarf + timeElf + timeGoblin + timeHuman;
		String playersTotal = playersDwarf + playersElf + playersGoblin + playersHuman;
		
		player.sendMessage(ChatColor.translateAlternateColorCodes('&', "-".repeat(17) + "\n&5Everyone&r's playtime:\n" + "-".repeat(17)));
		player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9Dwarves&r: " + timeDwarf / 20 / 60 / 60 + "H " + timeDwarf / 20 / 60 % 60 + "M\n" + playersDwarf));
		player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2Elves&r: " + timeElf / 20 / 60 / 60 + "H " + timeElf / 20 / 60 % 60 + "M\n" + playersElf));
		player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3Goblins&r: " + timeGoblin / 20 / 60 / 60 + "H " + timeGoblin / 20 / 60 % 60 + "M\n" + playersGoblin));
		player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&fHumans&r: " + timeHuman / 20 / 60 / 60 + "H " + timeHuman / 20 / 60 % 60 + "M\n" + playersHuman));
		player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&5Total&r: " + timeTotal / 20 / 60 / 60 + "H " + timeTotal / 20 / 60 % 60 + "M\n" + playersTotal + "\n" + "-".repeat(17)));
		player.sendMessage(list.size() + "");
	}
}
