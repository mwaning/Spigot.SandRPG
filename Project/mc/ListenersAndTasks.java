package rpg.sand.mc;

import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import rpg.sand.file.Config;
import rpg.sand.file.Players;

public class ListenersAndTasks implements Listener {
    private Main plugin;
    private Map<Player, Integer> taskLocation = new HashMap<>();
    private Map<Player, Integer> taskPlaytime = new HashMap<>();
    int totalSeconds;
	RaceProperties rp = new RaceProperties();

    public ListenersAndTasks(Main plugin) {
    	this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = (Player) event.getPlayer();
        String playerName = player.getName();
        String race = rp.raceCheck(player, "");
        
        if (race.equals("")) {

        }
        
        totalSeconds = serverTime();
        Players.get().set("total.all." + player.getName().toLowerCase() + ".serverTime" , serverTime());
        startLocationCheck(player);
        updatePlaytime(player);
    }
    
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = (Player) event.getPlayer();
        stopLocationCheck(player);
        stopUpdatePlaytime(player, true);
		//Bukkit.getConsoleSender().sendMessage("Server time upon quit: " + serverTime());
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
    
    public int updatePlaytime(Player player) {
    	stopUpdatePlaytime(player, false);
    	int taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(this.plugin, new Runnable() {
    		@Override
    		public void run() { 
    		    String oldRace = rp.raceCheck(player, "");
    		    Players.playtimeSet(player, oldRace, "", 6000, false);
    		}
    	}, 6000L, 6000L);
    	taskPlaytime.put(player, taskID);
    	return totalSeconds;
    }
    
    public void stopUpdatePlaytime(Player player, boolean interrupt) {
    	if (!taskPlaytime.containsKey(player)) {
    		return;
    	}
    	Bukkit.getScheduler().cancelTask(taskPlaytime.get(player));
    	taskPlaytime.remove(player);
    	
    	if (interrupt) {
	        totalSeconds = serverTime() - totalSeconds;
	        if (totalSeconds > 300) { totalSeconds = totalSeconds % 300; }
    	}
    	else {
    		totalSeconds = 300;
    	}
	    String oldRace = rp.raceCheck(player, "");
	    Players.playtimeSet(player, oldRace, "", totalSeconds * 20, false);
    }
    
    public int serverTime() {
    	DateTimeFormatter minutes = DateTimeFormatter.ofPattern("mm");
    	DateTimeFormatter seconds = DateTimeFormatter.ofPattern("ss");
    	DateTimeFormatter hours = DateTimeFormatter.ofPattern("hh");
    	LocalDateTime now = LocalDateTime.now();
    	int totalSeconds = Integer.parseInt(hours.format(now)) * 60 * 60 + Integer.parseInt(minutes.format(now)) * 60 + Integer.parseInt(seconds.format(now));
    	return totalSeconds;
    }
}
