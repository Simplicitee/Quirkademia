package me.simp.quirkademia.quirk.explosion;

import org.bukkit.ChatColor;
import org.bukkit.Location;

import me.simp.quirkademia.ability.QuirkAbility;
import me.simp.quirkademia.quirk.QuirkUser;
import me.simp.quirkademia.quirk.explosion.ExplosionTracker.ExplosionType;

public class ExplosionCycle extends QuirkAbility {

	public ExplosionCycle(QuirkUser user) {
		super(user);
		
		if (manager.hasAbility(user, ExplosionTracker.class)) {
			ExplosionType type = manager.getAbility(user, ExplosionTracker.class).cycleType();
			
			methods.sendActionBarMessage(ChatColor.RED + "Explosion Type: " + ChatColor.GOLD + type.toString(), player);
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
