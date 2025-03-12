package stellarwitch7.illusionist.mixin.client;

import net.fabricmc.loader.api.FabricLoader;
import stellarwitch7.illusionist.util.DefaultedMixinConfigPlugin;
// general sodium mixin note for later. sodium extends a lot of vanilla methods, so if we don't explicitly remap those methods, the whole thing will die in prod. DO NOT NOT REMAP
public class IllusionistClientMixinConfigPlugin implements DefaultedMixinConfigPlugin {
    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return !mixinClassName.contains("sodium") || FabricLoader.getInstance().isModLoaded("sodium");
    }
}
