package me.simp.quirkademia.quirk.halfcoldhalfhot;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.util.Vector;

import me.simp.quirkademia.ability.QuirkAbility;
import me.simp.quirkademia.configuration.ConfigType;
import me.simp.quirkademia.quirk.QuirkUser;
import me.simp.quirkademia.quirk.halfcoldhalfhot.BodyHeat.IcyHotAbility;

public class IceAttack extends QuirkAbility {
	
	private IcyHotAbility type;
	private BodyHeat passive;
	
	private Location loc, start;
	private Vector direction;
	private double range, damage, radius;
	private long cooldown;

	public IceAttack(QuirkUser user) {
		super(user);
		
		if (manager.hasAbility(user, BodyHeat.class)) {
			passive = manager.getAbility(user, BodyHeat.class);
		} else {
			return;
		}
		
		type = passive.getType();
		
		if (type == IcyHotAbility.NONE) {
			return;
		}
		
		loc = methods.getSide(player, 0.3, true);
		start = loc.clone();
		direction = player.getEyeLocation().getDirection().clone().normalize();
		
		if (type == IcyHotAbility.WALL) {
			direction.setY(0);
		}
		
		cooldown = configs.getConfiguration(ConfigType.ABILITIES).getLong("Abilities.HalfColdHalfHot.Ice." + type.toString() + ".Cooldown");
		range = configs.getConfiguration(ConfigType.ABILITIES).getDouble("Abilities.HalfColdHalfHot.Ice." + type.toString() + ".Range");
		damage = configs.getConfiguration(ConfigType.ABILITIES).getDouble("Abilities.HalfColdHalfHot.Ice." + type.toString() + ".Damage");
		radius = configs.getConfiguration(ConfigType.ABILITIES).getDouble("Abilities.HalfColdHalfHot.Ice." + type.toString() + ".Radius");
		
		passive.setTemperature(passive.getTemperature() - 5);
		
		manager.start(this);
	}

	@Override
	public Location getLocation() {
		return loc;
	}

	@Override
	public boolean progress() {
		if (type == IcyHotAbility.BLAST) {
			return progressBlast();
		} else if (type == IcyHotAbility.WALL) {
			return progressWall();
		}
		
		return false;
	}

	@Override
	public void onStart() {
		
	}

	@Override
	public void onRemove() {
		
	}

	public boolean progressBlast() {
		if (start.distance(loc) > range) {
			return false;
		}
		
		loc.add(direction);
		
		int i = ((int) start.distance(loc)) % 3;
		
		Block top = loc.getBlock();
		int j = 0;
		
		while (methods.isAir(top) && j < 2) {
			top = top.getRelative(BlockFace.DOWN);
			j++;
		}
		
		while (!methods.isAir(top.getRelative(BlockFace.UP))) {
			
		}
		
		return true;
	}
	
	public boolean progressWall() {
		return true;
	}
}
