package stellarwitch7.illusionist.mixin.client.sodium;

import net.caffeinemc.mods.sodium.client.world.LevelSlice;
import net.caffeinemc.mods.sodium.client.world.cloned.ChunkRenderContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = LevelSlice.class, remap = false)
public interface LevelSliceInvoker {

    /**
     * Gets the local section index for the given coordinates
     *
     * @param x the x-coordinate of the section (different from the block x-coordinate)
     * @param y the y-coordinate of the section (different from the block y-coordinate)
     * @param z the z-coordinate of the section (different from the block z-coordinate)
     * @return the index of the section in the local section array (the same as the one in {@link ChunkRenderContext})
     */
    @Invoker(value = "getLocalSectionIndex")
    static int invokeGetLocalSectionIndex(int x, int y, int z) {
        throw new AssertionError("Implemented By Mixin");
    }
}
