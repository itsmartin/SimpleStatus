package com.martinbrook.SimpleStatus;

import java.util.Calendar;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;


public class SimpleStatusListener implements Listener {
	private SimpleStatus plugin;
	protected static final long STATUS_FORGET_TIME = 600; // Forget statuses after 600 seconds
	
	public SimpleStatusListener(SimpleStatus plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		p.sendMessage(plugin.getStatusReport(p));
		
		if (plugin.hasStatus(p)) {
			// Check how long since this player was last seen
			Calendar lastSeenTime = plugin.getLastSeenTime(p);
			if (lastSeenTime != null
					&& ((Calendar.getInstance().getTimeInMillis() - lastSeenTime.getTimeInMillis()) / 1000) > STATUS_FORGET_TIME) {

				// cancel status				
				p.sendMessage(ChatColor.AQUA + "[SimpleStatus] Your previous status (" + plugin.getStatus(p) +
						ChatColor.RESET + ChatColor.AQUA + ") has been cleared.");
				plugin.clearStatus(p, true);
			} else {
				// display status
				p.sendMessage(ChatColor.AQUA + "[SimpleStatus] Your current status is: " + plugin.getStatus(p));
				plugin.broadcastOthers(p, plugin.getStatusMessage(p));
			}
		}
		p.sendMessage(ChatColor.GRAY + "To update your status, use " + ChatColor.BOLD + "/status");
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		plugin.setLastSeenTime(p);
	}

	
}
