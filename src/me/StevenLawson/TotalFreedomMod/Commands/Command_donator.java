package me.StevenLawson.TotalFreedomMod.Commands;

import me.StevenLawson.TotalFreedomMod.TFM_Donator;
import me.StevenLawson.TotalFreedomMod.TFM_DonatorList;
import me.StevenLawson.TotalFreedomMod.TFM_Util;
import me.StevenLawson.TotalFreedomMod.TotalFreedomMod;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


@CommandPermissions(level = AdminLevel.OP, source = SourceType.BOTH)
@CommandParameters(description = "Manage donators.", usage = "/<command> <list | <add|delete|info> <username>>")
public class Command_donator extends TFM_Command
{
    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        if (args.length == 1)
        {
            if (args[0].equals("list"))
            {
                playerMsg("Donators: " + StringUtils.join(TFM_DonatorList.getDonatorNames(), ", "), ChatColor.GOLD);
            }
            else
            {
                if (!senderIsConsole)
                {
                    playerMsg("This command may only be used from the console.");
                    return true;
                }
                else
                {
                    return false;
                }
            }

            return true;
        }
        else if (args.length == 2)
        {
            if (args[0].equalsIgnoreCase("info"))
            {
                if (!TFM_DonatorList.isUserDonator(sender))
                {
                    playerMsg(TotalFreedomMod.MSG_NO_PERMS);
                    return true;
                }

                TFM_Donator donator = TFM_DonatorList.getDonatorEntry(args[1].toLowerCase());

                if (donator == null)
                {
                    try
                    {
                        donator = TFM_DonatorList.getDonatorEntry(getPlayer(args[1]).getName().toLowerCase());
                    }
                    catch (PlayerNotFoundException ex)
                    {
                    }
                }

                if (donator == null)
                {
                    playerMsg("Donator not found: " + args[1]);
                }
                else
                {
                    playerMsg(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', donator.toString())));
                }

                return true;
            }

            if (!senderIsConsole)
            {
                playerMsg("This command may only be used from the console.");
                return true;
            }

            if (args[0].equalsIgnoreCase("add"))
            {
                if (!sender.getName().equalsIgnoreCase("CONSOLE") || sender.getName().equalsIgnoreCase("Rcon"))
                {
                    playerMsg(TotalFreedomMod.MSG_NO_PERMS);
                    return true;
                }
                
                Player p = null;
                String donator_name = null;

                try
                {
                    p = getPlayer(args[1]);
                }
                catch (PlayerNotFoundException ex)
                {
                    TFM_Donator donator = TFM_DonatorList.getDonatorEntry(args[1].toLowerCase());
                    if (donator != null)
                    {
                        donator_name = donator.getName();
                    }
                    else
                    {
                        playerMsg(ex.getMessage(), ChatColor.RED);
                        return true;
                    }
                }

                if (p != null)
                {
                    TFM_Util.adminAction(sender.getName(), "Adding " + p.getName() + " to the donators list.", true);
                    TFM_DonatorList.addDonator(p);
                }
                else if (donator_name != null)
                {
                    TFM_Util.adminAction(sender.getName(), "Adding " + donator_name + " to the donators list.", true);
                    TFM_DonatorList.addDonator(donator_name);
                }
            }
            else if (args[0].equalsIgnoreCase("delete") || args[0].equalsIgnoreCase("del") || args[0].equalsIgnoreCase("remove"))
            {
                if (!sender.getName().equalsIgnoreCase("CONSOLE") || sender.getName().equalsIgnoreCase("Rcon"))
                {
                    playerMsg(TotalFreedomMod.MSG_NO_PERMS);
                    return true;
                }

                String target_name = args[1];

                try
                {
                    target_name = getPlayer(target_name).getName();
                }
                catch (PlayerNotFoundException ex)
                {
                }

                if (!TFM_DonatorList.getDonatorNames().contains(target_name.toLowerCase()))
                {
                    playerMsg("Donator not found: " + target_name);
                    return true;
                }

                TFM_Util.adminAction(sender.getName(), "Removing " + target_name + " from the donator list", true);
                TFM_DonatorList.removeDonator(target_name);
            }
            else
            {
                return false;
            }

            return true;
        }
        else
        {
            return false;
        }
    }
}
