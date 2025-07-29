package net.zhaiji.kubejscurios.event;

import dev.latvian.mods.kubejs.event.EventResult;
import dev.latvian.mods.kubejs.script.ScriptType;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.IEventBus;
import net.zhaiji.kubejscurios.kubejs.KubeJSCuriosEventJS;
import net.zhaiji.kubejscurios.kubejs.KubeJSCuriosEvents;
import top.theillusivec4.curios.api.event.*;

public class EventHandler {
    public static void addGameBusListener(IEventBus gameEventBus) {
        gameEventBus.addListener(EventHandler::CurioAttributeModifierEvent);
        gameEventBus.addListener(EventHandler::CurioCanEquipEvent);
        gameEventBus.addListener(EventHandler::CurioCanUnequipEvent);
        gameEventBus.addListener(EventHandler::CurioChangeEvent);
        gameEventBus.addListener(EventHandler::CurioDropsEvent);
        gameEventBus.addListener(EventHandler::DropRulesEvent);
        gameEventBus.addListener(EventHandler::SlotModifiersUpdatedEvent);
    }

    public static void CurioAttributeModifierEvent(CurioAttributeModifierEvent event) {
        LivingEntity entity = event.getSlotContext().entity();
        ScriptType scriptType = entity != null && !entity.level().isClientSide() ? ScriptType.SERVER : ScriptType.CLIENT;
        KubeJSCuriosEvents.ATTRIBUTE_MODIFIER.post(
                scriptType,
                new KubeJSCuriosEventJS.CurioAttributeModifier(
                        event.getItemStack(),
                        event.getSlotContext(),
                        event.getId(),
                        event.getModifiers()
                )
        );
    }

    public static void CurioCanEquipEvent(CurioCanEquipEvent event) {
        ScriptType scriptType = event.getEntity().level().isClientSide() ? ScriptType.CLIENT : ScriptType.SERVER;
        KubeJSCuriosEventJS.CurioCanEquip evt = new KubeJSCuriosEventJS.CurioCanEquip(
                event.getEntity(),
                event.getSlotContext(),
                event.getStack(),
                event.getEquipResult()
        );
        KubeJSCuriosEvents.CAN_EQUIP.post(
                scriptType,
                evt
        );
        event.setEquipResult(evt.result);
    }

    public static void CurioCanUnequipEvent(CurioCanUnequipEvent event) {
        ScriptType scriptType = event.getEntity().level().isClientSide() ? ScriptType.CLIENT : ScriptType.SERVER;
        KubeJSCuriosEventJS.CurioCanUnequip evt = new KubeJSCuriosEventJS.CurioCanUnequip(
                event.getEntity(),
                event.getSlotContext(),
                event.getStack(),
                event.getUnequipResult()
        );
        KubeJSCuriosEvents.CAN_UNEQUIP.post(
                scriptType,
                evt
        );
        event.setUnequipResult(evt.result);
    }

    public static void CurioChangeEvent(CurioChangeEvent event) {
        KubeJSCuriosEvents.CHANGE.post(
                new KubeJSCuriosEventJS.CurioChange(
                        event.getEntity(),
                        event.getIdentifier(),
                        event.getSlotIndex(),
                        event.getFrom(),
                        event.getTo()
                )
        );
    }

    public static void CurioDropsEvent(CurioDropsEvent event) {
        EventResult eventResult = KubeJSCuriosEvents.DROPS.post(
                new KubeJSCuriosEventJS.CurioDrops(
                        event.getEntity(),
                        event.getSource(),
                        event.getDrops(),
                        event.getLootingLevel(),
                        event.isRecentlyHit(),
                        event.getCurioHandler()
                )
        );
        if (eventResult.interruptFalse()) {
            event.setCanceled(true);
        }
    }

    public static void DropRulesEvent(DropRulesEvent event) {
        KubeJSCuriosEvents.DROP_RULES.post(
                new KubeJSCuriosEventJS.DropRules(
                        event.getEntity(),
                        event.getSource(),
                        event.getLootingLevel(),
                        event.isRecentlyHit(),
                        event.getCurioHandler()
                )
        );
    }

    public static void SlotModifiersUpdatedEvent(SlotModifiersUpdatedEvent event) {
        ScriptType scriptType = event.getEntity().level().isClientSide() ? ScriptType.CLIENT : ScriptType.SERVER;
        KubeJSCuriosEvents.SLOT_MODIFIER_UPDATED.post(
                scriptType,
                new KubeJSCuriosEventJS.SlotModifiersUpdated(
                        event.getEntity(),
                        event.getTypes()
                )
        );
    }
}
