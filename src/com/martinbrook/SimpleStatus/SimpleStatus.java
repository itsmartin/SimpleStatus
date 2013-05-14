package com.martinbrook.SimpleStatus;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;


public class SimpleStatus extends JavaPlugin  {

	private HashMap<String, Status> statuses = new HashMap<String, Status>();
	
    @Override
    public void onEnable(){
    	getServer().getPluginManager().registerEvents(new SimpleStatusListener(this), this);
		
    }
 
    
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
    	String c = cmd.getName().toLowerCase();
    	String response = null;

		if (sender instanceof Player && (c.equals("status") || c.equals("st"))) {
			response = cStatus((Player) sender, args);
		} else if (sender instanceof Player && (c.equals("clearstatus") || c.equals("cs") || c.equals("back"))) {
			response = cClearstatus((Player) sender);
		} else if (sender instanceof Player && c.equals("afk")) {
			response = cAfk((Player) sender);
		} else if (sender instanceof Player && (c.equals("recording") || c.equals("rec"))) {
			response = cRecording((Player) sender);
		}
		
		if (response != null)
			sender.sendMessage(response);
		
		return true;
    }


	private String cStatus(Player sender, String[] args) {
		if (args.length == 0) {
			return cStatus(sender);
		} else {
			String status = "";
			for(String arg : args) {
				status += arg + " ";
			}
			status = status.substring(0, status.length()-1);
			updateStatus(sender, status);
			return null;
		}
	}
	
	private String cStatus(Player sender) {
		Status status = getStatus(sender);
		String response;
		if (status == null) response = ChatColor.YELLOW + "Your status is not set.";
		else response = ChatColor.YELLOW + "Your status is " + status + ChatColor.RESET + ChatColor.YELLOW + " (since " 
				+ status.getFormattedAge() + " ago).";;
		return response + "\n" 
				+ ChatColor.AQUA + "To change your status, type " + ChatColor.BOLD + "/status [your new status]\n"
				+ ChatColor.RESET + ChatColor.AQUA + "To clear your status, type " + ChatColor.BOLD + "/clear";
				
		
	}


	private String cClearstatus(Player sender) {
		clearStatus(sender);
		return null;
	}


	private String cAfk(Player sender) {
		updateStatus(sender, "AFK", ChatColor.LIGHT_PURPLE);
		return null;
	}

	private String cRecording(Player sender) {
		updateStatus(sender, "recording", ChatColor.RED);
		return null;
	}

	public void updateStatus(Player p, String status) { updateStatus(p, new Status(status)); }
	public void updateStatus(Player p, String status, ChatColor color) { updateStatus(p, new Status(status, color)); }
		
	public void updateStatus(Player p, Status status) {
		statuses.put(p.getName().toLowerCase(),status);
		getServer().broadcastMessage(getStatusMessage(p, " is now "));
	}
	
	public void clearStatus(Player p) {
		getServer().broadcastMessage(getStatusMessage(p, " is no longer "));
		statuses.remove(p.getName().toLowerCase());
	}
	
	public Status getStatus(Player p) {
		return statuses.get(p.getName().toLowerCase());
	}
	
	public boolean hasStatus(Player p) {
		return statuses.containsKey(p.getName().toLowerCase());
	}
	
	public String getStatusMessage(Player p) { return getStatusMessage(p, " is "); }
	public String getStatusMessage(Player p, String joiner) {
		return ChatColor.GRAY + p.getDisplayName() + ChatColor.RESET + ChatColor.GRAY + joiner
				+ ChatColor.RESET + getStatus(p);
	}


	protected String getStatusReport(Player player) {
		// Generate status list for all online players
		String response = "";
		for (Player p : getServer().getOnlinePlayers()) {
			if (hasStatus(p) && !p.getName().equalsIgnoreCase(player.getName())) response += getStatusMessage(p) + "\n";
		}
		return response;
	}


	protected void broadcastOthers(Player player, String message) {
		// Broadcast a message to all players except the specified one
		for (Player p : getServer().getOnlinePlayers()) {
			if (!p.getName().equalsIgnoreCase(player.getName())) p.sendMessage(message);
		}
		
	}

}