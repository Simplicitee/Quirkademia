package me.simp.quirkademia.quirk.invisibility;

import java.util.HashMap;
import java.util.Map;

import me.simp.quirkademia.ability.QuirkAbilityInfo;
import me.simp.quirkademia.quirk.Quirk;
import me.simp.quirkademia.quirk.QuirkType;
import me.simp.quirkademia.util.ActivationType;

public class InvisibilityQuirk extends Quirk {

	public InvisibilityQuirk() {
		super("Invisibility", QuirkType.MUTANT);
	}

	@Override
	public String getDescription() {
		return "Toru Hagakure's body is completely invisible! She can also manipulate and warp the light that passes through her body!";
	}

	@Override
	public Map<ActivationType, QuirkAbilityInfo> registerQuirkAbilities() {
		Map<ActivationType, QuirkAbilityInfo> register = new HashMap<>();
		register.put(ActivationType.PASSIVE, new InvisibleInfo());
		register.put(ActivationType.OFFHAND_TRIGGER_SNEAKING, new LightRefractionInfo());
		return register;
	}

}
