package me.simp.quirkademia.quirk.hardening;

import org.bukkit.Location;
import org.bukkit.boss.BarColor;

import me.simp.quirkademia.ability.QuirkAbility;
import me.simp.quirkademia.configuration.ConfigType;
import me.simp.quirkademia.object.Stamina;
import me.simp.quirkademia.quirk.QuirkUser;

public class Hardening extends QuirkAbility {
	
	public Stamina hardening;

	public Hardening(QuirkUser user) {
		super(user);
		
		int max = configs.getConfiguration(ConfigType.ABILITIES).getInt("Abilities.Hardening.Passive.MaxStamina");
		
		hardening = new Stamina(user.getUniqueId(), "Hardness", BarColor.RED, max, max);
		
		manager.start(this);
	}

	@Override
	public Location getLocation() {
		return player.getLocation();
	}

	@Override
	public boolean progress() {
		return true;
	}

	@Override
	public void onStart() {
		
	}

	@Override
	public void onRemove() {
		hardening.getBar().destroy(player);
	}

	public boolean use(int amount) {
		int diff = hardening.getValue() - 1;
		
		if (diff < 0) {
			return false;
		}
		
		hardening.setValue(hardening.getValue() - 1);
		return true;
	}
	
	public boolean recharge() {
		int diff = hardening.getValue() + 1;
		
		if (diff > hardening.getMaxStamina()) {
			return false;
		}
		
		hardening.setValue(diff);
		return true;
	}
}
