package me.simp.quirkademia.quirk.explosion;

import java.util.LinkedList;

import org.bukkit.Location;

import me.simp.quirkademia.ability.QuirkAbility;
import me.simp.quirkademia.quirk.QuirkUser;

public class ExplosionTracker extends QuirkAbility {

	private ExplosionType type;
	private LinkedList<ExplosionType> cycle;
	
	public ExplosionTracker(QuirkUser user) {
		super(user);
		
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
		return true;
	}

	@Override
	public void onStart() {
		
	}

	@Override
	public void onRemove() {
		
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
}
