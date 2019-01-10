package me.simp.quirkademia.quirk.hardening;

import org.bukkit.Location;

import me.simp.quirkademia.ability.QuirkAbility;
import me.simp.quirkademia.quirk.QuirkUser;

public class HardenRecharge extends QuirkAbility {

	public HardenRecharge(QuirkUser user) {
		super(user);
		
		manager.start(this);
	}

	@Override
	public Location getLocation() {
		return null;
	}

	@Override
	public boolean progress() {
		if (user.getStamina().get() >= user.getStamina().getMaxStamina()) {
			return false;
		}
		
		user.getStamina().set(user.getStamina().get() + 1);
		
		return true;
	}

	@Override
	public void onStart() {
		
	}

	@Override
	public void onRemove() {
		
	}

}
