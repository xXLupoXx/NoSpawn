package me.xXLupoXx.NoSpawn;

import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.configuration.file.FileConfiguration;

public class NoSpawnWorldListener implements Listener{

	ConfigBuffer cb;
	FileConfiguration config;

	public NoSpawnWorldListener(ConfigBuffer cb) {
		this.cb = cb;
		this.config = cb.config;
	}
    @EventHandler(priority = EventPriority.NORMAL)
	public void onWorldLoad(WorldLoadEvent event) {

		World w = event.getWorld();
		cb.worldSpawns.put(w, new Spawns(cb));

		if (config.get("worlds") != null) {
			cb.readConfig();

		} else {

			cb.addWorldToConfig(w);

			System.out.println("[NoSpawn] New world \"" + w.getName()
					+ "\" detected");

			cb.readConfig();

		}

	}

}
