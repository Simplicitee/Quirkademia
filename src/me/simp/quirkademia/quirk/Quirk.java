package me.simp.quirkademia.quirk;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;

import me.simp.quirkademia.QuirkPlugin;
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
		String title = plugin.getConfigs().getConfiguration(ConfigType.QUIRKS).getString("Quirks." + name.replace(" ", "") + ".Stamina.Title");
		
		if (title == null) {
			title = getName();
		}
		
		return title;
	}

	@Override
	public BarColor getStaminaColor() {
		String value = plugin.getConfigs().getConfiguration(ConfigType.QUIRKS).getString("Quirks." + name.replace(" ", "") + ".Stamina.Color");
		BarColor color = BarColor.WHITE;
		
		if (value == null) {
			if (this instanceof FusedQuirk) {
				color = BarColor.PURPLE;
			}
		} else {
			color = BarColor.valueOf(value.toUpperCase());
		}
		
		return color;
	}

	@Override
	public int getStaminaMax() {
		int max = plugin.getConfigs().getConfiguration(ConfigType.QUIRKS).getInt("Quirks." + name.replace(" ", "") + ".Stamina.Max");
		
		if (max == 0) {
			max = 1000;
		}
		
		return max;
	}

	@Override
	public int getStaminaRecharge() {
		int recharge = 50;
		
		if (plugin.getConfigs().getConfiguration(ConfigType.QUIRKS).contains("Quirks." + name.replace(" ", "") + ".Stamina.Recharge")) {
			recharge = plugin.getConfigs().getConfiguration(ConfigType.QUIRKS).getInt("Quirks." + name.replace(" ", "") + ".Stamina.Recharge");
		}
		
		return recharge;
	}
	
	public Map<ActivationType, QuirkAbilityInfo> getAbilities() {
		return new HashMap<>(abilities);
	}
	
	public boolean hasActivationType(ActivationType type) {
		return abilities.containsKey(type);
	}
	
	public abstract Set<QuirkAbilityInfo> registerAbilities();
}
