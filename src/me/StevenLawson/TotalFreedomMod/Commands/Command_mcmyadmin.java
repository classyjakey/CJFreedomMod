package me.StevenLawson.TotalFreedomMod.Commands;

import me.StevenLawson.TotalFreedomMod.TFM_Util;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = AdminLevel.SENIOR, source = SourceType.ONLY_CONSOLE)
@CommandParameters(description = "Runs the cleanup system.", usage = "/<command>")
public class Command_mcmyadmin extends TFM_Command
{
    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        TFM_Util.bcastMsg(ChatColor.RED + "Starting Midnightly Clean Up - Expect Lag");

        if (senderIsConsole)
        {
            server.dispatchCommand(sender, "nonuke off");
            server.dispatchCommand(sender, "opall");
            server.dispatchCommand(sender, "clearall");
            server.dispatchCommand(sender, "backup");
            server.dispatchCommand(sender, "tfipbanlist purge");
            server.dispatchCommand(sender, "tfbanlist purge");
        TFM_Util.bcastMsg(ChatColor.GREEN + "Midnightly Clean Up Completed. Reloading Server");
            server.dispatchCommand(sender, "reload");
        }

        return true;
    }
}
