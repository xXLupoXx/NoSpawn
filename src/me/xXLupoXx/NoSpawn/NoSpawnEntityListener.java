package me.xXLupoXx.NoSpawn;

import org.bukkit.block.BlockFace;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.configuration.file.FileConfiguration;

public class NoSpawnEntityListener extends EntityListener {
	ConfigBuffer cb;
	FileConfiguration config;
	int tmp = 0;

	public NoSpawnEntityListener(ConfigBuffer cb) {
		this.cb = cb;
		this.config = cb.config;
	}

	public void onCreatureSpawn(CreatureSpawnEvent event) {
		if (event.isCancelled()) {
			return;
		}

		if (config == null || cb == null) {
			return;
		}

		if (cb.plugin.isEnabled()) {
			if (event.getSpawnReason() != SpawnReason.CUSTOM) {
				if (config.get("worlds") != null) {
					if (cb.worldSpawns
							.containsKey(event.getEntity().getWorld())) {
						tmp = cb.worldSpawns.get(event.getEntity().getWorld()).CurrentMobCount
								.get(event.getCreatureType());
						tmp++;
						if (!cb.worldSpawns.get(event.getEntity().getWorld()).isSpawnAllowed(event.getCreatureType(), event.getLocation().getBlock().getRelative(BlockFace.DOWN))) {
							event.setCancelled(true);
							tmp--;
						}
						cb.worldSpawns.get(event.getEntity().getWorld()).CurrentMobCount
								.put(event.getCreatureType(), tmp);

					} else {
						event.setCancelled(true);
					}
				}
			}
		}
	}
}