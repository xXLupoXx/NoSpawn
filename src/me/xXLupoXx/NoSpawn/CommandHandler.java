package me.xXLupoXx.NoSpawn;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.util.config.Configuration;

public class CommandHandler {
	
	Server server;
	ConfigBuffer cb;
	Configuration config;
	public Map<String,CreatureType> MobMap  = new HashMap<String,CreatureType>();
	public Map<String,Entity> MobMapEntity  = new HashMap<String,Entity>();

	public CommandHandler(Server server, ConfigBuffer cb) {
		this.server= server;
		this.cb = cb;
		this.config = cb.plugin.getConfiguration();
		//Configfile Map
		
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

		
	}

		public boolean setMob(CommandSender sender,String[] args){
			
			  Player player = checkPerm(sender,"allowspawn");
			  	
			  if(player == null){
				  return false;
			  }
	  		  
			  if(args.length<4){
				  player.sendMessage(ChatColor.RED +"Invalid number of arguments! Usage is /nospawn allowspawn <world> <monster> <allow|deny>");
				  return false;
			  }
			  
			  String mob = Character.toUpperCase(args[2].charAt(0)) + args[2].substring(1, args[2].length()).toLowerCase();
			  if(args[2].toLowerCase().equals("zombie_pigman")){
				  mob = "Zombie_Pigman";
			  }
			  String w = args[1];
			  
			  if(!(args[3].equals("deny") || args[3].equals("allow"))){
				 
				  player.sendMessage(ChatColor.RED + "Invalid argument please use deny or allow");
				  return false;
			  }
			  
			  if(this.server.getWorld(args[1]) == null){
				  
				  player.sendMessage(ChatColor.RED + "This world does not exist!" );
				  return false;
			  }

			  
			  if(MobMap.containsKey(mob)){
				  
				  if(args[3].equals("allow")){
					  config.setProperty("worlds."+w+".spawn."+mob, true);
					  player.sendMessage(ChatColor.GREEN + mob + "s are now enabled!");
				  }else {
					  config.setProperty("worlds."+w+".spawn."+mob, false);
					  player.sendMessage(ChatColor.GREEN + mob + "s are now disabled!");
				  }
				  
				  if(!(GetBlacklistString(cb.worldSpawns.get(server.getWorld(w)).AnimalBlockBlackList).isEmpty())){
			    		config.setProperty("worlds."+w+".BlockBlacklist.Animal", GetBlacklistString(cb.worldSpawns.get(server.getWorld(w)).AnimalBlockBlackList));
				  }
				  if(!(GetBlacklistString(cb.worldSpawns.get(server.getWorld(w)).MonterBlockBlacklist).isEmpty())){
			    		config.setProperty("worlds."+w+".BlockBlacklist.Monster", GetBlacklistString(cb.worldSpawns.get(server.getWorld(w)).MonterBlockBlacklist));
				  }
				  
				  config.save();
				  this.cb.worldSpawns.get(this.server.getWorld(w)).SpawnAllowed.put(MobMap.get(mob), config.getBoolean("worlds."+w+".spawn."+mob, true));
				  return true;
				  
			  } else {
				  player.sendMessage(ChatColor.RED + "Creature "+args[2]+" does not exist!");
				  return false;
			  }

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
		
		private String GetBlacklistString(List<Integer> bl){
			
			String buffer = "";
			
			for(int i = 0; i< bl.size(); i++){
				if((bl.size()-1) == i){
				buffer += String.format("%s", bl.get(i));
				}else {
				buffer += String.format("%s,", bl.get(i));
				}
			}
			
			return buffer;
			
		}

}


