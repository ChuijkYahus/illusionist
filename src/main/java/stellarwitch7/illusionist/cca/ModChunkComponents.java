package stellarwitch7.illusionist.cca;

import org.ladysnake.cca.api.v3.chunk.ChunkComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.chunk.ChunkComponentInitializer;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;

import stellarwitch7.illusionist.Illusionist;

public class ModChunkComponents implements ChunkComponentInitializer {
    public static final ComponentKey<ShadowDisguiseMapComponent> SHADOW_DISGUISE_MAP =
            ComponentRegistry.getOrCreate(Illusionist.id("shadow_disguise_map"), ShadowDisguiseMapComponent.class);

    @Override
    public void registerChunkComponentFactories(ChunkComponentFactoryRegistry registry) {
        registry.register(SHADOW_DISGUISE_MAP, ShadowDisguiseMapComponent::new);
    }
}
