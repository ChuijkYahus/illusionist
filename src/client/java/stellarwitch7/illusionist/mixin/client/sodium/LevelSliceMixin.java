package stellarwitch7.illusionist.mixin.client.sodium;

import com.llamalad7.mixinextras.sugar.Local;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.caffeinemc.mods.sodium.client.world.LevelSlice;
import net.caffeinemc.mods.sodium.client.world.cloned.ChunkRenderContext;
import net.caffeinemc.mods.sodium.client.world.cloned.ClonedChunkSection;
import net.minecraft.block.Block;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import stellarwitch7.illusionist.accessor.ClonedChunkSectionAccessor;
import stellarwitch7.illusionist.accessor.LevelSliceExt;

@Mixin(value = LevelSlice.class, remap = false)
public abstract class LevelSliceMixin implements LevelSliceExt {
    @Accessor(value = "originBlockX")
    public abstract int getOriginBlockX();

    @Accessor(value = "originBlockY")
    public abstract int getOriginBlockY();

    @Accessor(value = "originBlockZ")
    public abstract int getOriginBlockZ();

    @Override
    public Int2ObjectOpenHashMap<Block> illusionist$getShadowBlockStates(int sectionIndex) {
        return shadowBlockStates[sectionIndex];
    }

    @Shadow
    @Final
    private static int SECTION_ARRAY_SIZE;
    @Unique
    @SuppressWarnings("unchecked")
    private final Int2ObjectOpenHashMap<Block>[] shadowBlockStates = new Int2ObjectOpenHashMap[SECTION_ARRAY_SIZE];

    @Inject(method = "copySectionData", at = @At("TAIL"))
    private void copySectionData(ChunkRenderContext context, int sectionIndex, CallbackInfo ci, @Local ClonedChunkSection section) {
        shadowBlockStates[sectionIndex] = ((ClonedChunkSectionAccessor) section).illusionist$getBlockStates();
    }

    /**
     * ties into the for loop in the reset method to reset the shadow block states for each section
     *
     * @param sectionIndex the current section index in iteration
     */
    @Inject(method = "reset", at = @At(value = "TAIL"))
    private void reset(CallbackInfo ci, @Local int sectionIndex) {
        shadowBlockStates[sectionIndex] = null;
    }
}
