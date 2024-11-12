package p.boomTest.Listener;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import p.boomTest.Manager.BoomManager;

public class BoomListener implements Listener {

    private final BoomManager boomManager;
    private final JavaPlugin plugin;

    public BoomListener(BoomManager boomManager, JavaPlugin plugin) {
        this.boomManager = boomManager;
        this.plugin = plugin;
    }

    public void equipNearestPlayerWithTNTHelmet(Location location) {
        Player nearestPlayer = getNearestPlayer(location);
        if (nearestPlayer != null) {
            equipPlayerWithTNTHelmet(nearestPlayer, location);
        }
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

    private void equipPlayerWithTNTHelmet(Player player, Location explosionLocation) {
        ItemStack originalHelmet = player.getInventory().getHelmet();
        player.getInventory().setHelmet(new ItemStack(Material.TNT));
        player.getWorld().createExplosion(player.getLocation(), 4.0f, false, false);
        player.damage(10);

        resetHelmet(player, originalHelmet);
    }

    private void resetHelmet(Player player, ItemStack originalHelmet) {
        player.getInventory().setHelmet(originalHelmet);
    }

    private void removeNearbyTNT(Location location) {
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    Block block = location.clone().add(x, y, z).getBlock();
                    if (block.getType() == Material.TNT) {
                        block.setType(Material.AIR);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onTNTExplosion(EntityExplodeEvent event) {
        if (!boomManager.isSystemEnabled()) return;

        Entity entity = event.getEntity();

        if (entity.getType() == EntityType.PRIMED_TNT) {
            Location explosionLocation = entity.getLocation();
            event.setCancelled(true);
            equipNearestPlayerWithTNTHelmet(explosionLocation);
            removeNearbyTNT(explosionLocation);
        }
    }
}
