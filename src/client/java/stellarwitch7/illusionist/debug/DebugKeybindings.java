package stellarwitch7.illusionist.debug;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import stellarwitch7.illusionist.cca.ModChunkComponents;

import java.util.Objects;

public class DebugKeybindings {
    public static final KeyBinding LOG_FUNNY_STATE = KeyBindingHelper.registerKeyBinding(
            new KeyBinding("key.illusionist.log", InputUtil.UNKNOWN_KEY.getCode(), "key.categories.illusionist")
    );

    static {
        ClientTickEvents.END_CLIENT_TICK.register(DebugKeybindings::onClientTick);
    }
    public static void register() {

    }
    private static void onClientTick(MinecraftClient client) {
            // only act if the keybinding was pressed
            if (LOG_FUNNY_STATE.wasPressed()) {
                // consume all remaining presses
                //noinspection StatementWithEmptyBody
                while (LOG_FUNNY_STATE.wasPressed()) ;
                // if the crosshair target is not a block hit result, we are not interested in it, return
                if (!(client.crosshairTarget instanceof BlockHitResult hitResult)) {
                    return;
                }
                //  if world is null, we have bigger things to worry about
                // the current world on the client (should be the one that is rendering)
                var world = Objects.requireNonNull(client.world);
                // the player
                var player = Objects.requireNonNull(client.player);
                // get the disguise map for the chunk that the block is in
                var map = ModChunkComponents.SHADOW_DISGUISE_MAP.get(world.getChunk(hitResult.getBlockPos()));
                // get the funny state of the block at the hit position
                BlockState funnyState = map.getFunnyState(hitResult.getBlockPos());
                // send a message to the player with the funny state
                player.sendMessage(Text.of("Funny state: " + funnyState), false);
            }
    }
}
