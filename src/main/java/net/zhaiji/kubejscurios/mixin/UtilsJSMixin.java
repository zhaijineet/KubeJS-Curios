package net.zhaiji.kubejscurios.mixin;

import dev.latvian.mods.kubejs.util.UtilsJS;
import net.zhaiji.kubejscurios.curios.CapabilityCurios;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = UtilsJS.class, remap = false)
public class UtilsJSMixin {
    @Inject(method = "postModificationEvents", at = @At("RETURN"))
    private static void KubeJSCurios$postModificationEvents(CallbackInfo ci) {
        CapabilityCurios.CuriosCapabilityBuilder.load();
    }
}
