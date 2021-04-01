package fr.tom.bvote.inventory;

import fr.tom.bvote.Icon;
import fr.tom.bvote.manager.LanguageManager;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Data
public abstract class BvoteInventoryView {

    final HashMap<Integer, Icon> iconHashMap = new HashMap<>();

    final Inventory inventory;
    final InventoryHolder holder;
    final LanguageManager manager;

    private final int INVENTORY_SIZE = 54;

    public BvoteInventoryView() {
        holder = new BInventoryHolder(this);
        this.manager = LanguageManager.getInstance();
        final BInventoryHolder owner = new BInventoryHolder(this);
        this.inventory = Bukkit.createInventory(owner, INVENTORY_SIZE, toColor(manager.getConf().getString("inventory.title")));


        //g galéré fdp de hash set
        iconHashMap.put(0, getEmptyBluePane());
        iconHashMap.put(1, getEmptyBluePane());
        iconHashMap.put(9, getEmptyBluePane());

        iconHashMap.put(7, getEmptyBluePane());
        iconHashMap.put(8, getEmptyBluePane());
        iconHashMap.put(17, getEmptyBluePane());

        iconHashMap.put(36, getEmptyBluePane());
        iconHashMap.put(45, getEmptyBluePane());
        iconHashMap.put(46, getEmptyBluePane());

        iconHashMap.put(44, getEmptyBluePane());
        iconHashMap.put(53, getEmptyBluePane());
        iconHashMap.put(52, getEmptyBluePane());
    }

    static String toColor(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    Icon getEmptyBluePane() {
        return new Icon() {
            @Override
            public ItemStack getItemStack() {
                final ItemStack itemStack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 11);
                ItemMeta meta = itemStack.getItemMeta();
                meta.setDisplayName(ChatColor.RESET + " ");
                itemStack.setItemMeta(meta);
                return itemStack;
            }

            @Override
            public void handleClick(Player player) {

            }
        };
    }

    Icon getWelcomeBeacon() {
        return new Icon() {
            @Override
            public ItemStack getItemStack() {
                ItemStack beacon = new ItemStack(Material.BEACON);
                ItemMeta meta = beacon.getItemMeta();
                meta.setDisplayName(toColor(manager.getConf().getString("main.welcomebeacon.title")));
                List<String> lore = manager.getConf()
                        .getStringList("main.welcomebeacon.lore")
                        .stream()
                        .map(MainMenuInventoryView::toColor)
                        .collect(Collectors.toList());
                meta.setLore(lore);
                beacon.setItemMeta(meta);
                return beacon;
            }

            @Override
            public void handleClick(Player player) {
            }
        };
    }

}
