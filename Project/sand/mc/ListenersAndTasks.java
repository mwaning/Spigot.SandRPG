package rpg.sand.mc;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ListenersAndTasks implements Listener {
    private Main plugin;
    private Map<Player, Integer> taskLocation = new HashMap<>();
	RaceProperties rp = new RaceProperties();

    public ListenersAndTasks(Main plugin) {
    	this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = (Player) event.getPlayer();
        rp.raceCheck(player, "");
        startLocationCheck(player);
    }
    
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = (Player) event.getPlayer();
        stopLocationCheck(player);
    }
    
	public void startLocationCheck(Player player) {
        stopLocationCheck(player);
        int taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(this.plugin,new Runnable() {
            @Override
            public void run() {
        		rp.raceCheck(player, "");
            }
        }, 100L, 100L);
        taskLocation.put(player, taskID);
    }

    public void stopLocationCheck(Player player) {
        if (!taskLocation.containsKey(player)) {
            return;
        }
        Bukkit.getScheduler().cancelTask(taskLocation.get(player));
        taskLocation.remove(player);
    }
}
