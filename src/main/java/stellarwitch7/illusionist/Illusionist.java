package stellarwitch7.illusionist;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import stellarwitch7.illusionist.spell.trick.ModTricks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Illusionist implements ModInitializer {
    public static final String MOD_ID = "illusionist";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        ModTricks.register();
    }

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }
}
