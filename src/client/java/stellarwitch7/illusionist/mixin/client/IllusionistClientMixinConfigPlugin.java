package stellarwitch7.illusionist.mixin.client;

import net.fabricmc.loader.api.FabricLoader;
import stellarwitch7.illusionist.util.DefaultedMixinConfigPlugin;

public class IllusionistClientMixinConfigPlugin implements DefaultedMixinConfigPlugin {
    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return !mixinClassName.contains("sodium") || FabricLoader.getInstance().isModLoaded("sodium");
    }
}
