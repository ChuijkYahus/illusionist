package stellarwitch7.illusionist.mixin.client.sodium;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.caffeinemc.mods.sodium.client.render.chunk.compile.pipeline.BlockOcclusionCache;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import stellarwitch7.illusionist.accessor.LevelSliceExt;

import static stellarwitch7.illusionist.cca.ShadowDisguiseMapComponent.encodePos;

@Mixin(value = BlockOcclusionCache.class, remap = false)
public class BlockOcclusionCacheMixin {
    @WrapOperation(method = "shouldDrawSide", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/BlockView;getBlockState(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/BlockState;", remap = true))
    private BlockState wrapGetBlockState(BlockView instance, BlockPos blockPos, Operation<BlockState> original) {
        if (instance instanceof LevelSliceExt accessor) {
            int sectionIndex = accessor.getLocalSectionIndexO1(blockPos);
            var map = accessor.illusionist$getShadowBlockStates(sectionIndex);
            var posIndex = encodePos(blockPos);

            if (map != null && map.containsKey(posIndex)) {
                return map.get(posIndex).getDefaultState();
            }
        }

        return original.call(instance, blockPos);
    }
}
