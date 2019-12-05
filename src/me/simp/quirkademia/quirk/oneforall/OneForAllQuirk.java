package me.simp.quirkademia.quirk.oneforall;

import java.util.Set;

import me.simp.quirkademia.ability.QuirkAbilityInfo;
import me.simp.quirkademia.quirk.FusedQuirk;
import me.simp.quirkademia.quirk.oneforall.blackwhip.BlackWhipQuirk;
import me.simp.quirkademia.quirk.oneforall.stockpile.StockpileQuirk;

public class OneForAllQuirk extends FusedQuirk {

	public OneForAllQuirk() {
		super("One For All", new StockpileQuirk(), new BlackWhipQuirk());
	}

	@Override
	public String getDescription() {
		return "One For All is a mysterious quirk that is the result of the fusing of a power-stockpiling quirk and a quirk which can be transferred to others, making it the ultimate quirk to fight against All For One. The current holder, Izuku Midoriya, inherited the power from All Might, the world's Symbol of Peace. Recently Deku learns that the previous holder's quirks were merged with the core of One For All, and that in time he will manifest them!";
	}

	@Override
	public Set<QuirkAbilityInfo> registerAbilities() {
		return null;
	}
}
