package me.simp.quirkademia.quirk.oneforall;

import me.simp.quirkademia.ability.QuirkAbilityInfo;
import me.simp.quirkademia.quirk.Quirk;

public class FullCowlInfo extends QuirkAbilityInfo {
	
	public FullCowlInfo() {
		super(FullCowlAbility.class);
	}

	@Override
	public String getName() {
		return "Full Cowling";
	}

	@Override
	public String getDescription() {
		return "Izuku Midoriya activates One For All throughout his entire body, making him stronger, more agile, and more durable. If he's hurt too much while doing this he cannot maintain it, but can easily reactivate it.";
	}

	@Override
	public String getInstruction() {
		return "Press the offhand trigger while sneaking to begin activating One For All throughout your body, and keep sneaking until it fully activates. Once it activates fully you can freely move around.";
	}

	@Override
	public Quirk getQuirk() {
		return Quirk.get(OneForAllQuirk.class);
	}
}
