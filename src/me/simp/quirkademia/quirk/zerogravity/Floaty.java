package me.simp.quirkademia.quirk.zerogravity;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import me.simp.quirkademia.ability.QuirkAbility;
import me.simp.quirkademia.quirk.QuirkUser;

public class Floaty extends QuirkAbility {
	
	private Set<Entity> floating;

	public Floaty(QuirkUser user) {
		super(user);
		
		floating = new HashSet<>();
		
		manager.start(this);
	}

	@Override
	public Location getLocation() {
		return player.getLocation();
	}

	@Override
	public boolean progress() {
		for (Entity e : floating) {
			if (e instanceof LivingEntity) {
				((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 5, 1, true, false), true);
			} else if (e instanceof FallingBlock) {
				FallingBlock fb = (FallingBlock) e;
				
				fb.setGravity(false);
				fb.setVelocity(new Vector(0, 0.15, 0));
				fb.setHurtEntities(false);
			}
		}
		
		player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 5, 2, true, false), true);
		
		if (user.getStamina().getValue() < user.getStamina().getMaxStamina()/6) {
			player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 40, 2, true, false));
		}
		
		return true;
	}
	
	public void makeFloat(Entity e, int weight) {
		int diff = user.getStamina().getValue() - weight;
		
		if (diff >= 0) {
			if (floating.add(e)) {
				user.getStamina().setValue(diff);
			}
		}
	}
	
	public void release() {
		for (Entity e : floating) {
			if (e instanceof FallingBlock) {
				FallingBlock fb = (FallingBlock) e;
				
				fb.setGravity(true);
				fb.setHurtEntities(true);
			}
		}
		
		floating.clear();
		user.getStamina().setValue(user.getStamina().getMaxStamina());
	}

	@Override
	public void onStart() {
		
	}

	@Override
	public void onRemove() {
		
	}

}
