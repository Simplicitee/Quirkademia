package me.simp.quirkademia.manager;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bukkit.plugin.java.JavaPlugin;

import me.simp.quirkademia.QuirkPlugin;
import me.simp.quirkademia.quirk.Quirk;
import me.simp.quirkademia.quirk.QuirkType;
import me.simp.quirkademia.quirk.electrification.ElectrificationQuirk;
import me.simp.quirkademia.quirk.engine.EngineQuirk;
import me.simp.quirkademia.quirk.explosion.ExplosionQuirk;
import me.simp.quirkademia.quirk.frog.FrogQuirk;
import me.simp.quirkademia.quirk.halfcoldhalfhot.HalfColdHalfHotQuirk;
import me.simp.quirkademia.quirk.hardening.HardeningQuirk;
import me.simp.quirkademia.quirk.invisibility.InvisibilityQuirk;
import me.simp.quirkademia.quirk.oneforall.OneForAllQuirk;

public class QuirkManager {

	private QuirkPlugin plugin;
	private Map<Class<? extends Quirk>, Quirk> classMap;
	private Map<String, Quirk> nameMap;
	private Map<QuirkType, Set<Quirk>> typeMap;
	private Map<JavaPlugin, Set<Quirk>> plugins;
	
	public QuirkManager(QuirkPlugin plugin) {
		this.plugin = plugin;
		this.classMap = new HashMap<>();
		this.nameMap = new HashMap<>();
		this.typeMap = new HashMap<>();
		this.plugins = new HashMap<>();
		this.init();
	}
	
	public QuirkPlugin getPlugin() {
		return plugin;
	}
	
	private void init() {
		register(plugin, new OneForAllQuirk());
		register(plugin, new FrogQuirk());
		register(plugin, new ElectrificationQuirk());
		register(plugin, new HardeningQuirk());
		register(plugin, new InvisibilityQuirk());
		register(plugin, new ExplosionQuirk());
		register(plugin, new EngineQuirk());
		register(plugin, new HalfColdHalfHotQuirk());
	}
	
	public boolean register(JavaPlugin provider, Quirk quirk) {
		if (quirk == null) {
			return false;
		}
		
		classMap.put(quirk.getClass(), quirk);
		nameMap.put(quirk.getName().toLowerCase(), quirk);
		
		if (!typeMap.containsKey(quirk.getType())) {
			typeMap.put(quirk.getType(), new HashSet<>());
		}
		
		if (!plugins.containsKey(provider)) {
			plugins.put(provider, new HashSet<>());
		}
		
		plugins.get(provider).add(quirk);
		
		return typeMap.get(quirk.getType()).add(quirk);
	}
	
	public Quirk getQuirk(Class<? extends Quirk> provider) {
		if (classMap.containsKey(provider)) {
			return classMap.get(provider);
		}
		
		return null;
	}
	
	public Quirk getQuirk(String name) {
		name = name.toLowerCase();
		if (nameMap.containsKey(name)) {
			return nameMap.get(name);
		}
		
		return null;
	}
	
	public Set<Quirk> getQuirks(QuirkType type) {
		if (typeMap.containsKey(type)) {
			return new HashSet<>(typeMap.get(type));
		}
		
		return Collections.emptySet();
	}
	
	public Set<Quirk> getQuirks(JavaPlugin provider) {
		if (plugins.containsKey(provider)) {
			return new HashSet<>(plugins.get(provider));
		}
		
		return Collections.emptySet();
	}
	
	public Set<Quirk> getQuirks() {
		return new HashSet<>(classMap.values());
	}
}
