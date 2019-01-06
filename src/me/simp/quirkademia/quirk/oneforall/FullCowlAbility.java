package me.simp.quirkademia.quirk.oneforall;

import org.bukkit.Location;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.simp.quirkademia.ability.QuirkAbility;
import me.simp.quirkademia.quirk.QuirkUser;
import me.simp.quirkademia.quirk.QuirkUser.StatusEffect;
import me.simp.quirkademia.util.ParticleEffect;

public class FullCowlAbility extends QuirkAbility {
	
	private long chargeTime;
	private long startTime;
	private double animationHeight;
	private double animationAngle;
	private double health;
	private double damageThreshold;
	private boolean charged;

	public FullCowlAbility(QuirkUser user) {
		super(user);
		
		chargeTime = 3000;
		animationHeight = 0;
		animationAngle = 0;
		charged = false;
		health = player.getHealth();
		damageThreshold = 6;
		
		plugin.getAbilityManager().start(this);
	}

	@Override
	public boolean progress() {
		if (player.isSneaking() && !charged) {
			Location display = player.getLocation().clone();
			Location display2 = player.getLocation().clone();
			
			double x = 0.3 * Math.cos(animationAngle);
			double z = 0.3 * Math.sin(animationAngle);
			
			display.add(x, animationHeight, z);
			display2.add(-x, animationHeight, -z);
			
			ParticleEffect.displayColoredParticle("03c58b", display, 2, 0.1, 0.1, 0.1);
			ParticleEffect.displayColoredParticle("03c58b", display2, 2, 0.1, 0.1, 0.1);
			
			animationAngle += Math.PI / 12;
			animationHeight += 1.6 / (chargeTime/50);
			
			if (System.currentTimeMillis() >= startTime + chargeTime) {
				charged = true;
			}
		} else if (!player.isSneaking() && !charged) {
			return false;
		}
		
		if (charged) {
			if (health - player.getHealth() >= damageThreshold) {
				return false;
			}
			
			user.getStatus().add(StatusEffect.INCREASED_STRENGTH, 2);
			user.getStatus().add(StatusEffect.INCREASED_SPEED, 3);
			user.getStatus().add(StatusEffect.INCREASED_JUMP, 3);
			user.getStatus().add(StatusEffect.INCREASED_ENDURANCE, 2);
			player.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 5, 2, true, false), true);
			
			ParticleEffect.displayColoredParticle("03c58b", player.getLocation().clone().add(0, 1, 0), 6, 0.25, 0.6, 0.25);
		}
		
		return true;
	}

	@Override
	public void onStart() {
		startTime = System.currentTimeMillis();
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
}
