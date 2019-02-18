package me.simp.quirkademia.quirk.halfcoldhalfhot;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import me.simp.quirkademia.ability.Collidable;
import me.simp.quirkademia.ability.QuirkAbility;
import me.simp.quirkademia.configuration.ConfigType;
import me.simp.quirkademia.quirk.QuirkUser;
import me.simp.quirkademia.quirk.halfcoldhalfhot.BodyHeat.IcyHotAbility;
import me.simp.quirkademia.util.ParticleEffect;

public class FlameAttack extends QuirkAbility implements Collidable {
	
	private BodyHeat passive;
	private IcyHotAbility type;
	
	private Location loc, start;
	private Vector direction;
	private long cooldown, startTime;
	private double range, damage, radius;

	public FlameAttack(QuirkUser user) {
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
		
		if (user.hasCooldown("flame " + type.toString())) {
			return;
		}
		
		loc = methods.getSide(player, 0.3, true).add(0, 1, 0);
		start = loc.clone();
		direction = player.getEyeLocation().getDirection().clone().normalize();
		
		if (type == IcyHotAbility.WALL) {
			direction.setY(0);
		}
		
		cooldown = configs.getConfiguration(ConfigType.ABILITIES).getLong("Abilities.HalfColdHalfHot.Flame." + type.toString() + ".Cooldown");
		range = configs.getConfiguration(ConfigType.ABILITIES).getDouble("Abilities.HalfColdHalfHot.Flame." + type.toString() + ".Range");
		damage = configs.getConfiguration(ConfigType.ABILITIES).getDouble("Abilities.HalfColdHalfHot.Flame." + type.toString() + ".Damage");
		radius = configs.getConfiguration(ConfigType.ABILITIES).getDouble("Abilities.HalfColdHalfHot.Flame." + type.toString() + ".Radius");
		int stamina = configs.getConfiguration(ConfigType.ABILITIES).getInt("Abilities.HalfColdHalfHot.Flame." + type.toString() + ".HeatRaise");
		
		if (!passive.raise(stamina)) {
			return;
		}
		
		user.addCooldown("flame " + type.toString(), cooldown);
		
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
		startTime = System.currentTimeMillis();
	}

	@Override
	public void onRemove() {
	}

	private boolean progressBlast() {
		if (start.distance(loc) > range) {
			return false;
		}
		
		loc.add(direction);
		
		if (!methods.isTransparent(loc.getBlock()) || loc.getBlock().isLiquid()) {
			loc.subtract(direction);
			if (methods.isAir(loc.getBlock()) && loc.getBlock().getRelative(BlockFace.DOWN).getType().isFlammable()) {
				loc.getBlock().setType(Material.FIRE);
			}
			return false;
		}
		
		ParticleEffect.FLAME.display(loc, (int) (range * 2 + start.distance(loc) + radius * 5), radius, radius, radius);
		
		for (Entity e : methods.getEntitiesAroundPoint(loc, radius + 1)) {
			if (e instanceof LivingEntity && e.getEntityId() != player.getEntityId()) {
				methods.damageEntity((LivingEntity) e, player, this, damage);
			}
		}
		
		radius += 0.1;
		
		return true;
	}
	
	private boolean progressWall() {
		Vector ortho = methods.getOrthogonalVector(direction, Math.PI/2);
		
		for (int i = 1; i <= range; i++) {
			Location display = loc.clone().add(direction.clone().multiply(i));
			
			for (double j = -radius; j <= radius; j += 0.5) {
				for (double y = -radius; y <= radius; y += 0.5) {
					Vector curr = ortho.clone().multiply(j);
					display.add(curr).add(0, y, 0);
					
					if (methods.isTransparent(display.getBlock()) && !display.getBlock().isLiquid()) {
						ParticleEffect.FLAME.display(display, 2, 0.3, 0.2, 0.3);
						
						for (Entity e : methods.getEntitiesAroundPoint(display, 1)) {
							if (e instanceof LivingEntity && e.getEntityId() != player.getEntityId()) {
								methods.damageEntity((LivingEntity) e, player, this, damage);
							}
						}
					}
					
					display.subtract(curr).subtract(0, y, 0);
				}
			}
		}
		
		if (System.currentTimeMillis() >= startTime + 5000) {
			return false;
		}
		
		return true;
	}

	@Override
	public double getRadius() {
		return radius;
	}

	@Override
	public boolean onCollision(QuirkAbility other) {
		return true;
	}
}
