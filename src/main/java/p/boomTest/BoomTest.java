package p.boomTest;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import p.boomTest.Command.BoomCommand;
import p.boomTest.Command.TabCompleter.BoomTabCompleter;
import p.boomTest.Listener.BoomListener;
import p.boomTest.Manager.BoomManager;

public final class BoomTest extends JavaPlugin {
    private BoomManager boomManager;

    @Override
    public void onEnable() {
        this.boomManager = new BoomManager(this);

        Bukkit.getServer().getPluginManager().registerEvents(new BoomListener(boomManager, this), this);
        this.getCommand("boomsystem").setExecutor(new BoomCommand(boomManager));
        this.getCommand("boomsystem").setTabCompleter(new BoomTabCompleter());

        getLogger().info("BoomTest activado correctamente :)");

    }

    @Override
    public void onDisable() {
        getLogger().info("BoomTest desactivado :(");
    }
}
