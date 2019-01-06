package me.simp.quirkademia.quirk.oneforall;

import org.bukkit.ChatColor;
import org.bukkit.Location;

import me.simp.quirkademia.ability.QuirkAbility;
import me.simp.quirkademia.quirk.QuirkUser;
import me.simp.quirkademia.quirk.oneforall.SmashAbility.SmashType;

public class SmashCycleAbility extends QuirkAbility {

	public SmashCycleAbility(QuirkUser user) {
		super(user);
		
		if (plugin.getAbilityManager().hasAbility(user, SmashAbility.class)) {
			SmashAbility abil = plugin.getAbilityManager().getAbility(user, SmashAbility.class);
			SmashType type = abil.cycleType();
			
			player.sendMessage(ChatColor.GREEN + "Smash ability: " + ChatColor.AQUA + type.toString());
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
