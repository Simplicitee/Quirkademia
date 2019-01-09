package me.simp.quirkademia.quirk.frog;

import me.simp.quirkademia.ability.QuirkAbilityInfo;
import me.simp.quirkademia.quirk.Quirk;

public class TongueAttackInfo extends QuirkAbilityInfo {

	public TongueAttackInfo() {
		super(TongueAttackAbility.class);
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Tongue Attack";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Tsuyu can use her tongue to grab people, attack, and other useful things. That's one strong tongue!";
	}

	@Override
	public String getInstruction() {
		// TODO Auto-generated method stub
		return "Sneak with a tongue ability selected to use it!";
	}

	@Override
	public Quirk getQuirk() {
		// TODO Auto-generated method stub
		return Quirk.get(FrogQuirk.class);
	}

}
