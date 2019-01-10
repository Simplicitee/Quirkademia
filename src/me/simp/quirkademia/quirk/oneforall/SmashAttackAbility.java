package me.simp.quirkademia.quirk.oneforall;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import me.simp.quirkademia.ability.QuirkAbility;
import me.simp.quirkademia.configuration.ConfigType;
import me.simp.quirkademia.quirk.QuirkUser;
import me.simp.quirkademia.quirk.oneforall.SmashAbility.SmashType;
import me.simp.quirkademia.util.ParticleEffect;

public class SmashAttackAbility extends QuirkAbility {
	
	private SmashType type;
	private Location start, loc;
	private Vector direction;
	private int range;
	private double radius;
	private double power;

	public SmashAttackAbility(QuirkUser user) {
		super(user);
			
		if (manager.hasAbility(user, SmashAbility.class)) {
			type = manager.getAbility(user, SmashAbility.class).getType();
			
			if (manager.hasAbility(user, this.getClass())) {
				SmashType type2 = manager.getAbility(user, this.getClass()).getType();
				
				if (type2 == type) {
					return;
				}
			}
		} else {
			type = SmashType.NONE;
		}
		
		if (type != SmashType.NONE) { 
			if (user.hasCooldown(type.toString().toLowerCase() + " smash")) {
				return;
			}
			
			range = configs.getConfiguration(ConfigType.ABILITIES).getInt("Abilities.OneForAll.Smash." + type.toString() + ".Range");
			radius = configs.getConfiguration(ConfigType.ABILITIES).getDouble("Abilities.OneForAll.Smash." + type.toString() + ".Radius");
			power = configs.getConfiguration(ConfigType.ABILITIES).getDouble("Abilities.OneForAll.Smash." + type.toString() + ".Power");
			start = player.getEyeLocation().clone();
			loc = start.clone();
			direction = start.getDirection().normalize();

			user.addCooldown(type.toString().toLowerCase() + " smash", 4000);
			
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
