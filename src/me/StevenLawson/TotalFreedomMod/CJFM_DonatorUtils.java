package me.StevenLawson.TotalFreedomMod;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class CJFM_DonatorUtils
{
  public static ArrayList getAvailableWorlds()
  {
    ArrayList availableWorlds = new ArrayList();
    for (World world : TotalFreedomMod.server.getWorlds())
    {
      if (((!world.getName().equals("doantorworld"))))
      {
        availableWorlds.add(world);
      }
    }
    return availableWorlds;
  }
}