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

package me.xXLupoXx.NoSpawn.Util;

import me.xXLupoXx.NoSpawn.NoSpawn;

import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;

import java.util.*;

public class ConfigBuffer {
	public Map<org.bukkit.World, Spawns> worldSpawns = new HashMap<org.bukkit.World, Spawns>();
	public static Map<String, EntityType> MobMap = new HashMap<String, EntityType>();
    public static boolean BukkitPerm = false;
    public static boolean Debugmode = false;
    public static boolean sendMetrics = true;
	static
	{
		MobMap.put("Sheep", EntityType.SHEEP);
		MobMap.put("Pig", EntityType.PIG);
		MobMap.put("Cow", EntityType.COW);
		MobMap.put("Chicken", EntityType.CHICKEN);
		MobMap.put("Squid", EntityType.SQUID);
		MobMap.put("Zombie_Pigman", EntityType.PIG_ZOMBIE);
		MobMap.put("Wolf", EntityType.WOLF);
		MobMap.put("Zombie", EntityType.ZOMBIE);
		MobMap.put("Skeleton", EntityType.SKELETON);
		MobMap.put("Spider", EntityType.SPIDER);
		MobMap.put("Creeper", EntityType.CREEPER);
		MobMap.put("Slime", EntityType.SLIME);
		MobMap.put("Ghast", EntityType.GHAST);
		MobMap.put("Giant", EntityType.GIANT);
        MobMap.put("Enderman", EntityType.ENDERMAN);
        MobMap.put("Mooshroom", EntityType.MUSHROOM_COW);
        MobMap.put("Villager", EntityType.VILLAGER);
        MobMap.put("Blaze", EntityType.BLAZE);
        MobMap.put("Cave_Spider", EntityType.CAVE_SPIDER);
        MobMap.put("Silverfish", EntityType.SILVERFISH);
        MobMap.put("Enderdragon", EntityType.ENDER_DRAGON);
        MobMap.put("Magma_Cube", EntityType.MAGMA_CUBE);
        MobMap.put("Ocelot",EntityType.OCELOT);
        MobMap.put("Snowman",EntityType.SNOWMAN);
        MobMap.put("Iron_Golem",EntityType.IRON_GOLEM);

	}

	public NoSpawn plugin;
	public int CountTimer;
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
                config.set("worlds." + w.getName() + ".creature." + Buffer + ".UseGlobalBlockBlacklist", true);
            }
            config.set("worlds." + w.getName() + ".properties.TotalMobLimit", 0);
            config.set("worlds." + w.getName() + ".properties.GlobalBlockBlacklist", "");
		}
        config.set("properties.RefreshTimer", 60000);
        config.set("properties.UseBukkitPermissions",false);
        config.set("properties.sendMetrics",true);
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
            config.set("worlds." + w.getName() + ".creature." + Buffer + ".UseGlobalBlockBlacklist", true);
        }
        config.set("worlds." + w.getName() + ".properties.TotalMobLimit", 0);
        config.set("worlds." + w.getName() + ".properties.GlobalBlockBlacklist", "");
		saveConfig();
	}

	public void readConfig() {

		String Buffer;

		for (World w : plugin.getServer().getWorlds()) {
			Iterator<String> Mobs = MobMap.keySet().iterator();



			if (config.get("worlds") != null) {
				while (Mobs.hasNext()) {
					Buffer = Mobs.next();
					this.worldSpawns.get(w).SpawnAllowed.put(MobMap.get(Buffer), config.getBoolean("worlds." + w.getName()+ ".creature." + Buffer + ".spawn", true));
					this.worldSpawns.get(w).BlockBlacklist.put(	MobMap.get(Buffer), getBlacklist(config.getString("worlds." + w.getName() + ".creature."+ Buffer + ".BlockBlacklist", "")));
                    this.worldSpawns.get(w).UseGlobalBlockBlacklist.put(MobMap.get(Buffer), config.getBoolean("worlds." + w.getName() + ".creature."+ Buffer + ".UseGlobalBlockBlacklist", true));
					this.worldSpawns.get(w).MobLimit.put(MobMap.get(Buffer),config.getInt("worlds." + w.getName()+ ".creature." + Buffer + ".Limit", 0));
				}

                this.worldSpawns.get(w).GlobalBlockBlacklist = getBlacklist(config.getString("worlds." + w.getName() + ".properties.GlobalBlockBlacklist", ""));

				this.worldSpawns.get(w).TotalMobLimit = config.getInt("worlds." + w.getName()+ ".properties.TotalMobLimit", 0);

			}
		}

        this.CountTimer = config.getInt("properties.RefreshTimer",20000);
        BukkitPerm = config.getBoolean("properties.UseBukkitPermissions",false);
        sendMetrics = config.getBoolean("properties.sendMetrics",true);

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
                    if(config.get("worlds." + w.getName() + ".creature." + Buffer + ".UseGlobalBlockBlacklist") == null){
                        config.set("worlds." + w.getName() + ".creature." + Buffer + ".UseGlobalBlockBlacklist", true);
                    }
                }

                if(config.get("worlds." + w.getName() + ".properties.TotalMobLimit") == null){
                   config.set("worlds." + w.getName() + ".properties.TotalMobLimit", 0);
                }
                if(config.get("worlds." + w.getName() + ".properties.GlobalBlockBlacklist") == null){
                   config.set("worlds." + w.getName() + ".properties.GlobalBlockBlacklist", "");
                }
            }
            if(config.get("properties.RefreshTimer") == null){
                config.set("properties.RefreshTimer", 60000);
            }
            if(config.get("properties.UseBukkitPermissions")==null){
                config.set("properties.UseBukkitPermissions",false);
            }
            if(config.get("properties.sendMetrics")== null){
                config.set("properties.sendMetrics",true);
            }

            saveConfig();

    }
}