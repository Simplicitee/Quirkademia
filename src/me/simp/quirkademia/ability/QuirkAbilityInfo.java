package me.simp.quirkademia.ability;

import me.simp.quirkademia.quirk.Quirk;

public interface QuirkAbilityInfo {

	public String getName();
	public String getDescription();
	public Quirk getQuirk();
	public Class<? extends QuirkAbility> getAbilityClass();
}
