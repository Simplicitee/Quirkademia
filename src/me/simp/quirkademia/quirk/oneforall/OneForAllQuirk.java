package me.simp.quirkademia.quirk.oneforall;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.boss.BarColor;

import me.simp.quirkademia.ability.QuirkAbilityInfo;
import me.simp.quirkademia.quirk.Quirk;
import me.simp.quirkademia.quirk.QuirkType;
import me.simp.quirkademia.util.ActivationType;

public class OneForAllQuirk extends Quirk {

	public OneForAllQuirk() {
		super("One For All", QuirkType.EMITTER);
	}

	@Override
	public String getDescription() {
		return "One For All is a mysterious quirk that is the result of the fusing of a power-stockpiling quirk and a quirk which can be transferred to others, making it the ultimate quirk to fight against All For One. The current holder, Izuku Midoriya, inherited the power from All Might, the world's Symbol of Peace.";
	}

	@Override
	public Map<ActivationType, QuirkAbilityInfo> registerQuirkAbilities() {
		Map<ActivationType, QuirkAbilityInfo> abilities = new HashMap<>();
		abilities.put(ActivationType.OFFHAND_TRIGGER_SNEAKING, new FullCowlInfo());
		abilities.put(ActivationType.PASSIVE, new SmashInfo());
		abilities.put(ActivationType.OFFHAND_TRIGGER, new SmashCycleInfo());
		abilities.put(ActivationType.LEFT_CLICK, new SmashAttackInfo());
		return abilities;
	}

	@Override
	public String getStaminaTitle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BarColor getStaminaColor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getStaminaMax() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getStaminaRecharge() {
		// TODO Auto-generated method stub
		return 0;
	}
}
