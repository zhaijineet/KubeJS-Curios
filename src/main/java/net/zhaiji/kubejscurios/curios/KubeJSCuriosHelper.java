package net.zhaiji.kubejscurios.curios;

import com.google.common.collect.Multimap;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandlerModifiable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.ISlotType;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

public interface KubeJSCuriosHelper {
    default Optional<ISlotType> getCuriosRegistrySlot(String slot) {
        return CuriosApi.getSlot(slot, ((LivingEntity) this).level());
    }

    default Map<String, ISlotType> getCuriosRegistrySlots() {
        return CuriosApi.getSlots(((LivingEntity) this).level());
    }

    default Map<String, ISlotType> getEntityCuriosRegistrySlots() {
        return CuriosApi.getEntitySlots((LivingEntity) this);
    }

    default LazyOptional<ICurio> getCurioCapability(ItemStack stack) {
        return CuriosApi.getCurio(stack);
    }

    default ICuriosItemHandler getCuriosInventory() {
        return CuriosApi.getCuriosInventory((LivingEntity) this).orElseThrow(()->new IllegalStateException("curios inventory not present : KubeJSCuriosHelper"));
    }

    default Map<String, ICurioStacksHandler> getAllCurios() {
        return this.getCuriosInventory().getCurios();
    }

    default Map<String, ISlotType> getStackInCuriosSlots(ItemStack stack) {
        return CuriosApi.getItemStackSlots(stack, ((LivingEntity) this).level());
    }

    default Optional<ICurioStacksHandler> getCuriosStacksHandler(String slot) {
        return this.getCuriosInventory().getStacksHandler(slot);
    }

    default IItemHandlerModifiable getEquippedCurios() {
        return this.getCuriosInventory().getEquippedCurios();
    }

    default void setEquippedCurio(String slot, int index, ItemStack stack) {
        this.getCuriosInventory().setEquippedCurio(slot, index, stack);
    }

    default boolean isCuriosEquipped(Item item) {
        return this.getCuriosInventory().isEquipped(item);
    }

    default boolean isCuriosEquipped(Predicate<ItemStack> filter) {
        return this.getCuriosInventory().isEquipped(filter);
    }

    default Optional<SlotResult> findFirstCurio(Item item) {
        return this.getCuriosInventory().findFirstCurio(item);
    }

    default Optional<SlotResult> findFirstCurio(Predicate<ItemStack> filter) {
        return this.getCuriosInventory().findFirstCurio(filter);
    }

    default List<SlotResult> findCurios(Item item) {
        return this.getCuriosInventory().findCurios(item);
    }

    default List<SlotResult> findCurios(Predicate<ItemStack> filter) {
        return this.getCuriosInventory().findCurios(filter);
    }

    default List<SlotResult> findCurios(String... slots) {
        return this.getCuriosInventory().findCurios(slots);
    }

    default Optional<SlotResult> findCurio(String slot, int index) {
        return this.getCuriosInventory().findCurio(slot, index);
    }

    default void addCuriosSlotModifier(String slot, UUID uuid, String identifier, double amount, AttributeModifier.Operation operation) {
        this.getCuriosInventory().addPermanentSlotModifier(slot, new UUID(identifier.hashCode(), identifier.hashCode()), identifier, amount, operation);
    }

    default void removeCuriosSlotModifier(String slot, String identifier) {
        this.getCuriosInventory().removeSlotModifier(slot, new UUID(identifier.hashCode(), identifier.hashCode()));
    }

    default void clearCuriosSlotModifiers() {
        this.getCuriosInventory().clearSlotModifiers();
    }

    default Multimap<String, AttributeModifier> getCuriosSlotModifiers() {
        return this.getCuriosInventory().getModifiers();
    }
}
