package net.zhaiji.kubejscurios.mixin;

import dev.latvian.mods.kubejs.item.ItemBuilder;
import net.zhaiji.kubejscurios.curios.CapabilityCurios;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = ItemBuilder.class, remap = false)
public class ItemBuilderMixin {
    public ItemBuilder attachCuriosCapability(CapabilityCurios capabilityCurios) {
        ItemBuilder itemBuilder = (ItemBuilder) (Object) this;
        CapabilityCurios.CuriosCapabilityBuilder.itemBuilders.put(itemBuilder, capabilityCurios.getCapability());
        return itemBuilder;
    }
}
