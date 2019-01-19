package me.simp.quirkademia.quirk.invisibility;

import java.util.HashSet;
import java.util.Set;

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
		return "Toru Hagakure's body is completely invisible! She can also manipulate and warp the light that passes through her body to some degree!";
	}

	@Override
	public Set<QuirkAbilityInfo> registerAbilities() {
		Set<QuirkAbilityInfo> register = new HashSet<>();
		register.add(new QuirkAbilityInfo(ActivationType.PASSIVE, Invisible.class, this, "Invisible", "You're always invisible, but your clothes aren't!", "Passively active"));
		register.add(new QuirkAbilityInfo(ActivationType.OFFHAND_TRIGGER_SNEAKING, LightRefraction.class, this, "Light Refraction", "Warp light through your body, blinding entities it strikes!", "Press the offhand trigger while sneaking, continue sneaking to warp light through your body!"));
		return register;
	}

}
