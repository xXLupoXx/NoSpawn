package me.xXLupoXx.NoSpawn;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.bukkit.block.Block;

public class Spawns {
	public Map<org.bukkit.entity.CreatureType,Boolean> SpawnAllowed  = new HashMap<org.bukkit.entity.CreatureType,Boolean>();

	public List<Integer> MonterBlockBlacklist = new LinkedList<Integer>();
	public List<Integer> AnimalBlockBlackList = new LinkedList<Integer>();
	public List<org.bukkit.entity.CreatureType> Animals = Arrays.asList(org.bukkit.entity.CreatureType.COW,org.bukkit.entity.CreatureType.PIG,org.bukkit.entity.CreatureType.SHEEP,org.bukkit.entity.CreatureType.WOLF,org.bukkit.entity.CreatureType.SQUID);
	public List<org.bukkit.entity.CreatureType> Monsters = Arrays.asList(org.bukkit.entity.CreatureType.CREEPER,org.bukkit.entity.CreatureType.GHAST,org.bukkit.entity.CreatureType.GIANT,org.bukkit.entity.CreatureType.PIG_ZOMBIE,org.bukkit.entity.CreatureType.SKELETON,org.bukkit.entity.CreatureType.SLIME,org.bukkit.entity.CreatureType.SPIDER,org.bukkit.entity.CreatureType.ZOMBIE);
	
	public boolean isSpawnAllowed(org.bukkit.entity.CreatureType type,Block block)
	{
		if(SpawnAllowed.containsKey(type) && SpawnAllowed.get(type) == true)
		{
			if(Animals.contains(type))
			{
				if(AnimalBlockBlackList != null && AnimalBlockBlackList.contains(block.getTypeId()))
				{
					return false;
				}
			}
			else if(Monsters.contains(type))
			{
				if(MonterBlockBlacklist != null && MonterBlockBlacklist.contains(block.getTypeId()))
				{
					return false;
				}
			}
			return true;
		}
		else
		{
			return false;
		}
	}
}
