package stellarwitch7.illusionist.mixin.client.sodium;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.caffeinemc.mods.sodium.client.world.cloned.ClonedChunkSection;
import net.caffeinemc.mods.sodium.client.render.chunk.compile.tasks.ChunkBuilderMeshingTask;
import net.minecraft.block.Block;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import stellarwitch7.illusionist.accessor.ClonedChunkSectionAccessor;
import stellarwitch7.illusionist.cca.ModChunkComponents;

import java.util.concurrent.atomic.AtomicReference;

@Mixin(ClonedChunkSection.class)
public class ClonedChunkSectionMixin implements ClonedChunkSectionAccessor {
    /**
     * The shadow block states for this chunk section.
     * stored as an atomic reference to allow for safe access in a {@link ChunkBuilderMeshingTask}
     */
    @Unique
    private volatile Int2ObjectOpenHashMap<Block> blockStates = null;

    /**
     * Stores a copy of a chunks shadow disguise map with this section, to safely access it later in a {@link ChunkBuilderMeshingTask }
     * @param level the world currently rendering
     * @param chunk the chunk sodium is copying this section from
     * @param section the chunk section sodium is copying
     * @param pos the position of the chunk section sodium is copying
     * @param ci no explanation needed
     */
    @Inject(method = "<init>", at = @At("TAIL"))
    private void appendShadowDisguiseMap(World level, WorldChunk chunk, ChunkSection section, ChunkSectionPos pos, CallbackInfo ci) {
        this.blockStates = ModChunkComponents.SHADOW_DISGUISE_MAP.get(chunk).toUpdateMap();
    }

    /**
     * Gets the shadow block states for this chunk section.
     * @return the shadow block states for this chunk section
     */
    @Override
    public Int2ObjectOpenHashMap<Block> illusionist$getBlockStates() {
        return blockStates;
    }
}
