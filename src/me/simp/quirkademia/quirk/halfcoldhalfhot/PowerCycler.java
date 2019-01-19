package me.simp.quirkademia.quirk.halfcoldhalfhot;

import org.bukkit.Location;

import me.simp.quirkademia.ability.QuirkAbility;
import me.simp.quirkademia.quirk.QuirkUser;
import me.simp.quirkademia.quirk.halfcoldhalfhot.BodyHeat.IcyHotAbility;
import net.md_5.bungee.api.ChatColor;

public class PowerCycler extends QuirkAbility {

	public PowerCycler(QuirkUser user) {
		super(user);
		
		if (manager.hasAbility(user, BodyHeat.class)) {
			IcyHotAbility type = manager.getAbility(user, BodyHeat.class).cycleAbility();
			
			methods.sendActionBarMessage(ChatColor.translateAlternateColorCodes('&', "&bIcy Hot Ability: &c" + type.toString()), player);
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
