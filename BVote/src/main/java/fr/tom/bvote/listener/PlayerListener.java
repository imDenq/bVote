package fr.tom.bvote.listener;

import fr.tom.bvote.BVote;
import fr.tom.bvote.Icon;
import fr.tom.bvote.entity.BVotePlayer;
import fr.tom.bvote.manager.BVotePlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import fr.tom.bvote.inventory.BInventoryHolder;
import fr.tom.bvote.inventory.MainMenuInventoryView;

public class PlayerListener implements Listener {

    @EventHandler
    public void onJoin(final PlayerJoinEvent event) {
        final BVotePlayer player = BVotePlayerManager.getInstance().getPlayer(event.getPlayer().getUniqueId());
        if (player != null) {
            player.setLastKnownName(event.getPlayer().getName());
        } else {
            Bukkit.getScheduler().runTaskLater(JavaPlugin.getPlugin(BVote.class), () -> {
                event.getPlayer().openInventory(new MainMenuInventoryView().getInventory());
            }, 1);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClickedInventory() == null)
            return;
        if (!(event.getInventory().getHolder() instanceof BInventoryHolder))
            return;
        event.setCancelled(true);
        if (event.getRawSlot() != event.getSlot())
            return;

        BInventoryHolder holder = (BInventoryHolder) event.getInventory().getHolder();
        holder.getInventoryView().getIconHashMap().getOrDefault(event.getRawSlot(), getEmptyIcon()).handleClick((Player) event.getWhoClicked());

    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (event.getInventory().getHolder() instanceof BInventoryHolder) {
            if (!BVotePlayerManager.getInstance().contains(event.getPlayer().getUniqueId())) {
                Bukkit.getScheduler().runTaskLater(JavaPlugin.getPlugin(BVote.class), () -> {

                    if (!(event.getPlayer().getOpenInventory().getTopInventory().getHolder() instanceof BInventoryHolder)) {
                        if (!BVotePlayerManager.getInstance().contains(event.getPlayer().getUniqueId()))
                            event.getPlayer().openInventory(new MainMenuInventoryView().getInventory());
                    }
                }, 1);
            }
        }
    }

    private Icon getEmptyIcon() {
        return new Icon() {
            @Override
            public ItemStack getItemStack() {
                return null;
            }

            @Override
            public void handleClick(Player player) {

            }
        };
    }
}
