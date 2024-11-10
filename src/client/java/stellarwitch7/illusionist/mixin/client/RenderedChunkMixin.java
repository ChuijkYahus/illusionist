package stellarwitch7.illusionist.mixin.client;

import com.google.common.base.Suppliers;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.WorldChunk;
import stellarwitch7.illusionist.cca.ModChunkComponents;
import stellarwitch7.illusionist.cca.ShadowDisguiseMapComponent;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Supplier;

@Mixin(targets = "net/minecraft/client/render/chunk/RenderedChunk")
public class RenderedChunkMixin {
    @Shadow
    @Final
    private WorldChunk chunk;

    @Unique
    private final Supplier<ShadowDisguiseMapComponent> disguises =
            Suppliers.memoize(() -> ModChunkComponents.SHADOW_DISGUISE_MAP.getNullable(chunk));

    @Inject(
            method = "getBlockState(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/BlockState;",
            at = @At("HEAD"),
            cancellable = true
    )
    private void disguiseBlockState(BlockPos pos, CallbackInfoReturnable<BlockState> cir) {
        if (disguises.get() != null) {
            var funnyState = disguises.get().getFunnyState(pos);
            if (funnyState != null) {
                cir.setReturnValue(funnyState);
            }
        }
    }
}
