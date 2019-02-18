package me.simp.quirkademia.quirk.zerogravity;

import org.bukkit.Location;

import me.simp.quirkademia.ability.QuirkAbility;
import me.simp.quirkademia.quirk.QuirkUser;

public class FloatSelf extends QuirkAbility {

	public FloatSelf(QuirkUser user) {
		super(user);
		
		if (player.isOnGround()) {
			if (manager.hasAbility(user, Floaty.class)) {
				Floaty passive = manager.getAbility(user, Floaty.class);
				
				if (!passive.canFloat(1500)) {
					return;
				}
				
				passive.makeFloat(player, 1500);
			}
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
