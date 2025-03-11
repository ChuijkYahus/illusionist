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
import org.joml.Vector3dc;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import stellarwitch7.illusionist.accessor.ClonedChunkSectionAccessor;
import stellarwitch7.illusionist.accessor.LevelSliceExt;

import static stellarwitch7.illusionist.cca.ShadowDisguiseMapComponent.encodePos;

@Mixin(value = ChunkBuilderMeshingTask.class, remap = false)
public abstract class ChunkBuilderMeshingTaskMixin extends ChunkBuilderTask<ChunkBuildOutput> {
    @Shadow(remap = false)
    @Final
    private ChunkRenderContext renderContext;

    public ChunkBuilderMeshingTaskMixin(RenderSection render, int time, Vector3dc absoluteCameraPos) {
        super(render, time, absoluteCameraPos);
    }

    @WrapOperation(method = "execute(Lnet/caffeinemc/mods/sodium/client/render/chunk/compile/ChunkBuildContext;Lnet/caffeinemc/mods/sodium/client/util/task/CancellationToken;)Lnet/caffeinemc/mods/sodium/client/render/chunk/compile/ChunkBuildOutput;", at = @At(value = "INVOKE", target = "Lnet/caffeinemc/mods/sodium/client/world/LevelSlice;getBlockState(III)Lnet/minecraft/block/BlockState;"))
    private BlockState wrapGetBlockState(LevelSlice instance, int x, int y, int z, Operation<BlockState> original) {
        BlockState state = original.call(instance, x, y, z);
        //noinspection ConstantValue
        if ((Object) instance instanceof LevelSliceExt accessor) {
            int localSectionIndex = accessor.getLocalSectionIndexO1(x, y, z);
            var map = ((ClonedChunkSectionAccessor) this.renderContext.getSections()[localSectionIndex]).illusionist$getBlockStates();
            int posIndex = encodePos(x, y, z);
            if (map.containsKey(posIndex)) {
                state = map.get(posIndex).getDefaultState();
            }
        }
        return state;
    }
}
