package me.outspending.embarkkcore.Pmines.Main;

import org.bukkit.Location;

import java.util.List;
import java.util.UUID;

public class Pmine {

    private String name;
    private UUID owner;
    private Location loc1;
    private Location loc2;
    private List<UUID> members;

    public Pmine(String name, UUID owner, Location loc1, Location loc2, List<UUID> members) {
        this.name = name;
        this.owner = owner;
        this.loc1 = loc1;
        this.loc2 = loc2;
        this.members = members;
    }

    public String getName() {
        return name;
    }

    public UUID getOwner() {
        return owner;
    }

    public Location getLoc1() {
        return loc1;
    }

    public Location getLoc2() {
        return loc2;
    }

    public List<UUID> getMembers() {
        return members;
    }
}
