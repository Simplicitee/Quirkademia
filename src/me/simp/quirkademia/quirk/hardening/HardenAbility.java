package me.simp.quirkademia.quirk.hardening;

import org.bukkit.ChatColor;
import org.bukkit.Location;

import me.simp.quirkademia.ability.QuirkAbility;
import me.simp.quirkademia.configuration.ConfigType;
import me.simp.quirkademia.quirk.QuirkUser;
import me.simp.quirkademia.quirk.QuirkUser.StatusEffect;
import me.simp.quirkademia.util.ActivationType;
import me.simp.quirkademia.util.ParticleEffect;

public class HardenAbility extends QuirkAbility {
	
	private int strength, endurance;
	private int stamina;
	private int unbreakable;
	private boolean hasUnbreakable;

	public HardenAbility(QuirkUser user) {
		super(user);
		
		if (manager.hasAbility(user, HardenRecharge.class)) {
			return;
		}
		
		if (manager.hasAbility(user, HardenAbility.class)) {
			manager.remove(manager.getAbility(user, HardenAbility.class));
			return;
		}
		
		strength = configs.getConfiguration(ConfigType.ABILITIES).getInt("Abilities.Hardening.Harden.Strength");
		endurance = configs.getConfiguration(ConfigType.ABILITIES).getInt("Abilities.Hardening.Harden.Endurance");
		unbreakable = configs.getConfiguration(ConfigType.ABILITIES).getInt("Abilities.Hardening.Harden.UnbreakableFactor");
		stamina = 1;
		
		manager.start(this);
	}

	@Override
	public Location getLocation() {
		return player.getLocation();
	}

	@Override
	public boolean progress() {
		if (user.getStamina().get() <= 0) {
			return false;
		}
		
		user.getStamina().set(user.getStamina().get() - stamina);
		
		ParticleEffect.CRIT.display(player.getLocation().clone().add(0, 1, 0), 3, 0.2, 0.55, 0.2);
		
		
		if (hasUnbreakable) {
			ParticleEffect.displayColoredParticle("ff6600", player.getLocation().clone().add(0, 1, 0), 2, 0.2, 0.55, 0.2);
		}
		
		return true;
	}

	@Override
	public void onStart() {
		user.getStatus().add(StatusEffect.INCREASED_STRENGTH, strength);
		user.getStatus().add(StatusEffect.INCREASED_ENDURANCE, endurance);
	}

	@Override
	public void onRemove() {
		user.getStatus().remove(StatusEffect.INCREASED_STRENGTH);
		user.getStatus().remove(StatusEffect.INCREASED_ENDURANCE);
		user.getQuirk().createAbilityInstance(user, ActivationType.MANUAL);
	}

	public void activateUnbreakable() {
		player.sendMessage(ChatColor.RED + "Unbreakable mode!");
		
		hasUnbreakable = true;
		
		user.getStatus().remove(StatusEffect.INCREASED_STRENGTH);
		user.getStatus().remove(StatusEffect.INCREASED_ENDURANCE);
		
		strength *= unbreakable;
		endurance *= unbreakable;
		stamina = 4;
		
		user.getStatus().add(StatusEffect.INCREASED_STRENGTH, strength);
		user.getStatus().add(StatusEffect.INCREASED_ENDURANCE, endurance);
	}
	
	public boolean hasUnbreakable() {
		return hasUnbreakable;
	}
}
