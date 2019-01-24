package me.simp.quirkademia;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
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
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import me.simp.quirkademia.ability.QuirkAbility;
import me.simp.quirkademia.event.QuirkAbilityDamageEntityEvent;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class GeneralMethods {

	private QuirkPlugin plugin;
	
	public GeneralMethods(QuirkPlugin plugin) {
		this.plugin = plugin;
	}
	
	public QuirkPlugin getPlugin() {
		return plugin;
	}
	
	public void damageEntity(final LivingEntity damaged, final Player damager, final QuirkAbility ability, final double damage) {
		QuirkAbilityDamageEntityEvent event = new QuirkAbilityDamageEntityEvent(damaged, damager, ability, damage);
		plugin.getServer().getPluginManager().callEvent(event);
		
		if (event.isCancelled()) {
			return;
		}
		
		if (event.getDamage() <= 0) {
			return;
		}
		
		damaged.damage(event.getDamage(), damager);
	}
	
	public LinkedList<Block> getBlocksAroundPoint(Location center, final int radius, final Material...ignore) {
		center = center.clone();
		LinkedList<Block> blocks = new LinkedList<>();
		List<Material> ignoring = Arrays.asList(ignore);
		
		for (int x = -radius; x <= radius; x++) {
			for (int y = -radius; y <= radius; y++) {
				for (int z = -radius; z <= radius; z++) {
					Block b = center.add(x, y, z).getBlock();
					
					if (ignoring.contains(b.getType())) {
						continue;
					} else {
						blocks.add(b);
					}
					
					center.subtract(x, y, z);
				}
			}
		}
		
		return blocks;
	}
	
	public LinkedList<Location> getCircle(Location center, double radius, final double interval, final int sections, final boolean hollow) {
		LinkedList<Location> circle = new LinkedList<>();
		
		radius = Math.abs(radius);
		center = center.clone();
		
		double init = (hollow ? radius : 0);
		double inc = radius / sections;
		
		for (double i = init; i <= radius; i += inc) {
			for (double j = 0; j < Math.PI * 2; j += interval/(i + 1)) {
				double x = i * Math.cos(j), z = i * Math.sin(j);
				
				center.add(x, 0, z);
				
				circle.add(center.clone());
				
				center.subtract(x, 0, z);
			}
		}
		
		return circle;
	}
	
	public PriorityQueue<Entity> getEntitiesAroundPoint(final Location center, final double radius, final EntityType...ignore) {
		PriorityQueue<Entity> entities = new PriorityQueue<>(100, new Comparator<Entity>() {
			@Override
			public int compare(final Entity a, final Entity b) {
				return (int) (center.distance(a.getLocation()) - center.distance(b.getLocation()));
			}
		});
		
		World world = center.getWorld();
		List<EntityType> ignoring = Arrays.asList(ignore);

		// To find chunks we use chunk coordinates (not block coordinates!)
		int x1 = (int) (center.getX() - radius) >> 4;
		int x2 = (int) (center.getX() + radius) >> 4;
		int z1 = (int) (center.getZ() - radius) >> 4;
		int z2 = (int) (center.getZ() + radius) >> 4;

		for (int x = x1; x <= x2; x++) {
			for (int z = z1; z <= z2; z++) {
				if (world.isChunkLoaded(x, z)) {
					entities.addAll(Arrays.asList(world.getChunkAt(x, z).getEntities()));
				}
			}
		}

		Iterator<Entity> entityIterator = entities.iterator();
		while (entityIterator.hasNext()) {
			Entity e = entityIterator.next();
			if (e.getWorld().equals(center.getWorld()) && e.getLocation().distanceSquared(center) > radius * radius) {
				entityIterator.remove();
			} else if (e instanceof Player && (((Player) e).isDead() || ((Player) e).getGameMode().equals(GameMode.SPECTATOR))) {
				entityIterator.remove();
			} else if (ignoring.contains(e.getType())) {
				entityIterator.remove();
			}
		}

		return entities;
	}
	
	public Vector getDirection(final Location start, final Location destination) {
		double x = destination.getX() - start.getX();
		double y = destination.getY() - start.getY();
		double z = destination.getZ() - start.getZ();
		
		return new Vector(x, y, z);
	}
	
	public Entity getTargetedEntity(final Player player, final double range, final Material...ignore) {
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
	
	public LivingEntity getTargetedLivingEntity(final Player player, final double range, final Material...ignore) {
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
	
	public Location getTargetedLocation(final Player player, final double range, final Material...ignore) {
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
	
	public Vector getOrthogonalVector(final Vector axis, final double radians) {
		Vector ortho = new Vector(axis.getY(), -axis.getX(), 0).normalize();
		
		return getVectorRotation(axis, ortho, radians);
	}
	
	public Location getSide(final Player player, final double length, final boolean left) {
		Vector ortho = getOrthogonalVector(player.getLocation().getDirection(), (left ? -Math.PI/2 : Math.PI/2));
		
		return player.getLocation().clone().add(ortho.multiply(length));
	}
	
	public Vector getVectorRotation(final Vector axis, final Vector rotating, final double radians) {
		Vector dupli = axis.clone().normalize();
		Vector rotor = rotating.clone();
		Vector third = dupli.crossProduct(rotor).normalize().multiply(rotor.length());
		
		rotor.multiply(Math.cos(radians));
		third.multiply(Math.sin(radians));
		
		return rotor.add(third);
	}
	
	public boolean isAir(final Block b) {
		return b.getType() == Material.AIR || b.getType() == Material.CAVE_AIR || b.getType() == Material.VOID_AIR;
	}
	
	public boolean isIce(final Block b) {
		return b.getType() == Material.BLUE_ICE || b.getType() == Material.FROSTED_ICE || b.getType() == Material.ICE || b.getType() == Material.PACKED_ICE;
	}
	
	public boolean isTransparent(final Block b) {
		return !b.getType().isOccluding() && !b.getType().isSolid();
	}
	
	public boolean isWater(final BlockData data) {
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
	
	public boolean isWater(final Block b) {
		return isWater(b.getBlockData());
	}
	
	public void sendActionBarMessage(final String message, final Player...players) {
		for (Player player : players) {
			player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
		}
	}
}
