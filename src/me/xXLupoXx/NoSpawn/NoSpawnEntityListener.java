package me.xXLupoXx.NoSpawn;

import org.bukkit.block.BlockFace;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.util.config.Configuration;

public class NoSpawnEntityListener extends EntityListener
{
  ConfigBuffer cb;
  Configuration config;
  
  public NoSpawnEntityListener(ConfigBuffer cb, Configuration config)
  {
    this.cb = cb;
    this.config = config;
  }

  public void onCreatureSpawn(CreatureSpawnEvent event)
  {
		if(config.getKeys("worlds") != null && config.getKeys("worlds").contains(event.getEntity().getWorld().getName()))
		{
			if(cb.worldSpawns.containsKey(event.getEntity().getWorld()))
			{
			
			  if(cb.worldSpawns.get(event.getEntity().getWorld()).isSpawnAllowed(event.getCreatureType(),event.getLocation().getBlock().getFace(BlockFace.DOWN)) == false)
			  {
				  //System.out.println(event.getCreatureType().getName() + "spawnt nicht!");
				  event.setCancelled(true);
			  }
			  
			} else {
				//System.out.println("ich warte!");
				event.setCancelled(true);
			}
		}
  }
}