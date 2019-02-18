package me.simp.quirkademia.configuration;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;

import me.simp.quirkademia.QuirkPlugin;

public class Configs {

	private QuirkPlugin plugin;
	private Map<ConfigType, Config> configs;
	
	public Configs(QuirkPlugin plugin) {
		this.plugin = plugin;
		this.configs = new HashMap<>();
		this.load();
	}
	
	public QuirkPlugin getPlugin() {
		return plugin;
	}
	
	private void load() {
		Config c = new Config(ConfigType.QUIRKS.getPath());
		FileConfiguration config = c.get();
		
		//quirk properties config
		
		config.addDefault("Quirks.OneForAll.AssignChance", 1);
		config.addDefault("Quirks.Frog.AssignChance", 4);
		config.addDefault("Quirks.Hardening.AssignChance", 7);
		config.addDefault("Quirks.Invisibility.AssignChance", 4);
		config.addDefault("Quirks.Explosion.AssignChance", 1);
		config.addDefault("Quirks.Creation.AssignChance", 4);
		config.addDefault("Quirks.Engine.AssignChance", 5);
		config.addDefault("Quirks.HalfColdHalfHot.AssignChance", 1);
		config.addDefault("Quirks.ZeroGravity.AssignChance", 4);
		
		c.save();
		configs.put(ConfigType.QUIRKS, c);
		
		c = new Config(ConfigType.ABILITIES.getPath());
		config = c.get();
		
		//ability config
		
		//one for all
		config.addDefault("Abilities.OneForAll.FullCowling.Cooldown", 2000);
		config.addDefault("Abilities.OneForAll.FullCowling.Effects.Speed", 2);
		config.addDefault("Abilities.OneForAll.FullCowling.Effects.Jump", 2);
		config.addDefault("Abilities.OneForAll.FullCowling.Effects.Strength", 1);
		config.addDefault("Abilities.OneForAll.FullCowling.Effects.Endurance", 2);
		config.addDefault("Abilities.OneForAll.FullCowling.DamageThreshold", 8.0);
		
		config.addDefault("Abilities.OneForAll.Smash.DELAWARE.Cooldown", 800);
		config.addDefault("Abilities.OneForAll.Smash.DELAWARE.Strength", 1.5);
		config.addDefault("Abilities.OneForAll.Smash.DELAWARE.Range", 10);
		config.addDefault("Abilities.OneForAll.Smash.DELAWARE.Radius", 0.4);
		config.addDefault("Abilities.OneForAll.Smash.DELAWARE.PowerUse", 10);
		
		config.addDefault("Abilities.OneForAll.Smash.DETROIT.Cooldown", 8000);
		config.addDefault("Abilities.OneForAll.Smash.DETROIT.Strength", 3.2);
		config.addDefault("Abilities.OneForAll.Smash.DETROIT.Range", 22);
		config.addDefault("Abilities.OneForAll.Smash.DETROIT.Radius", 1.1);
		config.addDefault("Abilities.OneForAll.Smash.DETROIT.PowerUse", 100);
		
		//frog
		config.addDefault("Abilities.Frog.Froglike.Jump", 4);
		config.addDefault("Abilities.Frog.Froglike.SwimSpeed", 3);
		config.addDefault("Abilities.Frog.Froglike.CamoChargeTime", 3000);
		
		config.addDefault("Abilities.Frog.Tongue.Cooldown", 2000);
		config.addDefault("Abilities.Frog.Tongue.Range", 10);
		config.addDefault("Abilities.Frog.Tongue.Damage", 2.0);
		
		//hardening
		config.addDefault("Abilities.Hardening.Passive.MaxStamina", 800);
		
		config.addDefault("Abilities.Hardening.Harden.StaminaUse", 1);
		config.addDefault("Abilities.Hardening.Harden.Strength", 1);
		config.addDefault("Abilities.Hardening.Harden.Endurance", 1);
		config.addDefault("Abilities.Hardening.Harden.UnbreakableFactor", 4);
		
		config.addDefault("Abilities.Invisibility.LightRefraction.Range", 10);
		config.addDefault("Abilities.Invisibility.LightRefraction.Radius", 1.1);
		config.addDefault("Abilities.Invisibility.LightRefraction.StaminaUse", 5);
		
		//explosion
		config.addDefault("Abilities.Explosion.Passive.MaxSweat", 700);
		
		config.addDefault("Abilities.Explosion.Blast.NORMAL.Cooldown", 3000);
		config.addDefault("Abilities.Explosion.Blast.NORMAL.Range", 5);
		config.addDefault("Abilities.Explosion.Blast.NORMAL.Radius", 0.6);
		config.addDefault("Abilities.Explosion.Blast.NORMAL.Power", 6);
		config.addDefault("Abilities.Explosion.Blast.NORMAL.StaminaUse", 50);
		
		config.addDefault("Abilities.Explosion.Blast.LARGE.Cooldown", 12000);
		config.addDefault("Abilities.Explosion.Blast.LARGE.Range", 12);
		config.addDefault("Abilities.Explosion.Blast.LARGE.Radius", 1.3);
		config.addDefault("Abilities.Explosion.Blast.LARGE.Power", 9);
		config.addDefault("Abilities.Explosion.Blast.LARGE.StaminaUse", 250);
		
		config.addDefault("Abilities.Explosion.Blast.APSHOT.Cooldown", 500);
		config.addDefault("Abilities.Explosion.Blast.APSHOT.Range", 18);
		config.addDefault("Abilities.Explosion.Blast.APSHOT.Radius", 0.2);
		config.addDefault("Abilities.Explosion.Blast.APSHOT.Power", 4);
		config.addDefault("Abilities.Explosion.Blast.APSHOT.StaminaUse", 10);
		
		config.addDefault("Abilities.Explosion.BlastRushTurbo.Power", 0.7);
		config.addDefault("Abilities.Explosion.BlastRushTurbo.StaminaUse", 7);
		
		config.addDefault("Abilities.Explosion.ExplosiveLaunch.Cooldown", 7000);
		config.addDefault("Abilities.Explosion.ExplosiveLaunch.ChargeTime", 1000);
		config.addDefault("Abilities.Explosion.ExplosiveLaunch.Power", 3.4);
		config.addDefault("Abilities.Explosion.ExplosiveLaunch.StaminaUse", 100);
		
		//engine
		config.addDefault("Abilities.Engine.Passive.MaxFuel", 2000);
		
		config.addDefault("Abilities.Engine.Engines.Speed", 2);
		config.addDefault("Abilities.Engine.Engines.FuelConsumption", 2);
		
		config.addDefault("Abilities.Engine.ReciproBurst.Factor", 3);
		config.addDefault("Abilities.Engine.ReciproBurst.StallTime", 15000);
		
		//half cold half hot
		config.addDefault("Abilities.HalfColdHalfHot.Flame.BLAST.Range", 17);
		config.addDefault("Abilities.HalfColdHalfHot.Flame.BLAST.Damage", 2);
		config.addDefault("Abilities.HalfColdHalfHot.Flame.BLAST.Cooldown", 3000);
		config.addDefault("Abilities.HalfColdHalfHot.Flame.BLAST.Radius", 0.4);
		config.addDefault("Abilities.HalfColdHalfHot.Flame.BLAST.HeatRaise", 5);
		
		config.addDefault("Abilities.HalfColdHalfHot.Flame.WALL.Range", 2);
		config.addDefault("Abilities.HalfColdHalfHot.Flame.WALL.Damage", 0.5);
		config.addDefault("Abilities.HalfColdHalfHot.Flame.WALL.Cooldown", 8000);
		config.addDefault("Abilities.HalfColdHalfHot.Flame.WALL.Radius", 4);
		config.addDefault("Abilities.HalfColdHalfHot.Flame.WALL.HeatRaise", 3);
		
		config.addDefault("Abilities.HalfColdHalfHot.Ice.BLAST.Range", 17);
		config.addDefault("Abilities.HalfColdHalfHot.Ice.BLAST.Damage", 2);
		config.addDefault("Abilities.HalfColdHalfHot.Ice.BLAST.Cooldown", 3000);
		config.addDefault("Abilities.HalfColdHalfHot.Ice.BLAST.Radius", 0.4);
		config.addDefault("Abilities.HalfColdHalfHot.Ice.BLAST.HeatLower", 4);
		
		config.addDefault("Abilities.HalfColdHalfHot.Ice.WALL.Range", 2);
		config.addDefault("Abilities.HalfColdHalfHot.Ice.WALL.Damage", 0.5);
		config.addDefault("Abilities.HalfColdHalfHot.Ice.WALL.Cooldown", 8000);
		config.addDefault("Abilities.HalfColdHalfHot.Ice.WALL.Radius", 4);
		config.addDefault("Abilities.HalfColdHalfHot.Ice.WALL.HeatLower", 3);
		
		config.addDefault("Abilities.HalfColdHalfHot.Freeze.Duration", 20000);
		config.addDefault("Abilities.HalfColdHalfHot.Freeze.MaxPower", 5);
		config.addDefault("Abilities.HalfColdHalfHot.Freeze.HeatLower", 2);
		
		//zero gravity
		config.addDefault("Abilities.ZeroGravity.Passive.WeightLimit", 6000);
		
		//creation
		config.addDefault("Abilities.Creation.Passive.MaxLipids", 700);
		
		c.save();
		configs.put(ConfigType.ABILITIES, c);
		
		c = new Config(ConfigType.CHAT.getPath());
		config = c.get();
		
		//chat config
		config.addDefault("Chat.Enabled", true);
		config.addDefault("Chat.Format", "&e[{quirkcolor}{quirk}&e] {player}&f: {message}");
		
		c.save();
		configs.put(ConfigType.CHAT, c);
		
		c = new Config(ConfigType.PROPERTIES.getPath());
		config = c.get();
		
		//plugin properties config
		config.addDefault("Storage.Type", "config");
		config.addDefault("Storage.SaveCooldowns", true);
		config.addDefault("Storage.MySQL.host", "localhost");
		config.addDefault("Storage.MySQL.port", 3306);
		config.addDefault("Storage.MySQL.pass", "");
		config.addDefault("Storage.MySQL.db", "minecraft");
		config.addDefault("Storage.MySQL.user", "root");
		
		config.addDefault("AutoAssign.Enabled", true);
		config.addDefault("AutoAssign.Fusions", false);
		
		c.save();
		configs.put(ConfigType.PROPERTIES, c);
		
		c = new Config(ConfigType.FUSIONS.getPath());
		//nothing to default here, just need to create the file and config
		configs.put(ConfigType.FUSIONS, c);
	}
	
	public Config get(ConfigType type) {
		if (type != null) {
			return configs.get(type);
		}
		
		return null;
	}
	
	public FileConfiguration getConfiguration(ConfigType type) {
		Config c = get(type);
		
		if (c == null) {
			return null;
		}
		
		return c.get();
	}
}
