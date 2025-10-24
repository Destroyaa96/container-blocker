package com.destroyaa.container_blocker;

import com.destroyaa.container_blocker.config.BlockedItemsConfig;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContainerBlocker implements ModInitializer {
    private static final Logger LOGGER = LoggerFactory.getLogger("container-blocker");

    @Override
    public void onInitialize() {
        BlockedItemsConfig.load();
        LOGGER.info("Container Protection initialized - Blocked items cannot be placed in containers");
    }
}
