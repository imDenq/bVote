package fr.tom.bvote;

import org.bukkit.plugin.java.JavaPlugin;
import fr.tom.bvote.manager.BVotePlayerManager;
import fr.tom.bvote.manager.LanguageManager;
import fr.tom.bvote.listener.PlayerListener;

public final class BVote extends JavaPlugin {

    @Override
    public void onEnable() {
        LanguageManager.getInstance().setup();
        BVotePlayerManager.getInstance().load();
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        getCommand("bvote").setExecutor(new BVoteCommand());
    }

    @Override
    public void onDisable() {
        BVotePlayerManager.getInstance().save();
    }
}
