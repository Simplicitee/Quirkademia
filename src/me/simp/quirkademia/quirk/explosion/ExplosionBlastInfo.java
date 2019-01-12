package me.simp.quirkademia.quirk.explosion;

import me.simp.quirkademia.ability.QuirkAbilityInfo;
import me.simp.quirkademia.quirk.Quirk;

public class ExplosionBlastInfo extends QuirkAbilityInfo {

	public ExplosionBlastInfo() {
		super(ExplosionBlast.class);
	}

	@Override
	public String getName() {
		return "Explosion Blast";
	}

	@Override
	public String getDescription() {
		return "Create an explosive blast from your hand, with varying range, radius, and power depending on the selected explosion type.";
	}

	@Override
	public String getInstruction() {
		return "Left click to use the selected explosion type!";
	}

	@Override
	public Quirk getQuirk() {
		return Quirk.get(ExplosionQuirk.class);
	}

}
