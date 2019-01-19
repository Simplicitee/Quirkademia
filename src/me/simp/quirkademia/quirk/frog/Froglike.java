package me.simp.quirkademia.quirk.frog;

import java.util.LinkedList;

import org.bukkit.Location;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.simp.quirkademia.ability.QuirkAbility;
import me.simp.quirkademia.quirk.QuirkUser;
import me.simp.quirkademia.util.StatusEffect;

public class Froglike extends QuirkAbility {

	private TongueType type;
	private LinkedList<TongueType> cycle;
	private double x, y, z;
	private int camoCounter, camoCharge;

	public Froglike(QuirkUser user) {
		super(user);

		type = TongueType.NONE;
		cycle = new LinkedList<>();
		
		cycle.add(TongueType.WHIP);
		cycle.add(TongueType.GRAB);
		
		x = player.getLocation().getX();
		y = player.getLocation().getY();
		z = player.getLocation().getZ();
		
		camoCounter = 0;
		camoCharge = 60;
		
		plugin.getAbilityManager().start(this);
	}

	@Override
	public Location getLocation() {
		return player.getLocation();
	}

	@Override
	public boolean progress() {
		user.getStatus().add(StatusEffect.INCREASED_JUMP, 4);
		if (methods.isWater(player.getLocation().getBlock())) {
			player.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 5, 3, true, false),  true);
		}
		
		Location loc = player.getLocation();
		double x1 = loc.getX(), y1 = loc.getY(), z1 = loc.getZ();
		
		if ((x - x1 < 0.2 && x - x1 > -0.2) && (y - y1 < 0.2 && y - y1 > -0.2) && (z - z1 < 0.2 && z - z1 > -0.2)) {
			camoCounter++;
			if (camoCounter > camoCharge) {
				camoCounter = camoCharge;
			}
		} else {
			camoCounter = 0;
		}
		
		if (manager.hasAbility(user, TongueAttack.class)) {
			camoCounter = 0;
		}
		
		if (camoCounter >= camoCharge) {
			user.getStatus().add(StatusEffect.INVISIBLE, 2);
		} else {
			user.getStatus().remove(StatusEffect.INVISIBLE);
		}
		
		x = player.getLocation().getX();
		y = player.getLocation().getY();
		z = player.getLocation().getZ();
		
		return true;
	}

	@Override
	public void onStart() {
	}

	@Override
	public void onRemove() {
		user.getStatus().remove(StatusEffect.INCREASED_JUMP);
		user.getStatus().remove(StatusEffect.INVISIBLE);
	}

	public static enum TongueType {
		NONE, WHIP, GRAB;
	}
	
	public TongueType getType() {
		return type;
	}
	
	public TongueType cycleType() {
		cycle.add(type);
		type = cycle.poll();
		return type;
	}
}
