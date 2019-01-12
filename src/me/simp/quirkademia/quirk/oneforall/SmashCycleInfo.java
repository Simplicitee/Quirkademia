package me.simp.quirkademia.quirk.oneforall;

import me.simp.quirkademia.ability.QuirkAbilityInfo;
import me.simp.quirkademia.quirk.Quirk;

public class SmashCycleInfo extends QuirkAbilityInfo {

	public SmashCycleInfo() {
		super(SmashCycle.class);
	}

	@Override
	public String getName() {
		return "Smash Cycle";
	}

	@Override
	public String getDescription() {
		return "There are a wide variety of smash abilities, use this to cycle through them.";
	}

	@Override
	public String getInstruction() {
		return "Use the offhand trigger while neither running nor sneaking to cycle through your smash abilities.";
	}

	@Override
	public Quirk getQuirk() {
		return Quirk.get(OneForAllQuirk.class);
	}
}
