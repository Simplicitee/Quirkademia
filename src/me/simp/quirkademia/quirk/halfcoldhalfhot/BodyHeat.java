package me.simp.quirkademia.quirk.halfcoldhalfhot;

import java.util.LinkedList;

import org.bukkit.Location;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.simp.quirkademia.ability.QuirkAbility;
import me.simp.quirkademia.quirk.QuirkUser;
import me.simp.quirkademia.util.ParticleEffect;
import me.simp.quirkademia.util.StatusEffect;

public class BodyHeat extends QuirkAbility {
	
	private int temperature, max;
	private long lastChange;
	private IcyHotAbility active;
	private LinkedList<IcyHotAbility> cycle;

	public BodyHeat(QuirkUser user) {
		super(user);
		
		max = user.getStamina().getMaxStamina();
		temperature = max/2;
		lastChange = System.currentTimeMillis();
		
		active = IcyHotAbility.NONE;
		cycle = new LinkedList<>();
		cycle.add(IcyHotAbility.BLAST);
		cycle.add(IcyHotAbility.WALL);
		
		manager.start(this);
	}

	@Override
	public Location getLocation() {
		return player.getLocation();
	}

	@Override
	public boolean progress() {
		if (System.currentTimeMillis() >= lastChange + 10000) {
			if (temperature > max/2) {
				temperature--;
			} else if (temperature < max/2) {
				temperature++;
			}
		}
		
		if (!inRange(temperature, max/2 + max/4, max/2 - max/4)) {
			if (temperature < max/2) {
				ParticleEffect.displayColoredParticle("caffff", player.getLocation().clone().add(0, 1, 0), Math.min(3, (max - temperature)/10), 0.1, 0.5, 0.1);
			} else if (temperature > max/2) {
				ParticleEffect.FLAME.display(player.getLocation().clone().add(0, 1, 0), Math.min(3, (max - temperature)), 0.1, 0.5, 0.1);
			}
			
			user.getStatus().remove(StatusEffect.INCREASED_SPEED);
		} else {
			user.getStatus().add(StatusEffect.INCREASED_SPEED, 1);
		}
		
		if (temperature == 0) {
			player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 5, 2, true, false), true);
		} else if (temperature == max) {
			player.setFireTicks(20);
		}
		
		user.getStamina().set(temperature);
		return true;
	}

	@Override
	public void onStart() {
		user.getStatus().add(StatusEffect.INCREASED_SPEED, 1);
	}

	@Override
	public void onRemove() {
		user.getStatus().remove(StatusEffect.INCREASED_SPEED);
	}
	
	private boolean inRange(int value, int max, int min) {
		return value <= max && value >= min;
	}
	
	public IcyHotAbility getType() {
		return active;
	}
	
	public IcyHotAbility cycleAbility() {
		this.cycle.add(active);
		this.active = this.cycle.poll();
		return active;
	}
	
	/**
	 * 
	 * @param temp the temperature to set body to, min temp is 0, max is the quirk's max stamina
	 * @return
	 */
	public BodyHeat setTemperature(int temp) {
		if (temp > max) {
			temp = max;
		} else if (temp < 0) {
			temp = 0;
		}
		
		this.lastChange = System.currentTimeMillis();
		this.temperature = temp;
		return this;
	}
	
	public int getTemperature() {
		return temperature;
	}

	public static enum IcyHotAbility {
		NONE, BLAST, WALL
	}
}
