package me.simp.quirkademia.quirk.engine;

import me.simp.quirkademia.ability.QuirkAbilityInfo;
import me.simp.quirkademia.quirk.Quirk;

public class EnginesInfo extends QuirkAbilityInfo {

	public EnginesInfo() {
		super(Engines.class);
	}

	@Override
	public String getName() {
		return "Engines";
	}

	@Override
	public String getDescription() {
		return "The engines in your calves allow you to run at incredible speeds!";
	}

	@Override
	public String getInstruction() {
		return "Press the offhand trigger to start your engines!";
	}

	@Override
	public Quirk getQuirk() {
		return Quirk.get(EngineQuirk.class);
	}

}
