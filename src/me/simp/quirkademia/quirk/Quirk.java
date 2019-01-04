package me.simp.quirkademia.quirk;

import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;

import me.simp.quirkademia.quirk.ability.QuirkAbility;
import me.simp.quirkademia.util.ActivationType;

public class Quirk implements IQuirk {
	
	private static final Map<String, Quirk> QUIRKS = new HashMap<>();
	private static final Map<Class<? extends Quirk>, Quirk> QUIRKS_CLASSES = new HashMap<>();

	private String name;
	private QuirkType type;
	private Map<ActivationType, Class<? extends QuirkAbility>> abilities;
	
	public Quirk(String name, QuirkType type) {
		this.name = name;
		this.type = type;
		this.abilities = new HashMap<>();
		
		QUIRKS.put(name.toLowerCase(), this);
		QUIRKS_CLASSES.put(this.getClass(), this);
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
	public String getDescription() {
		return null;
	}

	@Override
	public ChatColor getChatColor() {
		switch (type) {
			case EMITTER: return ChatColor.BLUE;
			case TRANSFORMATION: return ChatColor.RED;
			case MUTANT: return ChatColor.GREEN;
			default: return ChatColor.GRAY;
		}
	}
	
	public Map<ActivationType, Class<? extends QuirkAbility>> getAbilities() {
		return new HashMap<>(abilities);
	}
	
	public boolean hasActivationType(ActivationType type) {
		return abilities.containsKey(type);
	}
	
	public QuirkAbility createAbilityInstance(QuirkUser user, ActivationType type) {
		if (!hasActivationType(type)) {
			return null;
		}
		
		Class<? extends QuirkAbility> ability = abilities.get(type);
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
}
