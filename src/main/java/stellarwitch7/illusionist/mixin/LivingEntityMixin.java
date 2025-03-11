package stellarwitch7.illusionist.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.EmptyChunk;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import stellarwitch7.illusionist.cca.ModChunkComponents;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    @Unique
    private static final StatusEffectInstance PERM_BLINDNESS = new StatusEffectInstance(StatusEffects.BLINDNESS, 100, 2);

    @Unique
    private boolean inShadowBlock;

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @ModifyReturnValue(
            method = "hasStatusEffect",
            at = @At("RETURN")
    )
    public boolean hasStatusEffect(boolean original, RegistryEntry<StatusEffect> effect) {
        if (!(((LivingEntity)(Object)this) instanceof PlayerEntity))
            return original;

        if (effect == StatusEffects.BLINDNESS) {
            return original || inShadowBlock;
        }

        return original;
    }

    @Nullable
    @ModifyReturnValue(
            method = "getStatusEffect",
            at = @At("RETURN")
    )
    public StatusEffectInstance getStatusEffect(StatusEffectInstance original, RegistryEntry<StatusEffect> effect) {
        if (!(((LivingEntity)(Object)this) instanceof PlayerEntity))
            return original;

        if (effect == StatusEffects.BLINDNESS && original == null && inShadowBlock) {
            return PERM_BLINDNESS;
        }

        return original;
    }

    @Inject(
            method = "tick",
            at = @At("TAIL")
    )
    private void amIBlind(CallbackInfo ci) {
        if ((Object) this instanceof PlayerEntity) {
            inShadowBlock = inShadowBlock(getWorld(), BlockPos.ofFloored(this.getEyePos()));
        }
    }

    @Unique
    private static boolean inShadowBlock(World world, BlockPos blockPos) {
        var chunk = world.getChunk(blockPos);

        if (chunk instanceof EmptyChunk)
            return false;

        var shadowBlocks = ModChunkComponents.SHADOW_DISGUISE_MAP.get(chunk);
        var funnyState = shadowBlocks.getFunnyState(blockPos);

        return funnyState != null && funnyState.isSolidBlock(world, blockPos);
    }
}
