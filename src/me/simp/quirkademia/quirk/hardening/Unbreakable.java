package me.simp.quirkademia.quirk.hardening;

import org.bukkit.Location;

import me.simp.quirkademia.ability.QuirkAbility;
import me.simp.quirkademia.quirk.QuirkUser;

public class Unbreakable extends QuirkAbility {

	public Unbreakable(QuirkUser user) {
		super(user);
		
		if (manager.hasAbility(user, Harden.class)) {
			Harden abil = manager.getAbility(user, Harden.class);
			if (!abil.hasUnbreakable()) {
				abil.activateUnbreakable();
			}
		}
	}

	@Override
	public Location getLocation() {
		return player.getLocation();
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
