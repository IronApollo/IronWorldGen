package me.ironapollo;

import org.bukkit.plugin.java.JavaPlugin;

import me.ironapollo.listeners.WorldListener;

public class IronWorldGen extends JavaPlugin{
	
	@Override
	public void onEnable() {
		new WorldListener(this);
	}
}
