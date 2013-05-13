package com.martinbrook.SimpleStatus;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;


public class SimpleStatusListener implements Listener {
	private SimpleStatus plugin;
	
	public SimpleStatusListener(SimpleStatus plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		p.sendMessage(plugin.getStatusReport(p));
		if (plugin.hasStatus(p)) {
			p.sendMessage(ChatColor.AQUA + "Your current status is: " + ChatColor.BOLD + plugin.getStatus(p));
			plugin.getServer().broadcastMessage(plugin.getStatusMessage(p));
		}
		p.sendMessage(ChatColor.GRAY + "[SimpleStatus] To update your status, use " + ChatColor.BOLD + "/status");
	}

	
}
