package net.zhaiji.kubejscurios.kubejs;

import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.script.BindingsEvent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.zhaiji.kubejscurios.curios.CapabilityCurios;
import net.zhaiji.kubejscurios.mixin.CuriosRendererRegistryAccessor;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.client.ICurioRenderer;

import java.util.HashMap;

public class KubeJSCuriosPlugin extends KubeJSPlugin {
    @Override
    public void registerEvents() {
        KubeJSCuriosEvents.GROUP.register();
    }

    @Override
    public void afterInit() {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            KubeJSCuriosEventJS.RENDERER_REGISTRY = new HashMap<>(CuriosRendererRegistryAccessor.getRendererRegistry());
            KubeJSCuriosEventJS.loadRegister();
        });
        CapabilityCurios.CuriosCapabilityBuilder.load();
    }

    @Override
    public void registerBindings(BindingsEvent event) {
        event.add("CuriosJSCapabilityBuilder", CapabilityCurios.CuriosCapabilityBuilder.INSTANCE);
        event.add("CuriosApi", CuriosApi.class);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            event.add("CuriosRenderer", ICurioRenderer.class);
            event.add("ModelResourceLocation", ModelResourceLocation.class);
            event.add("OverlayTexture", OverlayTexture.class);
        });
    }
}
