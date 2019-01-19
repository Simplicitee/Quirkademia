package me.simp.quirkademia.quirk.halfcoldhalfhot;

import java.util.HashSet;
import java.util.Set;

import me.simp.quirkademia.ability.QuirkAbilityInfo;
import me.simp.quirkademia.quirk.Quirk;
import me.simp.quirkademia.quirk.QuirkType;
import me.simp.quirkademia.util.ActivationType;

public class HalfColdHalfHotQuirk extends Quirk {

	public HalfColdHalfHotQuirk() {
		super("Half Cold Half Hot", QuirkType.EMITTER);
	}

	@Override
	public String getDescription() {
		return "Shoto Todoroki's quirk is Half Cold Half Hot. He can raise temperatures and create flames with his left side, and can lower temperatures and create ice with his right side!";
	}

	@Override
	public Set<QuirkAbilityInfo> registerAbilities() {
		Set<QuirkAbilityInfo> register = new HashSet<>();
		register.add(new QuirkAbilityInfo(ActivationType.PASSIVE, BodyHeat.class, this, "Body Heat", "Maintaining a healthy body temperature while using fire and ice powers is important!", "Passively active"));
		register.add(new QuirkAbilityInfo(ActivationType.LEFT_CLICK, FlameAttack.class, this, "Flame Attack", "Use your left side to generate flames for various attacks!", "Left click with a selected ability!"));
		register.add(new QuirkAbilityInfo(ActivationType.RIGHT_CLICK_BLOCK, IceAttack.class, this, "Ice Attack", "Use your right side to generate attacks of ice!", "Right click the ground to create the selected ice attack!"));
		register.add(new QuirkAbilityInfo(ActivationType.OFFHAND_TRIGGER, PowerCycler.class, this, "Power Cycler", "Your fire and ice attacks have similar forms that you can switch between!", "Press the offhand trigger to switch abilities!"));
		register.add(new QuirkAbilityInfo(ActivationType.RIGHT_CLICK_ENTITY, Freeze.class, this, "Freeze", "Use a precise attack with your ice to slow an enemy! This has a stacking effect!", "Right click a living entity"));
		return register;
	}

}
