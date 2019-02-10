package me.simp.quirkademia.quirk.zerogravity;

import java.util.HashSet;
import java.util.Set;

import me.simp.quirkademia.ability.QuirkAbilityInfo;
import me.simp.quirkademia.quirk.Quirk;
import me.simp.quirkademia.quirk.QuirkType;

public class ZeroGravityQuirk extends Quirk {

	public ZeroGravityQuirk() {
		super("Zero Gravity", QuirkType.EMITTER);
	}

	@Override
	public String getDescription() {
		return "Ochaco Uraraka's quirk is Zero Gravity, she can make anything she touches float, including herself!";
	}

	@Override
	public Set<QuirkAbilityInfo> registerAbilities() {
		Set<QuirkAbilityInfo> register = new HashSet<>();
		return register;
	}

}
