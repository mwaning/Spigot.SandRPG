package rpg.sand.mc;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import rpg.sand.file.Config;
import rpg.sand.file.Players;

public class Main extends JavaPlugin {
	Plugin plugin = this;
	
	@Override
	public void onEnable() {	
		new ListenersAndTasks(this);

		this.getCommand("rpg-help").setExecutor(new Command_rpghelp());
		this.getCommand("rpg-race").setExecutor(new Command_rpgrace());
		this.getCommand("rpg-playtime").setExecutor(new Command_rpgplaytime());

        saveDefaultConfig();

        Config.setup();
       // Config.get().options().copyDefaults(true);
        
        Players.setup();
	}

	@Override
	public void onDisable() { }
	
	public void startLocationCheck(Player player) {
        int taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
               // Bukkit.getConsoleSender().sendMessage("started task for " + player);
            }
        }, 100L, 100L);
    }
	



}
