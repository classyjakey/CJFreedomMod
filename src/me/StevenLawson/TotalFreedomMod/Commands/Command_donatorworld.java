package me.StevenLawson.TotalFreedomMod.Commands;

import me.StevenLawson.TotalFreedomMod.TFM_DonatorWorld;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = AdminLevel.ALL, dlevel = DonatorLevel.STANDARD, source = SourceType.ONLY_IN_GAME)
@CommandParameters(description = "Go to the AdminWorld.", usage = "/<command>")
public class Command_donatorworld extends TFM_Command
{
    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
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
