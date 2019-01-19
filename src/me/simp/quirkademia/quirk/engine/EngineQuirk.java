package me.simp.quirkademia.quirk.engine;

import java.util.HashSet;
import java.util.Set;

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
	public Set<QuirkAbilityInfo> registerAbilities() {
		Set<QuirkAbilityInfo> register = new HashSet<>();
		register.add(new QuirkAbilityInfo(ActivationType.OFFHAND_TRIGGER, Engines.class, this, "Engines", "The engines in your calves allow you to run at incredible speeds!", "Press the offhand trigger to start your engines!"));
		register.add(new QuirkAbilityInfo(ActivationType.OFFHAND_TRIGGER_SNEAKING, ReciproBurst.class, this, "Recipro Burst", "Pushing your engines to the max, you can reach even higher speeds. After using it though, your engines will stall!", "While sneaking and with your engines activated, press the offhand trigger"));
		return register;
	}

}
