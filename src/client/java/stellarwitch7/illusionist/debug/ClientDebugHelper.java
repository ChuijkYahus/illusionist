package stellarwitch7.illusionist.debug;

import net.fabricmc.loader.api.FabricLoader;
/**
 * A helper class for conditionally registering debug features.
 */
public class ClientDebugHelper {
    /**
     * Registers the debug features if the game is in a development environment.
     */
    public static void registerIfInDev() {
        if (!FabricLoader.getInstance().isDevelopmentEnvironment())
            return;
        DebugKeybindings.register();
    }
}
