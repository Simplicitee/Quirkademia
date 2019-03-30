package me.simp.quirkademia.quirk.oneforall;

import org.bukkit.Location;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.simp.quirkademia.ability.QuirkAbility;
import me.simp.quirkademia.configuration.ConfigType;
import me.simp.quirkademia.quirk.QuirkUser;
import me.simp.quirkademia.util.ParticleEffect;
import me.simp.quirkademia.util.StatusEffect;

public class FullCowling extends QuirkAbility {
	
	private double health;
	private double damageThreshold;
	private boolean charged;
	private int strength, speed, jump, endurance, power, limit, charge;
	private SmashTracker passive;

	public FullCowling(QuirkUser user) {
		super(user);
		
		if (manager.hasAbility(user, this.getClass())) {
			manager.remove(manager.getAbility(user, this.getClass()));
			return;
		} else if (user.hasCooldown("full cowling")) {
			return;
		}
		
		if (!manager.hasAbility(user, SmashTracker.class)) {
			return;
		}
		
		passive = manager.getAbility(user, SmashTracker.class);
		
		if (player.hasPermission("quirk.oneforall.deku")) {
			limit = 20;
		}
		
		if (player.hasPermission("quirk.oneforall.allmight")) {
			limit = 1000;
		}
		
		if (player.hasPermission("quirk.oneforall.unbounded")) {
			limit = 1000000;
		}
		
		charge = 0;
		charged = false;
		health = player.getHealth();
		damageThreshold = configs.getConfiguration(ConfigType.ABILITIES).getDouble("Abilities.OneForAll.FullCowling.DamageThreshold");
		strength = configs.getConfiguration(ConfigType.ABILITIES).getInt("Abilities.OneForAll.FullCowling.Effects.Strength");
		speed = configs.getConfiguration(ConfigType.ABILITIES).getInt("Abilities.OneForAll.FullCowling.Effects.Speed");
		jump = configs.getConfiguration(ConfigType.ABILITIES).getInt("Abilities.OneForAll.FullCowling.Effects.Jump");
		endurance = configs.getConfiguration(ConfigType.ABILITIES).getInt("Abilities.OneForAll.FullCowling.Effects.Endurance");
		
		manager.start(this);
	}

	@Override
	public boolean progress() {
		if (!charged) {
			if (player.isSneaking()) {
				if (++charge % 10 == 0) {
					power++;
					
					if (power > limit) {
						power = limit;
						methods.sendActionBarMessage("&c!> &aOutput Limit Reached &c<!", player);
					} else {
						methods.sendActionBarMessage("&c!> &aPower Output - " + power + "% &c<!", player);
					}
				}
			} else {
				charged = true;
				passive.usePower(power);
			}
		} else {
			if (health - player.getHealth() >= damageThreshold) {
				user.addCooldown("full cowling", configs.getConfiguration(ConfigType.ABILITIES).getLong("Abilities.OneForAll.FullCowling.Cooldown"));
				return false;
			}
			
			user.getStatus().add(StatusEffect.INCREASED_STRENGTH, strength + power/20);
			user.getStatus().add(StatusEffect.INCREASED_SPEED, speed + power/20);
			user.getStatus().add(StatusEffect.INCREASED_JUMP, jump + power/20);
			user.getStatus().add(StatusEffect.INCREASED_ENDURANCE, endurance + power/20);
			
			player.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 5, speed + power/20, true, false), true);
			
			ParticleEffect.displayColoredParticle("03c58b", player.getLocation().clone().add(0, 1, 0), 9, 0.25, 0.6, 0.25);
		}
		
		return true;
	}

	@Override
	public void onStart() {
	}

	@Override
	public void onRemove() {
		user.getStatus().remove(StatusEffect.INCREASED_STRENGTH);
		user.getStatus().remove(StatusEffect.INCREASED_SPEED);
		user.getStatus().remove(StatusEffect.INCREASED_JUMP);
		user.getStatus().remove(StatusEffect.INCREASED_ENDURANCE);
	}

	@Override
	public Location getLocation() {
		return player.getLocation().clone().add(0, 1, 0);
	}
	
	public int getPower() {
		return power;
	}
}
