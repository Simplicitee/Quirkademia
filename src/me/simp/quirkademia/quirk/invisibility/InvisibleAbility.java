package me.simp.quirkademia.quirk.invisibility;

import org.bukkit.Location;

import me.simp.quirkademia.ability.QuirkAbility;
import me.simp.quirkademia.quirk.QuirkUser;
import me.simp.quirkademia.quirk.QuirkUser.StatusEffect;

public class InvisibleAbility extends QuirkAbility {

	public InvisibleAbility(QuirkUser user) {
		super(user);
		
		user.getStatus().add(StatusEffect.INVISIBLE, 1);
		
		manager.start(this);
	}

	@Override
	public Location getLocation() {
		return player.getLocation();
	}

	@Override
	public boolean progress() {
		return true;
	}

	@Override
	public void onStart() {
		
	}

	@Override
	public void onRemove() {
		user.getStatus().remove(StatusEffect.INVISIBLE);
	}

}
