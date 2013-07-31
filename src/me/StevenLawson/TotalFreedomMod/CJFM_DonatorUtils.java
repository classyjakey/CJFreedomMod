package me.StevenLawson.TotalFreedomMod;

import java.util.ArrayList;
import org.bukkit.World;

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