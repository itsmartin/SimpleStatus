package com.martinbrook.SimpleStatus;

import java.util.Calendar;

import org.bukkit.ChatColor;

public class Status {
	private String text;
	private ChatColor color;
	private Calendar timestamp;
	
	private static final ChatColor defaultColor = ChatColor.YELLOW;
	
	public Status(String text) { this(text, defaultColor); }
	
	public Status(String text, ChatColor color) {
		this.text = text;
		this.color = color;
		this.timestamp = Calendar.getInstance();
	}

	@Override
	public boolean equals(Object arg0) {
		return arg0 instanceof Status && ((Status) arg0).getText().equals(text);
	}

	@Override
	public String toString() { return color + text; }
	
	public String getText() { return text; }
	
	public ChatColor getColor() { return color; }
	
	public long getAge() {
		Calendar now = Calendar.getInstance();
		return (now.getTimeInMillis() - timestamp.getTimeInMillis()) / 1000;
	}
	
	public String getFormattedAge() {
		long s = getAge();
		if (s < 60) return s + " second" + (s != 1 ? "s" : "");
		
		long m = s / 60;
		if (m < 60) return m + " minute" + (m != 1 ? "s" : "");
		
		long h = m / 60;
		if (h < 24) return h + " hour" + (h != 1 ? "s" : "");
		
		long d = h / 24;
		return d + " day" + (d != 1 ? "s" : "");
	}
	
	
}
