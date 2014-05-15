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

package de.xXLupoXx.NoSpawn.Util;

import de.xXLupoXx.NoSpawn.NoSpawn;

import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;


import java.util.*;

public class ConfigBuffer {
	public Map<World, Spawns> worldSpawns = new HashMap<org.bukkit.World, Spawns>();
	public static Map<String, EntityType> MobMap = new HashMap<String, EntityType>();
    public static boolean BukkitPerm = false;
    public static boolean Debugmode = false;
    public static boolean sendMetrics = true;
    public static boolean hasMyPet = false;

    public Map<World,Boolean> allowEggSpawn = new HashMap<World, Boolean>();   //Note: allow becuase it cancels the mob check
    public Map<World,Boolean> allowSpawnerSapwn = new HashMap<World, Boolean>();
    public Map<World,Boolean> denyJockeySpawn = new HashMap<World, Boolean>();
	static
	{
        for(EntityType ET : EntityType.values())
        {
            if(ET.isAlive() && ET != EntityType.PLAYER)
            {
                MobMap.put(ET.getName(),ET);
            }
        }
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
            config.set("worlds." + w.getName() + ".properties.AllowEggSpawn", false);
            config.set("worlds." + w.getName() + ".properties.AllowSpawnerSpawn", false);
            config.set("worlds." + w.getName() + ".properties.DenyJockeySpawn", false);
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
        config.set("worlds." + w.getName() + ".properties.AllowEggSpawn", false);
        config.set("worlds." + w.getName() + ".properties.AllowSpawnerSpawn", false);
        config.set("worlds." + w.getName() + ".properties.DenyJockeySpawn", false);
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
                this.allowEggSpawn.put(w, config.getBoolean("worlds." + w.getName() + ".properties.AllowEggSpawn", false));
                this.allowSpawnerSapwn.put(w, config.getBoolean("worlds." + w.getName() + ".properties.AllowSpawnerSpawn", false));
                this.denyJockeySpawn.put(w, config.getBoolean("worlds." + w.getName() + ".properties.DenyJockeySpawn", false));
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
        if (config.getString("version").contains("1.8") || config.getString("version").contains("1.7")) convertConfig();

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
                if(config.get("worlds." + w.getName() + ".properties.AllowEggSpawn") == null){
                    config.set("worlds." + w.getName() + ".properties.AllowEggSpawn", false);
                }
                if(config.get("worlds." + w.getName() + ".properties.AllowSpawnerSpawn") == null) {
                    config.set("worlds." + w.getName() + ".properties.AllowSpawnerSpawn",false);
                }
                if(config.get("worlds." + w.getName() + ".properties.DenyJockeySpawn") == null) {
                    config.set("worlds." + w.getName() + ".properties.DenyJockeySpawn",false);
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

    private void convertConfig(){
        
        readConfig();
        loadOldData();
        config.set("worlds",null);
        saveConfig();
        
        for (World w : plugin.getServer().getWorlds()) {

            for (String s : MobMap.keySet())
            {
                config.set("worlds." + w.getName() + ".creature." + s + ".spawn", this.worldSpawns.get(w).SpawnAllowed.get(MobMap.get(s)));
                saveBlacklist("worlds." + w.getName() + ".creature." + s + ".BlockBlacklist",this.worldSpawns.get(w).BlockBlacklist.get(MobMap.get(s)),false);
                config.set("worlds." + w.getName() + ".creature." + s + ".Limit", this.worldSpawns.get(w).MobLimit.get(MobMap.get(s)));
                config.set("worlds." + w.getName() + ".creature." + s + ".UseGlobalBlockBlacklist", this.worldSpawns.get(w).UseGlobalBlockBlacklist.get(MobMap.get(s)));
            }
            config.set("worlds." + w.getName() + ".properties.TotalMobLimit", this.worldSpawns.get(w).TotalMobLimit);
            saveBlacklist("worlds." + w.getName() + ".properties.GlobalBlockBlacklist",this.worldSpawns.get(w).GlobalBlockBlacklist,false);
        }
        saveConfig();
    }
    
    private void loadOldData(){
        for (World w : plugin.getServer().getWorlds()) {
            if(config.get("worlds." + w.getName()+ ".creature.Mooshroom") != null) ReadOldData("MushroomCow","Mooshroom",w);
            if(config.get("worlds." + w.getName()+ ".creature.Magma_Cube") != null) ReadOldData("LavaSlime","Magma_Cube",w);
            if(config.get("worlds." + w.getName()+ ".creature.Zombie_Pigman") != null) ReadOldData("PigZombie","Zombie_Pigman",w);
            if(config.get("worlds." + w.getName()+ ".creature.Cave_Spider") != null) ReadOldData("CaveSpider","Cave_Spider",w);
            if(config.get("worlds." + w.getName()+ ".creature.Iron_Golem") != null) ReadOldData("VillagerGolem","Iron_Golem",w);
            if(config.get("worlds." + w.getName()+ ".creature.Enderdragon") != null) ReadOldData("EnderDragon","Enderdragon",w);
            if(config.get("worlds." + w.getName()+ ".creature.Snowman") != null) ReadOldData("SnowMan","Snowman",w);
            if(config.get("worlds." + w.getName()+ ".creature.Ocelot") != null) ReadOldData("Ozelot","Ocelot",w);
        }
    }
    
    private void ReadOldData(String NewMob, String OldMob, World w){
          
            this.worldSpawns.get(w).SpawnAllowed.put(MobMap.get(NewMob), config.getBoolean("worlds." + w.getName()+ ".creature." + OldMob + ".spawn", true));
            this.worldSpawns.get(w).BlockBlacklist.put(	MobMap.get(NewMob), getBlacklist(config.getString("worlds." + w.getName() + ".creature."+ OldMob + ".BlockBlacklist", "")));
            this.worldSpawns.get(w).UseGlobalBlockBlacklist.put(MobMap.get(NewMob), config.getBoolean("worlds." + w.getName() + ".creature."+ OldMob + ".UseGlobalBlockBlacklist", true));
            this.worldSpawns.get(w).MobLimit.put(MobMap.get(NewMob),config.getInt("worlds." + w.getName()+ ".creature." + OldMob + ".Limit", 0));
    }

    public void saveBlacklist(String path, List<Integer> blacklistList, boolean saveFlag)
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

            config.set(path, blacklistString);

        } else {

            config.set(path,"");

        }
        if(saveFlag) {
            
        saveConfig();
        readConfig();
            
        }

    }
    
}