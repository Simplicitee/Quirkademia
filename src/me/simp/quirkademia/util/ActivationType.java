package me.simp.quirkademia.util;

public enum ActivationType {

	LEFT_CLICK("L-click"),
	LEFT_CLICK_SNEAKING("sneak + L-click"), //avoid using w/sneak_down&up
	RIGHT_CLICK_BLOCK("R-click block"), 
	RIGHT_CLICK_BLOCK_SNEAKING("sneak + R-click block"), //avoid using w/sneak_down&up
	RIGHT_CLICK_ENTITY("R-click entity"),
	RIGHT_CLICK_ENTITY_SNEAKING("sneak + R-click entity"), //avoid using w/sneak_down&up
	SNEAK_DOWN("press sneak"),
	SNEAK_UP("release sneak"),
	TOGGLE_SPRINT("begin sprinting"),
	OFFHAND_TRIGGER("tap offhand"),
	OFFHAND_TRIGGER_SNEAKING("sneak + tap offhand"),
	OFFHAND_TRIGGER_SPRINTING("sprint + tap offhand"),
	PASSIVE("passively active"),
	MANUAL("external activation");
	
	private String friendly;
	
	private ActivationType(String friendly) {
		this.friendly = friendly;
	}
	
	public String getUserFriendly() {
		return friendly;
	}
}
