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

package me.xXLupoXx.NoSpawn.Listeners;

import me.xXLupoXx.NoSpawn.NoSpawn;
import me.xXLupoXx.NoSpawn.Util.ConfigBuffer;
import me.xXLupoXx.NoSpawn.Util.NoSpawnDebugLogger;

import me.xXLupoXx.NoSpawn.Zones.PlayerSelection;
import me.xXLupoXx.NoSpawn.Zones.Selection;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.util.Vector;

public class NoSpawnPlayerListener implements Listener
{
    PlayerSelection ps;
    NoSpawn plugin;

    public NoSpawnPlayerListener(NoSpawn noSpawn)
    {
        plugin = noSpawn;
        ps = plugin.getPlayerSelection();
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Vector vec;


        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (player.getItemInHand().getTypeId() == ConfigBuffer.wandID) {

                vec = player.getTargetBlock(null, 100).getLocation().toVector();
                ps.PlayerMap.get(player).pt1 = vec;
                ps.PlayerMap.get(player).w = player.getWorld();
                player.sendMessage("Point 1 set: X: "+vec.getX()+"Y: "+vec.getY()+"Z: "+vec.getZ()+ ChatColor.GREEN);

                NoSpawnDebugLogger.debugmsg("----PT1 Set----");

                if(ps.PlayerMap.get(player).pt2!= null && ps.PlayerMap.get(player).pt1 != null)
                {
                    ps.PlayerMap.get(player).createMinMax();
                }

            } else if (player.getItemInHand().getTypeId() == ConfigBuffer.readerID) {
                //TODO Display Zoneinfo
            }
        }
        if (event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            if (player.getItemInHand().getTypeId() == ConfigBuffer.wandID) {

                vec = player.getTargetBlock(null, 100).getLocation().toVector();
                ps.PlayerMap.get(player).pt2 = vec;
                ps.PlayerMap.get(player).w = player.getWorld();
                player.sendMessage("Point 2 set: X: "+vec.getX()+"Y: "+vec.getY()+"Z: "+vec.getZ()+ ChatColor.GREEN);

                NoSpawnDebugLogger.debugmsg("----PT2 Set----");

                if(ps.PlayerMap.get(player).pt2!= null && ps.PlayerMap.get(player).pt1 != null)
                {
                    ps.PlayerMap.get(player).createMinMax();
                }

            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        if(ps.PlayerMap.get(event.getPlayer()) == null)
        {
           ps.PlayerMap.put(event.getPlayer(),new Selection());
        }


    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event)
    {
         ps.PlayerMap.remove(event.getPlayer());
    }

}
