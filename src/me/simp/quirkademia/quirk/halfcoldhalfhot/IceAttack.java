package me.simp.quirkademia.quirk.halfcoldhalfhot;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import me.simp.quirkademia.ability.Collidable;
import me.simp.quirkademia.ability.QuirkAbility;
import me.simp.quirkademia.configuration.ConfigType;
import me.simp.quirkademia.quirk.QuirkUser;
import me.simp.quirkademia.quirk.halfcoldhalfhot.BodyHeat.IcyHotAbility;
import me.simp.quirkademia.util.ParticleEffect;

public class IceAttack extends QuirkAbility implements Collidable {
	
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
		} else if (user.hasCooldown("ice " + type.toString())) {
			return;
		}
		
		loc = methods.getSide(player, 0.3, true);
		start = loc.clone();
		direction = player.getEyeLocation().getDirection().clone().normalize();
		direction.setY(0);
		
		double x = direction.getX();
		double z = direction.getZ();
		
		if (x < 0.1 && x > 0) {
			if (z < 0.1 && z > 0) {
				direction.setZ(0.1);
				direction.setX(0.1);
			} else if (z < 0 && z > -0.1) {
				direction.setZ(-0.1);
				direction.setX(0.1);
			}
		} else if (x < 0 && x > -0.1) {
			if (z < 0.1 && z > 0) {
				direction.setZ(0.1);
				direction.setX(-0.1);
			} else if (z < 0 && z > -0.1) {
				direction.setZ(-0.1);
				direction.setX(-0.1);
			}
		}
		
		if (z < 0.1 && z > 0) {
			direction.setZ(0.1);
		} else if (z < 0 && z > -0.1) {
			direction.setZ(-0.1);
		}
		
		direction.normalize();
		
		cooldown = configs.getConfiguration(ConfigType.ABILITIES).getLong("Abilities.HalfColdHalfHot.Ice." + type.toString() + ".Cooldown");
		range = configs.getConfiguration(ConfigType.ABILITIES).getDouble("Abilities.HalfColdHalfHot.Ice." + type.toString() + ".Range");
		damage = configs.getConfiguration(ConfigType.ABILITIES).getDouble("Abilities.HalfColdHalfHot.Ice." + type.toString() + ".Damage");
		radius = configs.getConfiguration(ConfigType.ABILITIES).getDouble("Abilities.HalfColdHalfHot.Ice." + type.toString() + ".Radius");
		int stamina = configs.getConfiguration(ConfigType.ABILITIES).getInt("Abilities.HalfColdHalfHot.Ice." + type.toString() + ".HeatLower");
		
		if (!passive.lower(stamina)) {
			return;
		}
		
		user.addCooldown("ice " + type.toString(), cooldown);
		
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
		
		Block top = loc.getBlock();
		int j = 0;
		
		if (methods.isAir(top)) {
			while (methods.isAir(top) && j < 2) {
				top = top.getRelative(BlockFace.DOWN);
				j++;
			}
		} else {
			while ((top.getType().isSolid() && top.getType() != Material.BLUE_ICE) && j < 2) {
				top = top.getRelative(BlockFace.UP);
				j++;
			}
		}
		
		if (top.getRelative(BlockFace.UP).getType().isSolid() && top.getRelative(BlockFace.UP).getType() != Material.BLUE_ICE) {
			return false;
		}
		
		loc.add(0, top.getY() - loc.getBlockY(), 0);
		
		for (Entity e : methods.getEntitiesAroundPoint(loc, radius)) {
			if (e instanceof LivingEntity && e.getEntityId() != player.getEntityId()) {
				LivingEntity lent = (LivingEntity) e;
				int power = 1;
				
				if (lent.hasPotionEffect(PotionEffectType.SLOW)) {
					power += lent.getPotionEffect(PotionEffectType.SLOW).getAmplifier();
				}
				
				lent.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, power, true, false), true);
				methods.damageEntity(lent, player, this, damage);
			}
		}
		
		ParticleEffect.displayColoredParticle("caffff", loc, 8, radius/1.5, radius/1.5, radius/1.5);
		
		for (Block b : methods.getBlocksAroundPoint(loc, (int) Math.floor(radius))) {
			if (Math.random() < 0.42) {
				continue;
			}
			
			if (methods.isAir(b) || methods.isWater(b)) {
				plugin.getRegenManager().regenerator(b, Material.BLUE_ICE.createBlockData(), this, 40000);
			}
		}
		
		radius += 0.1;
		
		return true;
	}
	
	public boolean progressWall() {
		if (start.distance(loc) > range) {
			return false;
		}
		
		loc.add(direction);
		
		for (double i = -radius; i < radius; i += 0.2) {
			Location display = loc.clone();
			Vector ortho = methods.getOrthogonalVector(direction, Math.PI/2).multiply(i);
			
			display.add(ortho);
			
			Block top = display.getBlock();
			int j = 0;
			
			if (methods.isAir(top)) {
				while (methods.isAir(top) && j < 2) {
					top = top.getRelative(BlockFace.DOWN);
					j++;
				}
			} else {
				while ((top.getType().isSolid() && top.getType() != Material.BLUE_ICE) && j < 2) {
					top = top.getRelative(BlockFace.UP);
					j++;
				}
			}
			
			display.setY(display.getY() + (top.getY() - display.getBlockY()));
			
			for (int y = 0; y < radius; y += 1) {
				display.add(0, 1, 0);
				
				if (methods.isAir(display.getBlock()) || methods.isWater(display.getBlock())) {
					plugin.getRegenManager().regenerator(display.getBlock(), Material.BLUE_ICE.createBlockData(), this, 40000);
				}
			}
		}
		
		radius += 0.5;
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
