package me.simp.quirkademia.util;

public enum ActivationType {

	LEFT_CLICK,
	LEFT_CLICK_SNEAKING, //avoid using w/sneak_down&up
	RIGHT_CLICK_BLOCK, 
	RIGHT_CLICK_BLOCK_SNEAKING, //avoid using w/sneak_down&up
	RIGHT_CLICK_ENTITY,
	RIGHT_CLICK_ENTITY_SNEAKING, //avoid using w/sneak_down&up
	SNEAK_DOWN,
	SNEAK_UP,
	TOGGLE_SPRINT,
	OFFHAND_TRIGGER,
	OFFHAND_TRIGGER_SNEAKING,
	OFFHAND_TRIGGER_SPRINTING,
	PASSIVE,
	MANUAL;
}
