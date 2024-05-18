package faewulf.antiraidfarm;

import faewulf.antiraidfarm.utils.ConfigHandler;
import faewulf.antiraidfarm.utils.ModConfigs;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AntiRaidfarm implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("anti-raidfarm");

    //config
    private static ModConfigs config;

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing...");

        //load config from file
        config = ConfigHandler.loadConfig();

        //this will create new config file if it isn't exist
        ConfigHandler.saveConfig(config);
    }

    public static ModConfigs getConfig() {
        return config;
    }
}