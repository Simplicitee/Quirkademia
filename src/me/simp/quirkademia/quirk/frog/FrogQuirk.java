package me.simp.quirkademia.quirk.frog;

import java.util.HashMap;
import java.util.Map;

import me.simp.quirkademia.ability.QuirkAbilityInfo;
import me.simp.quirkademia.quirk.Quirk;
import me.simp.quirkademia.quirk.QuirkType;
import me.simp.quirkademia.util.ActivationType;

public class FrogQuirk extends Quirk {

	public FrogQuirk() {
		super("Frog", QuirkType.MUTANT);
	}

	@Override
	public String getDescription() {
		return "Tsuyu Asui's quirk is Frog, and it's as simple as it sounds. She can do pretty much anything a frog can and is really good at jumping.";
	}

	@Override
	public Map<ActivationType, QuirkAbilityInfo> registerQuirkAbilities() {
		Map<ActivationType, QuirkAbilityInfo> register = new HashMap<>();
		register.put(ActivationType.PASSIVE, new FroglikeInfo());
		return register;
	}

}
