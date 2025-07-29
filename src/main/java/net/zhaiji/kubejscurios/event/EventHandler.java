package net.zhaiji.kubejscurios.event;

import dev.latvian.mods.kubejs.event.EventResult;
import dev.latvian.mods.kubejs.script.ScriptType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventBus;
import net.zhaiji.kubejscurios.kubejs.KubeJSCuriosEventJS;
import net.zhaiji.kubejscurios.kubejs.KubeJSCuriosEvents;
import top.theillusivec4.curios.api.event.*;

public class EventHandler {
    public static void addForgeBusListener(IEventBus forgeEventBus) {
        forgeEventBus.addListener(EventHandler::CurioAttributeModifierEvent);
        forgeEventBus.addListener(EventHandler::CurioChangeEvent);
        forgeEventBus.addListener(EventHandler::CurioDropsEvent);
        forgeEventBus.addListener(EventHandler::CurioEquipEvent);
        forgeEventBus.addListener(EventHandler::CurioUnequipEvent);
        forgeEventBus.addListener(EventHandler::DropRulesEvent);
        forgeEventBus.addListener(EventHandler::SlotModifiersUpdatedEvent);
    }

    public static void CurioAttributeModifierEvent(CurioAttributeModifierEvent event) {
        LivingEntity entity = event.getSlotContext().entity();
        ScriptType scriptType = entity != null && !entity.level().isClientSide() ? ScriptType.SERVER : ScriptType.CLIENT;
        KubeJSCuriosEvents.ATTRIBUTE_MODIFIER.post(
                scriptType,
                new KubeJSCuriosEventJS.CurioAttributeModifier(
                        event.getItemStack(),
                        event.getSlotContext(),
                        event.getUuid(),
                        event.getModifiers()
                )
        );
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

    public static void CurioEquipEvent(CurioEquipEvent event) {
        ScriptType scriptType = event.getEntity().level().isClientSide() ? ScriptType.CLIENT : ScriptType.SERVER;
        KubeJSCuriosEventJS.CurioEquip evt = new KubeJSCuriosEventJS.CurioEquip(
                event.getEntity(),
                event.getSlotContext(),
                event.getStack()
        );
        KubeJSCuriosEvents.EQUIP.post(scriptType, evt);
        if (evt.result != Event.Result.DEFAULT) {
            event.setResult(evt.result);
        }
    }

    public static void CurioUnequipEvent(CurioUnequipEvent event) {
        ScriptType scriptType = event.getEntity().level().isClientSide() ? ScriptType.CLIENT : ScriptType.SERVER;
        KubeJSCuriosEventJS.CurioUnequip evt = new KubeJSCuriosEventJS.CurioUnequip(
                event.getEntity(),
                event.getSlotContext(),
                event.getStack()
        );
        KubeJSCuriosEvents.UNEQUIP.post(scriptType, evt);
        if (evt.result != Event.Result.DEFAULT) {
            event.setResult(evt.result);
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
