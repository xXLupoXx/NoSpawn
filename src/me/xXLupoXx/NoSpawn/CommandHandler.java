package me.xXLupoXx.NoSpawn;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.util.config.Configuration;

public class CommandHandler {
	
	Server server;
	ConfigBuffer cb;
	Configuration config;

	public CommandHandler(Server server, ConfigBuffer cb) {
		this.server= server;
		this.cb = cb;
		this.config = cb.plugin.getConfiguration();
	}

		public boolean setMob(CommandSender sender,String[] args){
			
			  Player player;

			  if(sender instanceof Player){
				  player = (Player)sender;
				  if(cb.Permissions == null){
					  if(!player.isOp()){
						  player.sendMessage(ChatColor.RED + "You don't have the permissions to do that!");
						  return false;
					  }
				  }else {
					  
					  if(!(cb.Permissions.has(player, "nospawn.allowspawn"))){
						  player.sendMessage(ChatColor.RED + "You don't have the permissions to do that!");
						  return false;
					  }
				  }
				  
			  } else {
				  player = null;
				  return false;
			  }
			  
			  
			  if(args.length<4){
				  player.sendMessage(ChatColor.RED +"Invalid number of arguments! Usage is /nospawn allowspawn <world> <monster> <allow|deny>");
				  return false;
			  }
			  
			  String mob = args[2].toLowerCase();
			  String w = args[1];
			  
			  if(!(args[3].equals("deny") || args[3].equals("allow"))){
				 
				  player.sendMessage(ChatColor.RED + "Invalid argument please use deny or allow");
				  return false;
			  }
			  
			  if(this.server.getWorld(args[1]) == null){
				  
				  player.sendMessage(ChatColor.RED + "This world does not exist!" );
				  return false;
			  }
			  
			  
					  
					  if(mob.equals("sheep")){
						  
						  if(args[3].equals("allow")){
							  config.setProperty("worlds."+w+".spawn.Sheep", true);
							  player.sendMessage(ChatColor.GREEN + "Sheeps are now enabled!");
						  }else {
							  config.setProperty("worlds."+w+".spawn.Sheep", false);
							  player.sendMessage(ChatColor.GREEN + "Sheeps are now disabled!");
						  }
						  config.save();
						  this.cb.worldSpawns.get(this.server.getWorld(w)).SpawnAllowed.put(CreatureType.SHEEP, config.getBoolean("worlds."+w+".spawn.Sheep", true));
						  return true;
					  } else if(mob.equals("pig")){
						  
						  if(args[3].equals("allow")){
							  config.setProperty("worlds."+w+".spawn.Pig", true);
							  player.sendMessage(ChatColor.GREEN + "Pigs are now enabled!");
						  }else {
							  config.setProperty("worlds."+w+".spawn.Pig", false);
							  player.sendMessage(ChatColor.GREEN + "Pigs are now disabled!");
						  }
						  config.save();
						  this.cb.worldSpawns.get(this.server.getWorld(w)).SpawnAllowed.put(CreatureType.PIG, config.getBoolean("worlds."+w+".spawn.Pig", true));
						  return true;
					  } else if(mob.equals("cow")){
						  
						  if(args[3].equals("allow")){
							  config.setProperty("worlds."+w+".spawn.Cow", true);
							  player.sendMessage(ChatColor.GREEN + "Cows are now enabled!");
						  }else {
							  config.setProperty("worlds."+w+".spawn.Cow", false);
							  player.sendMessage(ChatColor.GREEN + "Cows are now disabled!");
						  }
						  config.save();
						  this.cb.worldSpawns.get(this.server.getWorld(w)).SpawnAllowed.put(CreatureType.COW, config.getBoolean("worlds."+w+".spawn.Cow", true));
						  return true;
					  } else if(mob.equals("chicken")){
						  
						  if(args[3].equals("allow")){
							  config.setProperty("worlds."+w+".spawn.Chicken", true);
							  player.sendMessage(ChatColor.GREEN + "Chickens are now enabled!");
						  }else {
							  config.setProperty("worlds."+w+".spawn.Chicken", false);
							  player.sendMessage(ChatColor.GREEN + "Chickens are now disabled!");
						  }
						  config.save();
						  this.cb.worldSpawns.get(this.server.getWorld(w)).SpawnAllowed.put(CreatureType.CHICKEN, config.getBoolean("worlds."+w+".spawn.Chicken", true));
						  return true;
					  } else if(mob.equals("zombie_pigman")){
						  
						  if(args[3].equals("allow")){
							  config.setProperty("worlds."+w+".spawn.Zombie_Pigman", true);
							  player.sendMessage(ChatColor.GREEN + "Zombie Pigmen are now enabled!");
						  }else {
							  config.setProperty("worlds."+w+".spawn.Zombie_Pigman", false);
							  player.sendMessage(ChatColor.GREEN + "Zombie Pigmen are now disabled!");
						  }
						  config.save();
						  this.cb.worldSpawns.get(this.server.getWorld(w)).SpawnAllowed.put(CreatureType.PIG_ZOMBIE, config.getBoolean("worlds."+w+".spawn.Zombie_Pigman", true));
						  return true;
					  }  else if(mob.equals("squid")){
						  
						  if(args[3].equals("allow")){
							  config.setProperty("worlds."+w+".spawn.Squid", true);
							  player.sendMessage(ChatColor.GREEN + "Squids are now enabled!");
						  }else {
							  config.setProperty("worlds."+w+".spawn.Squid", false);
							  player.sendMessage(ChatColor.GREEN + "Squids are now disabled!");
						  }
						  config.save();
						  this.cb.worldSpawns.get(this.server.getWorld(w)).SpawnAllowed.put(CreatureType.SQUID, config.getBoolean("worlds."+w+".spawn.Squid", true));
						  return true;
					  } else if(mob.equals("wolf")){
						  
						  if(args[3].equals("allow")){
							  config.setProperty("worlds."+w+".spawn.Wolf", true);
							  player.sendMessage(ChatColor.GREEN + "Wolfs are now enabled!");
						  }else {
							  config.setProperty("worlds."+w+".spawn.Wolf", false);
							  player.sendMessage(ChatColor.GREEN + "Wolfs are now disabled!");
						  }
						  config.save();
						  this.cb.worldSpawns.get(this.server.getWorld(w)).SpawnAllowed.put(CreatureType.WOLF, config.getBoolean("worlds."+w+".spawn.Wolf", true));
						  return true;
					  } else if(mob.equals("zombie")){
						  
						  if(args[3].equals("allow")){
							  config.setProperty("worlds."+w+".spawn.Zombie", true);
							  player.sendMessage(ChatColor.GREEN + "Zombies are now enabled!");
						  }else {
							  config.setProperty("worlds."+w+".spawn.Zombie", false);
							  player.sendMessage(ChatColor.GREEN + "Zombies are now disabled!");
						  }
						  config.save();
						  this.cb.worldSpawns.get(this.server.getWorld(w)).SpawnAllowed.put(CreatureType.ZOMBIE, config.getBoolean("worlds."+w+".spawn.Zombie", true));
						  return true;
					  } else if(mob.equals("skeleton")){
						  
						  if(args[3].equals("allow")){
							  config.setProperty("worlds."+w+".spawn.Skeleton", true);
							  player.sendMessage(ChatColor.GREEN + "Skeletons are now enabled!");
						  }else {
							  config.setProperty("worlds."+w+".spawn.Skeleton", false);
							  player.sendMessage(ChatColor.GREEN + "Skeletons are now disabled!");
						  }
						  config.save();
						  this.cb.worldSpawns.get(this.server.getWorld(w)).SpawnAllowed.put(CreatureType.SKELETON, config.getBoolean("worlds."+w+".spawn.Skeleton", true));
						  return true;
					  } else if(mob.equals("spider")){
						  
						  if(args[3].equals("allow")){
							  config.setProperty("worlds."+w+".spawn.Spider", true);
							  player.sendMessage(ChatColor.GREEN + "Spiders are now enabled!");
						  }else {
							  config.setProperty("worlds."+w+".spawn.Spider", false);
							  player.sendMessage(ChatColor.GREEN + "Spiders are now disabled!");
						  }
						  config.save();
						  this.cb.worldSpawns.get(this.server.getWorld(w)).SpawnAllowed.put(CreatureType.SPIDER, config.getBoolean("worlds."+w+".spawn.Spider", true));
						  return true;
					  } else if(mob.equals("creeper")){
						  
						  if(args[3].equals("allow")){
							  config.setProperty("worlds."+w+".spawn.Creeper", true);
							  player.sendMessage(ChatColor.GREEN + "Creepers are now enabled!");
						  }else {
							  config.setProperty("worlds."+w+".spawn.Creeper", false);
							  player.sendMessage(ChatColor.GREEN + "Creepers are now disabled!");
						  }
						  config.save();
						  this.cb.worldSpawns.get(this.server.getWorld(w)).SpawnAllowed.put(CreatureType.CREEPER, config.getBoolean("worlds."+w+".spawn.Creeper", true));
						  return true;
					  } else if(mob.equals("slime")){
						  
						  if(args[3].equals("allow")){
							  config.setProperty("worlds."+w+".spawn.Slime", true);
							  player.sendMessage(ChatColor.GREEN + "Slime is now enabled!");
						  }else {
							  config.setProperty("worlds."+w+".spawn.Slime", false);
							  player.sendMessage(ChatColor.GREEN + "Slime is now disabled!");
						  }
						  config.save();
						  this.cb.worldSpawns.get(this.server.getWorld(w)).SpawnAllowed.put(CreatureType.SLIME, config.getBoolean("worlds."+w+".spawn.Slime", true));
						  return true;
					  } else if(mob.equals("ghast")){
						  
						  if(args[3].equals("allow")){
							  config.setProperty("worlds."+w+".spawn.Ghast", true);
							  player.sendMessage(ChatColor.GREEN + "Ghasts are now enabled!");
						  }else {
							  config.setProperty("worlds."+w+".spawn.Ghast", false);
							  player.sendMessage(ChatColor.GREEN + "Ghasts are now disabled!");
						  }
						  config.save();
						  this.cb.worldSpawns.get(this.server.getWorld(w)).SpawnAllowed.put(CreatureType.GHAST, config.getBoolean("worlds."+w+".spawn.Ghast", true));
						  return true;
					  } else if(mob.equals("giant")){
						  
						  if(args[3].equals("allow")){
							  config.setProperty("worlds."+w+".spawn.Giant", true);
							  player.sendMessage(ChatColor.GREEN + "Giants are now enabled!");
						  }else {
							  config.setProperty("worlds."+w+".spawn.Giant", false);
							  player.sendMessage(ChatColor.GREEN + "Giants are now disabled!");
						  }
						  config.save();
						  this.cb.worldSpawns.get(this.server.getWorld(w)).SpawnAllowed.put(CreatureType.GIANT, config.getBoolean("worlds."+w+".spawn.Giant", true));
						  return true;
					  } else {
						  
						  player.sendMessage(ChatColor.RED + "Creature"+args[2]+"does not exist!");
						  return false;
					  }

		}
		
		public boolean despawnMobs(CommandSender sender,String[] args){
			Player player;
			  List<Entity> le = new LinkedList<Entity>();
			  
			  
			  if(sender instanceof Player){
				  player = (Player)sender;
				  if(cb.Permissions == null){
					  if(!player.isOp()){
						  player.sendMessage(ChatColor.RED + "You don't have the permissions to do that!");
						  return false;
					  }
				  }else {
					  
					  if(!(cb.Permissions.has(player, "nospawn.despawn"))){
						  player.sendMessage(ChatColor.RED + "You don't have the permissions to do that!");
						  return false;
					  }
				  }
				  
			  } else {
				  player = null;
				  return false;
			  }
			  if(args.length<3){
				  player.sendMessage(ChatColor.RED +"Invalid number of arguments! Usage is /nospawn despawn <world> <monster>");
				  return false;
			  }
			  
			  String mob = args[2].toLowerCase();
			  String w = args[1];
			  
			  if(this.server.getWorld(args[1]) == null){
				  
				  player.sendMessage(ChatColor.RED + "This world does not exist!" );
				  return false;
			  }
			  else
			  {
				  le = server.getWorld(w).getEntities();
			  }
			  
			  if(mob.equals("pig")){
				  
				 for(int i = 0; i<le.size();i++){
					 
					 if(le.get(i) instanceof Pig){
						 le.get(i).remove();;
					 }
				 }
				  player.sendMessage(ChatColor.GREEN + "Removed all Pigs from "+w );
				  return true;
				  
			  } else if(mob.equals("sheep")){
				  
					 for(int i = 0; i<le.size();i++){
						 
						 if(le.get(i) instanceof Sheep){
							 le.get(i).remove();
						 }
					 }
					  player.sendMessage(ChatColor.GREEN + "Removed all Sheeps from "+w );
					  return true;
					  
				  } else if(mob.equals("cow")){
					  
					  for(int i = 0; i<le.size();i++){
							 
							 if(le.get(i) instanceof Cow){
								 le.get(i).remove();
							 }
						 }
						  player.sendMessage(ChatColor.GREEN + "Removed all Cows from "+w );
						  return true;
						  
					  
				  } else if(mob.equals("chicken")){
					  
					  for(int i = 0; i<le.size();i++){
							 
							 if(le.get(i) instanceof Chicken){
								 le.get(i).remove();
							 }
						 }
						  player.sendMessage(ChatColor.GREEN + "Removed all Chickens from "+w );
						  return true;
						  
				  } else if(mob.equals("zombie_pigman")){
					  
					  for(int i = 0; i<le.size();i++){
							 
							 if(le.get(i) instanceof PigZombie){
								 le.get(i).remove();
							 }
						 }
						  player.sendMessage(ChatColor.GREEN + "Removed all Zombie Pigmen from "+w );
						  return true;
						  
				  }  else if(mob.equals("squid")){
					  
					  for(int i = 0; i<le.size();i++){
							 
							 if(le.get(i) instanceof Squid){
								 le.get(i).remove();
							 }
						 }
						  player.sendMessage(ChatColor.GREEN + "Removed all Squids from "+w );
						  return true;
						  
				  } else if(mob.equals("wolf")){
					  
					  for(int i = 0; i<le.size();i++){
							 
							 if(le.get(i) instanceof Wolf){
								 le.get(i).remove();
							 }
						 }
						  player.sendMessage(ChatColor.GREEN + "Removed all Wolfs from "+w );
						  return true;
						  
				  } else if(mob.equals("zombie")){
					  
					  for(int i = 0; i<le.size();i++){
							 
							 if(le.get(i) instanceof Zombie){
								 le.get(i).remove();
							 }
						 }
						  player.sendMessage(ChatColor.GREEN + "Removed all Zombies from "+w );
						  return true;
						  
				  } else if(mob.equals("skeleton")){
					  
					  for(int i = 0; i<le.size();i++){
							 
							 if(le.get(i) instanceof Skeleton){
								 le.get(i).remove();
							 }
						 }
						  player.sendMessage(ChatColor.GREEN + "Removed all Skeletons from "+w );
						  return true;
						  
				  } else if(mob.equals("spider")){
					  
					  for(int i = 0; i<le.size();i++){
							 
							 if(le.get(i) instanceof Spider){
								 le.get(i).remove();
							 }
						 }
						  player.sendMessage(ChatColor.GREEN + "Removed all Spiders from "+w );
						  return true;
						  
				  } else if(mob.equals("creeper")){
					  
					  for(int i = 0; i<le.size();i++){
							 
							 if(le.get(i) instanceof Creeper){
								 le.get(i).remove();
							 }
						 }
						  player.sendMessage(ChatColor.GREEN + "Removed all Creepers from "+w );
						  return true;
						  
				  } else if(mob.equals("slime")){
					  
					  for(int i = 0; i<le.size();i++){
							 
							 if(le.get(i) instanceof Slime){
								 le.get(i).remove();
							 }
						 }
						  player.sendMessage(ChatColor.GREEN + "Removed all Slimes from "+w );
						  return true;
						  
				  } else if(mob.equals("ghast")){
					  
					  for(int i = 0; i<le.size();i++){
							 
							 if(le.get(i) instanceof Ghast){
								 le.get(i).remove();
							 }
						 }
						  player.sendMessage(ChatColor.GREEN + "Removed all Ghasts from "+w );
						  return true;
						  
				  } else if(mob.equals("giant")){
					  
					  for(int i = 0; i<le.size();i++){
							 
							 if(le.get(i) instanceof Giant){
								 le.get(i).remove();
							 }
						 }
						  player.sendMessage(ChatColor.GREEN + "Removed all Giants from "+w );
						  return true;
						  
				  } else {
					  
					  player.sendMessage(ChatColor.RED + "Creature"+args[2]+"does not exist!");
					  return false;
				  }

		}

}


