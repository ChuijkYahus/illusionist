package stellarwitch7.illusionist.spell.trick;

import dev.enjarai.trickster.spell.trick.Trick;
import dev.enjarai.trickster.spell.trick.Tricks;
import net.minecraft.registry.Registry;
import stellarwitch7.illusionist.Illusionist;
import stellarwitch7.illusionist.spell.trick.block.DisguiseBlockTrick;
import stellarwitch7.illusionist.spell.trick.block.DispelBlockDisguiseTrick;

public class ModTricks {
    public static final DisguiseBlockTrick DISGUISE_BLOCK = register("disguise_block", new DisguiseBlockTrick());
    public static final DispelBlockDisguiseTrick DISPEL_BLOCK_DISGUISE = register("dispel_block_disguise", new DispelBlockDisguiseTrick());

    public static <T extends Trick<T>> T register(String path, T trick) {
        return Registry.register(Tricks.REGISTRY, Illusionist.id(path), trick);
    }

    public static void register() {
        // init statics
    }
}
