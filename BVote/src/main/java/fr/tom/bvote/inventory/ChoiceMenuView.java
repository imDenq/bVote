package fr.tom.bvote.inventory;


import fr.tom.bvote.BVote;
import fr.tom.bvote.Icon;
import fr.tom.bvote.entity.BVotePlayer;
import fr.tom.bvote.manager.BVotePlayerManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChoiceMenuView extends BvoteInventoryView {

    final int BOOKSHELF_SLOT = 31;
    int choice = 0;
    int subChoice = 0;
    BVote voteInstance;
    List<String> stringList;

    public ChoiceMenuView(int choice) {
        super();
        voteInstance = JavaPlugin.getPlugin(BVote.class);
        this.choice = choice;
        iconHashMap.put(22, getWelcomeBeacon());
        iconHashMap.put(BOOKSHELF_SLOT, getBookShelfIcon());
        iconHashMap.put(48, getConfirmIcon());
        iconHashMap.put(50, getMainMenuIcon());

        iconHashMap.forEach((key, value) -> inventory.setItem(key, value.getItemStack()));

        final String string = manager.getConf().getString("second.choice" + choice);
        stringList = voteInstance.getConfig().getStringList(string);
    }

    private Icon getBookShelfIcon() {
        return new Icon() {
            ItemStack stack;

            @Override
            public ItemStack getItemStack() {
                stack = generateNewStack();
                return stack;
            }

            private ItemStack generateNewStack() {
                stack = new ItemStack(Material.BOOKSHELF);
                ItemMeta meta = stack.getItemMeta();
                meta.setDisplayName(
                        toColor(
                                manager.getConf().getString("second.title")
                                        .replace("%choice%", manager.getConf().getString("second.choice" + choice))));
                List<String> lore = new ArrayList<>();
                stringList = voteInstance.getConfig().getStringList(manager.getConf().getString("second.choice" + choice));
                String toFitIn = manager.getConf().getString("second.choicetemplate");
                for (String line : manager.getConf().getStringList("second.lore")) {
                    if (line.equalsIgnoreCase("%subchoices%")) {
                        for (int i = 0; i < stringList.size(); i++) {
                            if (i == subChoice) {
                                lore.add(toColor(ChatColor.BLUE + "" + ChatColor.BOLD + "> " + ChatColor.WHITE + toFitIn.replace("%subchoice%", stringList.get(i))));
                            } else {
                                lore.add(ChatColor.GRAY + toColor(toFitIn.replace("%subchoice%", stringList.get(i))));
                            }
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
                subChoice = (subChoice + 1 >= stringList.size()) ? 0 : subChoice + 1;
                ItemStack toReplaceWith = generateNewStack();
                getInventory().setItem(BOOKSHELF_SLOT, toReplaceWith);
                player.updateInventory();
            }
        };
    }

    private Icon getMainMenuIcon() {
        return new Icon() {
            @Override
            public ItemStack getItemStack() {
                ItemStack stack = new ItemStack(Material.FIREBALL);
                ItemMeta meta = stack.getItemMeta();
                meta.setDisplayName(toColor(manager.getConf().getString("second.main.title")));
                List<String> lore = manager.getConf().getStringList("second.main.lore")
                        .stream().map(BvoteInventoryView::toColor).collect(Collectors.toList());
                meta.setLore(lore);
                stack.setItemMeta(meta);
                return stack;
            }

            @Override
            public void handleClick(Player player) {
                player.openInventory(new MainMenuInventoryView().getInventory());
            }
        };
    }

    private Icon getConfirmIcon() {
        return new Icon() {
            @Override
            public ItemStack getItemStack() {
                ItemStack stack = new ItemStack(Material.SLIME_BALL);
                ItemMeta meta = stack.getItemMeta();
                meta.setDisplayName(toColor(manager.getConf().getString("second.confirm.title")));
                List<String> lore = manager.getConf().getStringList("second.confirm.lore")
                        .stream().map(BvoteInventoryView::toColor).collect(Collectors.toList());
                meta.setLore(lore);
                stack.setItemMeta(meta);
                return stack;
            }

            @Override
            public void handleClick(Player player) {
                player.closeInventory();
                player.sendMessage(toColor(manager.getConf().getString("vote.counted", ChatColor.AQUA + "Ton vote a été compté merci de nous aider !")));
                BVotePlayerManager.getInstance().addPlayer(new BVotePlayer(player.getUniqueId(), manager.getConf().getString("second.choice" + choice), stringList.get(subChoice), player.getName()));
                player.closeInventory();
            }
        };
    }
}
