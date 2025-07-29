package net.zhaiji.kubejscurios.kubejs;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import dev.latvian.mods.kubejs.client.ClientInitEventJS;
import dev.latvian.mods.kubejs.entity.LivingEntityEventJS;
import net.minecraft.util.Tuple;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.Event;
import net.zhaiji.kubejscurios.curios.CurioRenderer;
import net.zhaiji.kubejscurios.mixin.CuriosRendererRegistryAccessor;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;
import top.theillusivec4.curios.api.client.ICurioRenderer;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class KubeJSCuriosEventJS {
    public static Map<Item, Supplier<ICurioRenderer>> RENDERER_REGISTRY;

    public static void loadRegister() {
        CuriosRendererRegistryAccessor.getRendererRegistry().clear();
        CuriosRendererRegistryAccessor.getRendererRegistry().putAll(RENDERER_REGISTRY);
        KubeJSCuriosEvents.REGISTER_RENDERER.post(new RegisterRenderer());
        CuriosRendererRegistryAccessor.getRenderers().clear();
    }

    public static class RegisterRenderer extends ClientInitEventJS {
        public void register(Item item, Consumer<CurioRenderer.RenderContext> renderer) {
            CuriosRendererRegistry.register(item, () -> new CurioRenderer(renderer));
        }

        public void remove(Item item) {
            CuriosRendererRegistryAccessor.getRendererRegistry().remove(item);
        }
    }

    public static class CurioAttributeModifier extends LivingEntityEventJS {
        public final ItemStack stack;
        public final SlotContext slotContext;
        public final UUID uuid;
        public final Multimap<Attribute, AttributeModifier> originalModifiers;
        private Multimap<Attribute, AttributeModifier> unmodifiableModifiers;
        private Multimap<Attribute, AttributeModifier> modifiableModifiers;

        public CurioAttributeModifier(ItemStack stack, SlotContext slotContext, UUID uuid, Multimap<Attribute, AttributeModifier> modifiers) {
            this.stack = stack;
            this.slotContext = slotContext;
            this.uuid = uuid;
            this.unmodifiableModifiers = this.originalModifiers = modifiers;
        }

        @Override
        public LivingEntity getEntity() {
            return this.slotContext.entity();
        }

        public Multimap<Attribute, AttributeModifier> getModifiers() {
            return this.unmodifiableModifiers;
        }

        private Multimap<Attribute, AttributeModifier> getModifiableMap() {
            if (this.modifiableModifiers == null) {
                this.modifiableModifiers = LinkedHashMultimap.create(this.originalModifiers);
                this.unmodifiableModifiers = Multimaps.unmodifiableMultimap(this.modifiableModifiers);
            }
            return this.modifiableModifiers;
        }

        public boolean addModifier(Attribute attribute, AttributeModifier modifier) {
            return this.getModifiableMap().put(attribute, modifier);
        }

        public boolean removeModifier(Attribute attribute, AttributeModifier modifier) {
            return this.getModifiableMap().remove(attribute, modifier);
        }

        public Collection<AttributeModifier> removeAttribute(Attribute attribute) {
            return this.getModifiableMap().removeAll(attribute);
        }

        public void clearModifiers() {
            this.getModifiableMap().clear();
        }
    }

    public static class CurioChange extends LivingEntityEventJS {
        private final LivingEntity entity;
        public final String slotType;
        public final int index;
        public final ItemStack oldStack;
        public final ItemStack newStack;

        public CurioChange(LivingEntity entity, String slotType, int index, ItemStack oldStack, ItemStack newStack) {
            this.entity = entity;
            this.slotType = slotType;
            this.index = index;
            this.oldStack = oldStack;
            this.newStack = newStack;
        }

        @Override
        public LivingEntity getEntity() {
            return this.entity;
        }
    }

    public static class CurioDrops extends LivingEntityEventJS {
        private final LivingEntity entity;
        public final DamageSource source;
        public final Collection<ItemEntity> drops;
        public final int lootingLevel;
        public final boolean recentlyHit;
        public final ICuriosItemHandler curioHandler;

        public CurioDrops(LivingEntity entity, DamageSource source, Collection<ItemEntity> drops, int lootingLevel, boolean recentlyHit, ICuriosItemHandler curioHandler) {
            this.entity = entity;
            this.source = source;
            this.drops = drops;
            this.lootingLevel = lootingLevel;
            this.recentlyHit = recentlyHit;
            this.curioHandler = curioHandler;
        }

        @Override
        public LivingEntity getEntity() {
            return this.entity;
        }
    }

    public static class CurioEquip extends LivingEntityEventJS {
        private final LivingEntity entity;
        public final SlotContext slotContext;
        public final ItemStack stack;
        public Event.Result result = Event.Result.DEFAULT;

        public CurioEquip(LivingEntity entity, SlotContext slotContext, ItemStack stack) {
            this.entity = entity;
            this.slotContext = slotContext;
            this.stack = stack;
        }

        @Override
        public LivingEntity getEntity() {
            return this.entity;
        }

        public void setResult(Event.Result result) {
            this.result = result;
        }
    }

    public static class CurioUnequip extends LivingEntityEventJS {
        private final LivingEntity entity;
        public final SlotContext slotContext;
        public final ItemStack stack;
        public Event.Result result = Event.Result.DEFAULT;

        public CurioUnequip(LivingEntity entity, SlotContext slotContext, ItemStack stack) {
            this.entity = entity;
            this.slotContext = slotContext;
            this.stack = stack;
        }

        @Override
        public LivingEntity getEntity() {
            return this.entity;
        }

        public void setResult(Event.Result result) {
            this.result = result;
        }
    }

    public static class DropRules extends LivingEntityEventJS {
        private final LivingEntity entity;
        public final DamageSource source;
        public final int lootingLevel;
        public final boolean recentlyHit;
        public final ICuriosItemHandler curioHandler;
        public final List<Tuple<Predicate<ItemStack>, ICurio.DropRule>> overrides = new ArrayList<>();

        public DropRules(LivingEntity entity, DamageSource source, int lootingLevel, boolean recentlyHit, ICuriosItemHandler curioHandler) {
            this.entity = entity;
            this.source = source;
            this.lootingLevel = lootingLevel;
            this.recentlyHit = recentlyHit;
            this.curioHandler = curioHandler;
        }

        @Override
        public LivingEntity getEntity() {
            return this.entity;
        }

        public void addOverride(Predicate<ItemStack> predicate, ICurio.DropRule dropRule) {
            this.overrides.add(new Tuple<>(predicate, dropRule));
        }
    }

    public static class SlotModifiersUpdated extends LivingEntityEventJS {
        private final LivingEntity entity;
        private final Set<String> types;

        public SlotModifiersUpdated(LivingEntity entity, Set<String> types) {
            this.entity = entity;
            this.types = types;
        }

        @Override
        public LivingEntity getEntity() {
            return this.entity;
        }

        public Set<String> getTypes() {
            return ImmutableSet.copyOf(this.types);
        }
    }
}
