package me.simp.quirkademia.ability;

import org.bukkit.event.Listener;

import me.simp.quirkademia.quirk.Quirk;
import me.simp.quirkademia.util.ActivationType;

public class QuirkAbilityInfo implements Listener {

	private ActivationType type;
	private Class<? extends QuirkAbility> provider;
	private Quirk quirk;
	private String name, instruction, description;
	
	public QuirkAbilityInfo(ActivationType type, Class<? extends QuirkAbility> provider, Quirk quirk, String name, String description, String instruction) {
		this.type = type;
		this.provider = provider;
		this.quirk = quirk;
		this.name = name;
		this.description = description;
		this.instruction = instruction;
	}
	
	public ActivationType getActivation() {
		return type;
	}
	
	public Class<? extends QuirkAbility> getProvider() {
		return provider;
	}
	
	public Quirk getQuirk() {
		return quirk;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getInstruction() {
		return instruction;
	}
}
