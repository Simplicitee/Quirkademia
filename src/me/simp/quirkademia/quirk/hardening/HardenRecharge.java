package me.simp.quirkademia.quirk.hardening;

import org.bukkit.Location;

import me.simp.quirkademia.ability.QuirkAbility;
import me.simp.quirkademia.quirk.QuirkUser;

public class HardenRecharge extends QuirkAbility {
	
	private Hardening passive;

	public HardenRecharge(QuirkUser user) {
		super(user);
		
		if (!manager.hasAbility(user, Hardening.class)) {
			return;
		}
		
		passive = manager.getAbility(user, Hardening.class);
		
		manager.start(this);
	}

	@Override
	public Location getLocation() {
		return null;
	}

	@Override
	public boolean progress() {
		if (passive.recharge()) {
			return true;
		}
		
		return false;
	}

	@Override
	public void onStart() {
		
	}

	@Override
	public void onRemove() {
		
	}

}
