package me.StevenLawson.TotalFreedomMod;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class TFM_DonatorWorld
{
    private static final long CACHE_CLEAR_FREQUENCY = 30L * 1000L; //30 seconds, milliseconds
    private static final long TP_COOLDOWN_TIME = 500L; //0.5 seconds, milliseconds
    private static final String GENERATION_PARAMETERS = "16,stone,32,dirt,1,grass";
    private static final String DONATORWORLD_NAME = "donatorworld";
    //
    private final Map<Player, Long> teleportCooldown = new HashMap<Player, Long>();
    private final Map<CommandSender, Boolean> superadminCache = new HashMap<CommandSender, Boolean>();
    //
    private Long cacheLastCleared = null;
    private World donatorworld = null;

    private TFM_DonatorWorld()
    {
    }

    public void sendToDonatorWorld(Player player)
    {
        if (!TFM_DonatorList.isUserDonator(player))
        {
            return;
        }
        
         if (TFM_Util.SYSADMINS.contains(p.getName()))
        {
            return;
        }

        player.teleport(getDonatorWorld().getSpawnLocation());
    }

    public boolean validateMovement(PlayerMoveEvent event)
    {
        if (donatorworld != null)
        {
            if (event.getTo().getWorld() == donatorworld)
            {
                final Player player = event.getPlayer();
                if (!cachedIsUserSuperadmin(player))
                {
                    Long lastTP = teleportCooldown.get(player);
                    long currentTimeMillis = System.currentTimeMillis();
                    if (lastTP == null || lastTP.longValue() + TP_COOLDOWN_TIME <= currentTimeMillis)
                    {
                        teleportCooldown.put(player, currentTimeMillis);
                        TFM_Log.info(player.getName() + " attempted to access the DonatorWorld.");
                        new BukkitRunnable()
                        {
                            @Override
                            public void run()
                            {
                                player.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
                            }
                        }.runTaskLater(TotalFreedomMod.plugin, 1L);
                    }
                    event.setCancelled(true);
                    return false;
                }
            }
        }
        return true;
    }

    public World getDonatorWorld()
    {
        if (donatorworld == null || !Bukkit.getWorlds().contains(donatorworld))
        {
            generateWorld();
        }

        return donatorworld;
    }

    public void wipeSuperadminCache()
    {
        cacheLastCleared = System.currentTimeMillis();
        superadminCache.clear();
    }

    private boolean cachedIsUserSuperadmin(CommandSender user)
    {
        long currentTimeMillis = System.currentTimeMillis();
        if (cacheLastCleared == null || cacheLastCleared.longValue() + CACHE_CLEAR_FREQUENCY <= currentTimeMillis)
        {
            cacheLastCleared = currentTimeMillis;
            superadminCache.clear();
        }

        Boolean cached = superadminCache.get(user);
        if (cached == null)
        {
            cached = TFM_DonatorList.isUserDonator(user);
            superadminCache.put(user, cached);
        }
        return cached;
    }

    private void generateWorld()
    {
        WorldCreator donatorworldCreator = new WorldCreator(DONATORWORLD_NAME);
        donatorworldCreator.generateStructures(false);
        donatorworldCreator.type(WorldType.NORMAL);
        donatorworldCreator.environment(World.Environment.NORMAL);
        donatorworldCreator.generator(new CleanroomChunkGenerator(GENERATION_PARAMETERS));

        donatorworld = Bukkit.getServer().createWorld(donatorworldCreator);

        donatorworld.setSpawnFlags(false, false);
        donatorworld.setSpawnLocation(0, 50, 0);

        Block welcomeSignBlock = donatorworld.getBlockAt(0, 50, 0);
        welcomeSignBlock.setType(Material.SIGN_POST);
        org.bukkit.block.Sign welcomeSign = (org.bukkit.block.Sign) welcomeSignBlock.getState();

        org.bukkit.material.Sign signData = (org.bukkit.material.Sign) welcomeSign.getData();
        signData.setFacingDirection(BlockFace.NORTH);

        welcomeSign.setLine(0, ChatColor.GREEN + "DonatorWorld");
        welcomeSign.setLine(1, ChatColor.DARK_GRAY + "---");
        welcomeSign.setLine(2, ChatColor.YELLOW + "Spawn Point");
        welcomeSign.setLine(3, ChatColor.DARK_GRAY + "---");
        welcomeSign.update();

        TFM_GameRuleHandler.commitGameRules();
    }

    public static TFM_DonatorWorld getInstance()
    {
        return TFM_DonatorWorldHolder.INSTANCE;
    }

    private static class TFM_DonatorWorldHolder
    {
        private static final TFM_DonatorWorld INSTANCE = new TFM_DonatorWorld();
    }
}
