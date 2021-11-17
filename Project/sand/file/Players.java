package rpg.sand.file;

import java.io.File;
import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.configuration.file.FileConfiguration;

public class Players {
    private static File players;
    private static FileConfiguration playersConf;
 
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
    
	public static void raceSet(Player player, String newRace, String oldRace) {
		String playerID = player.getUniqueId().toString();
		String playerName = player.getName().toString();	

		get().set(oldRace + "." + playerName, null);
		get().set(newRace + "." + playerName, playerID);
				
		save();
	}
}
