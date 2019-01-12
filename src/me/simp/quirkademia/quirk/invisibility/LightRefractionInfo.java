package me.simp.quirkademia.quirk.invisibility;

import me.simp.quirkademia.ability.QuirkAbilityInfo;
import me.simp.quirkademia.quirk.Quirk;

public class LightRefractionInfo extends QuirkAbilityInfo {

	public LightRefractionInfo() {
		super(LightRefractionAbility.class);
	}

	@Override
	public String getName() {
		return "Light Refraction";
	}

	@Override
	public String getDescription() {
		return "Warp light through your body, blinding entities it strikes!";
	}

	@Override
	public String getInstruction() {
		return "Press the offhand trigger while sneaking, continue sneaking to warp light through your body!";
	}

	@Override
	public Quirk getQuirk() {
		return Quirk.get(InvisibilityQuirk.class);
	}

}
