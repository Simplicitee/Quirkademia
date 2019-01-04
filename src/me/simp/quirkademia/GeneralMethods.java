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
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class GeneralMethods {

	private QuirkPlugin plugin;
	
	public GeneralMethods(QuirkPlugin plugin) {
		this.plugin = plugin;
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
		final int smallX = (int) (location.getX() - radius) >> 4;
		final int bigX = (int) (location.getX() + radius) >> 4;
		final int smallZ = (int) (location.getZ() - radius) >> 4;
		final int bigZ = (int) (location.getZ() + radius) >> 4;

		for (int x = smallX; x <= bigX; x++) {
			for (int z = smallZ; z <= bigZ; z++) {
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
}
