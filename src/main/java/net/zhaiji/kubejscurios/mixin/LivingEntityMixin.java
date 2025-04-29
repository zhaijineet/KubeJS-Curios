package net.zhaiji.kubejscurios.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.zhaiji.kubejscurios.curios.KubeJSCuriosHelper;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LivingEntity.class)
public class LivingEntityMixin implements KubeJSCuriosHelper {
}
