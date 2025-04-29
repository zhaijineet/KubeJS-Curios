package net.zhaiji.kubejscurios.kubejs;

import dev.latvian.mods.kubejs.client.ClientKubeEvent;
import net.minecraft.world.item.Item;
import net.zhaiji.kubejscurios.curios.CurioRenderer;
import net.zhaiji.kubejscurios.mixin.CuriosRendererRegistryAccessor;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;
import top.theillusivec4.curios.api.client.ICurioRenderer;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class KubeJSCuriosEventJS {
    public static Map<Item, Supplier<ICurioRenderer>> RENDERER_REGISTRY;

    public static void loadRegister() {
        CuriosRendererRegistryAccessor.getRendererRegistry().clear();
        CuriosRendererRegistryAccessor.getRendererRegistry().putAll(RENDERER_REGISTRY);
        KubeJSCuriosEvents.REGISTER_RENDERER.post(new registerRenderer());
        CuriosRendererRegistryAccessor.getRenderers().clear();
        CuriosRendererRegistry.load();
    }

    public static class registerRenderer implements ClientKubeEvent {
        public void register(Item item, Consumer<CurioRenderer.RenderContext> renderer) {
            CuriosRendererRegistry.register(item, () -> new CurioRenderer(renderer));
        }

        public void remove(Item item) {
            CuriosRendererRegistryAccessor.getRendererRegistry().remove(item);
        }
    }
}
