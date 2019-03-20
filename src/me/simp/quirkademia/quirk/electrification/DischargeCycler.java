package me.simp.quirkademia.quirk.electrification;

import org.bukkit.Location;

import me.simp.quirkademia.ability.QuirkAbility;
import me.simp.quirkademia.quirk.QuirkUser;

public class DischargeCycler extends QuirkAbility {

	public DischargeCycler(QuirkUser user) {
		super(user);
		
		if (!manager.hasAbility(user, Static.class)) {
			return;
		}
		
		manager.getAbility(user, Static.class).cycleType();
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
