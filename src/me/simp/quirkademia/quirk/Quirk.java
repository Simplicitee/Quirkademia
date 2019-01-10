package me.simp.quirkademia.quirk;

import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;

import me.simp.quirkademia.QuirkPlugin;
import me.simp.quirkademia.ability.QuirkAbility;
import me.simp.quirkademia.ability.QuirkAbilityInfo;
import me.simp.quirkademia.configuration.ConfigType;
import me.simp.quirkademia.quirk.electrification.ElectrificationQuirk;
import me.simp.quirkademia.quirk.frog.FrogQuirk;
import me.simp.quirkademia.quirk.hardening.HardeningQuirk;
import me.simp.quirkademia.quirk.oneforall.OneForAllQuirk;
import me.simp.quirkademia.util.ActivationType;

public abstract class Quirk implements IQuirk {
	
	private static final Map<String, Quirk> QUIRKS = new HashMap<>();
	private static final Map<Class<? extends Quirk>, Quirk> QUIRKS_CLASSES = new HashMap<>();

	private String name;
	private QuirkType type;
	private Map<ActivationType, QuirkAbilityInfo> abilities;
	
	public Quirk(String name, QuirkType type) {
		this.name = name;
		this.type = type;
		
		QUIRKS.put(name.toLowerCase(), this);
		QUIRKS_CLASSES.put(this.getClass(), this);
		
		this.abilities = registerQuirkAbilities();
		
		for (QuirkAbilityInfo info : abilities.values()) {
			QuirkPlugin.get().getAbilityManager().registerInfo(info);
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
			case EMITTER: return ChatColor.AQUA;
			case TRANSFORMATION: return ChatColor.RED;
			case MUTANT: return ChatColor.GREEN;
			default: return ChatColor.GRAY;
		}
	}
	
	@Override
	public String getStaminaTitle() {
		return QuirkPlugin.get().getConfigs().getConfiguration(ConfigType.QUIRKS).getString("Quirks." + name.replace(" ", "") + ".Stamina.Title");
	}

	@Override
	public BarColor getStaminaColor() {
		BarColor color = BarColor.valueOf(QuirkPlugin.get().getConfigs().getConfiguration(ConfigType.QUIRKS).getString("Quirks." + name.replace(" ", "") + ".Stamina.Color").toUpperCase());
		
		if (color == null) {
			color = BarColor.WHITE;
		}
		
		return color;
	}

	@Override
	public int getStaminaMax() {
		return QuirkPlugin.get().getConfigs().getConfiguration(ConfigType.QUIRKS).getInt("Quirks." + name.replace(" ", "") + ".Stamina.Max");
	}

	@Override
	public int getStaminaRecharge() {
		return QuirkPlugin.get().getConfigs().getConfiguration(ConfigType.QUIRKS).getInt("Quirks." + name.replace(" ", "") + ".Stamina.Recharge");
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
		
		Class<? extends QuirkAbility> ability = abilities.get(type).getAbilityClass();
		try {
			Constructor<?> construct = ability.getConstructor(QuirkUser.class);
			return (QuirkAbility) construct.newInstance(user);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static Quirk get(String name) {
		name = name.toLowerCase();
		return (QUIRKS.containsKey(name) ? QUIRKS.get(name) : null);
	}
	
	public static Quirk get(Class<? extends Quirk> clazz) {
		return (QUIRKS_CLASSES.containsKey(clazz) ? QUIRKS_CLASSES.get(clazz) : null);
	}
	
	public static Collection<Quirk> getAll() {
		return QUIRKS.values();
	}
	
	public static void loadCoreQuirks() {
		new OneForAllQuirk();
		new FrogQuirk();
		new ElectrificationQuirk();
		new HardeningQuirk();
	}
	
	public abstract Map<ActivationType, QuirkAbilityInfo> registerQuirkAbilities();
}
