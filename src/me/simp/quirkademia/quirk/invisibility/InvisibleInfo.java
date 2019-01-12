package me.simp.quirkademia.quirk.invisibility;

import me.simp.quirkademia.ability.QuirkAbilityInfo;
import me.simp.quirkademia.quirk.Quirk;

public class InvisibleInfo extends QuirkAbilityInfo{

	public InvisibleInfo() {
		super(InvisibleAbility.class);
	}

	@Override
	public String getName() {
		return "Invisible";
	}

	@Override
	public String getDescription() {
		return "You're always invisible, but your clothes aren't!";
	}

	@Override
	public String getInstruction() {
		return "Passively active";
	}

	@Override
	public Quirk getQuirk() {
		return Quirk.get(InvisibilityQuirk.class);
	}

}
