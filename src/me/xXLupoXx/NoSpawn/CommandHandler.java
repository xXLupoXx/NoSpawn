package me.xXLupoXx.NoSpawn;


import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.util.config.Configuration;

public class CommandHandler {
	
	Server server;
	ConfigBuffer cb;
	Configuration config;
	public Map<String,CreatureType> MobMap;

	public CommandHandler(Server server, ConfigBuffer cb) {
		this.server= server;
		this.cb = cb;
		this.config = cb.plugin.getConfiguration();
		this.MobMap = cb.MobMap;
	}

		public boolean allowSpawn(CommandSender sender,String[] args){
			
			  Player player = checkPerm(sender,"allowspawn");
			  	
			  if(player == null){
				  return false;
			  }
	  		  
			  if(args.length<3){
				  player.sendMessage(ChatColor.RED +"Invalid number of arguments! Usage is /nospawn allowspawn <world> <monster>");
				  return false;
			  }
			  
			  return setConf(player,args,"allow");

		}
		public boolean denySpawn(CommandSender sender,String[] args){
			
			  Player player = checkPerm(sender,"denyspawn");
			  	
			  if(player == null){
				  return false;
			  }
	  		  
			  if(args.length<3){
				  player.sendMessage(ChatColor.RED +"Invalid number of arguments! Usage is /nospawn allowspawn <world> <monster>");
				  return false;
			  }
			  
			  return setConf(player,args,"deny");

		}
		

		
		public boolean despawnMobs(CommandSender sender,String[] args){
			  Player player = checkPerm(sender,"despawn");
			  	
			  if(player == null){
				  return false;
			  }
			  
			  
			  List<Entity> le = new LinkedList<Entity>();
			  
			  

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
					  
					  player.sendMessage(ChatColor.RED + "Creature "+args[2]+" does not exist!");
					  return false;
				  }

		}
		
		public boolean SpawnMob(CommandSender sender,String[] args){
			
			  Player player = checkPerm(sender,"spawn");
			  Location tmpLoc;
			  int amount = 0 ;	
			  if(player == null){
				  return false;
			  }
			  
			  if(args.length <2 || args.length > 3){
				  
				  player.sendMessage(ChatColor.RED +"Invalid number of arguments! Usage is /nospawn spawn <mob> [amount]");
				  
				  return false;
				  
			  }
			  
			  if(args.length <3){
				  amount = 1;
			  }
			  else{
				  if(isInt(args[2])){
					  amount = Integer.parseInt(args[2]);
				  }else {
					  player.sendMessage(ChatColor.RED + "This is not a number!");
					  return false;
				  }
			  }
			  
			  String mob = Character.toUpperCase(args[1].charAt(0)) + args[1].substring(1, args[1].length()).toLowerCase();
			  if(args[1].toLowerCase().equals("zombie_pigman")){
				  mob = "Zombie_Pigman";
			  }
			  

			  
			  if(!(MobMap.containsKey(mob))){
				  
				  player.sendMessage(ChatColor.RED + "Creature "+args[1]+" does not exist!");
				  return false;
				  
			  }
			  
			  
			  tmpLoc = player.getLocation();
			  tmpLoc.setPitch(0);
			  tmpLoc.setYaw(0);
			  
			  cb.AdminSpawns.put(tmpLoc, MobMap.get(mob));
			  		  
			  for(int i = 0; i < amount; i++){
				  
				  player.getWorld().spawnCreature(player.getLocation(), MobMap.get(mob));
				  
			  }
			  cb.AdminSpawns.clear();
			  
			  player.sendMessage(ChatColor.GREEN + "Spawned "+ amount +" "+ mob +"s");
			
			return true;
			
		}
		
		private boolean setConf(Player player, String[] args, String operation){
			  String mob = Character.toUpperCase(args[2].charAt(0)) + args[2].substring(1, args[2].length()).toLowerCase();
			  if(args[2].toLowerCase().equals("zombie_pigman")){
				  mob = "Zombie_Pigman";
			  }
			  String w = args[1];
			  
			  if(this.server.getWorld(args[1]) == null){
				  
				  player.sendMessage(ChatColor.RED + "This world does not exist!" );
				  return false;
			  }

			  
			  if(MobMap.containsKey(mob)){
				  
				  if(operation.equals("allow")){
					  config.setProperty("worlds."+w+".creature."+mob+".spawn", true);
					  player.sendMessage(ChatColor.GREEN + mob + "s are now enabled!");
				  } else {
					  config.setProperty("worlds."+w+".creature."+mob+".spawn", false);
					  player.sendMessage(ChatColor.GREEN + mob + "s are now disabled!");
				  }
				  
				  config.save();
				  this.cb.worldSpawns.get(this.server.getWorld(w)).SpawnAllowed.put(MobMap.get(mob), config.getBoolean("worlds."+w+".creature."+mob+".spawn", true));

				  
				  
				  return true;
				  
			  } else {
				  player.sendMessage(ChatColor.RED + "Creature "+args[2]+" does not exist!");
				  return false;
			  }
		}
		
		private Player checkPerm(CommandSender sender, String perm){
			
			Player player = null;
			
			  if(sender instanceof Player){
				  player = (Player)sender;
				  
				  if(cb.Permissions == null){
					  if(!player.isOp()){
						  player.sendMessage(ChatColor.RED + "You don't have the permissions to do that!");
						  return null;
					  }
				  }else {
					  
					  if(!(cb.Permissions.has(player, "nospawn."+perm))){
						  player.sendMessage(ChatColor.RED + "You don't have the permissions to do that!");
						  return null;
					  }
				  }
				  
				  return player;
				  
			  } else {
				 
				  return null;
			  }
		}

		  private boolean isInt(String args){
			  
			  try{
				  Integer.parseInt(args);
				  return true;
			  }
			  catch (Exception e)
			  {
				  return false;
			  }
		  }

}


