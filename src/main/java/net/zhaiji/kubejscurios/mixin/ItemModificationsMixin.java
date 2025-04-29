package net.zhaiji.kubejscurios.mixin;

import dev.latvian.mods.kubejs.item.ItemModificationKubeEvent;
import net.zhaiji.kubejscurios.curios.CapabilityCurios;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = ItemModificationKubeEvent.ItemModifications.class,remap = false)
public class ItemModificationsMixin {
    public void attachCuriosCapability(CapabilityCurios capabilityCurios) {
        CapabilityCurios.CuriosCapabilityBuilder.itemModifications.put(((ItemModificationKubeEvent.ItemModifications) (Object) this), capabilityCurios.getCapability());
    }
}
