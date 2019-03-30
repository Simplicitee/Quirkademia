package me.simp.quirkademia.quirk.navellaser;

import java.util.HashSet;
import java.util.Set;

import me.simp.quirkademia.ability.QuirkAbilityInfo;
import me.simp.quirkademia.quirk.Quirk;
import me.simp.quirkademia.quirk.QuirkType;
import me.simp.quirkademia.util.ActivationType;

public class NavelLaserQuirk extends Quirk {

	public NavelLaserQuirk() {
		super("Navel Laser", QuirkType.EMITTER);
	}

	@Override
	public String getDescription() {
		return "Quite simply, Yuga Aoyama can fire a laser out of his belly button.";
	}

	@Override
	public Set<QuirkAbilityInfo> registerAbilities() {
		Set<QuirkAbilityInfo> register = new HashSet<>();
		
		register.add(new QuirkAbilityInfo(ActivationType.OFFHAND_TRIGGER_SNEAKING, Laser.class, this, "Laser", "Fire a laser beam from your belly button! It only lasts a second!", "Press the offhand trigger while sneaking"));
		
		return register;
	}

}
