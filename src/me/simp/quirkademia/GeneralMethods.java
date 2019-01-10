package me.simp.quirkademia;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import me.simp.quirkademia.ability.QuirkAbility;
import me.simp.quirkademia.ability.QuirkAbilityInfo;
import me.simp.quirkademia.event.QuirkAbilityDamageEntityEvent;
import me.simp.quirkademia.quirk.QuirkUser;
import me.simp.quirkademia.quirk.QuirkUser.StatusEffect;

public class GeneralMethods {

	private QuirkPlugin plugin;
	
	public GeneralMethods(QuirkPlugin plugin) {
		this.plugin = plugin;
	}
	
	public QuirkPlugin getPlugin() {
		return plugin;
	}
	
	public boolean canUseAbility(QuirkAbilityInfo info, QuirkUser user) {
		if (info == null) {
			return false;
		} else if (user.isQuirkDisabled()) {
			return false;
		} else if (user.getStatus().has(StatusEffect.QUIRK_ERASED)) {
			return false;
		} else if (!info.getQuirk().equals(user.getQuirk())) {
			return false;
		}
		
		return true;
	}
	
	public boolean canUseAbility(Class<? extends QuirkAbility> clazz, QuirkUser user) {
		return canUseAbility(plugin.getAbilityManager().getAbilityInfo(clazz), user);
	}
	
	public void damageEntity(LivingEntity damaged, Player damager, QuirkAbility ability, double damage) {
		QuirkAbilityDamageEntityEvent event = new QuirkAbilityDamageEntityEvent(damaged, damager, ability, damage);
		plugin.getServer().getPluginManager().callEvent(event);
		
		if (event.isCancelled()) {
			return;
		}
		
		damage = event.getDamage();
		damaged.damage(damage, damager);
	}
	
	public PriorityQueue<Entity> getEntitiesAroundPoint(final Location location, final double radius) {
		final PriorityQueue<Entity> entities = new PriorityQueue<>(100, new Comparator<Entity>() {
			@Override
			public int compare(final Entity a, final Entity b) {
				return (int) (location.distance(a.getLocation()) - location.distance(b.getLocation()));
			}
		});
		final World world = location.getWorld();

		// To find chunks we use chunk coordinates (not block coordinates!)
		final int x1 = (int) (location.getX() - radius) >> 4;
		final int x2 = (int) (location.getX() + radius) >> 4;
		final int z1 = (int) (location.getZ() - radius) >> 4;
		final int z2 = (int) (location.getZ() + radius) >> 4;

		for (int x = x1; x <= x2; x++) {
			for (int z = z1; z <= z2; z++) {
				if (world.isChunkLoaded(x, z)) {
					entities.addAll(Arrays.asList(world.getChunkAt(x, z).getEntities()));
				}
			}
		}

		final Iterator<Entity> entityIterator = entities.iterator();
		while (entityIterator.hasNext()) {
			final Entity e = entityIterator.next();
			if (e.getWorld().equals(location.getWorld()) && e.getLocation().distanceSquared(location) > radius * radius) {
				entityIterator.remove();
			} else if (e instanceof Player && (((Player) e).isDead() || ((Player) e).getGameMode().equals(GameMode.SPECTATOR))) {
				entityIterator.remove();
			}
		}

		return entities;
	}
	
	public Vector getDirection(Location start, Location destination) {
		double x = destination.getX() - start.getX();
		double y = destination.getY() - start.getY();
		double z = destination.getZ() - start.getZ();
		
		return new Vector(x, y, z);
	}
	
	public Entity getTargetedEntity(Player player, double range, Material...ignore) {
		Set<Material> transparent = new HashSet<>();
		transparent.add(Material.AIR);
		transparent.add(Material.CAVE_AIR);
		transparent.add(Material.VOID_AIR);
		
		for (Material m : ignore) {
			transparent.add(m);
		}
		
		Entity entity = null;
		Location check = player.getEyeLocation().clone();
		Vector direction = check.getDirection().normalize().multiply(0.2);
		
		for (double i = 0; i < range; i += 0.2) {
			check.add(direction);
			
			Block block = check.getBlock();
			if (transparent.contains(block.getType())) {
				Queue<Entity> entities = getEntitiesAroundPoint(check, 0.6);
				if (!entities.isEmpty()) {
					entity = entities.poll();
					break;
				}
			} else {
				break;
			}
		}
		
		return entity;
	}
	
	public LivingEntity getTargetedLivingEntity(Player player, double range, Material...ignore) {
		Set<Material> transparent = new HashSet<>();
		transparent.add(Material.AIR);
		transparent.add(Material.CAVE_AIR);
		transparent.add(Material.VOID_AIR);
		
		for (Material m : ignore) {
			transparent.add(m);
		}
		
		LivingEntity entity = null;
		Location check = player.getEyeLocation().clone();
		Vector direction = check.getDirection().normalize().multiply(0.2);
		
		for (double i = 0; i < range; i += 0.2) {
			check.add(direction);
			
			Block block = check.getBlock();
			if (transparent.contains(block.getType())) {
				Queue<Entity> entities = getEntitiesAroundPoint(check, 0.6);
				Entity e = null;
				while (!entities.isEmpty()) {
					e = entities.poll();
					
					if (e instanceof LivingEntity) {
						break;
					} else {
						e = null;
					}
				}
				
				if (e != null && e instanceof LivingEntity) {
					entity = (LivingEntity) e;
					break;
				}
			} else {
				break;
			}
		}
		
		return entity;
	}
	
	public Location getTargetedLocation(Player player, double range, Material...ignore) {
		Set<Material> transparent = new HashSet<>();
		transparent.add(Material.AIR);
		transparent.add(Material.CAVE_AIR);
		transparent.add(Material.VOID_AIR);
		
		for (Material m : ignore) {
			transparent.add(m);
		}
		
		Location check = player.getEyeLocation().clone();
		Vector direction = check.getDirection().normalize().multiply(0.2);
		
		for (double i = 0; i < range; i += 0.2) {
			check.add(direction);
			
			Block block = check.getBlock();
			if (transparent.contains(block.getType())) {
				continue;
			} else {
				check.subtract(direction);
				break;
			}
		}
		
		return check;
	}
	
	public boolean isAir(Block b) {
		return b.getType() == Material.AIR || b.getType() == Material.CAVE_AIR || b.getType() == Material.VOID_AIR;
	}
	
	public boolean isTransparent(Block b) {
		return !b.getType().isOccluding() && !b.getType().isSolid();
	}
	
	public boolean isWater(BlockData data) {
		if (data instanceof Waterlogged) {
			return ((Waterlogged) data).isWaterlogged();
		} else {
			switch (data.getMaterial()) {
				case WATER:
				case KELP:
				case KELP_PLANT:
				case SEAGRASS:
				case TALL_SEAGRASS:
					return true;
				default: return false;
			}
		}
	}
	
	public boolean isWater(Block b) {
		return isWater(b.getBlockData());
	}
}
