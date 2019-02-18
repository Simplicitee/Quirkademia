package me.simp.quirkademia.quirk.explosion;

import java.util.LinkedList;

import org.bukkit.Location;
import org.bukkit.boss.BarColor;

import me.simp.quirkademia.ability.QuirkAbility;
import me.simp.quirkademia.configuration.ConfigType;
import me.simp.quirkademia.quirk.QuirkStamina;
import me.simp.quirkademia.quirk.QuirkUser;

public class ExplosionTracker extends QuirkAbility {

	private ExplosionType type;
	private LinkedList<ExplosionType> cycle;
	private QuirkStamina stamina;
	
	public ExplosionTracker(QuirkUser user) {
		super(user);
		
		int max = configs.getConfiguration(ConfigType.ABILITIES).getInt("Abilities.Explosion.Passive.MaxSweat");
		stamina = new QuirkStamina(user.getUniqueId(), "Nitroglycerin", BarColor.RED, 0, max);
		
		type = ExplosionType.NONE;
		cycle = new LinkedList<>();
		
		cycle.add(ExplosionType.NORMAL);
		cycle.add(ExplosionType.LARGE);
		cycle.add(ExplosionType.APSHOT);
		
		manager.start(this);
	}

	@Override
	public Location getLocation() {
		return player.getLocation();
	}

	@Override
	public boolean progress() {
		stamina.setValue(stamina.getValue() + 1);
		return true;
	}

	@Override
	public void onStart() {
		
	}

	@Override
	public void onRemove() {
		stamina.getBar().destroy(player);
	}
	
	public ExplosionType getType() {
		return type;
	}
	
	public ExplosionType cycleType() {
		this.cycle.add(type);
		this.type = cycle.poll();
		return type;
	}

	public static enum ExplosionType {
		NONE, NORMAL, LARGE, APSHOT;
	}
	
	public boolean expendNitroglycerin(int amount) {
		int diff = stamina.getValue() - amount;
		
		if (diff < 0) {
			return false;
		}
		
		stamina.setValue(diff);
		return true;
	}
}
