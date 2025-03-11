package stellarwitch7.illusionist.spell.trick.block;

import dev.enjarai.trickster.net.ModNetworking;
import dev.enjarai.trickster.net.RebuildChunkPacket;
import dev.enjarai.trickster.particle.ModParticles;
import dev.enjarai.trickster.spell.Pattern;
import dev.enjarai.trickster.spell.SpellContext;
import dev.enjarai.trickster.spell.trick.Trick;
import dev.enjarai.trickster.spell.type.Signature;
import net.minecraft.util.math.BlockPos;

public abstract class AbstractBlockDisguiseTrick<T extends AbstractBlockDisguiseTrick<T>> extends Trick<T> {
    public AbstractBlockDisguiseTrick(Pattern pattern) {
        super(pattern);
    }

    public AbstractBlockDisguiseTrick(Pattern pattern, Signature<T> primary) {
        super(pattern, primary);
    }

    protected static void updateShadow(SpellContext ctx, BlockPos blockPos) {
        ModNetworking.CHANNEL.serverHandle(ctx.source().getWorld(), blockPos).send(new RebuildChunkPacket(blockPos));

        var particlePos = blockPos.toCenterPos();
        ctx.source().getWorld().spawnParticles(
                ModParticles.PROTECTED_BLOCK, particlePos.x, particlePos.y, particlePos.z,
                1, 0, 0, 0, 0);
    }
}
