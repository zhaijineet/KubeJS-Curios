package net.zhaiji.kubejscurios.kubejs;

import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;

public interface KubeJSCuriosEvents {
    EventGroup GROUP = EventGroup.of("CuriosJSEvents");

    EventHandler REGISTER_RENDERER = GROUP.client("registerRenderer", () -> KubeJSCuriosEventJS.registerRenderer.class);
    EventHandler ATTRIBUTE_MODIFIER = GROUP.common("attributeModifier", () -> KubeJSCuriosEventJS.CurioAttributeModifier.class);
    EventHandler CAN_EQUIP = GROUP.common("canEquip", () -> KubeJSCuriosEventJS.CurioCanEquip.class);
    EventHandler CHANGE = GROUP.server("change", () -> KubeJSCuriosEventJS.CurioChange.class);
    EventHandler CAN_UNEQUIP = GROUP.common("canUnequip", () -> KubeJSCuriosEventJS.CurioCanUnequip.class);
    EventHandler DROPS = GROUP.server("drops", () -> KubeJSCuriosEventJS.CurioDrops.class).hasResult();
    EventHandler DROP_RULES = GROUP.server("dropRules", () -> KubeJSCuriosEventJS.DropRules.class);
    EventHandler SLOT_MODIFIER_UPDATED = GROUP.common("slotModifiersUpdated", () -> KubeJSCuriosEventJS.SlotModifiersUpdated.class);
}
