package net.zhaiji.kubejscurios.kubejs;

import dev.latvian.mods.kubejs.event.EventGroupRegistry;
import dev.latvian.mods.kubejs.plugin.KubeJSPlugin;
import dev.latvian.mods.kubejs.script.BindingRegistry;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.neoforged.fml.loading.FMLEnvironment;
import net.zhaiji.kubejscurios.curios.CapabilityCurios;
import net.zhaiji.kubejscurios.mixin.CuriosRendererRegistryAccessor;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.client.ICurioRenderer;

import java.util.HashMap;

public class KubeJSCuriosPlugin implements KubeJSPlugin {
    @Override
    public void registerEvents(EventGroupRegistry registry) {
        registry.register(KubeJSCuriosEvents.GROUP);
    }

    @Override
    public void afterInit() {
        if (FMLEnvironment.dist.isClient()) {
            KubeJSCuriosEventJS.RENDERER_REGISTRY = new HashMap<>(CuriosRendererRegistryAccessor.getRendererRegistry());
            KubeJSCuriosEventJS.loadRegister();
        }
    }

    @Override
    public void registerBindings(BindingRegistry bindings) {
        bindings.add("CuriosJSCapabilityBuilder", CapabilityCurios.CuriosCapabilityBuilder.INSTANCE);
        bindings.add("CuriosApi", CuriosApi.class);
        if (FMLEnvironment.dist.isClient()) {
            bindings.add("CuriosRenderer", ICurioRenderer.class);
            bindings.add("ModelResourceLocation", ModelResourceLocation.class);
            bindings.add("OverlayTexture", OverlayTexture.class);
        }
    }
}
