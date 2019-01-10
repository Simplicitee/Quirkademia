package me.simp.quirkademia.quirk.hardening;

import java.util.HashMap;
import java.util.Map;

import me.simp.quirkademia.ability.QuirkAbilityInfo;
import me.simp.quirkademia.quirk.Quirk;
import me.simp.quirkademia.quirk.QuirkType;
import me.simp.quirkademia.util.ActivationType;

public class HardeningQuirk extends Quirk {

	public HardeningQuirk() {
		super("Hardening", QuirkType.TRANSFORMATION);
	}

	@Override
	public String getDescription() {
		return "Ejiro Kirishima can harden any part of his body, making him harder than steel. When hardened, he can even run through walls!";
	}

	@Override
	public Map<ActivationType, QuirkAbilityInfo> registerQuirkAbilities() {
		Map<ActivationType, QuirkAbilityInfo> register = new HashMap<>();
		register.put(ActivationType.OFFHAND_TRIGGER_SNEAKING, new HardenInfo());
		register.put(ActivationType.OFFHAND_TRIGGER, new UnbreakableInfo());
		register.put(ActivationType.MANUAL, new HardenRechargeInfo());
		return register;
	}

}
