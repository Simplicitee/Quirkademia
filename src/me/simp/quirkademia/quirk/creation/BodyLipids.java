package me.simp.quirkademia.quirk.creation;

import org.bukkit.Location;
import org.bukkit.boss.BarColor;

import me.simp.quirkademia.ability.QuirkAbility;
import me.simp.quirkademia.configuration.ConfigType;
import me.simp.quirkademia.quirk.QuirkStamina;
import me.simp.quirkademia.quirk.QuirkUser;

public class BodyLipids extends QuirkAbility {
	
	private QuirkStamina bodyfat;

	public BodyLipids(QuirkUser user) {
		super(user);
		
		int max = configs.getConfiguration(ConfigType.ABILITIES).getInt("Abilities.Creation.Passive.MaxLipids");
		bodyfat = new QuirkStamina(user.getUniqueId(), "Body Lipids", BarColor.PINK, max, max);
		
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
		bodyfat.getBar().destroy(player);
	}

	public boolean useLipids(int amount) {
		int diff = bodyfat.getValue() - amount;
		
		if (diff < 0) {
			return false;
		}
		
		bodyfat.setValue(diff);
		return true;
	}
	
	public void restoreLipids() {
		bodyfat.setValue(bodyfat.getValue() + 50);
	}
}
