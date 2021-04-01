package fr.tom.bvote;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface Icon {

    ItemStack getItemStack();

    void handleClick(Player player);
}
