package me.simp.quirkademia.util;

import org.bukkit.potion.PotionEffectType;

public enum StatusEffect {
	
	INCREASED_STRENGTH(PotionEffectType.INCREASE_DAMAGE),
	INCREASED_ENDURANCE(PotionEffectType.DAMAGE_RESISTANCE),
	INCREASED_SPEED(PotionEffectType.SPEED), 
	INCREASED_JUMP(PotionEffectType.JUMP), 
	QUIRK_ERASED(null), 
	PARALYZED(PotionEffectType.SLOW), 
	HEALING(PotionEffectType.REGENERATION),
	INVISIBLE(PotionEffectType.INVISIBILITY);
	
	public PotionEffectType type;
	
	private StatusEffect(PotionEffectType type) {
		this.type = type;
	}
	
	public PotionEffectType getPotion() {
		return type;
	}
}
