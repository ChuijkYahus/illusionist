package stellarwitch7.illusionist;

import net.fabricmc.api.ClientModInitializer;
import stellarwitch7.illusionist.debug.ClientDebugHelper;

public class IllusionistClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientDebugHelper.registerIfInDev();
    }
}