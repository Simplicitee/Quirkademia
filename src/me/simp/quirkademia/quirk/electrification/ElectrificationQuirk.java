package me.simp.quirkademia.quirk.electrification;

import java.util.HashSet;
import java.util.Set;

import me.simp.quirkademia.ability.QuirkAbilityInfo;
import me.simp.quirkademia.quirk.Quirk;
import me.simp.quirkademia.quirk.QuirkType;

public class ElectrificationQuirk extends Quirk {

	public ElectrificationQuirk() {
		super("Electrification", QuirkType.EMITTER);
	}

	@Override
	public String getDescription() {
		return "Denki Kaminari's quirk is Electrification, he is able to produce a high-static charge throughout his body and also discharge it all around him.";
	}

	@Override
	public Set<QuirkAbilityInfo> registerAbilities() {
		Set<QuirkAbilityInfo> register = new HashSet<>();
		
		return register;
	}

}
