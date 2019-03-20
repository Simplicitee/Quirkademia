package me.simp.quirkademia.quirk.electrification;

import java.util.LinkedList;
import java.util.Queue;

import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.simp.quirkademia.ability.QuirkAbility;
import me.simp.quirkademia.quirk.QuirkStamina;
import me.simp.quirkademia.quirk.QuirkUser;
import me.simp.quirkademia.util.ParticleEffect;

public class Static extends QuirkAbility {
	
	private boolean dumbMode;
	private QuirkStamina charge;
	private Queue<DischargeType> cycle;

	public Static(QuirkUser user) {
		super(user);
		
		this.dumbMode = false;
		this.charge = new QuirkStamina(user.getUniqueId(), "Static Charge", BarColor.YELLOW, 1600, 1600);
		
		this.cycle = new LinkedList<>();
		this.cycle.add(DischargeType.NONE);
		this.cycle.add(DischargeType.INDISCRIMINATE);
		this.cycle.add(DischargeType.POINTER_N_SHOOTER);
		
		manager.start(this);
	}
	
	public static enum DischargeType {
		NONE, INDISCRIMINATE, POINTER_N_SHOOTER;
	}

	@Override
	public Location getLocation() {
		return player.getLocation();
	}

	@Override
	public boolean progress() {
		if (charge.getValue() < 400) {
			player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 160, 10, true, false));
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
	
	public DischargeType getSelectedType() {
		return cycle.peek();
	}
	
	public Static cycleType() {
		cycle.add(cycle.poll());
		
		DischargeType cycled = cycle.peek();
		
		methods.sendActionBarMessage("&6Discharged type: &e" + cycled.toString(), player);
		
		return this;
	}
}
