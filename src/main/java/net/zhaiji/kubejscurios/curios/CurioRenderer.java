package net.zhaiji.kubejscurios.curios;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

import java.util.function.Consumer;

public class CurioRenderer implements ICurioRenderer {
    public Consumer<RenderContext> renderer;

    public CurioRenderer(Consumer<RenderContext> renderer){
        this.renderer = renderer;
    }

    public static class RenderContext{
        public ItemStack stack;
        public SlotContext slotContext;
        public PoseStack matrixStack;
        public RenderLayerParent<LivingEntity, EntityModel<LivingEntity>> renderLayerParent;
        public MultiBufferSource renderTypeBuffer;
        public int light;
        public float limbSwing;
        public float limbSwingAmount;
        public float partialTicks;
        public float ageInTicks;
        public float netHeadYaw;
        public float headPitch;

        public RenderContext(
                ItemStack stack,
                SlotContext slotContext,
                PoseStack matrixStack,
                RenderLayerParent<LivingEntity, EntityModel<LivingEntity>> renderLayerParent,
                MultiBufferSource renderTypeBuffer,
                int light,
                float limbSwing,
                float limbSwingAmount,
                float partialTicks,
                float ageInTicks,
                float netHeadYaw,
                float headPitch
        ) {
            this.stack = stack;
            this.slotContext = slotContext;
            this.matrixStack = matrixStack;
            this.renderLayerParent = renderLayerParent;
            this.renderTypeBuffer = renderTypeBuffer;
            this.light = light;
            this.limbSwing = limbSwing;
            this.limbSwingAmount = limbSwingAmount;
            this.partialTicks = partialTicks;
            this.ageInTicks = ageInTicks;
            this.netHeadYaw = netHeadYaw;
            this.headPitch = headPitch;
        }
    }

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(
            ItemStack stack,
            SlotContext slotContext,
            PoseStack matrixStack,
            RenderLayerParent<T, M> renderLayerParent,
            MultiBufferSource renderTypeBuffer,
            int light,
            float limbSwing,
            float limbSwingAmount,
            float partialTicks,
            float ageInTicks,
            float netHeadYaw,
            float headPitch
    ) {
        this.renderer.accept(new RenderContext(
                stack,
                slotContext,
                matrixStack,
                (RenderLayerParent<LivingEntity, EntityModel<LivingEntity>>) renderLayerParent,
                renderTypeBuffer,
                light,
                limbSwing,
                limbSwingAmount,
                partialTicks,
                ageInTicks,
                netHeadYaw,
                headPitch
        ));
    }
}
