package me.simp.quirkademia.quirk.zerogravity;

import java.util.HashSet;
import java.util.Set;

import me.simp.quirkademia.ability.QuirkAbilityInfo;
import me.simp.quirkademia.quirk.Quirk;
import me.simp.quirkademia.quirk.QuirkType;
import me.simp.quirkademia.util.ActivationType;

public class ZeroGravityQuirk extends Quirk {

	public ZeroGravityQuirk() {
		super("Zero Gravity", QuirkType.EMITTER);
	}

	@Override
	public String getDescription() {
		return "Ochaco Uraraka's quirk is Zero Gravity, she can make anything she touches float, including herself! She has a weight limit of 3 tons and becomes nauseous when approaching this limit. Using the quirk on herself reduces her weight limit drastically.";
	}

	@Override
	public Set<QuirkAbilityInfo> registerAbilities() {
		Set<QuirkAbilityInfo> register = new HashSet<>();
		register.add(new QuirkAbilityInfo(ActivationType.PASSIVE, Floaty.class, this, "Floaty", "You passively make yourself lighter so you fall slowly!", "Passively active"));
		register.add(new QuirkAbilityInfo(ActivationType.OFFHAND_TRIGGER_SNEAKING, FloatSelf.class, this, "FloatSelf", "Make yourself weightless and push off the ground!", "Press the offhand trigger while sneaking on the ground!"));
		register.add(new QuirkAbilityInfo(ActivationType.RIGHT_CLICK_BLOCK, FloatBlock.class, this, "FloatBlock", "Make a block float upwards! It'll damage entities it falls on!", "Right click a block!"));
		register.add(new QuirkAbilityInfo(ActivationType.RIGHT_CLICK_ENTITY, FloatEntity.class, this, "FloatEntity", "Make an entity float upwards! Can save someone from falling from high up!", "Right click an entity!"));
		register.add(new QuirkAbilityInfo(ActivationType.OFFHAND_TRIGGER, Release.class, this, "Release", "Release your floaty hold on all blocks and entities! Falling blocks will damage anything they hit!", "Press the offhand trigger"));
		return register;
	}

}
