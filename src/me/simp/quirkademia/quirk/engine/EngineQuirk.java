package me.simp.quirkademia.quirk.engine;

import java.util.HashMap;
import java.util.Map;

import me.simp.quirkademia.ability.QuirkAbilityInfo;
import me.simp.quirkademia.quirk.Quirk;
import me.simp.quirkademia.quirk.QuirkType;
import me.simp.quirkademia.util.ActivationType;

public class EngineQuirk extends Quirk {

	public EngineQuirk() {
		super("Engine", QuirkType.MUTANT);
	}

	@Override
	public String getDescription() {
		return "Tenya Iida has engines in his calves, allowing him to run incredibly fast! His legs are super strong and he has trained his body for kicking.";
	}

	@Override
	public Map<ActivationType, QuirkAbilityInfo> registerQuirkAbilities() {
		Map<ActivationType, QuirkAbilityInfo> register = new HashMap<>();
		register.put(ActivationType.OFFHAND_TRIGGER, new EnginesInfo());
		register.put(ActivationType.OFFHAND_TRIGGER_SNEAKING, new ReciproBurstInfo());
		return register;
	}

}
