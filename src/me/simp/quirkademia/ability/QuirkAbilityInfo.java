package me.simp.quirkademia.ability;

import me.simp.quirkademia.quirk.Quirk;

public abstract class QuirkAbilityInfo {
	
	private Class<? extends QuirkAbility> clazz;
	
	public QuirkAbilityInfo(Class<? extends QuirkAbility> clazz) {
		this.clazz = clazz;
	}
	
	public Class<? extends QuirkAbility> getAbilityClass() {
		return clazz;
	}
	
	public abstract String getName();
	public abstract String getDescription();
	public abstract String getInstruction();
	public abstract Quirk getQuirk();
}
