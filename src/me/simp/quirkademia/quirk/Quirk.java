package me.simp.quirkademia.quirk;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;

import me.simp.quirkademia.QuirkPlugin;
import me.simp.quirkademia.ability.QuirkAbility;
import me.simp.quirkademia.ability.QuirkAbilityInfo;
import me.simp.quirkademia.configuration.ConfigType;
import me.simp.quirkademia.util.ActivationType;

public abstract class Quirk implements IQuirk {

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
	
	@Override
	public String getStaminaTitle() {
		return plugin.getConfigs().getConfiguration(ConfigType.QUIRKS).getString("Quirks." + name.replace(" ", "") + ".Stamina.Title");
	}

	@Override
	public BarColor getStaminaColor() {
		BarColor color = BarColor.valueOf(plugin.getConfigs().getConfiguration(ConfigType.QUIRKS).getString("Quirks." + name.replace(" ", "") + ".Stamina.Color").toUpperCase());
		
		if (color == null) {
			color = BarColor.WHITE;
		}
		
		return color;
	}

	@Override
	public int getStaminaMax() {
		return plugin.getConfigs().getConfiguration(ConfigType.QUIRKS).getInt("Quirks." + name.replace(" ", "") + ".Stamina.Max");
	}

	@Override
	public int getStaminaRecharge() {
		return plugin.getConfigs().getConfiguration(ConfigType.QUIRKS).getInt("Quirks." + name.replace(" ", "") + ".Stamina.Recharge");
	}
	
	public Map<ActivationType, QuirkAbilityInfo> getAbilities() {
		return new HashMap<>(abilities);
	}
	
	public boolean hasActivationType(ActivationType type) {
		return abilities.containsKey(type);
	}
	
	public QuirkAbility createAbilityInstance(QuirkUser user, ActivationType type) {
		if (!hasActivationType(type)) {
			return null;
		}
		
		Class<? extends QuirkAbility> ability = abilities.get(type).getProvider();
		
		if (!plugin.getMethods().canUseAbility(ability, user)) {
			return null;
		}
		
		try {
			Constructor<?> construct = ability.getConstructor(QuirkUser.class);
			return (QuirkAbility) construct.newInstance(user);
		} catch (Exception e) {
			return null;
		}
	}
	
	public abstract Set<QuirkAbilityInfo> registerAbilities();
}
