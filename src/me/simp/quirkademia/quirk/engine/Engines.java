package me.simp.quirkademia.quirk.engine;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import me.simp.quirkademia.ability.QuirkAbility;
import me.simp.quirkademia.configuration.ConfigType;
import me.simp.quirkademia.quirk.QuirkUser;
import me.simp.quirkademia.util.ParticleEffect;
import me.simp.quirkademia.util.StatusEffect;

public class Engines extends QuirkAbility {
	
	private int speed;
	private int fuelConsumption;
	private int reciproFactor;
	private boolean reciproBurst;
	private FuelGauge passive;
	
	public Engines(QuirkUser user) {
		super(user);
		
		if (manager.hasAbility(user, this.getClass())) {
			manager.remove(manager.getAbility(user, this.getClass()));
			return;
		}
		
		if (!manager.hasAbility(user, FuelGauge.class)) {
			return;
		}
		
		passive = manager.getAbility(user, FuelGauge.class);
		
		if (passive.isStalled()) {
			return;
		}
		
		speed = configs.getConfiguration(ConfigType.ABILITIES).getInt("Abilities.Engine.Engines.Speed");
		fuelConsumption = configs.getConfiguration(ConfigType.ABILITIES).getInt("Abilities.Engine.Engines.FuelConsumption");
		reciproFactor = configs.getConfiguration(ConfigType.ABILITIES).getInt("Abilities.Engine.ReciproBurst.Factor");
		
		manager.start(this);
	}

	@Override
	public Location getLocation() {
		return player.getLocation();
	}

	@Override
	public boolean progress() {
		if (!passive.useFuel(fuelConsumption)) {
			return false;
		}
		
		Location display = player.getLocation().clone().add(0, 0.25, 0);
		ParticleEffect.displayColoredParticle("2c538f", display, 6 * (reciproBurst ? reciproFactor : 1), 0.1, 0.1, 0.1);
		
		for (Entity e : methods.getEntitiesAroundPoint(player.getLocation().clone().add(0, 1, 0), 1.5)) {
			if (e instanceof LivingEntity && e.getEntityId() != player.getEntityId()) {
				methods.damageEntity((LivingEntity) e, player, this, speed/4);
			}
		}
		
		return true;
	}

	@Override
	public void onStart() {
		methods.sendActionBarMessage("&c!> &9ENGINES STARTED &c<!", player);
		user.getStatus().add(StatusEffect.INCREASED_SPEED, speed);
	}

	@Override
	public void onRemove() {
		methods.sendActionBarMessage("&c!> &9ENGINES STOPPED &c<!", player);
		user.getStatus().remove(StatusEffect.INCREASED_SPEED);
		
		if (reciproBurst) {
			user.addCooldown("Engines", configs.getConfiguration(ConfigType.ABILITIES).getLong("Abilities.Engine.Engines.ReciproBurst.StallTime"));
			passive.stallEnginges();
		}
	}

	public void activateReciproBurst() {
		if (!reciproBurst) {
			speed *= reciproFactor;
			fuelConsumption *= reciproFactor;
			reciproBurst = true;
			
			user.getStatus().remove(StatusEffect.INCREASED_SPEED);
			user.getStatus().add(StatusEffect.INCREASED_SPEED, speed);
			
			methods.sendActionBarMessage("&c!> &9RECIPROBURST &c<!", player);
		}
	}
}
