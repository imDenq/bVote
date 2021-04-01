package fr.tom.bvote.inventory;

import lombok.Getter;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class BInventoryHolder implements InventoryHolder {

    @Getter
    private final BvoteInventoryView inventoryView;

    public BInventoryHolder(BvoteInventoryView inventoryView) {
        this.inventoryView = inventoryView;
    }

    @Override
    public Inventory getInventory() {
        return inventoryView.getInventory();
    }
}
