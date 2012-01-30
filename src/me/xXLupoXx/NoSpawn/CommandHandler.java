/*
 * Copyright (c) <2012> <xXLupoXx>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package me.xXLupoXx.NoSpawn;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CommandHandler {

	Server server;
	ConfigBuffer cb;
	boolean hasPermission = false;

	public CommandHandler(Server server, ConfigBuffer cb) {
		this.server = server;
		this.cb = cb;
	}

    public boolean editGlobalBlockBlacklist(CommandSender sender, String[] args){

        hasPermission = checkPerm(sender, "editglobalblockblacklist");

        if (!hasPermission) {
            return false;
        }

        if (args.length < 3) {

            if(args[0].contains("add")){

            cb.plugin
                    .sendNospawnMessage(
                            sender,
                            "Invalid number of arguments! Usage is /nospawn addgbbl <world> <blockID> ",
                            ChatColor.RED);

            } else if (args[0].contains("del")){

                cb.plugin
                        .sendNospawnMessage(
                                sender,
                                "Invalid number of arguments! Usage is /nospawn delgbbl <world> <blockID> ",
                                ChatColor.RED);

            }
            return false;
        }

        if (this.server.getWorld(args[1]) == null) {

            cb.plugin.sendNospawnMessage(sender, "This world does not exist!",
                    ChatColor.RED);
            return false;
        }

        if (!isInt(args[2])) {
            cb.plugin.sendNospawnMessage(sender, args[2] + " is no block ID!",
                    ChatColor.RED);
            return false;
        }
        
        if(args[0].contains("add")){
            
            if(addBlacklistElement("worlds." + args[1] + ".properties.GlobalBlockBlacklist",Integer.parseInt(args[2]))){

                cb.plugin.sendNospawnMessage(sender, "Block with ID "+args[2]+" added to Blacklist for world: "+args[1],
                        ChatColor.GREEN);

            } else {

                cb.plugin.sendNospawnMessage(sender, "Blacklist for world: "+ args[1] + " already contains Block with ID "+args[2],
                        ChatColor.RED);

            }
            
        } else if (args[0].contains("del")) {

            if(deleteBlacklistElement("worlds." + args[1] + ".properties.GlobalBlockBlacklist",Integer.parseInt(args[2]))){

                cb.plugin.sendNospawnMessage(sender, "Block with ID "+args[2]+" removed from Blacklist for world: "+args[1],
                        ChatColor.GREEN);

            } else {

                cb.plugin.sendNospawnMessage(sender, "Blacklist for world: "+ args[1] + " don't contains Block with ID "+args[2],
                        ChatColor.RED);

            }
            
        }
        

        return true;
    }

    public boolean editBlockBlacklist(CommandSender sender, String[] args){

        hasPermission = checkPerm(sender, "editblockblacklist");
        String mob = Character.toUpperCase(args[2].charAt(0))
                + args[2].substring(1, args[2].length()).toLowerCase();

        if (args[2].toLowerCase().equals("zombie_pigman")) {
            mob = "Zombie_Pigman";
        }

        if (!hasPermission) {
            return false;
        }

        if (args.length < 4) {

            if(args[0].contains("add")){

                cb.plugin
                        .sendNospawnMessage(
                                sender,
                                "Invalid number of arguments! Usage is /nospawn addbl <world> <mob> <blockID> ",
                                ChatColor.RED);

            } else if (args[0].contains("del")){

                cb.plugin
                        .sendNospawnMessage(
                                sender,
                                "Invalid number of arguments! Usage is /nospawn delbl <world> <mob> <blockID> ",
                                ChatColor.RED);

            }
            return false;
        }

        if (!isInt(args[3])) {
            cb.plugin.sendNospawnMessage(sender, args[3] + " is no block ID!",
                    ChatColor.RED);
            return false;
        }

        if (ConfigBuffer.MobMap.containsKey(mob)) {

            if(args[0].contains("add")){

                if(addBlacklistElement("worlds." + args[1] + ".creature." + mob + ".BlockBlacklist",Integer.parseInt(args[3]))){

                    cb.plugin.sendNospawnMessage(sender, "Block with ID "+args[3]+" added to Blacklist for "+ mob +", world: "+args[1],
                            ChatColor.GREEN);

                } else {

                    cb.plugin.sendNospawnMessage(sender, "Blacklist for "+ mob +", world: "+ args[1] + " already contains Block with ID "+args[3],
                            ChatColor.RED);

                }

            } else if (args[0].contains("del")) {

                if(deleteBlacklistElement("worlds." + args[1] + ".creature." + mob + ".BlockBlacklist",Integer.parseInt(args[3]))){

                    cb.plugin.sendNospawnMessage(sender, "Block with ID "+args[3]+" removed from Blacklist for "+ mob +", world: "+args[1],
                            ChatColor.GREEN);

                } else {

                    cb.plugin.sendNospawnMessage(sender, "Blacklist for "+ mob +", world: "+ args[1] + " don't contains Block with ID "+args[3],
                            ChatColor.RED);

                }

            }

        } else {
            cb.plugin.sendNospawnMessage(sender, "Creature " + mob
                    + " does not exist!", ChatColor.RED);
            return false;
        }

        return true;
    }

    public boolean reloadConf(CommandSender sender){

        hasPermission = checkPerm(sender, "reloadconfig");

        if (!hasPermission) {
            return false;
        }

        cb.plugin.reloadConfig();
        cb.config = cb.plugin.getConfig();
        cb.readConfig();

        cb.plugin.sendNospawnMessage(sender, "Config reload complete",
                ChatColor.GREEN);

       return true;
    }
    
    
    public boolean setUseGlobalBlockBlacklist(CommandSender sender, String[] args){

         hasPermission = checkPerm(sender, "useglobalblockblacklist");
         String mob = Character.toUpperCase(args[2].charAt(0))
				+ args[2].substring(1, args[2].length()).toLowerCase();

        if (args[2].toLowerCase().equals("zombie_pigman")) {
			mob = "Zombie_Pigman";
		}

         if (!hasPermission) {
			return false;
		 }

         if (args.length < 4) {
			cb.plugin
					.sendNospawnMessage(
							sender,
							"Invalid number of arguments! Usage is /nospawn usegbbl <world> <monster> <true/false>",
							ChatColor.RED);
			return false;
		 }

        if (this.server.getWorld(args[1]) == null) {

			cb.plugin.sendNospawnMessage(sender, "This world does not exist!",
					ChatColor.RED);
			return false;
		}

        if (ConfigBuffer.MobMap.containsKey(mob)) {

        if(args[3].equals("true")){
            cb.config.set("worlds." + args[1] + ".creature." + mob + ".UseGlobalBlockBlacklist", true);

            		cb.plugin
					.sendNospawnMessage(
							sender,
							mob + " now use the global Blockblacklist ("+ args[1] +")",
							ChatColor.GREEN);

            saveConfig();
            cb.readConfig();
        }
        else if(args[3].equals("false")){
            cb.config.set("worlds." + args[1] + ".creature." + mob + ".UseGlobalBlockBlacklist", false);

                    cb.plugin
					.sendNospawnMessage(
							sender,
							mob + " now don't use the global Blockblacklist ("+ args[1] +")",
							ChatColor.GREEN);

            saveConfig();
            cb.readConfig();
        }

            return true;

        } else {
			cb.plugin.sendNospawnMessage(sender, "Creature " + mob
					+ " does not exist!", ChatColor.RED);
			return false;
		}


    }

	public boolean allowSpawn(CommandSender sender, String[] args) {

		hasPermission = checkPerm(sender, "allowspawn");

		if (!hasPermission) {
			return false;
		}

		if (args.length < 3) {
			cb.plugin
					.sendNospawnMessage(
							sender,
							"Invalid number of arguments! Usage is /nospawn allowspawn <world> <monster>",
							ChatColor.RED);
			return false;
		}

		return setConf(sender, args, "allow");

	}

	public boolean denySpawn(CommandSender sender, String[] args) {

		hasPermission = checkPerm(sender, "denyspawn");

		if (!hasPermission) {
			return false;
		}

		if (args.length < 3) {
			cb.plugin
					.sendNospawnMessage(
							sender,
							"Invalid number of arguments! Usage is /nospawn denyspawn <world> <monster>",
							ChatColor.RED);
			return false;
		}

		return setConf(sender, args, "deny");

	}

	public boolean setMobLimit(CommandSender sender, String[] args) {

		hasPermission = checkPerm(sender, "setmoblimit");

		if (!hasPermission) {
			return false;
		}

		if (args.length < 4) {
			cb.plugin
					.sendNospawnMessage(
							sender,
							"Invalid number of arguments! Usage is /nospawn setmoblimit <world> <monster> <ammount>",
							ChatColor.RED);
			return false;
		}

		if (!isInt(args[3])) {
			cb.plugin.sendNospawnMessage(sender, "This is not a number!",
					ChatColor.RED);
			return false;
		}

		return setConf(sender, args, "moblimit");

	}

	public boolean setTotalMobLimit(CommandSender sender, String[] args) {
		hasPermission = checkPerm(sender, "settotalmoblimit");

		if (!hasPermission) {
			return false;
		}

		if (args.length < 3) {
			cb.plugin
					.sendNospawnMessage(
							sender,
							"Invalid number of arguments! Usage is /nospawn settotalmoblimit <world> <ammount>",
							ChatColor.RED);
			return false;
		}

		if (!isInt(args[2])) {
			cb.plugin.sendNospawnMessage(sender, "This is not a number!",
					ChatColor.RED);
			return false;
		}

		return setConf2(sender, args, "totalmoblimit");
	}

	public boolean setMobTimer(CommandSender sender, String[] args) {
		hasPermission = checkPerm(sender, "settimer");

		if (!hasPermission) {
			return false;
		}

		if (args.length < 2) {
			cb.plugin
					.sendNospawnMessage(
							sender,
							"Invalid number of arguments! Usage is /nospawn settimer <milliseconds>",
							ChatColor.RED);
			return false;
		}

		if (!isInt(args[1])) {
			cb.plugin.sendNospawnMessage(sender, "This is not a number!",
					ChatColor.RED);
			return false;
		}

		cb.config.set("properties.RefreshTimer", Integer.parseInt(args[1]));
		cb.plugin.sendNospawnMessage(sender, "Timer set to " + args[1]
				+ " milliseconds!", ChatColor.GREEN);
		saveConfig();
		cb.readConfig();

		server.getScheduler().cancelTasks(cb.plugin);
		cb.plugin.mc.runSchedueler();

		return true;

	}

	public boolean despawnMobs(CommandSender sender, String[] args) {
		hasPermission = checkPerm(sender, "despawn");
		int killcount = 0;

		if (!hasPermission) {
			return false;
		}

		List<Entity> le;

		if (args.length < 3) {
			cb.plugin
					.sendNospawnMessage(
							sender,
							"Invalid number of arguments! Usage is /nospawn despawn <world> <monster>",
							ChatColor.RED);
			return false;
		}

		String mob = args[2].toLowerCase();
		String w = args[1];

		if (this.server.getWorld(args[1]) == null) {

			cb.plugin.sendNospawnMessage(sender, "This world does not exist!",
					ChatColor.RED);
			return false;
		} else {
			le = server.getWorld(w).getEntities();
		}

		if (mob.equals("pig")) {

            for (Entity aLe : le)
            {
                if (aLe instanceof Pig)
                {
                    aLe.remove();
                    killcount++;
                }
            }
			cb.plugin.sendNospawnMessage(sender, "Removed " + killcount
					+ " Pigs from " + w, ChatColor.GREEN);
			return true;

		} else if (mob.equals("sheep")) {

            for (Entity aLe : le)
            {
                if (aLe instanceof Sheep)
                {
                    aLe.remove();
                    killcount++;
                }
            }
			cb.plugin.sendNospawnMessage(sender, "Removed " + killcount
					+ " Sheeps from " + w, ChatColor.GREEN);
			return true;

		} else if (mob.equals("cow")) {

            for (Entity aLe : le)
            {
                if (aLe instanceof Cow)
                {
                    aLe.remove();
                    killcount++;
                }
            }
			cb.plugin.sendNospawnMessage(sender, "Removed " + killcount
					+ " Cows from " + w, ChatColor.GREEN);
			return true;

		} else if (mob.equals("chicken")) {

            for (Entity aLe : le)
            {
                if (aLe instanceof Chicken)
                {
                    aLe.remove();
                    killcount++;
                }
            }
			cb.plugin.sendNospawnMessage(sender, "Removed " + killcount
					+ " Chickens from " + w, ChatColor.GREEN);
			return true;

		} else if (mob.equals("zombie_pigman")) {

            for (Entity aLe : le)
            {
                if (aLe instanceof PigZombie)
                {
                    aLe.remove();
                    killcount++;
                }
            }
			cb.plugin.sendNospawnMessage(sender, "Removed " + killcount
					+ " Zombie Pigmen from " + w, ChatColor.GREEN);
			return true;

		} else if (mob.equals("squid")) {

            for (Entity aLe : le)
            {
                if (aLe instanceof Squid)
                {
                    aLe.remove();
                    killcount++;
                }
            }
			cb.plugin.sendNospawnMessage(sender, "Removed " + killcount
					+ " Squids from " + w, ChatColor.GREEN);
			return true;

		} else if (mob.equals("wolf")) {

            for (Entity aLe : le)
            {
                if (aLe instanceof Wolf)
                {
                    aLe.remove();
                    killcount++;
                }
            }
			cb.plugin.sendNospawnMessage(sender, "Removed " + killcount
					+ " Wolfs from " + w, ChatColor.GREEN);
			return true;

		} else if (mob.equals("zombie")) {

            for (Entity aLe : le)
            {
                if (aLe instanceof Zombie)
                {
                    aLe.remove();
                    killcount++;
                }
            }
			cb.plugin.sendNospawnMessage(sender, "Removed " + killcount
					+ " Zombies from " + w, ChatColor.GREEN);
			return true;

		} else if (mob.equals("skeleton")) {

            for (Entity aLe : le)
            {
                if (aLe instanceof Skeleton)
                {
                    aLe.remove();
                    killcount++;
                }
            }
			cb.plugin.sendNospawnMessage(sender, "Removed " + killcount
					+ " Skeletons from " + w, ChatColor.GREEN);
			return true;

		} else if (mob.equals("spider")) {

            for (Entity aLe : le)
            {
                if (aLe instanceof Spider)
                {
                    aLe.remove();
                    killcount++;
                }
            }
			cb.plugin.sendNospawnMessage(sender, "Removed " + killcount
					+ " Spiders from " + w, ChatColor.GREEN);
			return true;

		} else if (mob.equals("creeper")) {

            for (Entity aLe : le)
            {
                if (aLe instanceof Creeper)
                {
                    aLe.remove();
                    killcount++;
                }
            }
			cb.plugin.sendNospawnMessage(sender, "Removed " + killcount
					+ " Creeper from " + w, ChatColor.GREEN);
			return true;

		} else if (mob.equals("slime")) {

            for (Entity aLe : le)
            {
                if (aLe instanceof Slime)
                {
                    aLe.remove();
                    killcount++;
                }
            }
			cb.plugin.sendNospawnMessage(sender, "Removed " + killcount
					+ " Slimes from " + w, ChatColor.GREEN);
			return true;

		} else if (mob.equals("ghast")) {

            for (Entity aLe : le)
            {
                if (aLe instanceof Ghast)
                {
                    aLe.remove();
                    killcount++;
                }
            }
			cb.plugin.sendNospawnMessage(sender, "Removed " + killcount
					+ " Ghasts from " + w, ChatColor.GREEN);
			return true;

		} else if (mob.equals("giant")) {

            for (Entity aLe : le)
            {
                if (aLe instanceof Giant)
                {
                    aLe.remove();
                    killcount++;
                }
            }
			cb.plugin.sendNospawnMessage(sender, "Removed " + killcount
					+ " Giants from " + w, ChatColor.GREEN);
			return true;

		} else if (mob.equals("enderman")) {

            for (Entity aLe : le)
            {
                if (aLe instanceof Enderman)
                {
                    aLe.remove();
                    killcount++;
                }
            }
			cb.plugin.sendNospawnMessage(sender, "Removed " + killcount
					+ " Enderman from " + w, ChatColor.GREEN);
			return true;

		} else if (mob.equals("enderdragon")) {

            for (Entity aLe : le)
            {
                if (aLe instanceof EnderDragon)
                {
                    aLe.remove();
                    killcount++;
                }
            }
			cb.plugin.sendNospawnMessage(sender, "Removed " + killcount
					+ " Enderdragons from " + w, ChatColor.GREEN);
			return true;

		} else if (mob.equals("blaze")) {

            for (Entity aLe : le)
            {
                if (aLe instanceof Blaze)
                {
                    aLe.remove();
                    killcount++;
                }
            }
			cb.plugin.sendNospawnMessage(sender, "Removed " + killcount
					+ " Blaze from " + w, ChatColor.GREEN);
			return true;

		} else if (mob.equals("villager")) {

            for (Entity aLe : le)
            {
                if (aLe instanceof Villager)
                {
                    aLe.remove();
                    killcount++;
                }
            }
			cb.plugin.sendNospawnMessage(sender, "Removed " + killcount
					+ " Villagers from " + w, ChatColor.GREEN);
			return true;

		} else if (mob.equals("silverfish")) {

            for (Entity aLe : le)
            {
                if (aLe instanceof Silverfish)
                {
                    aLe.remove();
                    killcount++;
                }
            }
			cb.plugin.sendNospawnMessage(sender, "Removed " + killcount
					+ " Silverfish from " + w, ChatColor.GREEN);
			return true;

		} else if (mob.equals("cave_spider")) {

            for (Entity aLe : le)
            {
                if (aLe instanceof CaveSpider)
                {
                    aLe.remove();
                    killcount++;
                }
            }
			cb.plugin.sendNospawnMessage(sender, "Removed " + killcount
					+ " Cave Spiders from " + w, ChatColor.GREEN);
			return true;

		} else if (mob.equals("mooshroom")) {

            for (Entity aLe : le)
            {
                if (aLe instanceof MushroomCow)
                {
                    aLe.remove();
                    killcount++;
                }
            }
			cb.plugin.sendNospawnMessage(sender, "Removed " + killcount
					+ " Mooshrooms from " + w, ChatColor.GREEN);
			return true;

		} else if (mob.equals("magma_cube")) {

            for (Entity aLe : le)
            {
                if (aLe instanceof MagmaCube)
                {
                    aLe.remove();
                    killcount++;
                }
            }
			cb.plugin.sendNospawnMessage(sender, "Removed " + killcount
					+ " Magma Cubes from " + w, ChatColor.GREEN);
			return true;

		} else {

			cb.plugin.sendNospawnMessage(sender, "Creature " + args[2]
					+ " does not exist!", ChatColor.RED);
			return false;
		}

	}

	private boolean setConf(CommandSender sender, String[] args,
			String operation) {
		String mob = Character.toUpperCase(args[2].charAt(0))
				+ args[2].substring(1, args[2].length()).toLowerCase();
		if (args[2].toLowerCase().equals("zombie_pigman")) {
			mob = "Zombie_Pigman";
		}
		String w = args[1];

		if (this.server.getWorld(args[1]) == null) {

			cb.plugin.sendNospawnMessage(sender, "This world does not exist!",
					ChatColor.RED);
			return false;
		}

		if (ConfigBuffer.MobMap.containsKey(mob)) {

			if (operation.equals("allow")) {
				cb.config.set("worlds." + w + ".creature." + mob + ".spawn", true);
				cb.plugin.sendNospawnMessage(sender,
						mob + "s are now enabled!", ChatColor.GREEN);
			} else if (operation.equals("deny")) {
				cb.config.set("worlds." + w + ".creature." + mob + ".spawn", false);
				cb.plugin.sendNospawnMessage(sender, mob
						+ "s are now disabled!", ChatColor.GREEN);
			} else if (operation.equals("moblimit")) {
				cb.config.set("worlds." + w + ".creature." + mob + ".Limit", Integer.parseInt(args[3]));
				cb.plugin.sendNospawnMessage(sender, mob
						+ "s are now limited to " + args[3] + " !",
						ChatColor.GREEN);
			}

			saveConfig();

			cb.readConfig();

			return true;

		} else {
			cb.plugin.sendNospawnMessage(sender, "Creature " + args[2]
					+ " does not exist!", ChatColor.RED);
			return false;
		}
	}

	private boolean setConf2(CommandSender sender, String[] args,
			String operation) {

		String w = args[1];

		if (this.server.getWorld(args[1]) == null) {

			cb.plugin.sendNospawnMessage(sender, "This world does not exist!",
					ChatColor.RED);
			return false;
		}
		if (operation.equals("totalmoblimit")) {
			cb.config.set("worlds." + w + ".properties.TotalMobLimit",
					Integer.parseInt(args[2]));
			cb.plugin.sendNospawnMessage(sender, "Mobs are now limited to "
					+ args[2] + " !", ChatColor.GREEN);
		}

		saveConfig();

		cb.readConfig();

		return true;
	}

	private boolean checkPerm(CommandSender sender, String perm) {

		Player player;

		if (sender instanceof Player) {
			player = (Player) sender;

			if (cb.Permissions == null) {
				if (!player.isOp()) {
					player.sendMessage(ChatColor.RED
							+ "You don't have the permissions to do that!");
					return false;
				}
			} else {

				if (!(cb.Permissions.has(player, "nospawn." + perm))) {
					player.sendMessage(ChatColor.RED
							+ "You don't have the permissions to do that!");
					return false;
				}
			}

			return true;

		} else {

			return true;
		}
	}

	private boolean isInt(String args) {

		try {
			Integer.parseInt(args);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

    private boolean addBlacklistElement(String path, int blockId)
    {
        
        List<Integer> blacklistList = loadBlacklist(path);

        if(blacklistList == null){

            blacklistList = new ArrayList<Integer>();

        }
        
        if(!blacklistList.contains(blockId)){
            
            blacklistList.add(blockId);
            
            saveBlacklist(path,blacklistList);
            
        } else {
            
            return false;
            
        }
        
        return true;
    }
    
    private  boolean deleteBlacklistElement(String path, int blockId)
    {
        List<Integer> blacklistList = loadBlacklist(path);

        int idx;

        if(blacklistList == null){

            blacklistList = new ArrayList<Integer>();

        }

        if(blacklistList.contains(blockId)){

            idx = blacklistList.indexOf(blockId);
            blacklistList.remove(idx);

            saveBlacklist(path,blacklistList);

        } else {

            return false;

        }
        
        return true;
        
    }
    
    private void saveBlacklist(String path, List<Integer> blacklistList)
    {

        if(blacklistList != null){
            Iterator<Integer> blockedIds = blacklistList.iterator();
            String blacklistString = "";
            Integer IntBuffer;
            while(blockedIds.hasNext()){
                
                IntBuffer = blockedIds.next();

                if(blockedIds.hasNext()){

                    blacklistString += IntBuffer.toString() + ";";

                } else {

                    blacklistString += IntBuffer.toString();

                }
            }

            cb.config.set(path, blacklistString);

        } else {

        cb.config.set(path,"");

        }
        saveConfig();
        cb.readConfig();
        
    }
    
    private List<Integer> loadBlacklist(String path){
        
        List<Integer> blacklistList = new ArrayList<Integer>();
        String blacklistString = cb.config.getString(path,"");
        String[] blacklistArray;
        
        if(blacklistString.equals("")){
            return null;
        }
        
        blacklistArray = blacklistString.split(";");
        
        for(String s:blacklistArray){
            
            blacklistList.add(Integer.parseInt(s));
        }

        return blacklistList;
        
    }
    
    
    private void saveConfig()
    {
       // try
       // {
		cb.plugin.saveConfig();
      //  }
        //catch(IOException io){
       // }
    }

}

