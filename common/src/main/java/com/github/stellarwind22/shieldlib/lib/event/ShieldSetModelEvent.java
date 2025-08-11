package com.github.stellarwind22.shieldlib.lib.event;

import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;
import net.minecraft.client.model.geom.EntityModelSet;

@Deprecated
public interface ShieldSetModelEvent {

    Event<SetModel> EVENT = EventFactory.createLoop();

    interface SetModel {
        public void onSetModel(EntityModelSet loader);
    }
}
