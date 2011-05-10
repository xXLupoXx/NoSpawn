package me.xXLupoXx.NoSpawn;

import org.bukkit.World;
import org.bukkit.event.world.WorldListener;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.util.config.Configuration;

public class NoSpawnWorldListener extends WorldListener {

	ConfigBuffer cb;
	Configuration config;
	
	public NoSpawnWorldListener(ConfigBuffer cb){
		this.cb = cb;
		this.config = cb.config;
	}
	
	public void onWorldLoad(WorldLoadEvent event){
		  
		  World w = event.getWorld();
		  cb.worldSpawns.put(w, new Spawns(cb));
		  
			if(config.getKeys("worlds") != null && config.getKeys("worlds").contains(w.getName()))
			{
				cb.readConfig();
				
			}
			else
			{

				cb.addWorldToConfig(w);
	    		
	    		System.out.println("[NoSpawn] New world \""+w.getName()+"\" detected");
	    		
	    		cb.readConfig();
				
			}

	  }

}
