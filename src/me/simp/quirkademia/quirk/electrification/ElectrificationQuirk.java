package me.simp.quirkademia.quirk.electrification;

import java.util.HashSet;
import java.util.Set;

import me.simp.quirkademia.ability.QuirkAbilityInfo;
import me.simp.quirkademia.quirk.Quirk;
import me.simp.quirkademia.quirk.QuirkType;
import me.simp.quirkademia.util.ActivationType;

public class ElectrificationQuirk extends Quirk {

	public ElectrificationQuirk() {
		super("Electrification", QuirkType.EMITTER);
	}

	@Override
	public String getDescription() {
		return "Denki Kaminari's quirk is electrification. His body builds a massive static charge, zapping anyone he touches. He can also discharge the charge into his surrounding area to majorly shock anyone within range";
	}

	@Override
	public Set<QuirkAbilityInfo> registerAbilities() {
		Set<QuirkAbilityInfo> register = new HashSet<>();
		register.add(new QuirkAbilityInfo(ActivationType.PASSIVE, Static.class, this, "Static Charge", "Your body courses with electricity, but using too much of it will lower your cognitive functionality!", "Passively active"));
		register.add(new QuirkAbilityInfo(ActivationType.OFFHAND_TRIGGER_SNEAKING, Discharge.class, this, "Discharge", "Discharge the electricity from your body depending on what type of discharge you do!", "Press the offhand trigger while sneaking!"));
		return register;
	}

}
