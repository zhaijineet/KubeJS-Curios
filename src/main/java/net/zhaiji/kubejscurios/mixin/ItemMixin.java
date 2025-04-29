package net.zhaiji.kubejscurios.mixin;

import net.minecraft.world.item.Item;
import net.zhaiji.kubejscurios.curios.CapabilityCurios;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Item.class)
public class ItemMixin {
    public void attachCuriosCapability(CapabilityCurios capabilityCurios) {
        CapabilityCurios.CuriosCapabilityBuilder.items.put((Item) (Object) this, capabilityCurios.getCapability());
    }
}
