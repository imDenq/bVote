package fr.tom.bvote.entity;

import lombok.Data;

import java.util.Objects;
import java.util.UUID;

@Data
public class BVotePlayer {

    private UUID uuid;
    private String votedOn;
    private String lastKnownName;
    private String category;

    public BVotePlayer(UUID uuid, String category, String votedOn, String lastKnownName) {
        this.uuid = uuid;
        this.votedOn = votedOn;
        //int votedon 0 par defaut
        this.category = category;
        this.lastKnownName = lastKnownName;
    }

    public BVotePlayer(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BVotePlayer that = (BVotePlayer) o;
        return uuid.equals(that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}
