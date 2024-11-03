package p.boomTest.Command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import p.boomTest.Manager.BoomManager;

public class BoomCommand implements CommandExecutor {
    private final BoomManager boomManager;

    public BoomCommand(BoomManager boomManager) {
        this.boomManager = boomManager;
    }


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Este commando solo puede ser usado por jugadores.");
            return true;
        }

        boomManager.toggleSystem();
        sender.sendMessage("Sistema boomtest " + (boomManager.isSystemEnabled() ? "activado" : "desactivado"));
        return false;
    }
}
