package me.simp.quirkademia.quirk.engine;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import me.simp.quirkademia.ability.QuirkAbility;
import me.simp.quirkademia.configuration.ConfigType;
import me.simp.quirkademia.quirk.QuirkUser;
import me.simp.quirkademia.util.StatusEffect;

public class Engines extends QuirkAbility {
	
	private int speed;
	private int fuelConsumption;
	private int reciproFactor;
	private boolean reciproBurst;
	
	public Engines(QuirkUser user) {
		super(user);
		
		if (manager.hasAbility(user, this.getClass())) {
			manager.remove(manager.getAbility(user, this.getClass()));
			return;
		}
		
		speed = configs.getConfiguration(ConfigType.ABILITIES).getInt("Abilities.Engine.Engines.Speed");
		fuelConsumption = configs.getConfiguration(ConfigType.ABILITIES).getInt("Abilities.Engine.Engines.FuelConsumption");
		reciproFactor = configs.getConfiguration(ConfigType.ABILITIES).getInt("Abilities.Engine.Engines.ReciproBurst.Factor");
		
		manager.start(this);
	}

	@Override
	public Location getLocation() {
		return player.getLocation();
	}

	@Override
	public boolean progress() {
		int diff = user.getStamina().get() - fuelConsumption;
		
		if (diff < 0) {
			return false;
		}
		
		user.getStamina().set(diff);
		
		for (Entity e : methods.getEntitiesAroundPoint(player.getLocation().clone().add(0, 1, 0), 1.5)) {
			if (e instanceof LivingEntity && e.getEntityId() != player.getEntityId()) {
				methods.damageEntity((LivingEntity) e, player, this, speed/4);
			}
		}
		
		return true;
	}

	@Override
	public void onStart() {
		methods.sendActionBarMessage(ChatColor.BLUE + "ENGINES STARTED", player);
		user.getStatus().add(StatusEffect.INCREASED_SPEED, speed);
	}

	@Override
	public void onRemove() {
		methods.sendActionBarMessage(ChatColor.BLUE + "ENGINES STOPPED", player);
		user.getStatus().remove(StatusEffect.INCREASED_SPEED);
		
		if (reciproBurst) {
			user.addCooldown("Engines", configs.getConfiguration(ConfigType.ABILITIES).getLong("Abilities.Engine.Engines.ReciproBurst.StallTime"));
		}
	}

	public void activateReciproBurst() {
		if (!reciproBurst) {
			speed *= reciproFactor;
			fuelConsumption *= reciproFactor;
			reciproBurst = true;
			
			user.getStatus().remove(StatusEffect.INCREASED_SPEED);
			user.getStatus().add(StatusEffect.INCREASED_SPEED, speed);
			
			methods.sendActionBarMessage(ChatColor.BLUE + "RECIPROBURST ACTIVATED", player);
		}
	}
}
