package stellarwitch7.illusionist.debug.item;

import dev.enjarai.trickster.net.ModNetworking;
import dev.enjarai.trickster.net.RebuildChunkPacket;
import dev.enjarai.trickster.particle.ModParticles;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import stellarwitch7.illusionist.Illusionist;
import stellarwitch7.illusionist.cca.ModChunkComponents;

import java.util.Objects;

/**
 * A debug item that allows the player to set and clear a "funny state" on a block.
 */
public class SodiumDeathStick extends Item {
    public SodiumDeathStick() {
        super(new Item.Settings().maxCount(1));
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (context.getWorld().isClient()) return ActionResult.SUCCESS;
        var world = (ServerWorld) context.getWorld();
        var chunk = world.getChunk(context.getBlockPos());
        var player = Objects.requireNonNull(context.getPlayer());
        var map = ModChunkComponents.SHADOW_DISGUISE_MAP.get(chunk);
        if (map.getFunnyState(context.getBlockPos()) != null) {
            map.clearFunnyState(context.getBlockPos());
            updateShadow((ServerWorld) context.getWorld(), context.getBlockPos());
            // "Cleared funny state"
            player.sendMessage(Text.translatable(Illusionist.MOD_ID + ".debug.message.clear_state"), true);
        } else {
            map.setFunnyState(context.getBlockPos(), Blocks.AMETHYST_BLOCK);
            updateShadow((ServerWorld) context.getWorld(), context.getBlockPos());
            // "Set funny state"
            player.sendMessage(Text.translatable(Illusionist.MOD_ID + ".debug.message.set_state"), true);
        }
        return ActionResult.SUCCESS;
    }

    private static void updateShadow(ServerWorld world, BlockPos blockPos) {
        ModNetworking.CHANNEL.serverHandle(world, blockPos).send(new RebuildChunkPacket(blockPos));

        var particlePos = blockPos.toCenterPos();
        world.spawnParticles(ModParticles.PROTECTED_BLOCK, particlePos.x, particlePos.y, particlePos.z, 1, 0, 0, 0, 0);
    }
}
