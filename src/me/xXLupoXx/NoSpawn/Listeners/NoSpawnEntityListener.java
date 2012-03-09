/*
 * Copyright (c) <2012> <xXLupoXx>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package me.xXLupoXx.NoSpawn.Listeners;

import me.xXLupoXx.NoSpawn.Util.ConfigBuffer;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;


public class NoSpawnEntityListener implements Listener {
	ConfigBuffer cb;
	int tmp = 0;

	public NoSpawnEntityListener(ConfigBuffer cb) {
		this.cb = cb;
	}
    @EventHandler(priority = EventPriority.NORMAL)
    public void onCreatureSpawn(CreatureSpawnEvent event) {
		if (event.isCancelled()) {
			return;
		}
		if (cb.config == null) {
			return;
		}

        if(!ConfigBuffer.MobMap.containsValue(event.getEntityType()) )
        {
            return;
        }


		if (cb.plugin.isEnabled()) {
			if (event.getSpawnReason() != SpawnReason.CUSTOM) {
				if (cb.config.get("worlds") != null) {
					if (cb.worldSpawns.containsKey(event.getEntity().getWorld())) {

						tmp = cb.worldSpawns.get(event.getEntity().getWorld()).CurrentMobCount.get(event.getEntityType());
						tmp++;

						if (!cb.worldSpawns.get(event.getEntity().getWorld()).isSpawnAllowed(event.getEntityType(), event.getLocation().getBlock().getRelative(BlockFace.DOWN))) {

							event.setCancelled(true);
							tmp--;
						}

						cb.worldSpawns.get(event.getEntity().getWorld()).CurrentMobCount.put(event.getEntityType(), tmp);

					} else {
						event.setCancelled(true);
					}
				}
			}
		}
	}
}