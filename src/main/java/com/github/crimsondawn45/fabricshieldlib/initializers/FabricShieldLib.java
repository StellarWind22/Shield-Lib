package com.github.crimsondawn45.fabricshieldlib.initializers;

import com.github.crimsondawn45.fabricshieldlib.lib.config.FabricShieldLibConfig;
import com.github.crimsondawn45.fabricshieldlib.tests.FabricShieldLibTests;
import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main class for Fabric Shield Lib.
 */
public class FabricShieldLib implements ModInitializer {
	/**
	 * Fabric Shield Lib's mod id.
	 */
	public static final String MOD_ID = "fabricshieldlib";

	/**
	 * Fabric Shield Lib's logger.
	 */
	public static final Logger logger = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// Register Config
		MidnightConfig.init(MOD_ID, FabricShieldLibConfig.class);

		// Dev environment code.
		if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
			// Warn about dev code
			logger.warn("FABRIC SHIELD LIB DEVELOPMENT CODE RAN!!!, if you are not in a development environment this is very bad! Test items and test enchantments will be ingame!");

			FabricShieldLibTests.runTests();
		}

		// Announce having finished starting up
		logger.info("Fabric Shield Lib Initialized!");
	}
}
