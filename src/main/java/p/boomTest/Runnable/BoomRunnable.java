package p.boomTest.Runnable;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.scheduler.BukkitRunnable;
import p.boomTest.BoomTest;

public class BoomRunnable {

    private final BoomTest plugin;

    public BoomRunnable(BoomTest plugin) {
        this.plugin = plugin;
    }

    public void runBoomTask(TNTPrimed tnt, Player player) {
        Location headLocation = player.getLocation().add(0, 1.8, 0);
        World world = player.getWorld();

        int maxHeight = world.getMaxHeight();
        while (headLocation.getBlock().getType() != Material.AIR && headLocation.getY() < maxHeight) {
            headLocation.add(0, 1, 0);
        }


        // culo

        ArmorStand armorStand = (ArmorStand) world.spawnEntity(headLocation, EntityType.ARMOR_STAND);
        armorStand.setVisible(false);
        armorStand.setGravity(false);
        armorStand.setMarker(true);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (tnt.isDead() || !player.isOnline()) {
                    armorStand.remove();
                    this.cancel();
                    return;
                }

                Location newHeadLocation = player.getLocation().add(0, 1.8, 0);
                while (newHeadLocation.getBlock().getType() != Material.AIR && newHeadLocation.getY() < maxHeight) {
                    newHeadLocation.add(0, 1, 0);
                }
                armorStand.teleport(newHeadLocation);
                tnt.teleport(armorStand.getLocation());
            }
        }.runTaskTimer(plugin, 0L, 1L);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (!tnt.isDead()) {
                    player.damage(10);
                    armorStand.remove();
                }
            }
        }.runTaskLater(plugin, 80L);
    }
}
