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

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.util.Vector;

import java.util.*;

public class ZoneHandler
{
        public Map<World, List<Zone>> WorldZones = new HashMap<World, List<Zone>>();

        public ZoneHandler(NoSpawn plugin) {
            for (World w : plugin.getServer().getWorlds()) {
                WorldZones.put(w, new LinkedList<Zone>());
            }
        }

        public List<Zone> getZonesAt(Location loc) {
            List<Zone> zonesAt = new ArrayList<Zone>();
            if (WorldZones.get(loc.getWorld()) != null) {

                List<Zone> zonesList = WorldZones.get(loc.getWorld());
                for (Zone z : zonesList) {
                    if (z.isInZone(loc)) {
                        zonesAt.add(z);
                    }
                }
            }

            if(zonesAt.size() !=0){
                return zonesAt;
            }
            return null;
        }

        public List<Zone> getZonesAt(Vector min , Vector max, Location loc) {
            List<Zone> zonesAt = new ArrayList<Zone>();

            if (WorldZones.get(loc.getWorld()) != null) {

                List<Zone> zonesList = WorldZones.get(loc.getWorld());
                for (Zone z : zonesList) {

                    if (z.isInZone(min, max)) {
                        zonesAt.add(z);
                    }
                }
            }

            return zonesAt;
        }

        public boolean ZoneExists(Location loc, String name) {
            if (WorldZones.get(loc.getWorld()) != null) {
                List<Zone> zonesList = WorldZones.get(loc.getWorld());
                for (Zone z : zonesList) {
                    if (z.getName().equals(name)) {
                        return true;
                    }
                }
            }
            return false;
        }

        public void addAllow(String name, World w, String[] MobTypes)
        {
            Zone z = null;

            for(Zone zz:WorldZones.get(w))
            {
                if(zz.getName().equals(name))
                {
                    z = zz;
                    break;
                }
            }

            if(z != null)
            {
                EntityType e;
                for(String s:MobTypes)
                {
                    e = ConfigBuffer.MobMap.get(s);

                    if(!z.AllowedMobs.contains(e)){
                        z.AllowedMobs.add(e);
                    }
                    if(z.BlackListMob.contains(e)){
                        z.BlackListMob.remove(e);
                    }
                }
            }

        }

        public void addDeny(String name, World w, String[] MobTypes)
        {
            Zone z = null;

            for(Zone zz:WorldZones.get(w))
            {
                if(zz.getName().equals(name))
                {
                    z = zz;
                    break;
                }
            }

            if(z != null)
            {
                EntityType e;
                for(String s:MobTypes)
                {
                    e = ConfigBuffer.MobMap.get(s);

                    if(z.AllowedMobs.contains(e)){
                        z.AllowedMobs.remove(e);
                    }
                    if(!z.BlackListMob.contains(e)){
                        z.BlackListMob.add(e);
                    }
                }
            }
        }

}
