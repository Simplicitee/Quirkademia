package me.simp.quirkademia.quirk.halfcoldhalfhot;

import java.util.LinkedList;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.boss.BarColor;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.simp.quirkademia.ability.QuirkAbility;
import me.simp.quirkademia.configuration.ConfigType;
import me.simp.quirkademia.quirk.QuirkStamina;
import me.simp.quirkademia.quirk.QuirkUser;
import me.simp.quirkademia.util.ParticleEffect;
import me.simp.quirkademia.util.StatusEffect;

public class BodyHeat extends QuirkAbility {
	
	private QuirkStamina temperature;
	private long lastChange;
	private IcyHotAbility active;
	private LinkedList<IcyHotAbility> cycle;

	public BodyHeat(QuirkUser user) {
		super(user);
		
		int max = configs.getConfiguration(ConfigType.ABILITIES).getInt("Abilities.HalfColdHalfHot.Passive.MaxTemperature");
		
		temperature = new QuirkStamina(user.getUniqueId(), "Body Temperature", BarColor.RED, max/2, max);
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
			if (temperature.getValue() > temperature.getMaxStamina()/2) {
				temperature.setValue(temperature.getValue() - 1);
			} else if (temperature.getValue() < temperature.getMaxStamina()/2) {
				temperature.setValue(temperature.getValue() + 1);
			}
		}
		
		if (!inRange(temperature.getValue(), 3*temperature.getMaxStamina()/4, temperature.getMaxStamina()/4)) {
			if (temperature.getValue() < temperature.getMaxStamina()/2) {
				ParticleEffect.displayColoredParticle("caffff", player.getLocation().clone().add(0, 1, 0), Math.min(3, (temperature.getMaxStamina() - temperature.getValue())/10), 0.1, 0.5, 0.1);
			} else if (temperature.getValue() > temperature.getMaxStamina()/2) {
				ParticleEffect.FLAME.display(player.getLocation().clone().add(0, 1, 0), Math.min(3, (temperature.getMaxStamina() - temperature.getValue())), 0.1, 0.5, 0.1);
			}
			
			user.getStatus().remove(StatusEffect.INCREASED_SPEED);
		} else {
			int power = methods.isIce(player.getLocation().getBlock().getRelative(BlockFace.DOWN)) ? 2 : 1;
			
			if (user.getStatus().has(StatusEffect.INCREASED_SPEED) && user.getStatus().getPower(StatusEffect.INCREASED_SPEED) > power) {
				user.getStatus().remove(StatusEffect.INCREASED_SPEED);
			}
			
			user.getStatus().add(StatusEffect.INCREASED_SPEED, power);
		}
		
		if (temperature.getValue() == 0) {
			player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 5, 2, true, false), true);
		} else if (temperature.getValue() == temperature.getMaxStamina()) {
			player.setFireTicks(20);
		}
		
		return true;
	}

	@Override
	public void onStart() {
		user.getStatus().add(StatusEffect.INCREASED_SPEED, 1);
	}

	@Override
	public void onRemove() {
		user.getStatus().remove(StatusEffect.INCREASED_SPEED);
		temperature.getBar().destroy(player);
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
	public boolean raise(int amount) {
		int diff = temperature.getValue() + amount;
		
		if (diff > temperature.getMaxStamina()) {
			return false;
		}
		
		this.lastChange = System.currentTimeMillis();
		this.temperature.setValue(diff);
		return true;
	}
	
	public boolean lower(int amount) {
		int diff = temperature.getValue() - amount;
		
		if (diff < 0) {
			return false;
		}
		
		this.lastChange = System.currentTimeMillis();
		this.temperature.setValue(diff);
		return true;
	}

	public static enum IcyHotAbility {
		NONE, BLAST, WALL
	}
}
