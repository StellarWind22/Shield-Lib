package com.github.stellarwind22.shieldlib.neoforge.event;

import com.github.stellarwind22.shieldlib.init.ShieldLibEnchantment;
import com.github.stellarwind22.shieldlib.lib.object.ShieldLibTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.TagsUpdatedEvent;

@EventBusSubscriber()
public class OnTagsUpdatedEvent {

    @SubscribeEvent
    public static void onTagsUpdated(TagsUpdatedEvent event) {
        if (event.getUpdateCause() == TagsUpdatedEvent.UpdateCause.SERVER_DATA_LOAD) {
            HolderLookup.Provider lookup = event.getLookupProvider();

            lookup.lookupOrThrow(Registries.ITEM)
                    .get(ShieldLibTags.SHIELD_ENCHANTABLE)
                    .ifPresent(ShieldLibEnchantment::registerAll);
        }
    }
}
