package p.boomTest.Manager;

import p.boomTest.BoomTest;

public class BoomManager {
    private final BoomTest plugin;
    private boolean isEnabled;

    public BoomManager(BoomTest plugin) {
        this.plugin = plugin;
        this.isEnabled = false;
    }

    public boolean isSystemEnabled() {
        return isEnabled;
    }

    public void toggleSystem() {
        isEnabled = !isEnabled;
        plugin.getLogger().info("El sistema de BoomTest esta " + (isEnabled ? "activado" : "desactivado"));
    }
}
