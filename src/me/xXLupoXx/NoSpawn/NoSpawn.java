package me.xXLupoXx.NoSpawn;

import java.io.File;
import java.util.LinkedList;
import java.util.List;



import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

import com.nijikokun.bukkit.Permissions.Permissions;

public class NoSpawn extends JavaPlugin
{
  NoSpawnEntityListener el;
  NoSpawnWorldListener wl;
  ConfigBuffer cb = new ConfigBuffer();
  private static String configfile = "plugins/NoSpawn/config.yml";
  Configuration config;
  CommandHandler cmh;

  public void onEnable()
  {
	  for(World w : this.getServer().getWorlds())
	  {
		  cb.worldSpawns.put(w, new Spawns());
	  }
    config = getConfiguration();

    File ft = new File(configfile);
    if (!ft.exists())
    {
    	for(World w : this.getServer().getWorlds())
    	{
    		config.setProperty("worlds."+w.getName()+".spawn.Pig", Boolean.valueOf(true));
    		config.setProperty("worlds."+w.getName()+".spawn.Sheep", Boolean.valueOf(true));
    		config.setProperty("worlds."+w.getName()+".spawn.Cow", Boolean.valueOf(true));
    		config.setProperty("worlds."+w.getName()+".spawn.Chicken", Boolean.valueOf(true));
    		config.setProperty("worlds."+w.getName()+".spawn.Pigman", Boolean.valueOf(true));
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
    	}
    	config.save();

      System.out.println("You probably startet this plugin the first time. Please edit the configuration file in the your plugins folder!");
    }else
    {
    	for(World w : this.getServer().getWorlds())
    	{
    		if(config.getKeys("worlds") != null && config.getKeys("worlds").contains(w.getName()))
    		{
    			this.cb.worldSpawns.get(w).SpawnAllowed.put(CreatureType.PIG, config.getBoolean("worlds."+ w.getName() +".spawn.Pig", true));
    			this.cb.worldSpawns.get(w).SpawnAllowed.put(CreatureType.SHEEP, config.getBoolean("worlds."+ w.getName() +".spawn.Sheep", true));
    			this.cb.worldSpawns.get(w).SpawnAllowed.put(CreatureType.COW, config.getBoolean("worlds."+ w.getName() +".spawn.Cow", true));
    			this.cb.worldSpawns.get(w).SpawnAllowed.put(CreatureType.CHICKEN, config.getBoolean("worlds."+ w.getName() +".spawn.Chicken", true));
    			this.cb.worldSpawns.get(w).SpawnAllowed.put(CreatureType.PIG_ZOMBIE, config.getBoolean("worlds."+ w.getName() +".spawn.Pigman", true));
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
    	}
    }



    cb.plugin = this;
    this.el = new NoSpawnEntityListener(this.cb,config);
    wl = new NoSpawnWorldListener(cb);
    this.cmh = new CommandHandler(this.getServer(), this.cb);
    PluginDescriptionFile pdfFile = getDescription();
    
    if(setupPermissions()){
    	
    	System.out.println("[NoSpawn] Permissions found, using Permissions!");
    	
    }else{
    	
    	System.out.println("[NoSpawn] No Permissions found, Op's can use commands!");
    	
    }
    
    System.out.println(pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!");
    
    
    getServer().getPluginManager().registerEvent(Event.Type.CREATURE_SPAWN, this.el, Event.Priority.Normal, this);
    getServer().getPluginManager().registerEvent(Event.Type.WORLD_LOAD, this.wl, Event.Priority.Normal, this);
  }

  public void onDisable()
  {
    PluginDescriptionFile pdfFile = getDescription();
    System.out.println(pdfFile.getName() + " version " + pdfFile.getVersion() + " is disabled... Oh my god they are coming!!!");
  }


  
  
  @Override
  public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {

	  if(command.getName().equals("nospawn")){
		  
		  if(args[0].equals("allowspawn")){
			  
			  return cmh.setMob(sender, args);
			  
		  } else if (args[0].equals("despawn")){
			  
			  return cmh.despawnMobs(sender, args);
			  
		  } else {
			  if(sender instanceof Player){
				  Player player = (Player)sender;
				  player.sendMessage(args[0] + " isn't a parameter please use allowspawn or despawn!");
			  }
			  return false;
		  }
	  }
	  
	  return false;
  }
  
  private boolean setupPermissions(){
	    Plugin perm = this.getServer().getPluginManager().getPlugin("Permissions");

		if (cb.Permissions == null) {
		        if (perm != null) {
		            cb.Permissions = ((Permissions)perm).getHandler();
		            return true;
		            
		        } else {
		            return false;
		        }
		    }
		    return false;
		}
  
  
  private List<Integer> getBlacklist(String args)
  {
    if (!args.isEmpty()) {
      String[] arr = args.split(",");
      List<Integer> tmplist = new LinkedList<Integer>();

      for (int i = 0; i < arr.length; i++) {
        tmplist.add(Integer.valueOf(Integer.parseInt(arr[i].trim())));
      }

      return tmplist;
    }

    return null;
  }
}