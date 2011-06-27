package me.xXLupoXx.NoSpawn;

import org.bukkit.block.BlockFace;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.util.config.Configuration;

public class NoSpawnEntityListener extends EntityListener {
	ConfigBuffer cb;
	Configuration config;
	int tmp = 0;

	public NoSpawnEntityListener(ConfigBuffer cb) {
		this.cb = cb;
		this.config = cb.config;
	}

	public void onCreatureSpawn(CreatureSpawnEvent event) {
		if (event.isCancelled() == true) {
			return;
		}

		if (config.equals(null) || cb.equals(null)) {
			return;
		}

		if (cb.plugin.isEnabled()) {
			if (event.getSpawnReason() != SpawnReason.CUSTOM) {
				if (config.getKeys("worlds") != null
						&& config.getKeys("worlds").contains(
								event.getEntity().getWorld().getName())) {
					if (cb.worldSpawns
							.containsKey(event.getEntity().getWorld())) {
						tmp = cb.worldSpawns.get(event.getEntity().getWorld()).CurrentMobCount
								.get(event.getCreatureType());
						tmp++;
						if (cb.worldSpawns.get(event.getEntity().getWorld())
								.isSpawnAllowed(
										event.getCreatureType(),
										event.getLocation().getBlock()
												.getFace(BlockFace.DOWN),
										event.getLocation()) == false) {
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