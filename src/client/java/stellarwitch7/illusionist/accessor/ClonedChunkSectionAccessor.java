package stellarwitch7.illusionist.accessor;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.block.Block;

import java.util.concurrent.atomic.AtomicReference;

public interface ClonedChunkSectionAccessor {
    /**
     * Gets the shadow block states for this chunk section.
     * @return the shadow block states for this chunk section
     */
    AtomicReference<Int2ObjectOpenHashMap<Block>> illusionist$getBlockStates();
}
