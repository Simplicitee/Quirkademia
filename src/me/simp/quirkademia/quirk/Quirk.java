package me.simp.quirkademia.quirk;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import me.simp.quirkademia.QuirkPlugin;
import me.simp.quirkademia.ability.QuirkAbility;
import me.simp.quirkademia.ability.QuirkAbilityInfo;
import me.simp.quirkademia.util.ActivationType;

public abstract class Quirk implements IQuirk, Listener {

	protected QuirkPlugin plugin;
	
	private String name;
	private QuirkType type;
	private Map<ActivationType, QuirkAbilityInfo> abilities;
	
	public Quirk(String name, QuirkType type) {
		this.plugin = QuirkPlugin.get();
		this.name = name;
		this.type = type;
		this.abilities = new HashMap<>();
		
		Set<QuirkAbilityInfo> infos = registerAbilities();
		
		for (QuirkAbilityInfo info : infos) {
			abilities.put(info.getActivation(), info);
			plugin.getAbilityManager().registerInfo(info);
		}
		
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public QuirkType getType() {
		return type;
	}

	@Override
	public ChatColor getChatColor() {
		switch (type) {
			case EMITTER: return ChatColor.RED;
			case TRANSFORMATION: return ChatColor.AQUA;
			case MUTANT: return ChatColor.GREEN;
			case FUSION: return ChatColor.DARK_PURPLE;
			default: return ChatColor.GRAY;
		}
	}
	
	public QuirkAbility activateAbility(QuirkUser user, ActivationType type) {
		Player player = Bukkit.getPlayer(user.getUniqueId());
		
		if (player == null) {
			return null;
		}
		
		if (!hasActivationType(type)) {
			return null;
		}
		
		QuirkAbilityInfo info = abilities.get(type);
		
		if (type == ActivationType.PASSIVE && QuirkPlugin.get().getAbilityManager().hasAbility(user, info.getProvider())) {
			return null;
		} else if (!user.canUseAbility(info)) {
			return null;
		}
		
		try {
			Constructor<?> construct = info.getProvider().getConstructor(QuirkUser.class);
			
			return (QuirkAbility) construct.newInstance(this);
		} catch (Exception e) {
			return null;
		}
	}
	
	public String getDisplayName() {
		return getChatColor() + getName();
	}
	
	public Map<ActivationType, QuirkAbilityInfo> getAbilities() {
		return new HashMap<>(abilities);
	}
	
	public boolean hasActivationType(ActivationType type) {
		return abilities.containsKey(type);
	}
	
	public abstract Set<QuirkAbilityInfo> registerAbilities();
}
