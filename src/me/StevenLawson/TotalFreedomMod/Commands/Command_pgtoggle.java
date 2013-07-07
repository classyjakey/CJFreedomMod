package me.StevenLawson.TotalFreedomMod.Commands;

import me.StevenLawson.TotalFreedomMod.TotalFreedomMod;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

@CommandPermissions(level = AdminLevel.SUPER, source = SourceType.BOTH)
@CommandParameters(description = "To enable or disable Portalgun ~ Thanks Dartheh <3.", usage = "/<command>")
public class Command_pgtoggle extends TFM_Command
{
    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {	
    	boolean toggled = false;
    	boolean enabled = true;
    	for(Plugin p : TotalFreedomMod.server.getPluginManager().getPlugins()) {
    		if(p.getName().equalsIgnoreCase("PortalGun")) {
    			if(p.isEnabled()) {
    				p.getPluginLoader().disablePlugin(p);
    				enabled = false;
    			} else {
    				p.getPluginLoader().enablePlugin(p);
    				enabled = true;
    			}
    			toggled = true;
    		}
    	}
    	if(toggled) {
    		if(!enabled) {
    			TotalFreedomMod.server.broadcastMessage(ChatColor.RED + sender.getName() + " - Disabling PortalGun");
    		} else {
    			TotalFreedomMod.server.broadcastMessage(ChatColor.GREEN + sender.getName() + " - Enabling PortalGun");
    		}
    	}
        return true;
    }
}