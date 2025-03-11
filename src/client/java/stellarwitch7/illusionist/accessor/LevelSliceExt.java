package stellarwitch7.illusionist.accessor;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.caffeinemc.mods.sodium.client.world.cloned.ChunkRenderContext;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import stellarwitch7.illusionist.mixin.client.sodium.LevelSliceInvoker;

public interface LevelSliceExt {

    /**
     * Gets the x-coordinate of the origin block of this level slice.
     *
     * @return the x-coordinate of the origin block of this level slice
     */
    int getOriginBlockX();

    /**
     * Gets the y-coordinate of the origin block of this level slice.
     *
     * @return the y-coordinate of the origin block of this level slice
     */
    int getOriginBlockY();

    /**
     * Gets the z-coordinate of the origin block of this level slice.
     *
     * @return the z-coordinate of the origin block of this level slice
     */
    int getOriginBlockZ();

    Int2ObjectOpenHashMap<Block> illusionist$getShadowBlockStates(int sectionIndex);

    /**
     * Gets the local section index for the given coordinates
     *
     * @param x the x-coordinate of the section (different from the block x-coordinate)
     * @param y the y-coordinate of the section (different from the block y-coordinate)
     * @param z the z-coordinate of the section (different from the block z-coordinate)
     * @return the index of the section in the local section array (the same as the one in {@link ChunkRenderContext})
     */
    private static int invokeGetLocalSectionIndexInternal(int x, int y, int z) {
        return LevelSliceInvoker.invokeGetLocalSectionIndex(x, y, z);
    }

    private static int getLocalSectionIndexO(int x, int y, int z) {
        return invokeGetLocalSectionIndexInternal(x >> 4, y >> 4, z >> 4);
    }

    default int getLocalSectionIndexO1(int x, int y, int z) {
        return getLocalSectionIndexO(x - getOriginBlockX(), y - getOriginBlockY(), z - getOriginBlockZ());
    }
    default int getLocalSectionIndexO1(BlockPos pos) {
        return getLocalSectionIndexO1(pos.getX(), pos.getY(), pos.getZ());
    }
}
