package fr.tom.bvote.inventory;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import fr.tom.bvote.BVote;
import fr.tom.bvote.Icon;
import fr.tom.bvote.manager.BVotePlayerManager;
import fr.tom.bvote.entity.BVotePlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class VoteOverviewMenu extends BvoteInventoryView {

    public VoteOverviewMenu() {
        super();
        iconHashMap.put(21, getChoiceIcon(1));
        iconHashMap.put(22, getChoiceIcon(2));
        iconHashMap.put(23, getChoiceIcon(3));
        iconHashMap.put(31, getOtherChoiceIcon());
        iconHashMap.forEach((key, value) -> {
            inventory.setItem(key, value.getItemStack());
        });
    }

    private Icon getOtherChoiceIcon() {
        return new Icon() {
            @Override
            public ItemStack getItemStack() {
                String choice = manager.getConf().getString("second.choice.other", "Other");
                ItemStack stack = new ItemStack(Material.BOOKSHELF);
                ItemMeta meta = stack.getItemMeta();
                meta.setDisplayName(ChatColor.BLUE + choice + ChatColor.WHITE + ":");
                List<String> lore = new ArrayList<>();
                for (String line : manager.getConf().getStringList("second.lore")) {
                    if (line.contains("%subchoices%")) {
                        Set<BVotePlayer> players = BVotePlayerManager.getInstance()
                                .findPlayers(bVotePlayer ->
                                        bVotePlayer.getCategory().equalsIgnoreCase(choice) &&
                                                bVotePlayer.getVotedOn().equalsIgnoreCase(choice));
                        lore.add(toColor(ChatColor.GRAY + choice + ": &c" + players.size()));
                    } else {
                        lore.add(toColor(line));
                    }
                }
                meta.setLore(lore);
                stack.setItemMeta(meta);
                return stack;
            }

            @Override
            public void handleClick(Player player) {

            }
        };
    }

    private Icon getChoiceIcon(int i) {
        return new Icon() {
            @Override
            public ItemStack getItemStack() {
                String choice = manager.getConf().getString("second.choice" + i);
                ItemStack stack = new ItemStack(Material.BOOKSHELF);
                ItemMeta meta = stack.getItemMeta();
                meta.setDisplayName(ChatColor.BLUE + choice + ChatColor.WHITE + ":");
                List<String> lore = new ArrayList<>();
                List<String> options = JavaPlugin.getPlugin(BVote.class).getConfig().getStringList(choice);
                for (String line : manager.getConf().getStringList("second.lore")) {
                    if (line.contains("%subchoices%")) {
                        for (String s : options) {
                            Set<BVotePlayer> players = BVotePlayerManager.getInstance()
                                    .findPlayers(bVotePlayer ->
                                            bVotePlayer.getCategory().equalsIgnoreCase(choice) &&
                                                    bVotePlayer.getVotedOn().equalsIgnoreCase(s));
                            lore.add(toColor(ChatColor.GRAY + s + ": &c" + players.size()));
                        }
                    } else {
                        lore.add(toColor(line));
                    }
                }
                meta.setLore(lore);
                stack.setItemMeta(meta);
                return stack;
            }

            @Override
            public void handleClick(Player player) {

            }
        };
    }
}
