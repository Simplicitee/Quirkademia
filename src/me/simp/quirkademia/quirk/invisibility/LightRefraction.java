package me.simp.quirkademia.quirk.invisibility;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import me.simp.quirkademia.ability.QuirkAbility;
import me.simp.quirkademia.configuration.ConfigType;
import me.simp.quirkademia.quirk.QuirkUser;
import me.simp.quirkademia.util.ParticleEffect;

public class LightRefraction extends QuirkAbility {

	private Location loc;
	private int range, current;
	private double radius;
	
	public LightRefraction(QuirkUser user) {
		super(user);
		
		if (manager.hasAbility(user, LightRefraction.class)) {
			return;
		} else if (user.hasCooldown("light refraction")) {
			return;
		}
		
		current = 1;
		loc = player.getLocation().clone().add(0, 1, 0);
		range = configs.getConfiguration(ConfigType.ABILITIES).getInt("Abilities.Invisibility.LightRefraction.Range");
		radius = configs.getConfiguration(ConfigType.ABILITIES).getDouble("Abilities.Invisibility.LightRefraction.Radius");
		
		manager.start(this);
	}

	@Override
	public Location getLocation() {
		return loc;
	}

	@Override
	public boolean progress() {
		if (current >= range) {
			user.addCooldown("light refraction", 10000);
			return false;
		}
		
		if (!player.isSneaking()) {
			user.addCooldown("light refraction", 10000);
			return false;
		}
		
		Vector direction = player.getEyeLocation().getDirection().clone().normalize();
		Location last = loc.clone();
		
		for (int i = 0; i < current; i++) {
			last.add(direction);
			
			for (Entity e : methods.getEntitiesAroundPoint(last, radius + 1)) {
				if (e instanceof LivingEntity && e.getEntityId() != player.getEntityId()) {
					LivingEntity lent = (LivingEntity) e;
					
					lent.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60, 2, true, false), true);
				}
			}
			
			ParticleEffect.END_ROD.display(last, 2, radius, radius, radius);
			ParticleEffect.displayColoredParticle("ffffff", last, 2, radius, radius, radius);
		}
		
		current++;
		loc = last;
		
		return true;
	}

	@Override
	public void onStart() {
		
	}

	@Override
	public void onRemove() {
		
	}

}
