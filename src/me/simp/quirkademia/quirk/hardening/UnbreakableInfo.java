package me.simp.quirkademia.quirk.hardening;

import me.simp.quirkademia.ability.QuirkAbilityInfo;
import me.simp.quirkademia.quirk.Quirk;

public class UnbreakableInfo extends QuirkAbilityInfo {

	public UnbreakableInfo() {
		super(UnbreakableAbility.class);
	}

	@Override
	public String getName() {
		return "Unbreakable";
	}

	@Override
	public String getDescription() {
		return "Kirishima's ultimate move, he pushes past his limit and hardens his entire body even further!";
	}

	@Override
	public String getInstruction() {
		return "Press the offhand trigger while using the Harden ability";
	}

	@Override
	public Quirk getQuirk() {
		return Quirk.get(HardeningQuirk.class);
	}

}
