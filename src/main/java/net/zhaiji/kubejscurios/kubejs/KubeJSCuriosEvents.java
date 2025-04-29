package net.zhaiji.kubejscurios.kubejs;

import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;

public interface KubeJSCuriosEvents {
    EventGroup GROUP = EventGroup.of("CuriosJSEvents");

    EventHandler REGISTER_RENDERER = GROUP.client("registerRenderer", () -> KubeJSCuriosEventJS.registerRenderer.class);
}
