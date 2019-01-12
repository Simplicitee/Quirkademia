package me.simp.quirkademia.quirk.frog;

import org.bukkit.ChatColor;
import org.bukkit.Location;

import me.simp.quirkademia.ability.QuirkAbility;
import me.simp.quirkademia.quirk.QuirkUser;
import me.simp.quirkademia.quirk.frog.Froglike.TongueType;

public class TongueCycle extends QuirkAbility {
	
	public TongueCycle(QuirkUser user) {
		super(user);
		
		if (manager.hasAbility(user, Froglike.class)) {
			TongueType type = manager.getAbility(user, Froglike.class).cycleType();
			
			methods.sendActionBarMessage(ChatColor.LIGHT_PURPLE + "Selected Tongue: " + ChatColor.GREEN + type.toString(), player);
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
