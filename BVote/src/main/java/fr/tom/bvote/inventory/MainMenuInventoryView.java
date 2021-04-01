package fr.tom.bvote.inventory;

import fr.tom.bvote.Icon;
import fr.tom.bvote.entity.BVotePlayer;
import fr.tom.bvote.manager.BVotePlayerManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.stream.Collectors;

public class MainMenuInventoryView extends BvoteInventoryView {

    public MainMenuInventoryView() {
        super();
        iconHashMap.put(22, getWelcomeBeacon());
        iconHashMap.put(30, getChoiceIcon(1));
        iconHashMap.put(31, getChoiceIcon(2));
        iconHashMap.put(32, getChoiceIcon(3));
        iconHashMap.put(40, getOtherIcon());
        iconHashMap.forEach((key, value) -> inventory.setItem(key, value.getItemStack()));
    }


    private Icon getChoiceIcon(int choice) {
        if (choice < 1 || choice > 3) return null;

        return new Icon() {
            @Override
            public ItemStack getItemStack() {
                ItemStack choiceBook = new ItemStack(Material.BOOKSHELF);
                ItemMeta meta = choiceBook.getItemMeta();
                meta.setDisplayName(toColor(manager.getConf().getString("main.choice.title")));
                String type = manager.getConf().getString("main.choice." + choice);
                List<String> lore = manager.getConf()
                        .getStringList("main.choice.lore")
                        .stream()
                        .map(s -> toColor(s.replace("%choice%", type)))
                        .collect(Collectors.toList());
                meta.setLore(lore);
                choiceBook.setItemMeta(meta);
                return choiceBook;
            }

            @Override
            public void handleClick(Player player) {
                player.openInventory(new ChoiceMenuView(choice).getInventory());
            }
        };
    }

    private Icon getOtherIcon() {
        return new Icon() {
            @Override
            public ItemStack getItemStack() {
                ItemStack choiceBook = new ItemStack(Material.BOOKSHELF);
                ItemMeta meta = choiceBook.getItemMeta();
                meta.setDisplayName(toColor(manager.getConf().getString("main.choice.title")));
                String type = manager.getConf().getString("main.choice.other");
                List<String> lore = manager.getConf()
                        .getStringList("main.choice.lore")
                        .stream()
                        .map(s -> toColor(s.replace("%choice%", type)))
                        .collect(Collectors.toList());
                meta.setLore(lore);
                choiceBook.setItemMeta(meta);
                return choiceBook;
            }

            @Override
            public void handleClick(Player player) {
                player.closeInventory();
                player.sendMessage(toColor(manager.getConf().getString("vote.counted", ChatColor.AQUA + "Ton vote a été enrengistré merci de nous aider !")));
                final String other = manager.getConf().getString("second.choice.other", "Other");
                BVotePlayerManager.getInstance().addPlayer(new BVotePlayer(player.getUniqueId(), other, other, player.getName()));
                player.closeInventory();
            }
        };
    }
}
