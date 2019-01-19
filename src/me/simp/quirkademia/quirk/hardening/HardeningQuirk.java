package me.simp.quirkademia.quirk.hardening;

import java.util.HashSet;
import java.util.Set;

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
		return "Eijiro Kirishima can harden any part of his body, making him harder than steel. When hardened, he can even run through walls!";
	}

	@Override
	public Set<QuirkAbilityInfo> registerAbilities() {
		Set<QuirkAbilityInfo> register = new HashSet<>();
		register.add(new QuirkAbilityInfo(ActivationType.OFFHAND_TRIGGER_SNEAKING, Harden.class, this, "Harden", "Harden your body to become stronger, dealing more damage and receiving less! You can also run through walls depending on your speed and strength!", "While sneaking, press the offhand trigger once."));
		register.add(new QuirkAbilityInfo(ActivationType.OFFHAND_TRIGGER, Unbreakable.class, this, "Unbreakable", "Kirishima's ultimate move, he pushes past his limit and hardens his entire body even further!", "Press the offhand trigger while using the Harden ability"));
		register.add(new QuirkAbilityInfo(ActivationType.MANUAL, HardenRecharge.class, this, "Harden Recharge", "After using up all of your hardening, you must wait before using it again.", "Auto activates!"));
		return register;
	}

}
