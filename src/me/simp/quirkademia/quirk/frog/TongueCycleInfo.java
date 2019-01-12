package me.simp.quirkademia.quirk.frog;

import me.simp.quirkademia.ability.QuirkAbilityInfo;
import me.simp.quirkademia.quirk.Quirk;

public class TongueCycleInfo extends QuirkAbilityInfo {

	public TongueCycleInfo() {
		super(TongueCycle.class);
	}

	@Override
	public String getName() {
		return "Tongue Cycle";
	}

	@Override
	public String getDescription() {
		return "Tsuyu can do many things with her tongue, use this to choose what you'd like to do with it ;)";
	}

	@Override
	public String getInstruction() {
		return "Press the offhand trigger while neither sneaking or sprinting to cycle tongue ability type";
	}

	@Override
	public Quirk getQuirk() {
		return Quirk.get(FrogQuirk.class);
	}

}
