package me.simp.quirkademia.quirk.oneforall.blackwhip;

import java.util.Set;

import me.simp.quirkademia.ability.QuirkAbilityInfo;
import me.simp.quirkademia.quirk.Quirk;
import me.simp.quirkademia.quirk.QuirkType;

public class BlackWhipQuirk extends Quirk {

	public BlackWhipQuirk() {
		super("Black Whip", QuirkType.EMITTER);
	}

	@Override
	public String getDescription() {
		return "The user can shoot tendrils of dark energy from their arms to grapple and grab things.";
	}

	@Override
	public Set<QuirkAbilityInfo> registerAbilities() {
		return null;
	}

}
