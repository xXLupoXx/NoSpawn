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
	boolean hasPermission = false;

	public CommandHandler(Server server, ConfigBuffer cb) {
		this.server = server;
		this.cb = cb;
		this.config = cb.plugin.getConfiguration();
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

		config.setProperty("properties.RefreshTimer", Integer.parseInt(args[1]));
		cb.plugin.sendNospawnMessage(sender, "Timer set to " + args[1]
				+ " milliseconds!", ChatColor.GREEN);
		config.save();
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

		List<Entity> le = new LinkedList<Entity>();

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

			for (int i = 0; i < le.size(); i++) {

				if (le.get(i) instanceof Pig) {
					le.get(i).remove();
					killcount++;
					;
				}
			}
			cb.plugin.sendNospawnMessage(sender, "Removed " + killcount
					+ " Pigs from " + w, ChatColor.GREEN);
			return true;

		} else if (mob.equals("sheep")) {

			for (int i = 0; i < le.size(); i++) {

				if (le.get(i) instanceof Sheep) {
					le.get(i).remove();
					killcount++;
				}
			}
			cb.plugin.sendNospawnMessage(sender, "Removed " + killcount
					+ " Sheeps from " + w, ChatColor.GREEN);
			return true;

		} else if (mob.equals("cow")) {

			for (int i = 0; i < le.size(); i++) {

				if (le.get(i) instanceof Cow) {
					le.get(i).remove();
					killcount++;
				}
			}
			cb.plugin.sendNospawnMessage(sender, "Removed " + killcount
					+ " Cows from " + w, ChatColor.GREEN);
			return true;

		} else if (mob.equals("chicken")) {

			for (int i = 0; i < le.size(); i++) {

				if (le.get(i) instanceof Chicken) {
					le.get(i).remove();
					killcount++;
				}
			}
			cb.plugin.sendNospawnMessage(sender, "Removed " + killcount
					+ " Chickens from " + w, ChatColor.GREEN);
			return true;

		} else if (mob.equals("zombie_pigman")) {

			for (int i = 0; i < le.size(); i++) {

				if (le.get(i) instanceof PigZombie) {
					le.get(i).remove();
					killcount++;
				}
			}
			cb.plugin.sendNospawnMessage(sender, "Removed " + killcount
					+ " Zombie Pigmen from " + w, ChatColor.GREEN);
			return true;

		} else if (mob.equals("squid")) {

			for (int i = 0; i < le.size(); i++) {

				if (le.get(i) instanceof Squid) {
					le.get(i).remove();
					killcount++;
				}
			}
			cb.plugin.sendNospawnMessage(sender, "Removed " + killcount
					+ " Squids from " + w, ChatColor.GREEN);
			return true;

		} else if (mob.equals("wolf")) {

			for (int i = 0; i < le.size(); i++) {

				if (le.get(i) instanceof Wolf) {
					le.get(i).remove();
					killcount++;
				}
			}
			cb.plugin.sendNospawnMessage(sender, "Removed " + killcount
					+ " Wolfs from " + w, ChatColor.GREEN);
			return true;

		} else if (mob.equals("zombie")) {

			for (int i = 0; i < le.size(); i++) {

				if (le.get(i) instanceof Zombie) {
					le.get(i).remove();
					killcount++;
				}
			}
			cb.plugin.sendNospawnMessage(sender, "Removed " + killcount
					+ " Zombies from " + w, ChatColor.GREEN);
			return true;

		} else if (mob.equals("skeleton")) {

			for (int i = 0; i < le.size(); i++) {

				if (le.get(i) instanceof Skeleton) {
					le.get(i).remove();
					killcount++;
				}
			}
			cb.plugin.sendNospawnMessage(sender, "Removed " + killcount
					+ " Skeletons from " + w, ChatColor.GREEN);
			return true;

		} else if (mob.equals("spider")) {

			for (int i = 0; i < le.size(); i++) {

				if (le.get(i) instanceof Spider) {
					le.get(i).remove();
					killcount++;
				}
			}
			cb.plugin.sendNospawnMessage(sender, "Removed " + killcount
					+ " Spiders from " + w, ChatColor.GREEN);
			return true;

		} else if (mob.equals("creeper")) {

			for (int i = 0; i < le.size(); i++) {

				if (le.get(i) instanceof Creeper) {
					le.get(i).remove();
					killcount++;
				}
			}
			cb.plugin.sendNospawnMessage(sender, "Removed " + killcount
					+ " Creeper from " + w, ChatColor.GREEN);
			return true;

		} else if (mob.equals("slime")) {

			for (int i = 0; i < le.size(); i++) {

				if (le.get(i) instanceof Slime) {
					le.get(i).remove();
					killcount++;
				}
			}
			cb.plugin.sendNospawnMessage(sender, "Removed " + killcount
					+ " Slimes from " + w, ChatColor.GREEN);
			return true;

		} else if (mob.equals("ghast")) {

			for (int i = 0; i < le.size(); i++) {

				if (le.get(i) instanceof Ghast) {
					le.get(i).remove();
					killcount++;
				}
			}
			cb.plugin.sendNospawnMessage(sender, "Removed " + killcount
					+ " Ghasts from " + w, ChatColor.GREEN);
			return true;

		} else if (mob.equals("giant")) {

			for (int i = 0; i < le.size(); i++) {

				if (le.get(i) instanceof Giant) {
					le.get(i).remove();
					killcount++;
				}
			}
			cb.plugin.sendNospawnMessage(sender, "Removed " + killcount
					+ " Giants from " + w, ChatColor.GREEN);
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
				config.setProperty("worlds." + w + ".creature." + mob
						+ ".spawn", true);
				cb.plugin.sendNospawnMessage(sender,
						mob + "s are now enabled!", ChatColor.GREEN);
			} else if (operation.equals("deny")) {
				config.setProperty("worlds." + w + ".creature." + mob
						+ ".spawn", false);
				cb.plugin.sendNospawnMessage(sender, mob
						+ "s are now disabled!", ChatColor.GREEN);
			} else if (operation.equals("moblimit")) {
				config.setProperty("worlds." + w + ".creature." + mob
						+ ".Limit", Integer.parseInt(args[3]));
				cb.plugin.sendNospawnMessage(sender, mob
						+ "s are now limited to " + args[3] + " !",
						ChatColor.GREEN);
			}

			config.save();

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
			config.setProperty("worlds." + w + ".properties.TotalMobLimit",
					Integer.parseInt(args[2]));
			cb.plugin.sendNospawnMessage(sender, "Mobs are now limited to "
					+ args[2] + " !", ChatColor.GREEN);
		}

		config.save();

		cb.readConfig();

		return true;
	}

	private boolean checkPerm(CommandSender sender, String perm) {

		Player player = null;

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
}
