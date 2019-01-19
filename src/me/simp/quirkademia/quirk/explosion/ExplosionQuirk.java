package me.simp.quirkademia.quirk.explosion;

import java.util.HashSet;
import java.util.Set;

import me.simp.quirkademia.ability.QuirkAbilityInfo;
import me.simp.quirkademia.quirk.Quirk;
import me.simp.quirkademia.quirk.QuirkType;
import me.simp.quirkademia.util.ActivationType;

public class ExplosionQuirk extends Quirk {

	public ExplosionQuirk() {
		super("Explosion", QuirkType.EMITTER);
	}

	@Override
	public String getDescription() {
		return "Katsuki Bakugo has the powerful quirk of Explosion. He has nitroglycerin sweat that he can use to cause explosions!";
	}

	@Override
	public Set<QuirkAbilityInfo> registerAbilities() {
		Set<QuirkAbilityInfo> register = new HashSet<>();
		register.add(new QuirkAbilityInfo(ActivationType.PASSIVE, ExplosionTracker.class, this, "Explosions", "There are a variety of explosions one can create, this keeps track of them!", "This is a passive explosion type tracker!"));
		register.add(new QuirkAbilityInfo(ActivationType.OFFHAND_TRIGGER, ExplosionCycler.class, this, "Explosion Cycler", "Allows the user to cycle through the available explosion types.", "Press the offhand trigger while neither sprinting nor sneaking"));
		register.add(new QuirkAbilityInfo(ActivationType.OFFHAND_TRIGGER_SPRINTING, BlastRushTurbo.class, this, "Blast Rush Turbo", "Create backwards-facing explosions to propel yourself forward!", "Press the offhand trigger while sprinting."));
		register.add(new QuirkAbilityInfo(ActivationType.OFFHAND_TRIGGER_SNEAKING, ExplosiveLaunch.class, this, "Explosive Launch", "Create a powerful blast below yourself to launch high into the sky!", "Press the offhand trigger while sneaking to begin charging your launch"));
		register.add(new QuirkAbilityInfo(ActivationType.LEFT_CLICK, ExplosionBlast.class, this, "Explosion Blast", "Create an explosive blast from your hand, with varying range, radius, and power depending on the selected explosion type.", "Left click to use the selected explosion type!"));
		return register;
	}

}
