package me.simp.quirkademia.quirk.hardening;

import me.simp.quirkademia.ability.QuirkAbilityInfo;
import me.simp.quirkademia.quirk.Quirk;

public class HardenInfo extends QuirkAbilityInfo {

	public HardenInfo() {
		super(HardenAbility.class);
	}

	@Override
	public String getName() {
		return "Harden";
	}

	@Override
	public String getDescription() {
		return "Harden your body to become stronger, dealing more damage and receiving less!";
	}

	@Override
	public String getInstruction() {
		return "While sneaking, press the offhand trigger once.";
	}

	@Override
	public Quirk getQuirk() {
		return Quirk.get(HardeningQuirk.class);
	}

}
