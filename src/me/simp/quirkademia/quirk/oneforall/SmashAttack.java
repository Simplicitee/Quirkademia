package me.simp.quirkademia.quirk.oneforall;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import me.simp.quirkademia.ability.QuirkAbility;
import me.simp.quirkademia.configuration.ConfigType;
import me.simp.quirkademia.quirk.QuirkUser;
import me.simp.quirkademia.quirk.oneforall.SmashTracker.SmashType;
import me.simp.quirkademia.util.ParticleEffect;

public class SmashAttack extends QuirkAbility {
	
	private SmashType type;
	private Location start, loc;
	private Vector direction;
	private int range, stamina;
	private double radius, power;
	private long cooldown;

	public SmashAttack(QuirkUser user) {
		super(user);
			
		if (manager.hasAbility(user, SmashTracker.class)) {
			type = manager.getAbility(user, SmashTracker.class).getType();
		} else {
			return;
		}
		
		if (type != SmashType.NONE) { 
			if (user.hasCooldown(type.toString().toLowerCase() + " smash")) {
				return;
			}
			
			cooldown = configs.getConfiguration(ConfigType.ABILITIES).getLong("Abilities.OneForAll.Smash." + type.toString() + ".Cooldown");
			range = configs.getConfiguration(ConfigType.ABILITIES).getInt("Abilities.OneForAll.Smash." + type.toString() + ".Range");
			radius = configs.getConfiguration(ConfigType.ABILITIES).getDouble("Abilities.OneForAll.Smash." + type.toString() + ".Radius");
			power = configs.getConfiguration(ConfigType.ABILITIES).getDouble("Abilities.OneForAll.Smash." + type.toString() + ".Power");
			stamina = configs.getConfiguration(ConfigType.ABILITIES).getInt("Abilities.OneForAll.Smash." + type.toString() + ".StaminaUse");
			
			int diff = user.getStamina().getValue() - stamina; 
			if (diff < 0) {
				return;
			}
			
			user.getStamina().setValue(diff);
			
			start = player.getEyeLocation().clone();
			loc = start.clone();
			direction = start.getDirection().normalize();

			user.addCooldown(type.toString().toLowerCase() + " smash", cooldown);
			
			manager.start(this);
		}
	}

	@Override
	public Location getLocation() {
		return loc;
	}

	@Override
	public boolean progress() {
		loc = loc.add(direction);
		
		if (start.distance(loc) > range) {
			return false;
		}
		
		ParticleEffect.CLOUD.display(loc, 16, radius, radius, radius);
		if (type == SmashType.DETROIT) {
			ParticleEffect.displayColoredParticle("03c58b", loc, 5, 0.1, 0.1, 0.1);
		}
		
		for (Entity e : plugin.getMethods().getEntitiesAroundPoint(loc, radius + 1)) {
			if (e instanceof LivingEntity && player.getEntityId() != e.getEntityId()) {
				e.setVelocity(direction.clone().multiply(power));
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

	public SmashType getType() {
		return type;
	}
}
