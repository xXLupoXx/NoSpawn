package me.xXLupoXx.NoSpawn;

import org.bukkit.block.Block;
import org.bukkit.entity.CreatureType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	public boolean isSpawnAllowed(CreatureType type, Block block) {
		if ((getCurrentTotalMobsCount() >= TotalMobLimit) && TotalMobLimit != 0) {
			// System.out.println(CurrentTotalMobs + " "+ TotalMobLimit);
			return false;

		}

		if ((CurrentMobCount.get(type) >= MobLimit.get(type))
				&& MobLimit.get(type) != 0) {
			return false;
		}

		if (SpawnAllowed.containsKey(type) && SpawnAllowed.get(type)) {
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
