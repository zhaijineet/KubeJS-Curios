package net.zhaiji.kubejscurios.mixin;

import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;
import top.theillusivec4.curios.api.client.ICurioRenderer;

import java.util.Map;
import java.util.function.Supplier;

@Mixin(value = CuriosRendererRegistry.class, remap = false)
public interface CuriosRendererRegistryAccessor {
    @Accessor("RENDERER_REGISTRY")
    public static Map<Item, Supplier<ICurioRenderer>> getRendererRegistry() {
        throw new RuntimeException();
    }

    @Accessor("RENDERERS")
    public static Map<Item, ICurioRenderer> getRenderers() {
        throw new RuntimeException();
    }
}
