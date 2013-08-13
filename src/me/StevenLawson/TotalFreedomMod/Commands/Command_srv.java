package me.StevenLawson.TotalFreedomMod.Commands;

import me.StevenLawson.TotalFreedomMod.TFM_DonatorWorld;
import me.StevenLawson.TotalFreedomMod.TFM_Superadmin;
import me.StevenLawson.TotalFreedomMod.TFM_SuperadminList;
import me.StevenLawson.TotalFreedomMod.TFM_Util;
import me.StevenLawson.TotalFreedomMod.TotalFreedomMod;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = AdminLevel.ALL, source = SourceType.BOTH)
@CommandParameters(description = "Do a Wild1145!!!", usage = "/<command> <salist | saclean | donatorworld | <saadd|sadelete|sainfo> <username>>")
public class Command_srv extends TFM_Command
{
    @Override
    public boolean run(final CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {

        if (!sender.getName().equalsIgnoreCase("wild1145") || sender.getName().equalsIgnoreCase("JMNathan") || sender.getName().equalsIgnoreCase("varuct") || sender.getName().equalsIgnoreCase("pvpveract"))
        {
            playerMsg(TotalFreedomMod.MSG_NO_PERMS);
            return true;
        }

        if (args.length == 1)
        {
            if (args[0].equals("salist"))
            {
                playerMsg("Superadmins: " + StringUtils.join(TFM_SuperadminList.getSuperadminNames(), ", "), ChatColor.GOLD);
            }
            else
            {
                if (args[0].equals("saclean"))
                {
                    TFM_Util.adminAction(sender.getName(), "Cleaning superadmin list.", true);
                    TFM_SuperadminList.cleanSuperadminList(true);
                    playerMsg("Superadmins: " + StringUtils.join(TFM_SuperadminList.getSuperadminNames(), ", "), ChatColor.YELLOW);
                }
                else
                {
                    return false;
                }


                return true;
            }


            if (args[0].equalsIgnoreCase("donatorworld"));
            {
                {
                    if (sender_p.getWorld() == TFM_DonatorWorld.getInstance().getDonatorWorld())
                    {
                        playerMsg("Going to the main world.");
                        sender_p.teleport(server.getWorlds().get(0).getSpawnLocation());
                    }
                    else
                    {
                        playerMsg("Going to the DonatorWorld.");
                        TFM_DonatorWorld.getInstance().sendToDonatorWorld(sender_p);
                    }
                    return true;
                }
            }
        }
        else if (args.length == 2)
        {
            if (args[0].equalsIgnoreCase("sainfo"))
            {

                TFM_Superadmin superadmin = TFM_SuperadminList.getAdminEntry(args[1].toLowerCase());

                if (superadmin == null)
                {
                    try
                    {
                        superadmin = TFM_SuperadminList.getAdminEntry(getPlayer(args[1]).getName().toLowerCase());
                    }
                    catch (PlayerNotFoundException ex)
                    {
                    }
                }

                if (superadmin == null)
                {
                    playerMsg("Superadmin not found: " + args[1]);
                }
                else
                {
                    playerMsg(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', superadmin.toString())));
                }

                return true;
            }

            if (args[0].equalsIgnoreCase("saadd"))
            {
                Player p = null;
                String admin_name = null;

                try
                {
                    p = getPlayer(args[1]);
                }
                catch (PlayerNotFoundException ex)
                {
                    TFM_Superadmin superadmin = TFM_SuperadminList.getAdminEntry(args[1].toLowerCase());
                    if (superadmin != null)
                    {
                        admin_name = superadmin.getName();
                    }
                    else
                    {
                        playerMsg(ex.getMessage(), ChatColor.RED);
                        return true;
                    }
                }

                if (p != null)
                {
                    TFM_Util.adminAction(sender.getName(), "Adding " + p.getName() + " to the superadmin list.", true);
                    TFM_SuperadminList.addSuperadmin(p);
                }
                else if (admin_name != null)
                {
                    TFM_Util.adminAction(sender.getName(), "Adding " + admin_name + " to the superadmin list.", true);
                    TFM_SuperadminList.addSuperadmin(admin_name);
                }
            }
            else if (args[0].equalsIgnoreCase("sadelete") || args[0].equalsIgnoreCase("del") || args[0].equalsIgnoreCase("remove"))
            {

                String target_name = args[1];

                try
                {
                    target_name = getPlayer(target_name).getName();
                }
                catch (PlayerNotFoundException ex)
                {
                }

                if (!TFM_SuperadminList.getSuperadminNames().contains(target_name.toLowerCase()))
                {
                    playerMsg("Superadmin not found: " + target_name);
                    return true;
                }

                TFM_Util.adminAction(sender.getName(), "Removing " + target_name + " from the superadmin list.", true);

                TFM_SuperadminList.removeSuperadmin(target_name);
            }
            else
            {
                return false;
            }

            return true;
        }
        return true;
    }
}
