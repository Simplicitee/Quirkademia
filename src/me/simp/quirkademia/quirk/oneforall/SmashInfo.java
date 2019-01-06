package me.simp.quirkademia.quirk.oneforall;

import me.simp.quirkademia.ability.QuirkAbilityInfo;
import me.simp.quirkademia.quirk.Quirk;

public class SmashInfo extends QuirkAbilityInfo {

	public SmashInfo() {
		super(SmashAbility.class);
	}

	@Override
	public String getName() {
		return "Smash";
	}

	@Override
	public String getDescription() {
		return "When using his fists, Midoriya employs various abilities he calls 'Smashes', following in the footsteps of his mentor and predecesor, All Might.";
	}

	@Override
	public String getInstruction() {
		return "This is a passive ability for keeping track of smash attacks, reqires no user activation.";
	}

	@Override
	public Quirk getQuirk() {
		return Quirk.get(OneForAllQuirk.class);
	}
	
}
