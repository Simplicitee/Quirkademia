package me.simp.quirkademia.quirk.oneforall;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import me.simp.quirkademia.ability.QuirkAbility;
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
		
		if (plugin.getAbilityManager().hasAbility(user, this.getClass())) {
			
		}
			
		if (plugin.getAbilityManager().hasAbility(user, SmashAbility.class)) {
			type = plugin.getAbilityManager().getAbility(user, SmashAbility.class).getType();
		} else {
			type = SmashType.NONE;
		}
		
		
		if (type != SmashType.NONE) { 
			if (user.hasCooldown(type.toString().toLowerCase() + " smash")) {
				return;
			}
			
			if (type == SmashType.DELAWARE) {
				range = 10;
				radius = 0.4;
				power = 1.5;
			} else if (type == SmashType.DETROIT) {
				range = 21;
				radius = 1.1;
				power = 3.2;
			} else {
				range = 0;
				radius = 0;
				power = 0;
			}
			
			start = player.getEyeLocation().clone();
			loc = start.clone();
			direction = start.getDirection().normalize();

			user.addCooldown(type.toString().toLowerCase() + " smash", 4000);
			
			plugin.getAbilityManager().start(this);
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
				((LivingEntity) e).damage(2.0, player);
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
