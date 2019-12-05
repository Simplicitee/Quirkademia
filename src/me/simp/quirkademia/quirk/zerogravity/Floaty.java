package me.simp.quirkademia.quirk.zerogravity;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import me.simp.quirkademia.ability.QuirkAbility;
import me.simp.quirkademia.configuration.ConfigType;
import me.simp.quirkademia.object.Stamina;
import me.simp.quirkademia.quirk.QuirkUser;

public class Floaty extends QuirkAbility {
	
	private Set<Entity> floating;
	private Stamina floaty;

	public Floaty(QuirkUser user) {
		super(user);
		
		int max = configs.getConfiguration(ConfigType.ABILITIES).getInt("Abilities.ZeroGravity.Passive.WeightLimit");
		
		floating = new HashSet<>();
		floaty = new Stamina(user.getUniqueId(), "Weight Limit", BarColor.PINK, max, max);
		
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
		
		if (floaty.getValue() < floaty.getMaxStamina()/6) {
			player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 40, 2, true, false));
		}
		
		return true;
	}
	
	public boolean canFloat(int weight) {
		return floaty.getValue() - weight >= 0;
	}
	
	public void makeFloat(Entity e, int weight) {
		int diff = floaty.getValue() - weight;
		
		if (diff >= 0) {
			if (floating.add(e)) {
				floaty.setValue(diff);
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
		floaty.setValue(floaty.getMaxStamina());
	}

	@Override
	public void onStart() {
		
	}

	@Override
	public void onRemove() {
		floaty.getBar().destroy(player);
	}

}
