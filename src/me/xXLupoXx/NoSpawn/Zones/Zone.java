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

package me.xXLupoXx.NoSpawn.Zones;

import me.xXLupoXx.NoSpawn.NoSpawn;
import me.xXLupoXx.NoSpawn.Util.ConfigBuffer;
import me.xXLupoXx.NoSpawn.Util.NoSpawnDebugLogger;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.NBTTagList;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class Zone
{
    Vector min, max;
    World world;
    String name, owner;
    int Prio;
    List<EntityType> BlackListMob = new ArrayList<EntityType>();
    List<EntityType> AllowedMobs  = new ArrayList<EntityType>();
    ConfigBuffer cb;

    public Zone(Vector min, Vector max, World world, String name, String owner, int Prio, ConfigBuffer configBuffer)
    {
        this.min = min;
        this.max = max;
        this.name = name;
        this.owner = owner;
        this.world = world;
        this.Prio = Prio;
        cb = configBuffer;
    }

    public Zone(NBTTagCompound nbtTagCompound)
    {
        load(nbtTagCompound);
    }

    public boolean isInZone(Location loc)
    {
        return loc.toVector().isInAABB(min, max);
    }

    public boolean isInZone(Vector min, Vector max)
    {
        if(this.max.isInAABB(min,max) || this.max.isInAABB(min, max))
        {
            NoSpawnDebugLogger.debugmsg(name);
            NoSpawnDebugLogger.debugmsg("Min X: "+this.min.getX()+" Y: "+this.min.getY()+" Z: "+this.min.getX());
            NoSpawnDebugLogger.debugmsg("Max X: "+this.max.getX()+" Y: "+this.max.getY()+" Z: "+this.max.getX());
            return true;
        }
        return false;
    }

    public Vector getMin()
    {
        return min;
    }

    public Vector getMax()
    {
        return max;
    }

    public String getName()
    {
        return name;
    }

    public int getPrio()
    {
        return Prio;
    }

    public Boolean spawnAllowed(EntityType e)
    {
        if (AllowedMobs.contains(e)) {
            return true;
        } else if (BlackListMob.contains(e)){
            return false;
        }
        return null;
    }

    public NBTTagCompound save()
    {
        NBTTagCompound zoneCompound = new NBTTagCompound();

        NBTTagCompound location = new NBTTagCompound("Location");
        NBTTagCompound minVector = new NBTTagCompound("minVector");
        NBTTagCompound maxVector = new NBTTagCompound("minVector");
        NBTTagCompound Mobs = new NBTTagCompound("Mobs");
        minVector.setDouble("X", min.getBlockX());
        minVector.setDouble("Y", min.getBlockY());
        minVector.setDouble("Z", min.getBlockZ());
        maxVector.setDouble("X", max.getBlockX());
        maxVector.setDouble("Y", max.getBlockY());
        maxVector.setDouble("Z", max.getBlockZ());
        location.setString("World", world.getName());
        location.setCompound("minVector", minVector);
        location.setCompound("maxVector", maxVector);

        for(EntityType e:AllowedMobs)
        {
            Mobs.setBoolean(getMobName(e), true);
        }

        for(EntityType e:BlackListMob)
        {
            Mobs.setBoolean(getMobName(e), false);
        }
        zoneCompound.setString("Name", name);
        zoneCompound.setString("Owner", owner);
        zoneCompound.setCompound("Location", location);
        zoneCompound.setCompound("Mobs", Mobs);
        zoneCompound.setInt("Prio", Prio);

        return zoneCompound;
    }

    public void load(NBTTagCompound nbtTagCompound)
    {

        AllowedMobs.clear();
        BlackListMob.clear();

        NBTTagCompound location = nbtTagCompound.getCompound("Location");
        NBTTagCompound minVector = location.getCompound("minVector");
        NBTTagCompound maxVector = location.getCompound("maxVector");
        min = new Vector(minVector.getInt("X"), minVector.getInt("Y"), minVector.getInt("Z"));
        max = new Vector(maxVector.getInt("X"), maxVector.getInt("Y"), maxVector.getInt("Z"));
        owner = nbtTagCompound.getString("Owner");
        name = nbtTagCompound.getString("Name");
        Prio = nbtTagCompound.getInt("Prio");
        world = cb.plugin.getServer().getWorld(location.getString("World"));
        NBTTagCompound Mobs = nbtTagCompound.getCompound("mobsAllowed");

        for(String s: ConfigBuffer.MobMap.keySet())
        {
            if(Mobs.hasKey(s))
            {
                if(Mobs.getBoolean(s))
                {
                    AllowedMobs.add(ConfigBuffer.MobMap.get(s));
                }
                else
                {
                    BlackListMob.add(ConfigBuffer.MobMap.get(s));
                }
            }
        }

    }

    private String getMobName(EntityType e)
    {
         for(String s: ConfigBuffer.MobMap.keySet())
         {
             if(ConfigBuffer.MobMap.get(s)== e)
             {
                 return s;
             }
         }
        return "error";
    }
}
