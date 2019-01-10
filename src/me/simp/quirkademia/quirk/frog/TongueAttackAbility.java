package me.simp.quirkademia.quirk.frog;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import me.simp.quirkademia.ability.QuirkAbility;
import me.simp.quirkademia.configuration.ConfigType;
import me.simp.quirkademia.quirk.QuirkUser;
import me.simp.quirkademia.quirk.frog.FroglikeAbility.TongueType;
import me.simp.quirkademia.util.ParticleEffect;

public class TongueAttackAbility extends QuirkAbility {

	private int range, maxRange;
	private Location current;
	private boolean forward, backward;
	private LivingEntity grabbed;
	private TongueType type;
	private float walk, fly;
	private double damage;
	
	public TongueAttackAbility(QuirkUser user) {
		super(user);
		
		if (manager.hasAbility(user, this.getClass())) {
			return;
		}
		
		if (manager.hasAbility(user, FroglikeAbility.class)) {
			type = manager.getAbility(user, FroglikeAbility.class).getType();
		} else {
			type = TongueType.NONE;
		}
		
		if (type != TongueType.NONE) {
			if (user.hasCooldown(type.toString().toLowerCase() + " tongue")) {
				return;
			}
		} else {
			return;
		}
		
		range = 0;
		maxRange = configs.getConfiguration(ConfigType.ABILITIES).getInt("Abilities.Frog.Tongue.Range");
		damage = configs.getConfiguration(ConfigType.ABILITIES).getDouble("Abilities.Frog.Tongue.Damage");
		current = player.getEyeLocation().clone();
		forward = true;
		backward = false;
		grabbed = null;
		walk = 0;
		fly = 0;
		
		manager.start(this);
	}

	@Override
	public Location getLocation() {
		return current;
	}

	@Override
	public boolean progress() {
		if (forward) {
			range++;
			
			if (range > maxRange) {
				forward = false;
				backward = true;
			} 
		} else if (backward) {
			range--;
			
			if (range <= 0) {
				return false;
			}
		}
		
		Location last = player.getEyeLocation().clone();
		
		for (int i = 0; i < range; i++) {
			last.add(last.getDirection().clone().normalize());
			
			if (!locationCheck(last) && forward) {
				this.forward = false;
			}
		}
		
		current = last;
		
		if (grabbed != null) {
			if (grabbed instanceof Player) {
				Player p = (Player) grabbed;
				p.setWalkSpeed(0);
				p.setFlySpeed(0);
			}
			
			Location ent = grabbed.getLocation().clone().add(0, 1, 0);
			Vector v = methods.getDirection(ent, current).normalize();
			grabbed.setVelocity(v.multiply(0.4));
			ParticleEffect.displayColoredParticle("#FFC0CB", ent, 10, 0.2, 0.1, 0.2);
			
			if (!player.isSneaking()) {
				grabbed.setVelocity(v.normalize().multiply(1.2));
			}
		}
		
		if (!player.isSneaking()) {
			backward = true;
			forward = false;
			
			if (grabbed != null) {
				if (grabbed instanceof Player) {
					Player p = (Player) grabbed;
					p.setWalkSpeed(walk);
					p.setFlySpeed(fly);
				}
				grabbed = null;
			}
		}
		
		return true;
	}

	@Override
	public void onStart() {
		
	}

	@Override
	public void onRemove() {
		user.addCooldown(type.toString().toLowerCase() + " tongue", configs.getConfiguration(ConfigType.ABILITIES).getLong("Abilities.Frog.Tongue.Cooldown"));
	}

	private boolean locationCheck(Location loc) {
		if (loc.getBlock().getType().isSolid()) {
			this.backward = true;
			return false;
		}
		
		ParticleEffect.displayColoredParticle("#FFC0CB", loc, 2, 0.1, 0.1, 0.1);
		
		if (grabbed == null) {
			for (Entity e : methods.getEntitiesAroundPoint(loc, 1.5)) {
				if (e instanceof LivingEntity && e.getEntityId() != player.getEntityId()) {
					if (type == TongueType.GRAB && grabbed == null) {
						grabbed = (LivingEntity) e;
						if (grabbed instanceof Player) {
							walk = ((Player) grabbed).getWalkSpeed();
							fly = ((Player) grabbed).getFlySpeed();
						}
					} else {
						methods.damageEntity((LivingEntity) e, player, this, damage);
						this.backward = true;
					}
					return false;
				}
			}
		}
		
		return true;
	}
}
