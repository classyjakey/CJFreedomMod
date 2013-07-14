package me.StevenLawson.TotalFreedomMod.Commands;

import me.StevenLawson.TotalFreedomMod.TFM_Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import static com.earth2me.essentials.I18n._;

@CommandPermissions(level = AdminLevel.SUPER, source = SourceType.BOTH)
@CommandParameters(description = "Removes all nicknames from online ", usage = "/<command>")
public class Command_denickn extends TFM_Command
{
    
    
    
    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        TFM_Util.adminAction(sender.getName(), "Running Test Command 001", false);

        for (Player p : server.getOnlinePlayers())
        {

			server.dispatchCommand(sender, "wildcard gcmd ? nick off");
			p.sendMessage(_("nickNoMore"));
        }
        

        return true;
    }
}
