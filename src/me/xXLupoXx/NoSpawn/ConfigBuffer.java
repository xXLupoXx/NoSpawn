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

import org.bukkit.World;
import org.bukkit.entity.CreatureType;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import ru.tehkode.permissions.PermissionManager;

import java.util.*;

public class ConfigBuffer {
	public Map<org.bukkit.World, Spawns> worldSpawns = new HashMap<org.bukkit.World, Spawns>();
	public static Map<String, CreatureType> MobMap = new HashMap<String, CreatureType>();
	static
	{
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
        MobMap.put("Enderman", CreatureType.ENDERMAN);
        MobMap.put("Mooshroom", CreatureType.MUSHROOM_COW);
        MobMap.put("Villager", CreatureType.VILLAGER);
        MobMap.put("Blaze", CreatureType.BLAZE);
        MobMap.put("Cave_Spider", CreatureType.CAVE_SPIDER);
        MobMap.put("Silverfish", CreatureType.SILVERFISH);
        MobMap.put("Enderdragon", CreatureType.ENDER_DRAGON);
	}
	public PermissionManager Permissions;
	public NoSpawn plugin;
	public int CountTimer;
	//public Configuration config;
    public FileConfiguration config;

	public ConfigBuffer(NoSpawn plugin) {
		config = plugin.getConfig();
		this.plugin = plugin;
	}

	public void setupConfig() {

        config.set("version", plugin.getDescription().getVersion());

		for (World w : plugin.getServer().getWorlds()) {
			String Buffer;

            for (String s : MobMap.keySet())
            {
                Buffer = s;
                config.set("worlds." + w.getName() + ".creature." + Buffer + ".spawn", true);
                config.set("worlds." + w.getName() + ".creature." + Buffer + ".BlockBlacklist", "");
                config.set("worlds." + w.getName() + ".creature." + Buffer + ".Limit", 0);
            }
            config.set("worlds." + w.getName() + ".properties.TotalMobLimit", 0);
		}
        config.set("properties.RefreshTimer", 60000);
        saveConfig();
	}

	public void addWorldToConfig(World w) {
		
		String Buffer;
        for (String s : MobMap.keySet())
        {
            Buffer = s;
            config.set("worlds." + w.getName() + ".creature." + Buffer + ".spawn", true);
            config.set("worlds." + w.getName() + ".creature." + Buffer + ".BlockBlacklist", "");
            config.set("worlds." + w.getName() + ".creature." + Buffer + ".Limit", 0);
        }
        config.set("worlds." + w.getName() + ".properties.TotalMobLimit", 0);
		saveConfig();
	}

	public void readConfig() {

		String Buffer;

		for (World w : plugin.getServer().getWorlds()) {
			Iterator<String> Mobs = MobMap.keySet().iterator();

			if (config.get("worlds") != null) {
				while (Mobs.hasNext()) {
					Buffer = Mobs.next();
					this.worldSpawns.get(w).SpawnAllowed.put(
							MobMap.get(Buffer),
							config.getBoolean("worlds." + w.getName()
									+ ".creature." + Buffer + ".spawn", true));
					this.worldSpawns.get(w).BlockBlacklist.put(
							MobMap.get(Buffer),
							getBlacklist(config.getString(
									"worlds." + w.getName() + ".creature."
											+ Buffer + ".BlockBlacklist", "")));

					if (config.get("worlds." + w.getName()
							+ ".creature." + Buffer + ".Limit") != null) {

						this.worldSpawns.get(w).MobLimit.put(
								MobMap.get(Buffer),
								config.getInt("worlds." + w.getName()
										+ ".creature." + Buffer + ".Limit", 0));

					} else {

						config.set("worlds." + w.getName()
								+ ".creature." + Buffer + ".Limit", 0);
						saveConfig();
						this.worldSpawns.get(w).MobLimit.put(
								MobMap.get(Buffer),
								config.getInt("worlds." + w.getName()
										+ ".creature." + Buffer + ".Limit", 0));
					}

				}

				if (config.get("worlds." + w.getName()
						+ ".properties.TotalMobLimit") != null) {

					this.worldSpawns.get(w).TotalMobLimit = config.getInt(
							"worlds." + w.getName()
									+ ".properties.TotalMobLimit", 0);

				} else {

					config.set("worlds." + w.getName()
							+ ".properties.TotalMobLimit", 0);
					saveConfig();
					this.worldSpawns.get(w).TotalMobLimit = config.getInt(
							"worlds." + w.getName()
									+ ".properties.TotalMobLimit", 0);
				}
				if (config.get("properties.RefreshTimer") != null) {

					this.CountTimer = config.getInt("properties.RefreshTimer",
							20000);

				} else {

					config.set("properties.RefreshTimer", 60000);
					saveConfig();
					this.CountTimer = config.getInt(
							"properties.RefreshTimer", 60000);
				}

			}
		}

	}

	private List<Integer> getBlacklist(String args) {
		if (!args.isEmpty()) {
			String[] arr = args.split(";");
			List<Integer> tmplist = new LinkedList<Integer>();

            for (String anArr : arr)
            {
                tmplist.add(Integer.parseInt(anArr.trim()));
            }

			return tmplist;
		}

		return null;
	}


    private void saveConfig()
    {
    //    try
        //{
		//config.save(config.getCurrentPath());
        plugin.saveConfig();
       // }
      //  catch(IOException io){
       // }
    }

    public void checkConfig()
    {
            repairConfig();
    }

    public void repairConfig()
    {
        String Buffer;
        config.set("version",plugin.getDescription().getVersion());
            for (World w : plugin.getServer().getWorlds()) {

                for (String s : MobMap.keySet())
                {
                    Buffer = s;
                    if (config.get("worlds." + w.getName() + ".creature." + Buffer + ".spawn") == null){
                        config.set("worlds." + w.getName() + ".creature." + Buffer + ".spawn", true);
                    }
                    if(config.get("worlds." + w.getName() + ".creature." + Buffer + ".BlockBlacklist") == null){
                        config.set("worlds." + w.getName() + ".creature." + Buffer + ".BlockBlacklist", "");
                    }
                    if(config.get("worlds." + w.getName() + ".creature." + Buffer + ".Limit") == null ){
                        config.set("worlds." + w.getName() + ".creature." + Buffer + ".Limit", 0);
                    }
                }

                if(config.get("worlds." + w.getName() + ".properties.TotalMobLimit") == null){
                   config.set("worlds." + w.getName() + ".properties.TotalMobLimit", 0);
                }
            }
            if(config.get("properties.RefreshTimer") == null){
                config.set("properties.RefreshTimer", 60000);
            }
            saveConfig();

    }
}