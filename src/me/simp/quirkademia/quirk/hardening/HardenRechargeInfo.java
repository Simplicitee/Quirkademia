package me.simp.quirkademia.quirk.hardening;

import me.simp.quirkademia.ability.QuirkAbilityInfo;
import me.simp.quirkademia.quirk.Quirk;

public class HardenRechargeInfo extends QuirkAbilityInfo {

	public HardenRechargeInfo() {
		super(HardenRecharge.class);
	}

	@Override
	public String getName() {
		return "Harden Recharge";
	}

	@Override
	public String getDescription() {
		return "After using up all of his hardening, Kirishima must wait before he can use it again.";
	}

	@Override
	public String getInstruction() {
		return "Auto activates!";
	}

	@Override
	public Quirk getQuirk() {
		return Quirk.get(HardeningQuirk.class);
	}

}
