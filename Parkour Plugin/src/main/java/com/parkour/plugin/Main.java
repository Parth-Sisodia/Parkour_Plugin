package com.parkour.plugin;

import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;
    private Material checkpointMaterial;
    private ParkourManager parkourManager;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        loadCheckpointMaterial();

        this.parkourManager = new ParkourManager();
        
        getCommand("parkour").setExecutor(new ParkourCommand(this));
        getCommand("discord").setExecutor(new DiscordCommand(this));
        getServer().getPluginManager().registerEvents(new ParkourListener(this), this);
    }

    @Override
    public void onDisable() {
    }

    public void loadCheckpointMaterial() {
        String materialName = getConfig().getString("checkpoint-block", "GOLD_BLOCK");
        try {
            this.checkpointMaterial = Material.valueOf(materialName.toUpperCase());
        } catch (IllegalArgumentException e) {
            getLogger().severe("Invalid material type in config.yml! Defaulting to GOLD_BLOCK.");
            this.checkpointMaterial = Material.GOLD_BLOCK;
        }
    }

    public static Main getInstance() {
        return instance;
    }

    public Material getCheckpointMaterial() {
        return checkpointMaterial;
    }

    public ParkourManager getParkourManager() {
        return parkourManager;
    }
}
