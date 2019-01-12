package me.simp.quirkademia.quirk.explosion;

import me.simp.quirkademia.ability.QuirkAbilityInfo;
import me.simp.quirkademia.quirk.Quirk;

public class ExplosiveLaunchInfo extends QuirkAbilityInfo {

	public ExplosiveLaunchInfo() {
		super(ExplosiveLaunch.class);
	}

	@Override
	public String getName() {
		return "Explosive Launch";
	}

	@Override
	public String getDescription() {
		return "Bakugo can create a powerful blast below him to launch high into the sky!";
	}

	@Override
	public String getInstruction() {
		return "Press the offhand trigger while sneaking to begin charging your launch";
	}

	@Override
	public Quirk getQuirk() {
		return Quirk.get(ExplosionQuirk.class);
	}

}
