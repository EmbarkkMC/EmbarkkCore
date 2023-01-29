package me.outspending.embarkkcore.Storage;

import java.util.UUID;

public class PlayerData {

    private final UUID uuid;
    private double balance;
    private double tokens;
    private int level;
    private int xp;
    private int xp_max;
    private int prestige;
    private double blocks_broken;
    private int multiplier;

    public PlayerData(UUID uuid, double balance, double tokens, int level, int xp, int xp_max, int prestige, double blocks_broken, int multiplier) {
        this.uuid = uuid;
        this.balance = balance;
        this.tokens = tokens;
        this.level = level;
        this.xp = xp;
        this.xp_max = xp_max;
        this.prestige = prestige;
        this.blocks_broken = blocks_broken;
        this.multiplier = multiplier;
    }

    public PlayerData(UUID uuid) {
        this.uuid = uuid;
        this.balance = 0;
        this.tokens = 0;
        this.level = 0;
        this.xp = 0;
        this.xp_max = 250;
        this.prestige = 0;
        this.blocks_broken = 0;
        this.multiplier = 1;
    }

    public UUID getUuid() {
        return uuid;
    }

    public double getBalance() {
        return balance;
    }

    public double getTokens() {
        return tokens;
    }

    public int getLevel() {
        return level;
    }

    public int getXp() {
        return xp;
    }

    public int getXp_max() {
        return xp_max;
    }

    public int getPrestige() {
        return prestige;
    }

    public double getBlocks_broken() {
        return blocks_broken;
    }

    public int getMultiplier() {
        return multiplier;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setTokens(double tokens) {
        this.tokens = tokens;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public void setXp_max(int xp_max) {
        this.xp_max = xp_max;
    }

    public void setPrestige(int prestige) {
        this.prestige = prestige;
    }

    public void setBlocks_broken(double blocks_broken) {
        this.blocks_broken = blocks_broken;
    }

    public void setMultiplier(int multiplier) {
        this.multiplier = multiplier;
    }
}
