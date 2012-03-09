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

package me.xXLupoXx.NoSpawn.Util;

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
					int tmpCountPig = 0;
					int tmpCountCow = 0;
					int tmpCountSheep = 0;
					int tmpCountChicken = 0;
					int tmpCountWolf = 0;
					int tmpCountZombie = 0;
					int tmpCountSkeleton = 0;
					int tmpCountPigZombie = 0;
					int tmpCountSpider = 0;
					int tmpCountGiant = 0;
					int tmpCountCreeper = 0;
					int tmpCountGhast = 0;
					int tmpCountSlime = 0;
					int tmpCountSquid = 0;
                    int tmpCountMooshroom = 0;
                    int tmpCountVillager = 0;
                    int tmpCountEnderman = 0;
                    int tmpCountBlaze = 0;
                    int tmpCountCaveSpider = 0;
                    int tmpCountSilverfish = 0;
                    int tmpCountEnderdragon = 0;
                    int tmpCoutMagmaCube = 0;

					for (LivingEntity e : w.getLivingEntities()) {
						if (e instanceof Pig) {

							tmpCountPig++;

						} else if (e instanceof Cow) {

							tmpCountCow++;

						} else if (e instanceof Chicken) {

							tmpCountChicken++;

						} else if (e instanceof Wolf) {

							tmpCountWolf++;

						} else if (e instanceof Squid) {

							tmpCountSquid++;

						} else if (e instanceof Creeper) {

							tmpCountCreeper++;

						} else if (e instanceof Spider) {

							tmpCountSpider++;

						} else if (e instanceof Giant) {

							tmpCountGiant++;

						} else if (e instanceof Ghast) {

							tmpCountGhast++;

						} else if (e instanceof Slime) {

							tmpCountSlime++;

						} else if (e instanceof PigZombie) {

							tmpCountPigZombie++;

						} else if (e instanceof Zombie) {

							tmpCountZombie++;

						} else if (e instanceof Skeleton) {

							tmpCountSkeleton++;

						} else if (e instanceof Sheep) {

							tmpCountSheep++;

						} else if (e instanceof Villager) {

							tmpCountVillager++;

						} else if (e instanceof Enderman) {

							tmpCountEnderman++;

						} else if (e instanceof Blaze) {

							tmpCountBlaze++;

						} else if (e instanceof CaveSpider) {

							tmpCountCaveSpider++;

						} else if (e instanceof Silverfish) {

							tmpCountSilverfish++;

						} else if (e instanceof EnderDragon) {

							tmpCountEnderdragon++;

						} else if (e instanceof MushroomCow) {

							tmpCountMooshroom++;

						} else if (e instanceof MagmaCube) {

                            tmpCoutMagmaCube++;
                        }

					}

					cb.worldSpawns.get(w).CurrentMobCount.put(EntityType.SHEEP, tmpCountSheep);
					cb.worldSpawns.get(w).CurrentMobCount.put(EntityType.COW, tmpCountCow);
					cb.worldSpawns.get(w).CurrentMobCount.put(EntityType.CHICKEN, tmpCountChicken);
					cb.worldSpawns.get(w).CurrentMobCount.put(EntityType.WOLF, tmpCountWolf);
					cb.worldSpawns.get(w).CurrentMobCount.put(EntityType.ZOMBIE, tmpCountZombie);
					cb.worldSpawns.get(w).CurrentMobCount.put(EntityType.PIG_ZOMBIE, tmpCountPigZombie);
					cb.worldSpawns.get(w).CurrentMobCount.put(EntityType.PIG, tmpCountPig);
					cb.worldSpawns.get(w).CurrentMobCount.put(EntityType.GHAST, tmpCountGhast);
					cb.worldSpawns.get(w).CurrentMobCount.put(EntityType.SLIME, tmpCountSlime);
					cb.worldSpawns.get(w).CurrentMobCount.put(EntityType.GIANT, tmpCountGiant);
					cb.worldSpawns.get(w).CurrentMobCount.put(EntityType.CREEPER, tmpCountCreeper);
					cb.worldSpawns.get(w).CurrentMobCount.put(EntityType.SKELETON, tmpCountSkeleton);
					cb.worldSpawns.get(w).CurrentMobCount.put(EntityType.SQUID, tmpCountSquid);
					cb.worldSpawns.get(w).CurrentMobCount.put(EntityType.SPIDER, tmpCountSpider);
                    cb.worldSpawns.get(w).CurrentMobCount.put(EntityType.VILLAGER, tmpCountVillager);
                    cb.worldSpawns.get(w).CurrentMobCount.put(EntityType.CAVE_SPIDER, tmpCountCaveSpider);
                    cb.worldSpawns.get(w).CurrentMobCount.put(EntityType.MUSHROOM_COW, tmpCountMooshroom);
                    cb.worldSpawns.get(w).CurrentMobCount.put(EntityType.ENDER_DRAGON, tmpCountEnderdragon);
                    cb.worldSpawns.get(w).CurrentMobCount.put(EntityType.ENDERMAN, tmpCountEnderman);
                    cb.worldSpawns.get(w).CurrentMobCount.put(EntityType.SILVERFISH, tmpCountSilverfish);
                    cb.worldSpawns.get(w).CurrentMobCount.put(EntityType.BLAZE, tmpCountBlaze);
                    cb.worldSpawns.get(w).CurrentMobCount.put(EntityType.MAGMA_CUBE,tmpCoutMagmaCube);

				}
			}
		}, 0, ((cb.CountTimer / 1000) * 20));
	}
}
