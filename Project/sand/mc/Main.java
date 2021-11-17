package rpg.sand.mc;

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

        saveDefaultConfig();

        Config.setup();        
        Players.setup();
	}

	@Override
	public void onDisable() { }
}
