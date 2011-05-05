package me.xXLupoXx.NoSpawn;

import java.util.HashMap;
import java.util.Map;

import com.nijiko.permissions.PermissionHandler;

public class ConfigBuffer
{
	public Map<org.bukkit.World , Spawns> worldSpawns = new HashMap<org.bukkit.World , Spawns>();
	public PermissionHandler Permissions;
	NoSpawn plugin;
}