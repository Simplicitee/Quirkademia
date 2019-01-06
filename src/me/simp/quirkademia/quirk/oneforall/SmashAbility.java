package me.simp.quirkademia.quirk.oneforall;

import java.util.LinkedList;

import org.bukkit.Location;

import me.simp.quirkademia.ability.QuirkAbility;
import me.simp.quirkademia.quirk.QuirkUser;

public class SmashAbility extends QuirkAbility {
	
	private SmashType active;
	private LinkedList<SmashType> cycle;

	public SmashAbility(QuirkUser user) {
		super(user);
		
		active = SmashType.NONE;
		cycle = new LinkedList<>();
		
		cycle.add(SmashType.DETROIT);
		cycle.add(SmashType.DELAWARE);
		
		plugin.getAbilityManager().start(this);
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
	}

	public static enum SmashType {
		NONE, DETROIT, DELAWARE, 
	}
	
	public SmashType getType() {
		return active;
	}
	
	public SmashType cycleType() {
		cycle.add(active);
		active = cycle.poll();
		return active;
	}
}
