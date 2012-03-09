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

package me.xXLupoXx.NoSpawn;

import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Spawns {
	public Map<EntityType, Boolean> SpawnAllowed = new HashMap<EntityType, Boolean>();
	public Map<EntityType, List<Integer>> BlockBlacklist = new HashMap<EntityType, List<Integer>>();
	public Map<EntityType, Integer> MobLimit = new HashMap<EntityType, Integer>();
	public Map<EntityType, Integer> CurrentMobCount = new HashMap<EntityType, Integer>();
    public Map<EntityType, Boolean> UseGlobalBlockBlacklist = new HashMap<EntityType, Boolean>();
    public List<Integer> GlobalBlockBlacklist = new ArrayList<Integer>();

	public int TotalMobLimit = 0;

	ConfigBuffer cb;

	public Spawns(ConfigBuffer cb) {
		this.cb = cb;
	}

	public boolean isSpawnAllowed(EntityType type, Block block) {
		if ((getCurrentTotalMobsCount() >= TotalMobLimit) && TotalMobLimit != 0) {
			// System.out.println(CurrentTotalMobs + " "+ TotalMobLimit);
			return false;
		}

		if ((CurrentMobCount.get(type) >= MobLimit.get(type))
				&& MobLimit.get(type) != 0) {
			return false;
		}

		if (SpawnAllowed.containsKey(type) && SpawnAllowed.get(type)) {

            if(UseGlobalBlockBlacklist.get(type) != null && GlobalBlockBlacklist != null){

                if(UseGlobalBlockBlacklist.get(type) && GlobalBlockBlacklist.contains(block.getTypeId())){
                    return false;
                }
            }
            if (BlockBlacklist.get(type) != null) {

				if (BlockBlacklist.get(type).contains(block.getTypeId())) {
					return false;
				}

			}

			return true;
		} else {
			return false;
		}
	}

	public int getCurrentTotalMobsCount() {
		int count = 0;
		for (int mt : CurrentMobCount.values()) {
			count += mt;
		}
		return count;
	}

}
