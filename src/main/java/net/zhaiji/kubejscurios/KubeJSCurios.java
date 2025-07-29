package net.zhaiji.kubejscurios;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.zhaiji.kubejscurios.event.EventHandler;

@Mod(KubeJSCurios.MOD_ID)
public class KubeJSCurios {
    public static final String MOD_ID = "kubejs_curios";

    public KubeJSCurios() {
        EventHandler.addForgeBusListener(MinecraftForge.EVENT_BUS);
    }
}
