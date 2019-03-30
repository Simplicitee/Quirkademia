package me.simp.quirkademia.quirk.navellaser;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import me.simp.quirkademia.ability.QuirkAbility;
import me.simp.quirkademia.configuration.ConfigType;
import me.simp.quirkademia.quirk.QuirkUser;
import me.simp.quirkademia.util.ParticleEffect;

public class Laser extends QuirkAbility {
	
	private Map<Location, Location> beam;
	private double range, damage, radius;
	private long start;

	public Laser(QuirkUser user) {
		super(user);
		
		if (manager.hasAbility(user, this.getClass())) {
			return;
		} else if (user.hasCooldown("navellaser")) {
			return;
		}
		
		beam = new HashMap<>();
		radius = 0.2;
		
		damage = configs.getConfiguration(ConfigType.ABILITIES).getDouble("Abilities.NavelLaser.Laser.Damage");
		range = configs.getConfiguration(ConfigType.ABILITIES).getDouble("Abilities.NavelLaser.Laser.Range");
		
		start = System.currentTimeMillis();
		
		manager.start(this);
	}

	@Override
	public Location getLocation() {
		return beam.keySet().iterator().next();
	}

	@Override
	public boolean progress() {
		if (!player.isSneaking()) {
			return false;
		}
		
		if (System.currentTimeMillis() - start >= 1000) {
			return false;
		}
		
		Location current = player.getLocation().clone().add(0, 0.9, 0);
		current.getDirection().normalize();
		
		if (!player.isOnGround()) {
			player.setVelocity(current.getDirection().clone().multiply(-0.6));
		}
		
		beam.put(current, current.clone());
		
		Map<Location, Location> map = new HashMap<>();
		
		Iterator<Location> locIter = beam.keySet().iterator();
		while (locIter.hasNext()) {
			Location loc = locIter.next();
			Location start = beam.get(loc);
			
			if (loc.distance(start) >= range) {
				locIter.remove();
				continue;
			} else if (!loc.getBlock().isPassable()) {
				locIter.remove();
				continue;
			}
			
			ParticleEffect.displayColoredParticle("a9fffd", loc, 4, radius, radius, radius);
			
			for (Entity e : methods.getEntitiesAroundPoint(loc, 1 + radius)) {
				if (e instanceof LivingEntity && e.getEntityId() != player.getEntityId()) {
					methods.damageEntity((LivingEntity) e, player, this, damage);
				}
			}
			
			locIter.remove();
			loc.add(loc.getDirection());
			map.put(loc, start);
		}
		
		beam.clear();
		beam.putAll(map);
		
		return true;
	}

	@Override
	public void onStart() {
		
	}

	@Override
	public void onRemove() {
		user.addCooldown("navellaser", 1000);
		beam.clear();
	}

}
