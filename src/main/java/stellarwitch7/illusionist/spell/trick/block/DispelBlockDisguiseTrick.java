package stellarwitch7.illusionist.spell.trick.block;

import dev.enjarai.trickster.spell.Pattern;
import dev.enjarai.trickster.spell.SpellContext;
import dev.enjarai.trickster.spell.fragment.BooleanFragment;
import dev.enjarai.trickster.spell.fragment.FragmentType;
import dev.enjarai.trickster.spell.fragment.VectorFragment;
import dev.enjarai.trickster.spell.type.Signature;
import dev.enjarai.trickster.spell.blunder.BlockUnoccupiedBlunder;
import dev.enjarai.trickster.spell.blunder.BlunderException;
import net.minecraft.world.chunk.EmptyChunk;
import stellarwitch7.illusionist.cca.ModChunkComponents;

public class DispelBlockDisguiseTrick extends AbstractBlockDisguiseTrick<DispelBlockDisguiseTrick> {
    public DispelBlockDisguiseTrick() {
        super(Pattern.of(0, 4, 8, 5, 2, 4, 6, 3, 0, 1, 4, 7, 8),
                Signature.of(FragmentType.VECTOR, DispelBlockDisguiseTrick::run, FragmentType.BOOLEAN));
    }

    public BooleanFragment run(SpellContext ctx, VectorFragment pos) throws BlunderException {
        var blockPos = pos.toBlockPos();
        var world = ctx.source().getWorld();

        if (world.getBlockState(blockPos).isAir()) {
            throw new BlockUnoccupiedBlunder(this, pos);
        }

        var chunk = world.getChunk(blockPos);

        if (!(chunk instanceof EmptyChunk)) {
            ctx.useMana(this, 10);

            var component = ModChunkComponents.SHADOW_DISGUISE_MAP.get(chunk);

            if (component.clearFunnyState(blockPos)) {
                updateShadow(ctx, blockPos);
                return BooleanFragment.TRUE;
            }
        }

        return BooleanFragment.FALSE;
    }
}
