package me.simp.quirkademia.quirk.hardening;

import org.bukkit.Location;

import me.simp.quirkademia.ability.QuirkAbility;
import me.simp.quirkademia.quirk.QuirkUser;

public class UnbreakableAbility extends QuirkAbility {

	public UnbreakableAbility(QuirkUser user) {
		super(user);
		
		if (manager.hasAbility(user, HardenAbility.class)) {
			HardenAbility abil = manager.getAbility(user, HardenAbility.class);
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
