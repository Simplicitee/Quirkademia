package me.simp.quirkademia.quirk.electrification;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.boss.BarColor;

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
		return "Denki Kaminari's quirk is Electrification, he is able to produce a high-static charge throughout his body and also discharge it all around him.";
	}

	@Override
	public Map<ActivationType, QuirkAbilityInfo> registerQuirkAbilities() {
		Map<ActivationType, QuirkAbilityInfo> register = new HashMap<>();
		
		return register;
	}

	@Override
	public String getStaminaTitle() {
		// TODO Auto-generated method stub
		return "Static Charge";
	}

	@Override
	public BarColor getStaminaColor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getStaminaMax() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getStaminaRecharge() {
		// TODO Auto-generated method stub
		return 0;
	}

}
