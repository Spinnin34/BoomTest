package p.boomTest.Listener;

import org.apache.logging.log4j.util.Base64Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import p.boomTest.BoomTest;
import p.boomTest.Manager.BoomManager;
import p.boomTest.Runnable.BoomRunnable;

public class BoomListener implements Listener {

    private final BoomManager boomManager;
    private final BoomTest plugin;

    public BoomListener(BoomManager boomManager, BoomTest plugin) {
        this.boomManager = boomManager;
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        if (!boomManager.isSystemEnabled()) return;

        if (event.getEntity() instanceof TNTPrimed) {
            TNTPrimed tnt = (TNTPrimed) event.getEntity();
            Player nearestPlayer = getNearestPlayer(tnt.getLocation());

            if (nearestPlayer != null) {
                new BoomRunnable(plugin).runBoomTask(tnt, nearestPlayer);
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (!boomManager.isSystemEnabled()) return;

        Block clickedBlock = event.getClickedBlock();
        if (clickedBlock != null && clickedBlock.getType() == Material.LEVER) {
            if (isTNTAdjacent(clickedBlock)) {
                Player player = event.getPlayer();
                TNTPrimed tnt = (TNTPrimed) player.getWorld().spawn(player.getLocation(), TNTPrimed.class);
                new BoomRunnable(plugin).runBoomTask(tnt, player);
            }
        }
    }

    private boolean isTNTAdjacent(Block block) {
        for (BlockFace face : BlockFace.values()) {
            Block relativeBlock = block.getRelative(face);
            if (relativeBlock.getType() == Material.TNT) {
                relativeBlock.setType(Material.AIR);
                return true;
            }
        }
        return false;
    }

    private Player getNearestPlayer(Location location) {
        double closestDistance = Double.MAX_VALUE;
        Player closestPlayer = null;

        for (Player player : Bukkit.getOnlinePlayers()) {
            double distance = player.getLocation().distance(location);
            if (distance < closestDistance) {
                closestDistance = distance;
                closestPlayer = player;
            }
        }
        return closestPlayer;
    }
}
