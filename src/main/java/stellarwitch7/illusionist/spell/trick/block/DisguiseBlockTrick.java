package stellarwitch7.illusionist.spell.trick.block;

import dev.enjarai.trickster.spell.Pattern;
import dev.enjarai.trickster.spell.SpellContext;
import dev.enjarai.trickster.spell.fragment.BlockTypeFragment;
import dev.enjarai.trickster.spell.fragment.BooleanFragment;
import dev.enjarai.trickster.spell.fragment.FragmentType;
import dev.enjarai.trickster.spell.fragment.VectorFragment;
import dev.enjarai.trickster.spell.type.Signature;
import dev.enjarai.trickster.spell.blunder.BlockInvalidBlunder;
import dev.enjarai.trickster.spell.blunder.BlockUnoccupiedBlunder;
import dev.enjarai.trickster.spell.blunder.BlunderException;
import net.minecraft.world.chunk.EmptyChunk;
import stellarwitch7.illusionist.cca.ModChunkComponents;

public class DisguiseBlockTrick extends AbstractBlockDisguiseTrick<DisguiseBlockTrick> {
    public DisguiseBlockTrick() {
        super(Pattern.of(0, 2, 8, 6, 3, 0, 1, 2, 5, 8, 7, 6, 0),
                Signature.of(FragmentType.VECTOR, FragmentType.BLOCK_TYPE, DisguiseBlockTrick::run, FragmentType.BOOLEAN));
    }

    public BooleanFragment run(SpellContext ctx, VectorFragment pos, BlockTypeFragment blockType) throws BlunderException {
        var blockPos = pos.toBlockPos();
        var world = ctx.source().getWorld();

        if (blockType.block().getDefaultState().isAir()) {
            throw new BlockInvalidBlunder(this);
        }

        if (world.getBlockState(blockPos).isAir()) {
            throw new BlockUnoccupiedBlunder(this, pos);
        }

        var chunk = world.getChunk(blockPos);

        if (!(chunk instanceof EmptyChunk)) {
            ctx.useMana(this, 20);

            var component = ModChunkComponents.SHADOW_DISGUISE_MAP.get(chunk);

            if (component.setFunnyState(blockPos, blockType.block())) {
                updateShadow(ctx, blockPos);
                return BooleanFragment.TRUE;
            }
        }

        return BooleanFragment.FALSE;
    }
}
