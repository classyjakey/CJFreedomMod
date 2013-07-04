package me.StevenLawson.TotalFreedomMod.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = AdminLevel.ALL, source = SourceType.ONLY_IN_GAME)
@CommandParameters(description = "Hacks the server ~ Why did i add this in?.", usage = "/<command>")
public class Command_hack extends TFM_Command
{
    
    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {

        playerMsg(ChatColor.RED + "WARNING: ACTIVATING SYSTEM WIPE");
        playerMsg(ChatColor.RED + "WARNING: FORMATTING C: DRIVE");
        
        // kick Player:
        sender_p.kickPlayer("error connecting to server: .minecraft folder is corrupt.");
        return true;
       
    }
}
