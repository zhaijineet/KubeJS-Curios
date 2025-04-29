package net.zhaiji.kubejscurios.mixin;

import dev.latvian.mods.kubejs.client.KubeJSClient;
import net.minecraft.client.Minecraft;
import net.zhaiji.kubejscurios.kubejs.KubeJSCuriosEventJS;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = KubeJSClient.class, remap = false)
public class KubeJSClientMixin {
    @Inject(method = "reloadClientScripts", at = @At("RETURN"))
    private static void KubeJSCurios$reloadClientScripts(CallbackInfo ci) {
        if (Minecraft.getInstance().player != null) {
            KubeJSCuriosEventJS.loadRegister();
        }
    }
}
