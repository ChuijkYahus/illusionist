package stellarwitch7.illusionist.mixin.client.sodium;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.caffeinemc.mods.sodium.client.render.chunk.RenderSection;
import net.caffeinemc.mods.sodium.client.render.chunk.compile.ChunkBuildOutput;
import net.caffeinemc.mods.sodium.client.render.chunk.compile.tasks.ChunkBuilderMeshingTask;
import net.caffeinemc.mods.sodium.client.render.chunk.compile.tasks.ChunkBuilderTask;
import net.caffeinemc.mods.sodium.client.world.LevelSlice;
import net.caffeinemc.mods.sodium.client.world.cloned.ChunkRenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import org.joml.Vector3dc;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import stellarwitch7.illusionist.accessor.ClonedChunkSectionAccessor;

@Debug(export = true)
@Mixin(ChunkBuilderMeshingTask.class)
public abstract class ChunkBuilderMeshingTaskMixin extends ChunkBuilderTask<ChunkBuildOutput> {
    @Shadow(remap = false)
    @Final
    private ChunkRenderContext renderContext;

    public ChunkBuilderMeshingTaskMixin(RenderSection render, int time, Vector3dc absoluteCameraPos) {
        super(render, time, absoluteCameraPos);
    }

    @WrapOperation(
            method = "execute(Lnet/caffeinemc/mods/sodium/client/render/chunk/compile/ChunkBuildContext;Lnet/caffeinemc/mods/sodium/client/util/task/CancellationToken;)Lnet/caffeinemc/mods/sodium/client/render/chunk/compile/ChunkBuildOutput;",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/caffeinemc/mods/sodium/client/world/LevelSlice;getBlockState(III)Lnet/minecraft/block/BlockState;"
            )
    )
    private BlockState wrapGetBlockState(LevelSlice instance, int x, int y, int z, Operation<BlockState> original) {
        BlockState state = original.call(instance, x, y, z);
        int relBlockX = x - ((LevelSliceAccessor)(Object)instance).getOriginBlockX();
        @SuppressWarnings("DataFlowIssue")
        int relBlockY = y - ((LevelSliceAccessor)(Object)instance).getOriginBlockY();
        @SuppressWarnings("DataFlowIssue")
        int relBlockZ = z - ((LevelSliceAccessor)(Object)instance).getOriginBlockZ();
        var map = ((ClonedChunkSectionAccessor) this.renderContext.getSections()[LevelSliceAccessor.invokeGetLocalSectionIndex(relBlockX >> 4, relBlockY >> 4, relBlockZ >> 4)]).illusionist$getBlockStates();
        if (map.get().containsKey(encodePos(x, y, z))) {
            state = map.get().get(encodePos(x, y, z)).getDefaultState();
        }
        return state;
    }

    @Unique
    private static int encodePos(int x, int y, int z) {
        var xe = x & 15;
        var ze = (z & 15) << 4;
        var ye = y << 8;
        return ye | ze | xe;
    }
}
