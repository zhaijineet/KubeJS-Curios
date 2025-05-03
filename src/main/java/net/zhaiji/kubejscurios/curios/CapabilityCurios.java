package net.zhaiji.kubejscurios.curios;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import dev.latvian.mods.kubejs.item.ItemBuilder;
import dev.latvian.mods.kubejs.item.ItemModificationKubeEvent;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

public class CapabilityCurios {
    private BiConsumer<SlotContext,ItemStack> curioTick;
    private EquipConsumer onEquip;
    private EquipConsumer onUnequip;
    private BiPredicate<SlotContext,ItemStack> canEquip;
    private BiPredicate<SlotContext,ItemStack> canUnequip;
    private SlotsTooltipFunction slotsTooltip;
    private final Multimap<ResourceKey<Attribute>, AttributeModifier> modifiers = HashMultimap.create();
    private final Multimap<Holder<Attribute>, AttributeModifier> attributes = HashMultimap.create();
    private boolean attributeInit = false;
    private Consumer<AttributeModificationContext> modifyAttribute;
    private BiConsumer<SlotContext, ItemStack> onEquipFromUse;
    private BiFunction<SlotContext, ItemStack, ICurio.SoundInfo> modifyEquipSound;
    private BiPredicate<SlotContext, ItemStack> canEquipFromUse;
    private DropRulePredicate canDrop;
    private AttributesTooltipFunction attributesTooltip;
    private FortuneFunction modifyFortuneLevel;
    private LootingFunction modifyLootingLevel;
    private BiPredicate<SlotContext, ItemStack> makesPiglinsNeutral;
    private BiPredicate<SlotContext, ItemStack> canWalkOnPowderedSnow;
    private EnderMaskPredicate isEnderMask;

    @FunctionalInterface
    public interface EquipConsumer {
        void accept(SlotContext slotContext, ItemStack oldStack, ItemStack newStack);
    }

    @FunctionalInterface
    public interface SlotsTooltipFunction {
        List<Component> apply(List<Component> tooltips, Item.TooltipContext context, ItemStack stack);
    }

    @FunctionalInterface
    public interface DropRulePredicate {
        boolean test(SlotContext slotContext, DamageSource source, boolean recentlyHit, ItemStack stack);
    }

    @FunctionalInterface
    public interface AttributesTooltipFunction{
        List<Component> apply(List<Component> tooltips, Item.TooltipContext context, ItemStack stack);
    }

    @FunctionalInterface
    public interface FortuneFunction {
        int apply(SlotContext slotContext, LootContext lootContext, ItemStack stack);
    }

    @FunctionalInterface
    public interface LootingFunction {
        int apply(SlotContext slotContext, @Nullable LootContext lootContext, ItemStack stack);
    }

    @FunctionalInterface
    public interface EnderMaskPredicate {
        boolean test(SlotContext slotContext, EnderMan enderMan, ItemStack stack);
    }

    public CapabilityCurios curioTick(BiConsumer<SlotContext,ItemStack> curioTick) {
        this.curioTick = curioTick;
        return this;
    }

    public CapabilityCurios onEquip(EquipConsumer onEquip) {
        this.onEquip = onEquip;
        return this;
    }

    public CapabilityCurios onUnequip(EquipConsumer onUnequip) {
        this.onUnequip = onUnequip;
        return this;
    }

    public CapabilityCurios canEquip(BiPredicate<SlotContext, ItemStack> canEquip) {
        this.canEquip = canEquip;
        return this;
    }

    public CapabilityCurios canUnequip(BiPredicate<SlotContext, ItemStack> canUnequip) {
        this.canUnequip = canUnequip;
        return this;
    }

    public CapabilityCurios modifySlotsTooltip(SlotsTooltipFunction slotsTooltip) {
        this.slotsTooltip = slotsTooltip;
        return this;
    }

    public CapabilityCurios addAttribute(ResourceKey<Attribute> attribute, ResourceLocation identifier, double amount, AttributeModifier.Operation operation) {
        this.modifiers.put(attribute, new AttributeModifier(identifier, amount, operation));
        return this;
    }

    public CapabilityCurios modifyAttribute(Consumer<AttributeModificationContext> modifyAttribute) {
        this.modifyAttribute = modifyAttribute;
        return this;
    }

    public CapabilityCurios onEquipFromUse(BiConsumer<SlotContext, ItemStack> onEquipFromUse) {
        this.onEquipFromUse = onEquipFromUse;
        return this;
    }

    public CapabilityCurios modifyEquipSound(BiFunction<SlotContext, ItemStack, ICurio.SoundInfo> modifyEquipSound) {
        this.modifyEquipSound = modifyEquipSound;
        return this;
    }

    public CapabilityCurios canEquipFromUse(BiPredicate<SlotContext, ItemStack> canEquipFromUse) {
        this.canEquipFromUse = canEquipFromUse;
        return this;
    }

    public CapabilityCurios canDrop(DropRulePredicate canDrop) {
        this.canDrop = canDrop;
        return this;
    }

    public CapabilityCurios modifyAttributesTooltip(AttributesTooltipFunction attributesTooltip) {
        this.attributesTooltip = attributesTooltip;
        return this;
    }

    public CapabilityCurios modifyFortuneLevel(FortuneFunction modifyFortuneLevel) {
        this.modifyFortuneLevel = modifyFortuneLevel;
        return this;
    }

    public CapabilityCurios modifyLootingLevel(LootingFunction modifyLootingLevel) {
        this.modifyLootingLevel = modifyLootingLevel;
        return this;
    }

    public CapabilityCurios makesPiglinsNeutral(BiPredicate<SlotContext, ItemStack> makesPiglinsNeutral) {
        this.makesPiglinsNeutral = makesPiglinsNeutral;
        return this;
    }

    public CapabilityCurios canWalkOnPowderedSnow(BiPredicate<SlotContext, ItemStack> canWalkOnPowderedSnow) {
        this.canWalkOnPowderedSnow = canWalkOnPowderedSnow;
        return this;
    }

    public CapabilityCurios isEnderMask(EnderMaskPredicate isEnderMask) {
        this.isEnderMask = isEnderMask;
        return this;
    }

    @HideFromJS
    public ICurioItem getCapability(){
        return new ICurioItem() {
            @Override
            public void curioTick(SlotContext slotContext, ItemStack stack) {
                if (curioTick != null) {
                    curioTick.accept(slotContext, stack);
                } else {
                    ICurioItem.super.curioTick(slotContext, stack);
                }
            }

            @Override
            public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
                if (onEquip != null) {
                    onEquip.accept(slotContext, prevStack, stack);
                } else {
                    ICurioItem.super.onEquip(slotContext, prevStack, stack);
                }
            }

            @Override
            public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
                if (onUnequip != null) {
                    onUnequip.accept(slotContext, stack, newStack);
                } else {
                    ICurioItem.super.onUnequip(slotContext, newStack, stack);
                }
            }

            @Override
            public boolean canEquip(SlotContext slotContext, ItemStack stack) {
                if (canEquip != null) {
                    return canEquip.test(slotContext, stack);
                }
                return ICurioItem.super.canEquip(slotContext, stack);
            }

            @Override
            public boolean canUnequip(SlotContext slotContext, ItemStack stack) {
                if (canUnequip != null) {
                    return canUnequip.test(slotContext, stack);
                }
                return ICurioItem.super.canUnequip(slotContext, stack);
            }

            @Override
            public List<Component> getSlotsTooltip(List<Component> tooltips, Item.TooltipContext context, ItemStack stack) {
                if (slotsTooltip != null) {
                    return slotsTooltip.apply(tooltips, context, stack);
                }
                return ICurioItem.super.getSlotsTooltip(tooltips, context, stack);
            }

            @Override
            public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation identifier, ItemStack stack) {
                if(!attributeInit){
                    for (Map.Entry<ResourceKey<Attribute>, AttributeModifier> entry : modifiers.entries()) {
                        ResourceKey<Attribute> key = entry.getKey();
                        AttributeModifier value = entry.getValue();
                        attributes.put(DeferredHolder.create(key), value);
                    }
                    attributeInit = true;
                }
                if (modifyAttribute != null) {
                    Multimap<Holder<Attribute>, AttributeModifier> tempAttributes = HashMultimap.create(attributes);
                    modifyAttribute.accept(new AttributeModificationContext(slotContext, identifier, stack, tempAttributes));
                    return tempAttributes;
                }
                return attributes;
            }

            @Override
            public void onEquipFromUse(SlotContext slotContext, ItemStack stack) {
                if (onEquipFromUse != null) {
                    onEquipFromUse.accept(slotContext, stack);
                } else {
                    ICurioItem.super.onEquipFromUse(slotContext, stack);
                }
            }

            @NotNull
            @Override
            public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
                if (modifyEquipSound != null) {
                    return modifyEquipSound.apply(slotContext, stack);
                }
                return ICurioItem.super.getEquipSound(slotContext, stack);
            }

            @Override
            public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
                if (canEquipFromUse != null) {
                    return canEquipFromUse.test(slotContext, stack);
                }
                return true;
            }

            @NotNull
            @Override
            public ICurio.DropRule getDropRule(SlotContext slotContext, DamageSource source, boolean recentlyHit, ItemStack stack) {
                if (canDrop != null) {
                    return canDrop.test(slotContext, source, recentlyHit, stack) ? ICurio.DropRule.ALWAYS_DROP : ICurio.DropRule.ALWAYS_KEEP;
                }
                return ICurioItem.super.getDropRule(slotContext, source, recentlyHit, stack);
            }

            @Override
            public List<Component> getAttributesTooltip(List<Component> tooltips, Item.TooltipContext context, ItemStack stack) {
                if (attributesTooltip != null) {
                    return attributesTooltip.apply(tooltips, context, stack);
                }
                return ICurioItem.super.getAttributesTooltip(tooltips, context, stack);
            }

            @Override
            public int getFortuneLevel(SlotContext slotContext, LootContext lootContext, ItemStack stack) {
                if (modifyFortuneLevel != null) {
                    return modifyFortuneLevel.apply(slotContext, lootContext, stack);
                }
                return ICurioItem.super.getFortuneLevel(slotContext, lootContext, stack);
            }

            @Override
            public int getLootingLevel(SlotContext slotContext, @Nullable LootContext lootContext, ItemStack stack) {
                if (modifyLootingLevel != null) {
                    return modifyLootingLevel.apply(slotContext, lootContext, stack);
                }
                return ICurioItem.super.getLootingLevel(slotContext, lootContext, stack);
            }

            @Override
            public boolean makesPiglinsNeutral(SlotContext slotContext, ItemStack stack) {
                if (makesPiglinsNeutral != null) {
                    return makesPiglinsNeutral.test(slotContext, stack);
                }
                return ICurioItem.super.makesPiglinsNeutral(slotContext, stack);
            }

            @Override
            public boolean canWalkOnPowderedSnow(SlotContext slotContext, ItemStack stack) {
                if (canWalkOnPowderedSnow != null) {
                    return canWalkOnPowderedSnow.test(slotContext, stack);
                }
                return ICurioItem.super.canWalkOnPowderedSnow(slotContext, stack);
            }

            @Override
            public boolean isEnderMask(SlotContext slotContext, EnderMan enderMan, ItemStack stack) {
                if (isEnderMask != null) {
                    return isEnderMask.test(slotContext, enderMan, stack);
                }
                return ICurioItem.super.isEnderMask(slotContext, enderMan, stack);
            }
        };
    }

    public static class AttributeModificationContext {
        private final SlotContext slotContext;
        private final ResourceLocation identifier;
        private final ItemStack stack;
        private final Multimap<Holder<Attribute>, AttributeModifier> modifiers;

        public SlotContext getSlotContext() {
            return slotContext;
        }

        public ResourceLocation getIdentifier() {
            return identifier;
        }

        public ItemStack getStack() {
            return stack;
        }

        public Multimap<Holder<Attribute>, AttributeModifier> getModifiers() {
            return modifiers;
        }

        public AttributeModificationContext(SlotContext slotContext, ResourceLocation identifier, ItemStack stack, Multimap<Holder<Attribute>, AttributeModifier> modifiers) {
            this.slotContext = slotContext;
            this.identifier = identifier;
            this.stack = stack;
            this.modifiers = modifiers;
        }

        public AttributeModificationContext modify(Holder<Attribute> attribute, ResourceLocation identifier, double amount, AttributeModifier.Operation operation) {
            this.modifiers.put(attribute, new AttributeModifier(identifier, amount, operation));
            return this;
        }

        public AttributeModificationContext remove(Holder<Attribute> attribute, ResourceLocation identifier) {
            this.modifiers.get(attribute).removeIf(modifier -> modifier.id().equals(identifier));
            return this;
        }
    }

    public static class CuriosCapabilityBuilder {
        public static CuriosCapabilityBuilder INSTANCE = new CuriosCapabilityBuilder();
        public static final Map<ItemBuilder, ICurioItem> itemBuilders = new HashMap<>();
        public static final Map<ItemModificationKubeEvent.ItemModifications, ICurioItem> itemModifications = new HashMap<>();

        public static void load() {
            itemBuilders.forEach((itemBuilder, iCurioItem) -> {
                CuriosApi.registerCurio(itemBuilder.get(), iCurioItem);
            });

            itemModifications.forEach((itemModification, iCurioItem) -> {
                CuriosApi.registerCurio(itemModification.item(), iCurioItem);
            });
        }

        public CapabilityCurios create() {
            return new CapabilityCurios();
        }
    }
}
