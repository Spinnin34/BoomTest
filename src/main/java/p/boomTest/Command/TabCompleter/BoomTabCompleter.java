package p.boomTest.Command.TabCompleter;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BoomTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (sender instanceof Player) {
            if (args.length == 1) {
                List<String> options = Arrays.asList("on", "off");
                for (String option : options) {
                    if (option.toLowerCase().startsWith(args[0].toLowerCase())) {
                        completions.add(option);
                    }
                }
            }
        }

        return completions;
    }
}
