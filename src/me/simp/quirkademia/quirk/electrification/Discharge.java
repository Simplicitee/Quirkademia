package me.simp.quirkademia.quirk.electrification;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.simp.quirkademia.ability.QuirkAbility;
import me.simp.quirkademia.configuration.ConfigType;
import me.simp.quirkademia.quirk.QuirkUser;
import me.simp.quirkademia.util.ParticleEffect;

public class Discharge extends QuirkAbility {
	
	private Set<Location> bolts;
	private double range;
	private Location start;
	private int charge;
	private Static passive;

	public Discharge(QuirkUser user) {
		super(user);
		
		if (!manager.hasAbility(user, Static.class)) {
			return;
		}
		
		this.passive = manager.getAbility(user, Static.class);
		
		if (passive.isDumbMode()) {
			return;
		}
		
		this.bolts = new HashSet<>();
		this.charge = 0;
		this.range = configs.getConfiguration(ConfigType.ABILITIES).getDouble("Abilities.Electrification.Discharge.Range");
		
		manager.start(this);
	}

	@Override
	public Location getLocation() {
		return player.getLocation();
	}

	@Override
	public boolean progress() {
		if (!player.isSneaking()) {
			return false;
		}
		
		if (charge++ % 20 == 0) {
			if (passive.useCharge(240)) {
				this.start = player.getLocation().clone().add(0, 1, 0);
				
				for (int i = -180; i < 180; i += 4) {
					Location bolt = start.clone();
					
					bolt.setYaw(bolt.getYaw() + i);
					bolt.setPitch((new Random().nextFloat() * 90) - 45f);
					bolt.getDirection().normalize();
					
					bolts.add(bolt);
				}
			}
		}
		
		Iterator<Location> iter = bolts.iterator();
		while (iter.hasNext()) {
			Location bolt = iter.next();
			
			if (bolt.distance(start) >= range) {
				iter.remove();
				continue;
			} else if (!bolt.getBlock().isPassable()) {
				iter.remove();
				continue;
			}
			
			Random r = new Random();
			int rand = r.nextInt(11) - 5;
			
			bolt.setYaw(bolt.getYaw() + rand);
			bolt.setPitch(bolt.getPitch() + (r.nextFloat() - 0.5f));
			bolt.add(bolt.getDirection());
			
			for (Entity e : methods.getEntitiesAroundPoint(bolt, 1.3)) {
				if (e instanceof LivingEntity && e.getEntityId() != player.getEntityId()) {
					LivingEntity le = (LivingEntity) e;
					
					le.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 10, 10, true, false), true);
					le.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20, 10, true, false), true);
					methods.damageEntity(le, player, this, 2);
				}
			}
			
			ParticleEffect.displayColoredParticle("f6f600", bolt, 4, 0.1, 0.1, 0.1);
		}
		
		if (bolts.isEmpty()) {
			return false;
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
