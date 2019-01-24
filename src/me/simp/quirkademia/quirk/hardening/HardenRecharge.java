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
		if (user.getStamina().getValue() >= user.getStamina().getMaxStamina()) {
			return false;
		}
		
		user.getStamina().setValue(user.getStamina().getValue() + 1);
		
		return true;
	}

	@Override
	public void onStart() {
		
	}

	@Override
	public void onRemove() {
		
	}

}
