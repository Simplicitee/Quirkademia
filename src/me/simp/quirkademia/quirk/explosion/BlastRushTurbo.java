package me.simp.quirkademia.quirk.explosion;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import me.simp.quirkademia.ability.QuirkAbility;
import me.simp.quirkademia.configuration.ConfigType;
import me.simp.quirkademia.quirk.QuirkUser;
import me.simp.quirkademia.util.ParticleEffect;

public class BlastRushTurbo extends QuirkAbility {

	public BlastRushTurbo(QuirkUser user) {
		super(user);
		
		int stamina = configs.getConfiguration(ConfigType.ABILITIES).getInt("Abilities.Explosion.BlastRushTurbo.StaminaUse");
		int diff = user.getStamina().get() - stamina;
		
		if (diff < 0) {
			return;
		}
		
		user.getStamina().set(diff);
		
		Vector direction = player.getEyeLocation().getDirection().clone().normalize();
		double power = configs.getConfiguration(ConfigType.ABILITIES).getDouble("Abilities.Explosion.BlastRushTurbo.Power");
		
		if (direction.getY() > 0.2) {
			direction.setY(0.2);
		}
		
		player.setVelocity(direction.multiply(power));
		
		Location display = player.getLocation().clone().add(0, 1, 0);
		ParticleEffect.displayColoredParticle("fbff18", display, 8, 0.25, 0.25, 0.25);
		ParticleEffect.EXPLOSION_LARGE.display(display.add(direction.multiply(-1)), 1);
		
		player.setFallDistance(0);
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
