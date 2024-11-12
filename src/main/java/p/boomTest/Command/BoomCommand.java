package p.boomTest.Command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import p.boomTest.Manager.BoomManager;

public class BoomCommand implements CommandExecutor {

    private final BoomManager boomManager;
    private String PREFIX = "§x§F§B§E§7§6§4B§x§D§6§E§F§6§Do§x§B§2§F§7§7§6o§x§8§D§F§F§7§Fm §f»§7 ";

    public BoomCommand(BoomManager boomManager) {
        this.boomManager = boomManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(PREFIX + "Este comando solo puede ser usado por jugadores.");
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage(PREFIX + "Uso correcto: /boomsystem <on|off>");
            return true;
        }

        if (args[0].equalsIgnoreCase("on")) {
            if (!boomManager.isSystemEnabled()) {
                boomManager.toggleSystem();
                sender.sendMessage(PREFIX + "Sistema BoomTest §x§8§2§F§B§7§3activado");
            } else {
                sender.sendMessage(PREFIX + "El sistema ya está §x§8§2§F§B§7§3activado");
            }
        } else if (args[0].equalsIgnoreCase("off")) {
            if (boomManager.isSystemEnabled()) {
                boomManager.toggleSystem();
                sender.sendMessage(PREFIX + "Sistema BoomTest §x§F§B§0§0§4§4desactivado");
            } else {
                sender.sendMessage(PREFIX + "El sistema ya está §x§F§B§0§0§4§4desactivado");
            }
        } else {
            sender.sendMessage(PREFIX + "Uso correcto: /boomsystem <on|off>");
        }

        return true;
    }
}
