package me.simp.quirkademia.quirk.engine;

import org.bukkit.Location;
import org.bukkit.boss.BarColor;

import me.simp.quirkademia.ability.QuirkAbility;
import me.simp.quirkademia.configuration.ConfigType;
import me.simp.quirkademia.quirk.QuirkStamina;
import me.simp.quirkademia.quirk.QuirkUser;

public class FuelGauge extends QuirkAbility {
	
	private QuirkStamina fuel;
	private boolean stalled;
	private long stalledTime, stallTime;

	public FuelGauge(QuirkUser user) {
		super(user);
		
		int max = configs.getConfiguration(ConfigType.ABILITIES).getInt("Abilities.Engine.Passive.MaxFuel");
		fuel = new QuirkStamina(user.getUniqueId(), "Fuel Gauge", BarColor.BLUE, max, max);
		stalled = false;
		stallTime = configs.getConfiguration(ConfigType.ABILITIES).getLong("Abilities.Engine.ReciproBurst.StallTime");
		
		manager.start(this);
	}

	@Override
	public Location getLocation() {
		return player.getLocation();
	}

	@Override
	public boolean progress() {
		fuel.setValue(fuel.getValue() + 2);
		
		if (stalled && System.currentTimeMillis() > stalledTime + stallTime) {
			stalled = false;
		}
		
		return true;
	}

	@Override
	public void onStart() {
		
	}

	@Override
	public void onRemove() {
		fuel.getBar().destroy(player);
	}

	public boolean isStalled() {
		return stalled;
	}
	
	public void stallEnginges() {
		this.stalled = true;
		this.stalledTime = System.currentTimeMillis();
	}
	
	public boolean useFuel(int amount) {
		int diff = fuel.getValue() - amount;
		
		if (diff < 0) {
			return false;
		}
		
		fuel.setValue(diff);
		return true;
	}
}
