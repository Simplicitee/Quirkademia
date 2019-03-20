package me.simp.quirkademia.quirk.electrification;

import org.bukkit.Location;

import me.simp.quirkademia.ability.QuirkAbility;
import me.simp.quirkademia.quirk.QuirkUser;

public class Shooter extends QuirkAbility {

	public Shooter(QuirkUser user) {
		super(user);
	}

	@Override
	public Location getLocation() {
		return null;
	}

	@Override
	public boolean progress() {
		return false;
	}

	@Override
	public void onStart() {
		
	}

	@Override
	public void onRemove() {
		
	}

}
