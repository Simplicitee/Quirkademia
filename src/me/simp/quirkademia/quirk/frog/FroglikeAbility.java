package me.simp.quirkademia.quirk.frog;

import org.bukkit.Location;

import me.simp.quirkademia.ability.QuirkAbility;
import me.simp.quirkademia.quirk.QuirkUser;
import me.simp.quirkademia.quirk.QuirkUser.StatusEffect;

public class FroglikeAbility extends QuirkAbility {

	public FroglikeAbility(QuirkUser user) {
		super(user);
		
		plugin.getAbilityManager().start(this);
	}

	@Override
	public Location getLocation() {
		return player.getLocation();
	}

	@Override
	public boolean progress() {
		user.getStatus().add(StatusEffect.INCREASED_JUMP, 4);
		return true;
	}

	@Override
	public void onStart() {
	}

	@Override
	public void onRemove() {
		user.getStatus().remove(StatusEffect.INCREASED_JUMP);
	}

}
