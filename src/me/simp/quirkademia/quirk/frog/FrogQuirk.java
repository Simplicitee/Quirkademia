package me.simp.quirkademia.quirk.frog;

import java.util.HashSet;
import java.util.Set;

import me.simp.quirkademia.ability.QuirkAbilityInfo;
import me.simp.quirkademia.quirk.Quirk;
import me.simp.quirkademia.quirk.QuirkType;
import me.simp.quirkademia.util.ActivationType;

public class FrogQuirk extends Quirk {

	public FrogQuirk() {
		super("Frog", QuirkType.MUTANT);
	}

	@Override
	public String getDescription() {
		return "Tsuyu Asui's quirk is Frog, and it's as simple as it sounds. She can do pretty much anything a frog can.";
	}

	@Override
	public Set<QuirkAbilityInfo> registerAbilities() {
		Set<QuirkAbilityInfo> register = new HashSet<>();
		register.add(new QuirkAbilityInfo(ActivationType.PASSIVE, Froglike.class, this, "Froglike", "Being like a frog, Tsuyu Asui can do just about anything a frog can. She's especially good at jumping and swimming. By standing still long enough, she can even camoflauge herself!", "This is a passive effect and requires no activation."));
		register.add(new QuirkAbilityInfo(ActivationType.SNEAK_DOWN, TongueAttack.class, this, "Tongue Attack", "Tsuyu can use her tongue to grab people, attack, and other useful things. That's one strong tongue!", "Sneak with a tongue ability selected to use it!"));
		register.add(new QuirkAbilityInfo(ActivationType.OFFHAND_TRIGGER, TongueCycler.class, this, "Tongue Cycler", "Tsuyu can do many things with her tongue, use this to choose what you'd like to do with it ;)", "Press the offhand trigger while neither sneaking or sprinting to cycle tongue ability type"));
		return register;
	}

}
