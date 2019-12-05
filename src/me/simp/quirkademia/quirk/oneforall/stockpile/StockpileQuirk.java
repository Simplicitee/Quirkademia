package me.simp.quirkademia.quirk.oneforall.stockpile;

import java.util.HashSet;
import java.util.Set;

import me.simp.quirkademia.ability.QuirkAbilityInfo;
import me.simp.quirkademia.quirk.Quirk;
import me.simp.quirkademia.quirk.QuirkType;
import me.simp.quirkademia.util.ActivationType;

public class StockpileQuirk extends Quirk {

	public StockpileQuirk() {
		super("Stockpile", QuirkType.EMITTER);
	}

	@Override
	public String getDescription() {
		return "Stockpile energy ambiently over time, and then release it to increase strength and speed.";
	}

	@Override
	public Set<QuirkAbilityInfo> registerAbilities() {
		Set<QuirkAbilityInfo> abilities = new HashSet<>();
		abilities.add(new QuirkAbilityInfo(ActivationType.OFFHAND_TRIGGER_SNEAKING, FullCowling.class, this, "Full Cowling", "Izuku Midoriya activates One For All throughout his entire body, making him stronger, more agile, and more durable. If he's hurt too much while doing this he cannot maintain it, but can easily reactivate it.", "Press the offhand trigger while sneaking to begin activating One For All throughout your body, and keep sneaking until it fully activates. Once it activates fully you can freely move around."));
		abilities.add(new QuirkAbilityInfo(ActivationType.PASSIVE, SmashTracker.class, this, "Smash Tracker", "When using his fists, Midoriya employs various abilities he calls 'Smashes', following in the footsteps of his mentor and predecesor, All Might.", "This is a passive ability for keeping track of smash attacks, reqires no user activation."));
		abilities.add(new QuirkAbilityInfo(ActivationType.OFFHAND_TRIGGER, SmashCycle.class, this, "Smash Cycler", "There are a wide variety of smash abilities, use this to cycle through them.", "Use the offhand trigger while neither running nor sneaking to cycle through your smash abilities."));
		abilities.add(new QuirkAbilityInfo(ActivationType.LEFT_CLICK, SmashAttack.class, this, "Smash Attack", "Use the selected smash attack", "Left click to use the selected smash attack"));
		return abilities;
	}

}
