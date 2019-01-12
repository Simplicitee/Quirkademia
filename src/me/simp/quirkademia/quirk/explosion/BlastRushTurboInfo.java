package me.simp.quirkademia.quirk.explosion;

import me.simp.quirkademia.ability.QuirkAbilityInfo;
import me.simp.quirkademia.quirk.Quirk;

public class BlastRushTurboInfo extends QuirkAbilityInfo {

	public BlastRushTurboInfo() {
		super(BlastRushTurbo.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Blast Rush Turbo";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Bakugo creates backwards-facing explosions to propel himself forward!";
	}

	@Override
	public String getInstruction() {
		// TODO Auto-generated method stub
		return "Press the offhand trigger while sprinting.";
	}

	@Override
	public Quirk getQuirk() {
		// TODO Auto-generated method stub
		return Quirk.get(ExplosionQuirk.class);
	}

}
