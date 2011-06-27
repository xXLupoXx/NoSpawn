package me.xXLupoXx.NoSpawn;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.block.Block;

public class Spawns {
	public Map<org.bukkit.entity.CreatureType, Boolean> SpawnAllowed = new HashMap<org.bukkit.entity.CreatureType, Boolean>();
	public Map<org.bukkit.entity.CreatureType, List<Integer>> BlockBlacklist = new HashMap<org.bukkit.entity.CreatureType, List<Integer>>();
	public Map<org.bukkit.entity.CreatureType, Integer> MobLimit = new HashMap<org.bukkit.entity.CreatureType, Integer>();
	public Map<org.bukkit.entity.CreatureType, Integer> CurrentMobCount = new HashMap<org.bukkit.entity.CreatureType, Integer>();

	public int TotalMobLimit = 0;

	ConfigBuffer cb;

	public Spawns(ConfigBuffer cb) {
		this.cb = cb;
	}

	public boolean isSpawnAllowed(org.bukkit.entity.CreatureType type, Block block, Location spawnLoc) {
		if ((getCurrentTotalMobsCount() >= TotalMobLimit) && TotalMobLimit != 0) {
			// System.out.println(CurrentTotalMobs + " "+ TotalMobLimit);
			return false;

		}

		if ((CurrentMobCount.get(type) >= MobLimit.get(type))
				&& MobLimit.get(type) != 0) {
			return false;
		}

		if (SpawnAllowed.containsKey(type) && SpawnAllowed.get(type) == true) {
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
