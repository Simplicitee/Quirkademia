package me.simp.quirkademia.quirk.engine;

import org.bukkit.Location;

import me.simp.quirkademia.ability.QuirkAbility;
import me.simp.quirkademia.quirk.QuirkUser;

public class ReciproBurst extends QuirkAbility {

	public ReciproBurst(QuirkUser user) {
		super(user);
		
		if (manager.hasAbility(user, Engines.class)) {
			manager.getAbility(user, Engines.class).activateReciproBurst();
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
