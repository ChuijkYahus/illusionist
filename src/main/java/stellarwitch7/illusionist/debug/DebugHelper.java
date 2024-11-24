package stellarwitch7.illusionist.debug;

import net.fabricmc.loader.api.FabricLoader;
import stellarwitch7.illusionist.debug.item.DebugItems;

/**
 * A helper class for conditionally registering debug features.
 */
public class DebugHelper {
    /**
     * Registers the debug features if the game is in a development environment.
     */
    public static void registerIfInDev() {
        if (!FabricLoader.getInstance().isDevelopmentEnvironment())
            return;
        DebugItems.register();
    }
}