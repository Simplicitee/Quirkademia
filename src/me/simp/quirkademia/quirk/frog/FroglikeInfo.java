package me.simp.quirkademia.quirk.frog;

import me.simp.quirkademia.ability.QuirkAbilityInfo;
import me.simp.quirkademia.quirk.Quirk;

public class FroglikeInfo extends QuirkAbilityInfo {
	
	public FroglikeInfo() {
		super(FroglikeAbility.class);
	}

	@Override
	public String getName() {
		return "Froglike";
	}

	@Override
	public String getDescription() {
		return "Being like a frog, Tsuyu Asui can do just about anything a frog can. She's especially good at jumping.";
	}
	
	@Override
	public String getInstruction() {
		return "This is a passive effect and requires no activation.";
	}

	@Override
	public Quirk getQuirk() {
		return Quirk.get(FrogQuirk.class);
	}
}
