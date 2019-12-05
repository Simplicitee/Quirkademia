package me.simp.quirkademia.quirk.oneforall.stockpile;

import java.util.LinkedList;

import org.bukkit.Location;
import org.bukkit.boss.BarColor;

import me.simp.quirkademia.ability.QuirkAbility;
import me.simp.quirkademia.object.Stamina;
import me.simp.quirkademia.quirk.QuirkUser;

public class SmashTracker extends QuirkAbility {
	
	private SmashType active;
	private LinkedList<SmashType> cycle;
	private Stamina battery;

	public SmashTracker(QuirkUser user) {
		super(user);
		
		battery = new Stamina(user.getUniqueId(), "Stockpiled Power", BarColor.GREEN, 0, 1000000);
		
		active = SmashType.NONE;
		cycle = new LinkedList<>();
		
		cycle.add(SmashType.DETROIT);
		cycle.add(SmashType.DELAWARE);
		
		manager.start(this);
	}

	@Override
	public Location getLocation() {
		return player.getLocation();
	}

	@Override
	public boolean progress() {
		battery.setValue(battery.getValue() + 1);
		return true;
	}

	@Override
	public void onStart() {
	}

	@Override
	public void onRemove() {
		battery.getBar().destroy(player);
	}

	public static enum SmashType {
		NONE, DETROIT, DELAWARE, 
	}
	
	public SmashType getType() {
		return active;
	}
	
	public SmashType cycleType() {
		this.cycle.add(active);
		this.active = cycle.poll();
		return active;
	}
	
	public boolean usePower(int amount) {
		int diff = battery.getValue() - amount;
		
		if (diff < 0) {
			return false;
		}
		
		battery.setValue(diff);
		return true;
	}
}
