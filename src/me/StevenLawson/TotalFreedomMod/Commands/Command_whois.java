/*package me.StevenLawson.TotalFreedomMod.Commands;

import com.earth2me.essentials.I18n;
import com.earth2me.essentials.IEssentials;
import com.earth2me.essentials.ISettings;
import com.earth2me.essentials.User;
import com.earth2me.essentials.Util;
import com.earth2me.essentials.craftbukkit.SetExpFix;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Locale;
import me.StevenLawson.MGESS.MGESS_Utils;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.earth2me.essentials.commands.EssentialsCommand;
import com.earth2me.essentials.commands.NotEnoughArgumentsException;
import org.bukkit.command.Command;
import me.StevenLawson.TotalFreedomMod.TFM_Util;
import com.earth2me.essentials.Essentials;

public class Command_whois extends TFM_Command
{
    
 
    public void run(Server server, CommandSender sender, String commandLabel, String[] args)
            throws Exception
    {
        if (args.length < 1)
        {
            throw new NotEnoughArgumentsException();
        }
        boolean showhidden = false;
        if ((sender instanceof Player))
        {
            if (TFM_Command.getPlayer(sender).isAuthorized("essentials.list.hidden"))
            {
                showhidden = true;
            }
        }
        else
        {
            showhidden = true;
        }
        String whois = args[0].toLowerCase(Locale.ENGLISH);
        int prefixLength = Util.stripFormat(this.ess.getSettings().getNicknamePrefix()).length();
        boolean foundUser = false;
        for (Player onlinePlayer : server.getOnlinePlayers())
        {
            User user = CJFM_Essentials.getUser(onlinePlayer);
            if ((!user.isHidden()) || (showhidden))
            {
                String nickName = Util.stripFormat(user.getNickname());
                if ((whois.equalsIgnoreCase(nickName)) || (whois.substring(prefixLength).equalsIgnoreCase(nickName)) || (whois.equalsIgnoreCase(user.getName())))
                {
                    foundUser = true;
                    sender.sendMessage(I18n._("whoisTop", new Object[]
                    {
                        user.getName()
                    }));
                    user.setDisplayNick();
                    sender.sendMessage(I18n._("whoisNick", new Object[]
                    {
                        user.getDisplayName()
                    }));
                    sender.sendMessage(I18n._("whoisHealth", new Object[]
                    {
                        Integer.valueOf(user.getHealth())
                    }));
                    sender.sendMessage(I18n._("whoisExp", new Object[]
                    {
                        Integer.valueOf(SetExpFix.getTotalExperience(user)), Integer.valueOf(user.getLevel())
                    }));
                    sender.sendMessage(I18n._("whoisLocation", new Object[]
                    {
                        user.getLocation().getWorld().getName(), Integer.valueOf(user.getLocation().getBlockX()), Integer.valueOf(user.getLocation().getBlockY()), Integer.valueOf(user.getLocation().getBlockZ())
                    }));

                    sender.sendMessage(I18n._("whoisIPAddress", new Object[]
                    {
                        MGESS_Utils.maskIpAddress(user.getAddress().getAddress().toString(), sender)
                    }));
                    String location = user.getGeoLocation();

                    sender.sendMessage(I18n._("whoisGeoLocation", new Object[]
                    {
                        location
                    }));
                    sender.sendMessage(I18n._("whoisGamemode", new Object[]
                    {
                        I18n._(user.getGameMode().toString().toLowerCase(Locale.ENGLISH), new Object[0])
                    }));
                    sender.sendMessage(I18n._("whoisGod", new Object[]
                    {
                        user.isGodModeEnabled() ? I18n._("true", new Object[0]) : I18n._("false", new Object[0])
                    }));
                    sender.sendMessage(I18n._("whoisOp", new Object[]
                    {
                        user.isOp() ? I18n._("true", new Object[0]) : I18n._("false", new Object[0])
                    }));
                    sender.sendMessage(I18n._("whoisFly", new Object[]
                    {
                        user.getAllowFlight() ? I18n._("true", new Object[0]) : I18n._("false", new Object[0]), user.isFlying() ? I18n._("flying", new Object[0]) : I18n._("notFlying", new Object[0])
                    }));
                    sender.sendMessage(I18n._("whoisAFK", new Object[]
                    {
                        user.isAfk() ? I18n._("true", new Object[0]) : I18n._("false", new Object[0])
                    }));
                    sender.sendMessage(I18n._("whoisJail", new Object[]
                    {
                        user.isJailed() ? I18n._("true", new Object[0]) : user.getJailTimeout() > 0L ? Util.formatDateDiff(user.getJailTimeout()) : I18n._("false", new Object[0])
                    }));

                    sender.sendMessage(I18n._("whoisMuted", new Object[]
                    {
                        user.isMuted() ? I18n._("true", new Object[0]) : user.getMuteTimeout() > 0L ? Util.formatDateDiff(user.getMuteTimeout()) : I18n._("false", new Object[0])
                    }));
                }

            }

        }

        if (!foundUser)
        {
            throw new NoSuchFieldException(I18n._("playerNotFound", new Object[0]));
        }
    }

    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}