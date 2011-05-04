package me.xXLupoXx.NoSpawn;

import org.bukkit.block.BlockFace;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityListener;

public class NoSpawnEntityListener extends EntityListener
{
  ConfigBuffer cb;

  public NoSpawnEntityListener(ConfigBuffer cb)
  {
    this.cb = cb;
  }

  public void onCreatureSpawn(CreatureSpawnEvent event)
  {
	  if(cb.worldSpawns.get(event.getEntity().getWorld()).isSpawnAllowed(event.getCreatureType(),event.getLocation().getBlock().getFace(BlockFace.DOWN)) == false)
	  {
		  event.setCancelled(true);
	  }
  }
}