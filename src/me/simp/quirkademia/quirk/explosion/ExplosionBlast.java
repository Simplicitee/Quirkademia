package me.simp.quirkademia.quirk.explosion;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import me.simp.quirkademia.ability.QuirkAbility;
import me.simp.quirkademia.configuration.ConfigType;
import me.simp.quirkademia.quirk.QuirkUser;
import me.simp.quirkademia.quirk.explosion.ExplosionTracker.ExplosionType;
import me.simp.quirkademia.util.ParticleEffect;

public class ExplosionBlast extends QuirkAbility {
	
	private ExplosionType type;
	private Location loc, start;
	private Vector direction;
	private int range, stamina;
	private double radius, power;
	private long cooldown; 

	public ExplosionBlast(QuirkUser user) {
		super(user);
		
		if (manager.hasAbility(user, ExplosionTracker.class)) {
			type = manager.getAbility(user, ExplosionTracker.class).getType();
		} else {
			return;
		}
		
		if (type != ExplosionType.NONE) {
			if (user.hasCooldown(type.toString().toLowerCase() + " blast")) {
				return;
			}
			
			cooldown = configs.getConfiguration(ConfigType.ABILITIES).getLong("Abilities.Explosion.Blast." + type.toString() + ".Cooldown");
			range = configs.getConfiguration(ConfigType.ABILITIES).getInt("Abilities.Explosion.Blast." + type.toString() + ".Range");
			radius = configs.getConfiguration(ConfigType.ABILITIES).getDouble("Abilities.Explosion.Blast." + type.toString() + ".Radius");
			power = configs.getConfiguration(ConfigType.ABILITIES).getDouble("Abilities.Explosion.Blast." + type.toString() + ".Power");
			stamina = configs.getConfiguration(ConfigType.ABILITIES).getInt("Abilities.Explosion.Blast." + type.toString() + ".StaminaUse");
			
			if (player.getVelocity().length() > 0.5) {
				cooldown += 1000 * player.getVelocity().length();
				range *= player.getVelocity().length();
				radius += player.getVelocity().length()/5;
				power += player.getVelocity().length()/5;
				stamina += 20 * player.getVelocity().length();
			}
			
			int diff = user.getStamina().get() - stamina;
			
			if (diff < 0) {
				return;
			}
			
			user.getStamina().set(diff);
			
			double originPower = power;
			
			
			
			loc = player.getEyeLocation().clone();
			start = loc.clone();
			direction = loc.getDirection().clone().normalize();
			
			user.addCooldown(type.toString().toLowerCase() + " blast", cooldown);
			
			if (!player.isOnGround()) {
				player.setFallDistance(0);
				player.setVelocity(direction.clone().multiply(-originPower/6));
			}
			
			manager.start(this);
		}
	}

	@Override
	public Location getLocation() {
		return loc;
	}

	@Override
	public boolean progress() {
		if (loc.distance(start) >= range) {
			return false;
		}
		
		loc.add(direction);
		
		if (loc.getBlock().getType().isSolid()) {
			loc.getWorld().createExplosion(loc, (float) radius * 3, true);
			return false;
		}
		
		ParticleEffect.displayColoredParticle("fbff18", loc, (int) (50 * radius), radius, radius, radius);
		
		if (type == ExplosionType.NORMAL) {
			ParticleEffect.SMOKE_NORMAL.display(loc, 2, radius, radius, radius);
		} else if (type == ExplosionType.LARGE) {
			ParticleEffect.EXPLOSION_LARGE.display(loc, 1);
		}
		
		for (Entity e : methods.getEntitiesAroundPoint(loc, radius + 1)) {
			if (e instanceof LivingEntity && e.getEntityId() != player.getEntityId()) {
				e.setVelocity(direction.clone().multiply(power/4));
				methods.damageEntity((LivingEntity) e, player, this, power);
			}
		}
		
		return true;
	}

	@Override
	public void onStart() {
		
	}

	@Override
	public void onRemove() {
		
	}

}
