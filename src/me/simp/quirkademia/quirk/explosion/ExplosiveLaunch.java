package me.simp.quirkademia.quirk.explosion;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import me.simp.quirkademia.ability.QuirkAbility;
import me.simp.quirkademia.configuration.ConfigType;
import me.simp.quirkademia.quirk.QuirkUser;
import me.simp.quirkademia.util.ParticleEffect;

public class ExplosiveLaunch extends QuirkAbility {

	private long chargeTime, cooldown, startTime;
	private double power;
	private boolean charged;
	private int stamina;
	
	public ExplosiveLaunch(QuirkUser user) {
		super(user);
		
		if (!player.isOnGround()) {
			return;
		} else if (user.hasCooldown("Explosive Launch")) {
			return;
		}
		
		chargeTime = configs.getConfiguration(ConfigType.ABILITIES).getLong("Abilities.Explosion.ExplosiveLaunch.ChargeTime");
		cooldown = configs.getConfiguration(ConfigType.ABILITIES).getLong("Abilities.Explosion.ExplosiveLaunch.Cooldown");
		power = configs.getConfiguration(ConfigType.ABILITIES).getDouble("Abilities.Explosion.ExplosiveLaunch.Power");
		stamina = configs.getConfiguration(ConfigType.ABILITIES).getInt("Abilities.Explosion.ExplosiveLaunch.StaminaUse");
		
		int diff = user.getStamina().get() - stamina;
		
		if (diff < 0) {
			return;
		}
		
		user.getStamina().set(diff);
		
		manager.start(this);
	}

	@Override
	public Location getLocation() {
		return player.getLocation();
	}

	@Override
	public boolean progress() {
		if (!charged) {
			if (!player.isSneaking()) {
				return false;
			}
			
			if (System.currentTimeMillis() >= startTime + chargeTime) {
				charged = true;
			} else {
				ParticleEffect.displayColoredParticle("fbff18", player.getLocation(), 13, 0.1, 0.01, 0.1);
			}
			
			return true;
		}
		
		ParticleEffect.EXPLOSION_HUGE.display(player.getLocation(), 1);
		ParticleEffect.displayColoredParticle("fbff18", player.getLocation(), 13, 0.3, 0.3, 0.3);
		
		Vector direction = player.getEyeLocation().getDirection().clone();
		
		player.setVelocity(direction.normalize().setY(power));
		user.addCooldown("Explosive Launch", cooldown);
		
		return false;
	}

	@Override
	public void onStart() {
		startTime = System.currentTimeMillis();
	}

	@Override
	public void onRemove() {
		
	}

}
