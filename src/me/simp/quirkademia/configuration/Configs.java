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
		
		config.addDefault("Quirks.OneForAll.Stamina.Title", "Stamina");
		config.addDefault("Quirks.OneForAll.Stamina.Color", "GREEN");
		config.addDefault("Quirks.OneForAll.Stamina.Max", 1000);
		config.addDefault("Quirks.OneForAll.Stamina.Recharge", 40);
		config.addDefault("Quirks.OneForAll.AssignChance", 1);
		
		config.addDefault("Quirks.Frog.Stamina.Title", "Sleepiness");
		config.addDefault("Quirks.Frog.Stamina.Color", "GREEN");
		config.addDefault("Quirks.Frog.Stamina.Max", 100);
		config.addDefault("Quirks.Frog.Stamina.Recharge", 0);
		config.addDefault("Quirks.Frog.AssignChance", 4);
		
		config.addDefault("Quirks.Electrification.Stamina.Title", "Static Charge");
		config.addDefault("Quirks.Electrification.Stamina.Color", "YELLOW");
		config.addDefault("Quirks.Electrification.Stamina.Max", 200);
		config.addDefault("Quirks.Electrification.Stamina.Recharge", 20);
		config.addDefault("Quirks.Electrification.AssignChance", 10);
		
		config.addDefault("Quirks.Hardening.Stamina.Title", "Hardness");
		config.addDefault("Quirks.Hardening.Stamina.Color", "RED");
		config.addDefault("Quirks.Hardening.Stamina.Max", 800);
		config.addDefault("Quirks.Hardening.Stamina.Recharge", 0);
		config.addDefault("Quirks.Hardening.AssignChance", 7);
		
		config.addDefault("Quirks.Invisibility.Stamina.Title", "Light Refraction");
		config.addDefault("Quirks.Invisibility.Stamina.Color", "WHITE");
		config.addDefault("Quirks.Invisibility.Stamina.Max", 100);
		config.addDefault("Quirks.Invisibility.Stamina.Recharge", 5);
		config.addDefault("Quirks.Invisibility.AssignChance", 4);
		
		config.addDefault("Quirks.Explosion.Stamina.Title", "Nitrosweat");
		config.addDefault("Quirks.Explosion.Stamina.Color", "RED");
		config.addDefault("Quirks.Explosion.Stamina.Max", 700);
		config.addDefault("Quirks.Explosion.Stamina.Recharge", 35);
		config.addDefault("Quirks.Explosion.AssignChance", 1);
		
		config.addDefault("Quirks.Creation.Stamina.Title", "Lipids");
		config.addDefault("Quirks.Creation.Stamina.Color", "PURPLE");
		config.addDefault("Quirks.Creation.Stamina.Max", 500);
		config.addDefault("Quirks.Creation.Stamina.Recharge", 0);
		config.addDefault("Quirks.Creation.AssignChance", 4);
		
		config.addDefault("Quirks.Engine.Stamina.Title", "Fuel");
		config.addDefault("Quirks.Engine.Stamina.Color", "BLUE");
		config.addDefault("Quirks.Engine.Stamina.Max", 1000);
		config.addDefault("Quirks.Engine.Stamina.Recharge", 50);
		config.addDefault("Quirks.Engine.AssignChance", 5);
		
		config.addDefault("Quirks.HalfColdHalfHot.Stamina.Title", "Body Heat");
		config.addDefault("Quirks.HalfColdHalfHot.Stamina.Color", "RED");
		config.addDefault("Quirks.HalfColdHalfHot.Stamina.Max", 100);
		config.addDefault("Quirks.HalfColdHalfHot.Stamina.Recharge", 0);
		config.addDefault("Quirks.HalfColdHalfHot.AssignChance", 1);
		
		config.addDefault("Quirks.NavelLaser.Stamina.Title", "Laser");
		config.addDefault("Quirks.NavelLaser.Stamina.Color", "BLUE");
		config.addDefault("Quirks.NavelLaser.Stamina.Max", 20);
		config.addDefault("Quirks.NavelLaser.Stamina.Recharge", 0);
		config.addDefault("Quirks.NavelLaser.AssignChance", 4);
		
		config.addDefault("Quirks.ZeroGravity.Stamina.Title", "Weight Limit");
		config.addDefault("Quirks.ZeroGravity.Stamina.Color", "PINK");
		config.addDefault("Quirks.ZeroGravity.Stamina.Max", 6000);
		config.addDefault("Quirks.ZeroGravity.Stamina.Recharge", 0);
		config.addDefault("Quirks.ZeroGravity.AssignChance", 4);
		
		config.addDefault("Quirks.Acid.Stamina.Title", "Acidity");
		config.addDefault("Quirks.Acid.Stamina.Color", "PINK");
		config.addDefault("Quirks.Acid.Stamina.Max", 200);
		config.addDefault("Quirks.Acid.Stamina.Recharge", 5);
		config.addDefault("Quirks.Acid.AssignChance", 3);
		
		c.save();
		configs.put(ConfigType.QUIRKS, c);
		
		c = new Config(ConfigType.ABILITIES.getPath());
		config = c.get();
		
		//ability config
		config.addDefault("Abilities.OneForAll.FullCowling.Cooldown", 2000);
		config.addDefault("Abilities.OneForAll.FullCowling.Effects.Speed", 2);
		config.addDefault("Abilities.OneForAll.FullCowling.Effects.Jump", 2);
		config.addDefault("Abilities.OneForAll.FullCowling.Effects.Strength", 1);
		config.addDefault("Abilities.OneForAll.FullCowling.Effects.Endurance", 2);
		config.addDefault("Abilities.OneForAll.FullCowling.ChargeTime", 3000);
		config.addDefault("Abilities.OneForAll.FullCowling.DamageThreshold", 8.0);
		
		config.addDefault("Abilities.OneForAll.Smash.DELAWARE.Cooldown", 800);
		config.addDefault("Abilities.OneForAll.Smash.DELAWARE.Power", 1.5);
		config.addDefault("Abilities.OneForAll.Smash.DELAWARE.Range", 10);
		config.addDefault("Abilities.OneForAll.Smash.DELAWARE.Radius", 0.4);
		
		config.addDefault("Abilities.OneForAll.Smash.DETROIT.Cooldown", 8000);
		config.addDefault("Abilities.OneForAll.Smash.DETROIT.Power", 3.2);
		config.addDefault("Abilities.OneForAll.Smash.DETROIT.Range", 22);
		config.addDefault("Abilities.OneForAll.Smash.DETROIT.Radius", 1.1);
		
		config.addDefault("Abilities.Frog.Froglike.Jump", 4);
		config.addDefault("Abilities.Frog.Froglike.SwimSpeed", 3);
		config.addDefault("Abilities.Frog.Froglike.CamoChargeTime", 3000);
		
		config.addDefault("Abilities.Frog.Tongue.Cooldown", 2000);
		config.addDefault("Abilities.Frog.Tongue.Range", 10);
		config.addDefault("Abilities.Frog.Tongue.Damage", 2.0);
		
		config.addDefault("Abilities.Hardening.Harden.StaminaUse", 1);
		config.addDefault("Abilities.Hardening.Harden.Strength", 1);
		config.addDefault("Abilities.Hardening.Harden.Endurance", 1);
		config.addDefault("Abilities.Hardening.Harden.UnbreakableFactor", 4);
		
		config.addDefault("Abilities.Invisibility.LightRefraction.Range", 10);
		config.addDefault("Abilities.Invisibility.LightRefraction.Radius", 1.1);
		config.addDefault("Abilities.Invisibility.LightRefraction.StaminaUse", 5);
		
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
		
		config.addDefault("Abilities.Engine.Engines.Speed", 2);
		config.addDefault("Abilities.Engine.Engines.FuelConsumption", 2);
		config.addDefault("Abilities.Engine.Engines.ReciproBurst.Factor", 3);
		config.addDefault("Abilities.Engine.Engines.ReciproBurst.StallTime", 15000);
		
		config.addDefault("Abilities.HalfColdHalfHot.Flame.BLAST.Range", 17);
		config.addDefault("Abilities.HalfColdHalfHot.Flame.BLAST.Damage", 2);
		config.addDefault("Abilities.HalfColdHalfHot.Flame.BLAST.Cooldown", 3000);
		config.addDefault("Abilities.HalfColdHalfHot.Flame.BLAST.Radius", 0.4);
		
		config.addDefault("Abilities.HalfColdHalfHot.Flame.WALL.Range", 2);
		config.addDefault("Abilities.HalfColdHalfHot.Flame.WALL.Damage", 0.5);
		config.addDefault("Abilities.HalfColdHalfHot.Flame.WALL.Cooldown", 8000);
		config.addDefault("Abilities.HalfColdHalfHot.Flame.WALL.Radius", 4);
		
		config.addDefault("Abilities.HalfColdHalfHot.Freeze.Duration", 20000);
		config.addDefault("Abilities.HalfColdHalfHot.Freeze.MaxPower", 5);
		
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
		
		c.save();
		configs.put(ConfigType.PROPERTIES, c);
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
