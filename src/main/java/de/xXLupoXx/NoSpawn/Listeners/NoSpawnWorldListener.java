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

package de.xXLupoXx.NoSpawn.Listeners;

import de.xXLupoXx.NoSpawn.Util.Spawns;
import de.xXLupoXx.NoSpawn.Util.ConfigBuffer;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;


public class NoSpawnWorldListener implements Listener{

	ConfigBuffer cb;

	public NoSpawnWorldListener(ConfigBuffer cb) {
		this.cb = cb;
	}
    @EventHandler(priority = EventPriority.NORMAL)
	public void onWorldLoad(WorldLoadEvent event) {

		World w = event.getWorld();
		cb.worldSpawns.put(w, new Spawns(cb));
        cb.allowSpawnerSapwn.put(w,false);
        cb.allowEggSpawn.put(w,false);
        cb.denyJockeySpawn.put(w,false);

		if (cb.config.get("worlds") != null) {
			cb.readConfig();

		} else {

			cb.addWorldToConfig(w);

			System.out.println("[NoSpawn] New world \"" + w.getName()
					+ "\" detected");

			cb.readConfig();

		}

	}

}
