package me.xXLupoXx.NoSpawn;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.CreatureType;
import org.bukkit.util.config.Configuration;

import com.nijiko.permissions.PermissionHandler;

public class ConfigBuffer
{
	public Map<org.bukkit.World , Spawns> worldSpawns = new HashMap<org.bukkit.World , Spawns>();
	public Map<Location,CreatureType> AdminSpawns = new HashMap<Location,CreatureType>();
	public Map<String,CreatureType> MobMap  = new HashMap<String,CreatureType>();
	public PermissionHandler Permissions;
	public NoSpawn plugin;
	public int CountTimer;
	public Configuration config;
	
	public ConfigBuffer(NoSpawn plugin){
	MobMap.put("Sheep", CreatureType.SHEEP);
	MobMap.put("Pig", CreatureType.PIG);
	MobMap.put("Cow", CreatureType.COW);
	MobMap.put("Chicken", CreatureType.CHICKEN);
	MobMap.put("Squid", CreatureType.SQUID);
	MobMap.put("Zombie_Pigman", CreatureType.PIG_ZOMBIE);
	MobMap.put("Wolf", CreatureType.WOLF);
	MobMap.put("Zombie", CreatureType.ZOMBIE);
	MobMap.put("Skeleton", CreatureType.SKELETON);
	MobMap.put("Spider", CreatureType.SPIDER);
	MobMap.put("Creeper", CreatureType.CREEPER);
	MobMap.put("Slime", CreatureType.SLIME);
	MobMap.put("Ghast", CreatureType.GHAST);
	MobMap.put("Giant", CreatureType.GIANT);
	config = plugin.getConfiguration();
	this.plugin = plugin;
	}
	
	public void setupConfig(){
		
		
		
		for(World w : plugin.getServer().getWorlds())
    	{
			String Buffer;
			Iterator<String> Mobs = MobMap.keySet().iterator();
			
			while(Mobs.hasNext()){
				Buffer = Mobs.next();
				config.setProperty("worlds."+w.getName()+".creature."+Buffer+".spawn", Boolean.valueOf(true));
				config.setProperty("worlds."+w.getName()+".creature."+Buffer+".BlockBlacklist", "");
				config.setProperty("worlds."+w.getName()+".creature."+Buffer+".Limit", 0);
				config.setProperty("worlds."+w.getName()+".properties.TotalMobLimit",0);
				config.setProperty("properties.RefrechTimer",20000);
				
			}
    	}
    	config.save();
	}
	
	public void addWorldToConfig(World w){
		String Buffer;
		
		Iterator<String> Mobs = MobMap.keySet().iterator();
		while(Mobs.hasNext()){
			Buffer = Mobs.next();
			config.setProperty("worlds."+w.getName()+".creature."+Buffer+".spawn", Boolean.valueOf(true));
			config.setProperty("worlds."+w.getName()+".creature."+Buffer+".BlockBlacklist", "");
			config.setProperty("worlds."+w.getName()+".creature."+Buffer+".Limit", 0);
			config.setProperty("worlds."+w.getName()+".properties.TotalMobLimit",0);
			config.setProperty("properties.RefrechTimer",20000);
			
		}
		config.save();
	}
	
	public void readConfig(){
		
		String Buffer;
		
	  	for(World w : plugin.getServer().getWorlds())
		{
	  		Iterator<String> Mobs = MobMap.keySet().iterator();
	  		
			if(config.getKeys("worlds") != null && config.getKeys("worlds").contains(w.getName()))
			{
				while(Mobs.hasNext()){
					Buffer = Mobs.next();
					this.worldSpawns.get(w).SpawnAllowed.put(MobMap.get(Buffer), config.getBoolean("worlds."+ w.getName() +".creature."+Buffer+".spawn", true));
					this.worldSpawns.get(w).BlockBlacklist.put(MobMap.get(Buffer), getBlacklist(config.getString("worlds."+w.getName()+".creature."+Buffer+".BlockBlacklist", "")));
					
					if(config.getProperty("worlds."+w.getName()+".creature."+Buffer+".Limit")!= null){
						
					this.worldSpawns.get(w).MobLimit.put(MobMap.get(Buffer),config.getInt("worlds."+w.getName()+".creature."+Buffer+".Limit", 0));
					
					} else {
						
						config.setProperty("worlds."+w.getName()+".creature."+Buffer+".Limit", 0);
						config.save();
						this.worldSpawns.get(w).MobLimit.put(MobMap.get(Buffer),config.getInt("worlds."+w.getName()+".creature."+Buffer+".Limit", 0));
					}

				}
				
				if(config.getProperty("worlds."+w.getName()+".properties.TotalMobLimit")!= null){
					
					this.worldSpawns.get(w).TotalMobLimit = config.getInt("worlds."+w.getName()+".properties.TotalMobLimit", 0);
					
					} else {
						
						config.setProperty("worlds."+w.getName()+".properties.TotalMobLimit",0);
						config.save();
						this.worldSpawns.get(w).TotalMobLimit = config.getInt("worlds."+w.getName()+".properties.TotalMobLimit", 0);
						//System.out.println("HHHHHHAAAAAAAAAAAAAAAAAAAAAALO");
						
					}
					if(config.getProperty("properties.RefrechTimer")!= null){
					
					this.CountTimer = config.getInt("properties.RefrechTimer", 20000);
					
					} else {
						
						config.setProperty("properties.RefrechTimer", 20000);
						config.save();
						this.CountTimer = config.getInt("properties.RefrechTimer", 20000);
					}
			}
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