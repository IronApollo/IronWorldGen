package me.ironapollo.listeners;

import java.lang.reflect.Field;

import org.bukkit.craftbukkit.v1_14_R1.CraftWorld;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.plugin.Plugin;

import me.ironapollo.IronWorldGen;
import me.ironapollo.implementations.v1_14_R1.IronChunkGenerator;
import net.minecraft.server.v1_14_R1.ChunkGenerator;
import net.minecraft.server.v1_14_R1.PlayerChunkMap;

public class WorldListener implements Listener{
	
	private final IronWorldGen plugin;
	
	public WorldListener(Plugin plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		this.plugin = (IronWorldGen) plugin;
	}
	
	@EventHandler
	public void onWorldLoad(WorldLoadEvent e) {
		if(e.getWorld().getName().equalsIgnoreCase("irontest")) {
			CraftWorld world = ((CraftWorld)e.getWorld());
			try {
				final PlayerChunkMap chunkMap = world.getHandle().getChunkProvider().playerChunkMap;
				final Field chunkGenField = PlayerChunkMap.class.getDeclaredField("chunkGenerator");
				chunkGenField.setAccessible(true);
				final Object chunkGenObject = chunkGenField.get(chunkMap);
				final ChunkGenerator<?> chunkGen = (ChunkGenerator<?>) chunkGenObject;
				chunkGenField.set(chunkMap, new IronChunkGenerator(chunkGen));
			} catch (NoSuchFieldException | IllegalAccessException e1) {e1.printStackTrace();}
		}
	}
}
