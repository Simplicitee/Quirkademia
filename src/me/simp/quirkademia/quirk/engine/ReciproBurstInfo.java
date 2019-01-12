package me.simp.quirkademia.quirk.engine;

import me.simp.quirkademia.ability.QuirkAbilityInfo;
import me.simp.quirkademia.quirk.Quirk;

public class ReciproBurstInfo extends QuirkAbilityInfo {

	public ReciproBurstInfo() {
		super(ReciproBurst.class);
	}

	@Override
	public String getName() {
		return "Recipro Burst";
	}

	@Override
	public String getDescription() {
		return "Pushing your engines to the max, you can reach even higher speeds. After using it though, your engines will stall!";
	}

	@Override
	public String getInstruction() {
		return "While sneaking and with your engines activated, press the offhand trigger";
	}

	@Override
	public Quirk getQuirk() {
		return Quirk.get(EngineQuirk.class);
	}

}
