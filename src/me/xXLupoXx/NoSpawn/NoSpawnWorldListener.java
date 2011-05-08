package me.xXLupoXx.NoSpawn;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.World;
import org.bukkit.entity.CreatureType;
import org.bukkit.event.world.WorldListener;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.util.config.Configuration;

public class NoSpawnWorldListener extends WorldListener {

	ConfigBuffer cb;
	Configuration config;
	
	public NoSpawnWorldListener(ConfigBuffer cb){
		this.cb = cb;
		this.config = cb.plugin.getConfiguration();
	}
	
	public void onWorldLoad(WorldLoadEvent event){
		  
		  World w = event.getWorld();
		  cb.worldSpawns.put(w, new Spawns());
		  
			if(config.getKeys("worlds") != null && config.getKeys("worlds").contains(w.getName()))
			{
				this.cb.worldSpawns.get(w).SpawnAllowed.put(CreatureType.PIG, config.getBoolean("worlds."+ w.getName() +".spawn.Pig", true));
				this.cb.worldSpawns.get(w).SpawnAllowed.put(CreatureType.SHEEP, config.getBoolean("worlds."+ w.getName() +".spawn.Sheep", true));
				this.cb.worldSpawns.get(w).SpawnAllowed.put(CreatureType.COW, config.getBoolean("worlds."+ w.getName() +".spawn.Cow", true));
				this.cb.worldSpawns.get(w).SpawnAllowed.put(CreatureType.CHICKEN, config.getBoolean("worlds."+ w.getName() +".spawn.Chicken", true));
				this.cb.worldSpawns.get(w).SpawnAllowed.put(CreatureType.PIG_ZOMBIE, config.getBoolean("worlds."+ w.getName() +".spawn.Zombie_Pigman", true));
				this.cb.worldSpawns.get(w).SpawnAllowed.put(CreatureType.SQUID, config.getBoolean("worlds."+ w.getName() +".spawn.Squid", true));
				this.cb.worldSpawns.get(w).SpawnAllowed.put(CreatureType.WOLF, config.getBoolean("worlds."+ w.getName() +".spawn.Wolf", true));
				this.cb.worldSpawns.get(w).SpawnAllowed.put(CreatureType.ZOMBIE, config.getBoolean("worlds."+ w.getName() +".spawn.Zombie", true));
				this.cb.worldSpawns.get(w).SpawnAllowed.put(CreatureType.SKELETON, config.getBoolean("worlds."+ w.getName() +".spawn.Skeleton", true));
				this.cb.worldSpawns.get(w).SpawnAllowed.put(CreatureType.SPIDER, config.getBoolean("worlds."+ w.getName() +".spawn.Spieder", true));
				this.cb.worldSpawns.get(w).SpawnAllowed.put(CreatureType.CREEPER, config.getBoolean("worlds."+ w.getName() +".spawn.Creeper", true));
				this.cb.worldSpawns.get(w).SpawnAllowed.put(CreatureType.SLIME, config.getBoolean("worlds."+ w.getName() +".spawn.Slime", true));
				this.cb.worldSpawns.get(w).SpawnAllowed.put(CreatureType.GHAST, config.getBoolean("worlds."+ w.getName() +".spawn.Ghast", true));
				this.cb.worldSpawns.get(w).SpawnAllowed.put(CreatureType.SPIDER, config.getBoolean("worlds."+ w.getName() +".spawn.Spider", true));
				this.cb.worldSpawns.get(w).SpawnAllowed.put(CreatureType.GIANT, config.getBoolean("worlds."+ w.getName() +".spawn.Giant", true));
				
				this.cb.worldSpawns.get(w).AnimalBlockBlackList = getBlacklist(config.getString("worlds."+ w.getName() +".BlockBlacklist.Animal", ""));
				this.cb.worldSpawns.get(w).MonterBlockBlacklist = getBlacklist(config.getString("worlds."+ w.getName() +".BlockBlacklist.Monster", ""));
				
			}
			else
			{
				config.setProperty("worlds."+w.getName()+".spawn.Pig", Boolean.valueOf(true));
	    		config.setProperty("worlds."+w.getName()+".spawn.Sheep", Boolean.valueOf(true));
	    		config.setProperty("worlds."+w.getName()+".spawn.Cow", Boolean.valueOf(true));
	    		config.setProperty("worlds."+w.getName()+".spawn.Chicken", Boolean.valueOf(true));
	    		config.setProperty("worlds."+w.getName()+".spawn.Zombie_Pigman", Boolean.valueOf(true));
	    		config.setProperty("worlds."+w.getName()+".spawn.Squid", Boolean.valueOf(true));
	    		config.setProperty("worlds."+w.getName()+".spawn.Wolf", Boolean.valueOf(true));
	    		config.setProperty("worlds."+w.getName()+".spawn.Zombie", Boolean.valueOf(true));
	    		config.setProperty("worlds."+w.getName()+".spawn.Skeleton", Boolean.valueOf(true));
	    		config.setProperty("worlds."+w.getName()+".spawn.Spider", Boolean.valueOf(true));
	    		config.setProperty("worlds."+w.getName()+".spawn.Creeper", Boolean.valueOf(true));
	    		config.setProperty("worlds."+w.getName()+".spawn.Slime", Boolean.valueOf(true));
	    		config.setProperty("worlds."+w.getName()+".spawn.Ghast", Boolean.valueOf(true));
	    		config.setProperty("worlds."+w.getName()+".spawn.Spider", Boolean.valueOf(true));
	    		config.setProperty("worlds."+w.getName()+".spawn.Giant", Boolean.valueOf(true));
	    		config.setProperty("worlds."+w.getName()+".BlockBlacklist.Animal", "");
	    		config.setProperty("worlds."+w.getName()+".BlockBlacklist.Monster", "");
	    		config.save();
	    		
	    		System.out.println("[NoSpawn] New world \""+w.getName()+"\" detected");
	    		this.cb.worldSpawns.get(w).SpawnAllowed.put(CreatureType.PIG, true);
				this.cb.worldSpawns.get(w).SpawnAllowed.put(CreatureType.SHEEP, true);
				this.cb.worldSpawns.get(w).SpawnAllowed.put(CreatureType.COW, true);
				this.cb.worldSpawns.get(w).SpawnAllowed.put(CreatureType.CHICKEN, true);
				this.cb.worldSpawns.get(w).SpawnAllowed.put(CreatureType.PIG_ZOMBIE, true);
				this.cb.worldSpawns.get(w).SpawnAllowed.put(CreatureType.SQUID, true);
				this.cb.worldSpawns.get(w).SpawnAllowed.put(CreatureType.WOLF, true);
				this.cb.worldSpawns.get(w).SpawnAllowed.put(CreatureType.ZOMBIE, true);
				this.cb.worldSpawns.get(w).SpawnAllowed.put(CreatureType.SKELETON, true);
				this.cb.worldSpawns.get(w).SpawnAllowed.put(CreatureType.SPIDER, true);
				this.cb.worldSpawns.get(w).SpawnAllowed.put(CreatureType.CREEPER, true);
				this.cb.worldSpawns.get(w).SpawnAllowed.put(CreatureType.SLIME, true);
				this.cb.worldSpawns.get(w).SpawnAllowed.put(CreatureType.GHAST, true);
				this.cb.worldSpawns.get(w).SpawnAllowed.put(CreatureType.GIANT, true);

				this.cb.worldSpawns.get(w).AnimalBlockBlackList = getBlacklist(config.getString("worlds."+ w.getName() +".BlockBlacklist.Animal", ""));
				this.cb.worldSpawns.get(w).MonterBlockBlacklist = getBlacklist(config.getString("worlds."+ w.getName() +".BlockBlacklist.Monster", ""));
			}

	  }
	
	  private List<Integer> getBlacklist(String args)
	  {
	    if (!args.isEmpty()) {
	      String[] arr = args.split(";");
	      List<Integer> tmplist = new LinkedList<Integer>();

	      for (int i = 0; i < arr.length; i++) {
	        tmplist.add(Integer.valueOf(Integer.parseInt(arr[i].trim())));
	      }

	      return tmplist;
	    }

	    return null;
	  }
	
	
}
