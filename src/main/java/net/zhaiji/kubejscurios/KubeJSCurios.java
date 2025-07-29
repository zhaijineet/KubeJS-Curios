package net.zhaiji.kubejscurios;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.zhaiji.kubejscurios.event.EventHandler;

@Mod(KubeJSCurios.MODID)
public class KubeJSCurios {
    public static final String MODID = "kubejs_curios";
    public KubeJSCurios(IEventBus modEventBus, ModContainer modContainer) {
        EventHandler.addGameBusListener(NeoForge.EVENT_BUS);
    }
}
