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
			p.sendMessage(ChatColor.AQUA + "[SimpleStatus] Your current status is: " + plugin.getStatus(p));
			plugin.broadcastOthers(p, plugin.getStatusMessage(p));
		}
		p.sendMessage(ChatColor.GRAY + "To update your status, use " + ChatColor.BOLD + "/status");
	}

	
}
