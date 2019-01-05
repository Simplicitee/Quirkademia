package me.simp.quirkademia.quirk.oneforall;

import me.simp.quirkademia.ability.QuirkAbility;
import me.simp.quirkademia.ability.QuirkAbilityInfo;
import me.simp.quirkademia.quirk.Quirk;

public class FullCowlInfo implements QuirkAbilityInfo {
	
	@Override
	public String getName() {
		return "Full Cowl";
	}

	@Override
	public String getDescription() {
		return "Izuku Midoriya activates One For All throughout his entire body, making him stronger, more agile, and more durable. If he's hurt too much while doing this he cannot maintain it, but can easily reactivate it.";
	}

	@Override
	public Quirk getQuirk() {
		return Quirk.get(OneForAllQuirk.class);
	}

	@Override
	public Class<? extends QuirkAbility> getAbilityClass() {
		return FullCowlAbility.class;
	}
}
