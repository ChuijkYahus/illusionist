package stellarwitch7.illusionist.debug.item;

import dev.enjarai.trickster.Trickster;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

import static stellarwitch7.illusionist.Illusionist.id;

public class DebugItems {
    public static final Item SODIUM_DEATH_STICK =
            Registry.register(
                    Registries.ITEM,
                    id("sodium_death_stick"),
                    new SodiumDeathStick()
            );

    public static void register() {
        // Register the item in the Trickster item group
        ItemGroupEvents.modifyEntriesEvent(
                RegistryKey.of(RegistryKeys.ITEM_GROUP, Trickster.id("trickster"))
        ).register((entries) -> entries.add(DebugItems.SODIUM_DEATH_STICK));
    }
}
