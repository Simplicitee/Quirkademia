package me.simp.quirkademia.manager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import me.simp.quirkademia.QuirkPlugin;
import me.simp.quirkademia.configuration.Config;
import me.simp.quirkademia.configuration.ConfigType;
import me.simp.quirkademia.quirk.FusedQuirk;
import me.simp.quirkademia.quirk.Quirk;
import me.simp.quirkademia.quirk.QuirkType;
import me.simp.quirkademia.quirk.QuirkUser;
import me.simp.quirkademia.quirk.engine.EngineQuirk;
import me.simp.quirkademia.quirk.explosion.ExplosionQuirk;
import me.simp.quirkademia.quirk.frog.FrogQuirk;
import me.simp.quirkademia.quirk.halfcoldhalfhot.HalfColdHalfHotQuirk;
import me.simp.quirkademia.quirk.hardening.HardeningQuirk;
import me.simp.quirkademia.quirk.invisibility.InvisibilityQuirk;
import me.simp.quirkademia.quirk.oneforall.OneForAllQuirk;

public class QuirkManager extends Manager {
	
	private Map<Class<? extends Quirk>, Quirk> classMap;
	private Map<String, Quirk> nameMap;
	private Map<QuirkType, Set<Quirk>> typeMap;
	private Map<JavaPlugin, Set<Quirk>> plugins;
	private Map<String, FusedQuirk> fusions;
	
	public QuirkManager(QuirkPlugin plugin) {
		super(plugin);
		
		this.classMap = new HashMap<>();
		this.nameMap = new HashMap<>();
		this.typeMap = new HashMap<>();
		this.plugins = new HashMap<>();
		this.fusions = new HashMap<>();
		
		this.init();
		this.loadFusions();
	}
	
	private void init() {
		register(plugin, new FrogQuirk());
		register(plugin, new HardeningQuirk());
		register(plugin, new InvisibilityQuirk());
		register(plugin, new ExplosionQuirk());
		register(plugin, new EngineQuirk());
		register(plugin, new HalfColdHalfHotQuirk());
		register(plugin, new OneForAllQuirk());
	}
	
	public boolean register(JavaPlugin provider, Quirk quirk) {
		if (quirk == null) {
			return false;
		}
		
		if (quirk instanceof FusedQuirk) {
			fusions.put(quirk.getName().toLowerCase(), (FusedQuirk) quirk);
		} else {
			classMap.put(quirk.getClass(), quirk);
		}
		
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
	
	/**
	 * Gets a FusedQuirk by it's name, cannot return a normal Quirk.
	 * @param name Name of the FusedQuirk
	 * @return null if FusedQuirk by name doesn't exist
	 */
	public FusedQuirk getFusion(String name) {
		name = name.toLowerCase();
		if (fusions.containsKey(name)) {
			return fusions.get(name);
		}
		
		return null;
	}
	
	/**
	 * Gets a Quirk by it's providing class, cannot return a FusedQuirk.
	 * @param provider Class of the Quirk
	 * @return null if Quirk by provider doesn't exist
	 */
	public Quirk getQuirk(Class<? extends Quirk> provider) {
		if (classMap.containsKey(provider)) {
			return classMap.get(provider);
		}
		
		return null;
	}
	
	/**
	 * Gets a Quirk by it's name, can return a FusedQuirk
	 * @param name Name of the Quirk
	 * @return null if Quirk by name doesn't exist
	 */
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
	
	/**
	 * Gets all the Quirk instances (including FusedQuirk) from the plugin
	 * @return copy of the name Map values collection
	 */
	public Set<Quirk> getQuirks() {
		return new HashSet<>(nameMap.values());
	}
	
	/**
	 * Gets all the FusedQuirk instances from the plugin
	 * @return copy of the fusions Map value collection
	 */
	public Set<FusedQuirk> getFusions() {
		return new HashSet<>(fusions.values());
	}
	
	public void saveFusions() {
		Config c = plugin.getConfigs().get(ConfigType.FUSIONS);
		FileConfiguration config = c.get();
		
		for (String name : fusions.keySet()) {
			FusedQuirk fusion = fusions.get(name);
			List<String> quirks = new ArrayList<>();
			
			for (Quirk q : fusion.getQuirks()) {
				quirks.add(q.getName());
			}
			
			config.set("Fusions." + fusion.getName().replace(" ", "-") + ".Quirks", quirks);
		}
		
		c.save();
		fusions.clear();
	}
	
	public void loadFusions() {
		FileConfiguration config = plugin.getConfigs().getConfiguration(ConfigType.FUSIONS);
		if (config.isConfigurationSection("Fusions")) {
			ConfigurationSection section = config.getConfigurationSection("Fusions");
			
			for (String key : section.getKeys(false)) {
				String name = key.replace("-", " ");
				List<String> quirks = section.getStringList(key + ".Quirks");
				
				FusedQuirk fusion;
				
				if (fusions.containsKey(name.toLowerCase())) {
					fusion = fusions.get(name.toLowerCase());
				} else {
					fusion = new FusedQuirk(name);
				}
				
				for (String sub : quirks) {
					Quirk quirk = getQuirk(sub);
					fusion.addQuirk(quirk);
				}
				
				register(plugin, fusion);
			}
		}
	}
	
	public boolean deleteFusion(FusedQuirk fusion) {
		fusions.remove(fusion.getName().toLowerCase());
		nameMap.remove(fusion.getName().toLowerCase());
		typeMap.get(QuirkType.FUSION).remove(fusion);
		
		for (QuirkUser user : plugin.getUserManager().getOnlineUsers()) {
			if (user.getQuirk().equals(fusion)) {
				user.setQuirk(null);
			}
		}
		
		Config config = plugin.getConfigs().get(ConfigType.FUSIONS);
		
		if (config.getFile().delete()) {
			config.reload();
			config.save();
			
			saveFusions();
			loadFusions();
			return true;
		}
		
		return false;
	}

	@Override
	public void run() {}
}
