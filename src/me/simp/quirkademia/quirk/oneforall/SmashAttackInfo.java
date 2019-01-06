package me.simp.quirkademia.quirk.oneforall;

import me.simp.quirkademia.ability.QuirkAbilityInfo;
import me.simp.quirkademia.quirk.Quirk;

public class SmashAttackInfo extends QuirkAbilityInfo {

	public SmashAttackInfo() {
		super(SmashAttackAbility.class);
	}

	@Override
	public String getName() {
		return "Smash Attack";
	}

	@Override
	public String getDescription() {
		return "Use the selected smash attack";
	}

	@Override
	public String getInstruction() {
		return "Left click to use a selected smash attack";
	}

	@Override
	public Quirk getQuirk() {
		return Quirk.get(OneForAllQuirk.class);
	}

	
}
