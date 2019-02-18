package me.simp.quirkademia.quirk.halfcoldhalfhot;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.simp.quirkademia.ability.QuirkAbility;
import me.simp.quirkademia.configuration.ConfigType;
import me.simp.quirkademia.quirk.QuirkUser;

public class Freeze extends QuirkAbility {
	
	public Freeze(QuirkUser user) {
		super(user);
		
		BodyHeat passive;
		
		if (manager.hasAbility(user, BodyHeat.class)) {
			passive = manager.getAbility(user, BodyHeat.class);
		} else {
			return;
		}
		
		if (user.hasCooldown("freeze")) {
			return;
		}
		
		LivingEntity lent = methods.getTargetedLivingEntity(player, 2);
		int power = 1;
		long duration = configs.getConfiguration(ConfigType.ABILITIES).getLong("Abilities.HalfColdHalfHot.Freeze.Duration");
		int max = configs.getConfiguration(ConfigType.ABILITIES).getInt("Abilities.HalfColdHalfHot.Freeze.MaxPower");
		int stamina = configs.getConfiguration(ConfigType.ABILITIES).getInt("Abilities.HalfColdHalfHot.Freeze.HeatLower");
		
		if (lent.hasPotionEffect(PotionEffectType.SLOW)) {
			if (lent.getPotionEffect(PotionEffectType.SLOW).getAmplifier() >= max) {
				return;
			}
			
			power += lent.getPotionEffect(PotionEffectType.SLOW).getAmplifier();
		}
		
		lent.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, (int) duration/1000*20, power, true, false), true);
		
		if (!passive.lower(stamina)) {
			return;
		}
		
		user.addCooldown("freeze", 500);
	}

	@Override
	public Location getLocation() {
		return player.getLocation();
	}

	@Override
	public boolean progress() {
		return false;
	}

	@Override
	public void onStart() {
		
	}

	@Override
	public void onRemove() {
		
	}

}
