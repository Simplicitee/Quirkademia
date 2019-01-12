package me.simp.quirkademia.quirk.explosion;

import me.simp.quirkademia.ability.QuirkAbilityInfo;
import me.simp.quirkademia.quirk.Quirk;

public class ExplosionCycleInfo extends QuirkAbilityInfo {

	public ExplosionCycleInfo() {
		super(ExplosionCycle.class);
	}

	@Override
	public String getName() {
		return "Explosion Cycle";
	}

	@Override
	public String getDescription() {
		return "Allows the user to cycle through the available explosion types.";
	}

	@Override
	public String getInstruction() {
		return "Press the offhand trigger while neither sprinting nor sneaking";
	}

	@Override
	public Quirk getQuirk() {
		return Quirk.get(ExplosionQuirk.class);
	}

}
