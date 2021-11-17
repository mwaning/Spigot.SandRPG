package rpg.sand.file;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Config {
    private static File config;
    private static FileConfiguration configConf;
 
    public static void setup(){
    	config = new File(Bukkit.getServer().getPluginManager().getPlugin("SandRPG").getDataFolder(), "config.yml");
 
        if (!config.exists()){
            try{
            	config.createNewFile();
            }catch (IOException e){
				e.printStackTrace();
            }
        }
        configConf = YamlConfiguration.loadConfiguration(config);
        //configConf = new YamlConfiguration();

    }
 
    public static FileConfiguration get(){
        return configConf;
    }
 
    public static void save(){
        try{
        	configConf.save(config);
        }catch (IOException e){
            System.out.println("Couldn't save file");
        }
    }
 
    public static void reload(){
    	configConf = YamlConfiguration.loadConfiguration(config);
    }
   
    
	public static ArrayList<String> loadMsg(String keyName) {
		ArrayList<String> list = new ArrayList<String>();
		list.addAll(Config.get().getStringList(keyName));
		return list;
	}
}

