package fr.tom.bvote;

import fr.tom.bvote.inventory.VoteOverviewMenu;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import fr.tom.bvote.manager.BVotePlayerManager;
import fr.tom.bvote.manager.LanguageManager;
import fr.tom.bvote.entity.BVotePlayer;

public class BVoteCommand implements CommandExecutor {
    FileConfiguration languageConf;

    public BVoteCommand() {
        languageConf = LanguageManager.getInstance().getConf();
    }

    static String toColor(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            if (sender.hasPermission("bvote.use")){
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&f&l&m------------------"));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9> &f/b info"));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9> &f/b check &9[joueur]"));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&f&l&m------------------"));
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cnon"));
                return true;
            }
        } else {
            if (args[0].equalsIgnoreCase("check")){
                handleCheck(sender, args);
            } else if (args[0].equalsIgnoreCase("info")) {
                if (!sender.hasPermission("bvote.info")) {
                    sender.sendMessage(toColor(languageConf.getString("vote.noperm")));
                } else {
                    if (!(sender instanceof Player)) {
                        sender.sendMessage("in game");
                    } else {
                        ((Player) sender).openInventory(new VoteOverviewMenu().getInventory());
                    }
                }
            }
        }
        return true;
    }

    private void handleCheck(CommandSender sender, String[] args) {
        if (!sender.hasPermission("bvote.check")) {
            sender.sendMessage(toColor(languageConf.getString("vote.noperm")));
            return;
        }
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "/b check [joueur]");
            return;
        }
        String username = args[1];
        BVotePlayer player = BVotePlayerManager.getInstance().findByName(username);
        if (player == null) {
            sender.sendMessage(toColor(languageConf.getString("vote.notfound").replace("%player%", username)));
        } else {
            sender.sendMessage(toColor(languageConf.getString("vote.check")
                    .replace("%player%", username)
                    .replace("%choice%", player.getVotedOn())));
        }
    }
}
