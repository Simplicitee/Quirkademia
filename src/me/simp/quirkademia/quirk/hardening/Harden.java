package me.simp.quirkademia.quirk.hardening;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.util.Vector;

import me.simp.quirkademia.ability.QuirkAbility;
import me.simp.quirkademia.configuration.ConfigType;
import me.simp.quirkademia.quirk.QuirkUser;
import me.simp.quirkademia.util.ActivationType;
import me.simp.quirkademia.util.ParticleEffect;
import me.simp.quirkademia.util.StatusEffect;

public class Harden extends QuirkAbility {
	
	private int strength, endurance;
	private int stamina;
	private int unbreakable;
	private boolean hasUnbreakable;
	private Hardening passive;

	public Harden(QuirkUser user) {
		super(user);
		
		if (manager.hasAbility(user, HardenRecharge.class)) {
			return;
		}
		
		if (manager.hasAbility(user, Harden.class)) {
			manager.remove(manager.getAbility(user, Harden.class));
			return;
		}
		
		if (!manager.hasAbility(user, Hardening.class)) {
			return;
		}
		
		passive = manager.getAbility(user, Hardening.class);
		
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
		if (!passive.use(stamina)) {
			return false;
		}
		
		ParticleEffect.CRIT.display(player.getLocation().clone().add(0, 1, 0), 3, 0.2, 0.55, 0.2);
		
		if (player.isSprinting()) {
			Vector direction = player.getEyeLocation().getDirection().clone();
			double power = direction.length() * 2 * (hasUnbreakable ? unbreakable : 1);
			
			BlockFace facing = player.getFacing();
			Block top = player.getEyeLocation().getBlock().getRelative(facing);
			Block bottom = player.getLocation().getBlock().getRelative(facing);
			
			if (top.getType().getHardness() != -1) {
				if (top.getType().getHardness() < power && methods.isAir(top.getRelative(facing, (int) power))) {
					top.breakNaturally();
				}
			}
			
			if (top.getType().getHardness() != -1) {
				if (bottom.getType().getHardness() < power && methods.isAir(top.getRelative(facing, (int) power))) {
					bottom.breakNaturally();
				}
			}
		}
		
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
		if (user.getQuirk().equals(plugin.getQuirkManager().getQuirk(HardeningQuirk.class))) {
			user.createAbilityInstance(ActivationType.MANUAL);
		}
	}

	public void activateUnbreakable() {
		methods.sendActionBarMessage("&c!> &6UNBREAKABLE &c<!", player);
		
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
