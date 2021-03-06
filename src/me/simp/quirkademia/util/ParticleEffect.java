package me.simp.quirkademia.util;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.block.data.BlockData;
import org.bukkit.inventory.ItemStack;

public enum ParticleEffect {

	BARRIER (Particle.BARRIER),
	
	/**
	 * Applicable data: {@link BlockData}
	 */
	BLOCK_CRACK (Particle.BLOCK_CRACK),
	
	/**
	 * Applicable data: {@link BlockData}
	 */
	BLOCK_DUST (Particle.BLOCK_DUST),
	BUBBLE_COLUMN_UP (Particle.BUBBLE_COLUMN_UP),
	BUBBLE_POP (Particle.BUBBLE_POP),
	CLOUD (Particle.CLOUD),
	CRIT (Particle.CRIT),
	CRIT_MAGIC (Particle.CRIT_MAGIC),
	CURRENT_DOWN (Particle.CURRENT_DOWN),
	DAMAGE_INDICATOR (Particle.DAMAGE_INDICATOR),
	DOLPHIN (Particle.DOLPHIN),
	DRAGON_BREATH (Particle.DRAGON_BREATH),
	DRIP_LAVA (Particle.DRIP_LAVA),
	DRIP_WATER (Particle.DRIP_WATER),
	ENCHANTMENT_TABLE (Particle.ENCHANTMENT_TABLE),
	END_ROD (Particle.END_ROD),
	EXPLOSION_HUGE (Particle.EXPLOSION_HUGE),
	EXPLOSION_LARGE (Particle.EXPLOSION_LARGE),
	EXPLOSION_NORMAL (Particle.EXPLOSION_NORMAL),
	
	/**
	 * Applicable data: {@link BlockData}
	 */
	FALLING_DUST (Particle.FALLING_DUST),
	FIREWORKS_SPARK (Particle.FIREWORKS_SPARK),
	FLAME (Particle.FLAME),
	HEART (Particle.HEART),
	
	/**
	 * Applicable data: {@link ItemStack}
	 */
	ITEM_CRACK (Particle.ITEM_CRACK),
	LAVA (Particle.LAVA),
	MOB_APPEARANCE (Particle.MOB_APPEARANCE),
	NAUTILUS (Particle.NAUTILUS),
	NOTE (Particle.NOTE),
	PORTAL (Particle.PORTAL),
	
	/**
	 * Applicable data: {@link DustOptions}
	 */
	REDSTONE (Particle.REDSTONE),
	SLIME (Particle.SLIME),
	SMOKE_NORMAL (Particle.SMOKE_NORMAL),
	SMOKE_LARGE (Particle.SMOKE_LARGE),
	SNOW_SHOVEL (Particle.SNOW_SHOVEL),
	SNOWBALL (Particle.SNOWBALL),
	SPELL (Particle.SPELL),
	SPELL_INSTANT (Particle.SPELL_INSTANT),
	SPELL_MOB (Particle.SPELL_MOB),
	SPELL_MOB_AMBIENT (Particle.SPELL_MOB_AMBIENT),
	SPELL_WITCH (Particle.SPELL_WITCH),
	SPIT (Particle.SPIT),
	SQUID_INK (Particle.SQUID_INK),
	SUSPENDED (Particle.SUSPENDED),
	SUSPENDED_DEPTH (Particle.SUSPENDED_DEPTH),
	SWEEP_ATTACK (Particle.SWEEP_ATTACK),
	TOTEM (Particle.TOTEM),
	TOWN_AURA (Particle.TOWN_AURA),
	VILLAGER_ANGRY (Particle.VILLAGER_ANGRY),
	VILLAGER_HAPPY (Particle.VILLAGER_HAPPY),
	WATER_BUBBLE (Particle.WATER_BUBBLE),
	WATER_DROP (Particle.WATER_DROP),
	WATER_SPLASH (Particle.WATER_SPLASH),
	WATER_WAKE (Particle.WATER_WAKE);
	
	Particle particle;
	Class<?> dataClass;
	
	private ParticleEffect(Particle particle) {
		this.particle = particle;
		this.dataClass = particle.getDataType();
	}
	
	public Particle getParticle() {
		return particle;
	}
	
	public Class<?> getDataClass() {
		return dataClass;
	}
	
	/**
	 * Displays the particle at the specified location without offsets
	 * @param loc Location to display the particle at
	 * @param amount how many of the particle to display
	 */
	public void display(Location loc, int amount) {
		display(loc, amount, 0, 0, 0);
	}
	
	/**
	 * Displays the particle at the specified location with no extra data
	 * @param loc Location to spawn the particle
	 * @param amount how many of the particle to spawn
	 * @param offsetX random offset on the x axis
	 * @param offsetY random offset on the y axis
	 * @param offsetZ random offset on the z axis
	 */
	public void display(Location loc, int amount, double offsetX, double offsetY, double offsetZ) {
		display(loc, amount, offsetX, offsetY, offsetZ, 0);
	}
	
	/**
	 * Displays the particle at the specified location with extra data
	 * @param loc Location to spawn the particle
	 * @param amount how many of the particle to spawn
	 * @param offsetX random offset on the x axis
	 * @param offsetY random offset on the y axis
	 * @param offsetZ random offset on the z axis
	 * @param extra extra data to affect the particle, usually affects speed or does nothing
	 */
	public void display(Location loc, int amount, double offsetX, double offsetY, double offsetZ, double extra) {
		loc.getWorld().spawnParticle(particle, loc, amount, offsetX, offsetY, offsetZ, extra, null, true);
	}
	
	/**
	 * Displays the particle at the specified location with data
	 * @param loc Location to spawn the particle
	 * @param amount how many of the particle to spawn
	 * @param offsetX random offset on the x axis
	 * @param offsetY random offset on the y axis
	 * @param offsetZ random offset on the z axis
	 * @param data data to display the particle with, only applicable on several particle types (check the enum)
	 */
	public void display(Location loc, int amount, double offsetX, double offsetY, double offsetZ, Object data) {
		display(loc, amount, offsetX, offsetY, offsetZ, 0, data);
	}
	
	/**
	 * Displays the particle at the specified location with regular and extra data
	 * @param loc Location to spawn the particle
	 * @param amount how many of the particle to spawn
	 * @param offsetX random offset on the x axis
	 * @param offsetY random offset on the y axis
	 * @param offsetZ random offset on the z axis
	 * @param extra extra data to affect the particle, usually affects speed or does nothing
	 * @param data data to display the particle with, only applicable on several particle types (check the enum)
	 */
	public void display(Location loc, int amount, double offsetX, double offsetY, double offsetZ, double extra, Object data) {
		if (dataClass.isAssignableFrom(Void.class) || data == null || !dataClass.isAssignableFrom(data.getClass())) {
			display(loc, amount, offsetX, offsetY, offsetZ, extra);
		} else {
			loc.getWorld().spawnParticle(particle, loc, amount, offsetX, offsetY, offsetZ, extra, data, true);
		}
	}
	
	public static void displayColoredParticle(String hex, Location loc, int amount, double offsetX, double offsetY, double offsetZ) {
		if (hex.startsWith("#")) {
			hex = hex.substring(1);
		}
		
		HexColor color = new HexColor(hex);
		int[] rgb = color.toRGB();
		DustOptions dust = new DustOptions(Color.fromRGB(rgb[0], rgb[1], rgb[2]), (float) 0.8);
		ParticleEffect.REDSTONE.display(loc, amount, offsetX, offsetY, offsetZ, dust);
	}
}
