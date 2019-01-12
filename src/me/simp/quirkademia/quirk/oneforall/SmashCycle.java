package me.simp.quirkademia.quirk.oneforall;

import org.bukkit.ChatColor;
import org.bukkit.Location;

import me.simp.quirkademia.ability.QuirkAbility;
import me.simp.quirkademia.quirk.QuirkUser;
import me.simp.quirkademia.quirk.oneforall.SmashTracker.SmashType;

public class SmashCycle extends QuirkAbility {

	public SmashCycle(QuirkUser user) {
		super(user);
		
		if (manager.hasAbility(user, SmashTracker.class)) {
			SmashType type = manager.getAbility(user, SmashTracker.class).cycleType();
			
			methods.sendActionBarMessage(ChatColor.GREEN + "Smash ability: " + ChatColor.AQUA + type.toString(), player);
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
