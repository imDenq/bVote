package fr.tom.bvote.manager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import fr.tom.bvote.BVote;
import fr.tom.bvote.entity.BVotePlayer;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class BVotePlayerManager {

    private static final BVotePlayerManager instance = new BVotePlayerManager();
    Set<BVotePlayer> hasVotedSet = new HashSet<>();
    File file;

    public static BVotePlayerManager getInstance() {
        return instance;
    }

    public void load() {
        BVote bVote = JavaPlugin.getPlugin(BVote.class);
        if (!bVote.getDataFolder().exists()) {
            bVote.getDataFolder().mkdirs();
        }
        file = new File(bVote.getDataFolder(), "players.json");
        try {
            if (file.createNewFile()) {
                hasVotedSet = new HashSet<>();
            } else {
                if (file.length() == 0) {
                    hasVotedSet = new HashSet<>();
                } else {
                    hasVotedSet = new Gson().fromJson(new FileReader(file), new TypeToken<Set<BVotePlayer>>() {
                    }.getType());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            Writer writer = Files.newBufferedWriter(file.toPath());
            new Gson().toJson(hasVotedSet, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean contains(UUID uuid) {
        return hasVotedSet.contains(new BVotePlayer(uuid));
    }

    public void addPlayer(BVotePlayer bVotePlayer) {
        hasVotedSet.add(bVotePlayer);
    }

    public BVotePlayer getPlayer(UUID uuid) {
        for (BVotePlayer player : hasVotedSet) {
            if (player.getUuid().equals(uuid))
                return player;
        }
        return null;
    }

    public BVotePlayer findByName(String name) {
        for (BVotePlayer player : hasVotedSet) {
            if (player.getLastKnownName().equalsIgnoreCase(name)) {
                return player;
            }
        }
        return null;
    }

    public Set<BVotePlayer> findPlayers(Predicate<BVotePlayer> predicate) {
        return hasVotedSet.stream().filter(predicate).collect(Collectors.toSet());
    }
}
