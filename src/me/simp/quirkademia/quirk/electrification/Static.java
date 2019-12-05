package me.simp.quirkademia.quirk.electrification;

import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.simp.quirkademia.ability.QuirkAbility;
import me.simp.quirkademia.object.Stamina;
import me.simp.quirkademia.quirk.QuirkUser;
import me.simp.quirkademia.util.ParticleEffect;

public class Static extends QuirkAbility {
	
	private boolean dumbMode;
	private Stamina charge;

	public Static(QuirkUser user) {
		super(user);
		
		this.dumbMode = false;
		this.charge = new Stamina(user.getUniqueId(), "Static Charge", BarColor.YELLOW, 1600, 1600);
		
		manager.start(this);
	}

	@Override
	public Location getLocation() {
		return player.getLocation();
	}

	@Override
	public boolean progress() {
		if (charge.getValue() < 400) {
			player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 670, 10, true, false));
			dumbMode = true;
		} else {
			dumbMode = false;
		}
		
		charge.setValue(charge.getValue() + 1);
		
		for (Entity e : methods.getEntitiesAroundPoint(player.getLocation(), 1.1)) {
			if (e instanceof LivingEntity && e.getEntityId() != player.getEntityId()) {
				LivingEntity le = (LivingEntity) e;
				
				methods.damageEntity(le, player, this, 0.5);
				le.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 10, 3, true, false));
				
				ParticleEffect.displayColoredParticle("f6f600", e.getLocation().clone().add(0, 1, 0), 4, 0.1, 0.1, 0.1);
			}
		}
		
		return true;
	}

	@Override
	public void onStart() {
		
	}

	@Override
	public void onRemove() {
		charge.getBar().destroy(player);
	}

	public boolean isDumbMode() {
		return dumbMode;
	}
	
	public boolean useCharge(int amount) {
		int diff = charge.getValue() - amount;
		
		if (diff < 0) {
			return false;
		}
		
		charge.setValue(diff);
		return true;
	}
	
	public int expendAll() {
		int val = charge.getValue();
		
		charge.setValue(0);
		
		return val;
	}
}
