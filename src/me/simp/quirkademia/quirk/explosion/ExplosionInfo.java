package me.simp.quirkademia.quirk.explosion;

import me.simp.quirkademia.ability.QuirkAbilityInfo;
import me.simp.quirkademia.quirk.Quirk;

public class ExplosionInfo extends QuirkAbilityInfo {

	public ExplosionInfo() {
		super(ExplosionTracker.class);
	}

	@Override
	public String getName() {
		return "Explosion";
	}

	@Override
	public String getDescription() {
		return "There are a variety of explosions one can create, this keeps track of them!";
	}

	@Override
	public String getInstruction() {
		return "This is a passive explosion type tracker!";
	}

	@Override
	public Quirk getQuirk() {
		return Quirk.get(ExplosionQuirk.class);
	}

}
