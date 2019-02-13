package me.simp.quirkademia.quirk.zerogravity;

import org.bukkit.Location;

import me.simp.quirkademia.ability.QuirkAbility;
import me.simp.quirkademia.quirk.QuirkUser;

public class Release extends QuirkAbility {

	public Release(QuirkUser user) {
		super(user);
		
		if (manager.hasAbility(user, Floaty.class)) {
			manager.getAbility(user, Floaty.class).release();
		}
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
