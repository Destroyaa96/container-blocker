package com.destroyaa.container_blocker.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BlockedItemsConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger("container-blocker");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = Paths.get("config", "blocked_items.json");
    
    private static Set<Item> blockedItems = new HashSet<>();
    
    public static void load() {
        try {
            // Create config directory if it doesn't exist
            Files.createDirectories(CONFIG_PATH.getParent());
            
            ConfigData config;
            
            if (Files.exists(CONFIG_PATH)) {
                // Load existing config
                String json = Files.readString(CONFIG_PATH);
                config = GSON.fromJson(json, ConfigData.class);
                LOGGER.info("Loaded blocked items config from {}", CONFIG_PATH);
            } else {
                // Create default config
                config = createDefaultConfig();
                saveConfig(config);
                LOGGER.info("Created default blocked items config at {}", CONFIG_PATH);
            }
            
            // Parse item IDs into Item objects
            blockedItems.clear();
            if (config.blockedItems == null || config.blockedItems.isEmpty()) {
                LOGGER.warn("No blocked items configured");
            } else {
                LOGGER.info("Loading {} blocked item(s)", config.blockedItems.size());
                for (String itemId : config.blockedItems) {
                    try {
                        Identifier id = Identifier.of(itemId);
                        if (Registries.ITEM.containsId(id)) {
                            Item item = Registries.ITEM.get(id);
                            blockedItems.add(item);
                            LOGGER.info("✓ Blocking item: {} ({})", itemId, item.toString());
                        } else {
                            LOGGER.warn("✗ Unknown item ID in config: {}", itemId);
                        }
                    } catch (Exception e) {
                        LOGGER.error("✗ Failed to parse item ID: {}", itemId, e);
                    }
                }
            }
            
        } catch (IOException e) {
            LOGGER.error("Failed to load blocked items config", e);
            // Use default if loading fails
            blockedItems.clear();
            blockedItems.add(Registries.ITEM.get(Identifier.of("minecraft", "dragon_egg")));
        }
    }
    
    private static ConfigData createDefaultConfig() {
        ConfigData config = new ConfigData();
        config.blockedItems = new ArrayList<>();
        config.blockedItems.add("minecraft:dragon_egg");
        return config;
    }
    
    private static void saveConfig(ConfigData config) throws IOException {
        String json = GSON.toJson(config);
        Files.writeString(CONFIG_PATH, json);
    }
    
    public static boolean isBlocked(Item item) {
        return blockedItems.contains(item);
    }
    
    private static class ConfigData {
        public List<String> blockedItems;
    }
}
