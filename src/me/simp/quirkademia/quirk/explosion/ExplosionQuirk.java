package me.simp.quirkademia.quirk.explosion;

import java.util.HashMap;
import java.util.Map;

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
	public Map<ActivationType, QuirkAbilityInfo> registerQuirkAbilities() {
		Map<ActivationType, QuirkAbilityInfo> register = new HashMap<>();
		register.put(ActivationType.PASSIVE, new ExplosionInfo());
		register.put(ActivationType.OFFHAND_TRIGGER, new ExplosionCycleInfo());
		register.put(ActivationType.OFFHAND_TRIGGER_SPRINTING, new BlastRushTurboInfo());
		register.put(ActivationType.LEFT_CLICK, new ExplosionBlastInfo());
		register.put(ActivationType.OFFHAND_TRIGGER_SNEAKING, new ExplosiveLaunchInfo());
		return register;
	}

}
