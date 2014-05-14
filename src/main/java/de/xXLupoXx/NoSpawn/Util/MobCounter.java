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

package de.xXLupoXx.NoSpawn.Util;


import de.Keyle.MyPet.api.entity.MyPetEntity;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.*;


public class MobCounter {

	Server server;
	ConfigBuffer cb;

	public MobCounter(final Server server, final ConfigBuffer cb) {

		this.server = server;
		this.cb = cb;

		runSchedueler();
	}

	public void runSchedueler() {

		server.getScheduler().scheduleSyncRepeatingTask(cb.plugin, new Runnable() {
			public void run() {

				for (World w : server.getWorlds()) {

                    for(EntityType e:ConfigBuffer.MobMap.values())
                    {
                        cb.worldSpawns.get(w).CurrentMobCount.put(e,0);
                    }
					for (LivingEntity e : w.getLivingEntities()) {

                        if((!(e instanceof Player))) {
                            if(ConfigBuffer.hasMyPet && (!(e instanceof MyPetEntity))) {
                                cb.worldSpawns.get(w).CurrentMobCount.put(e.getType(),(cb.worldSpawns.get(w).CurrentMobCount.get(e.getType())+1));
                            }
                        }
				    }
                }
			}
		}, 0, ((cb.CountTimer / 1000) * 20));
	}
}
