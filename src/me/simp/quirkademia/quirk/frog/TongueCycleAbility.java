package me.simp.quirkademia.quirk.frog;

import org.bukkit.ChatColor;
import org.bukkit.Location;

import me.simp.quirkademia.ability.QuirkAbility;
import me.simp.quirkademia.quirk.QuirkUser;
import me.simp.quirkademia.quirk.frog.FroglikeAbility.TongueType;

public class TongueCycleAbility extends QuirkAbility {
	
	public TongueCycleAbility(QuirkUser user) {
		super(user);
		
		if (manager.hasAbility(user, FroglikeAbility.class)) {
			FroglikeAbility abil = manager.getAbility(user, FroglikeAbility.class);
			TongueType type = abil.cycleType();
			player.sendMessage(ChatColor.LIGHT_PURPLE + "Selected Tongue: " + ChatColor.GREEN + type.toString());
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
