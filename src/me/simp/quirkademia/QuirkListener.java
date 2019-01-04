package me.simp.quirkademia;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;

import me.simp.quirkademia.quirk.Quirk;
import me.simp.quirkademia.quirk.QuirkUser;
import me.simp.quirkademia.util.ActivationType;

public class QuirkListener implements Listener {

	@EventHandler
	public void onPlayerSneak(PlayerToggleSneakEvent event) {
		if (event.isCancelled()) {
			return;
		}
		
		Player player = event.getPlayer();
		ActivationType type = player.isSneaking() ? ActivationType.SNEAK_UP : ActivationType.SNEAK_DOWN;
		QuirkUser user = QuirkUser.from(player.getUniqueId());
		
		activateAbility(user, type);
	}
	
	@EventHandler
	public void onPlayerClick(PlayerAnimationEvent event) {
		if (event.isCancelled()) {
			return;
		}
		
		Player player = event.getPlayer();
		ActivationType type = player.isSneaking() ? ActivationType.LEFT_CLICK_SNEAKING : ActivationType.LEFT_CLICK;
		QuirkUser user = QuirkUser.from(player.getUniqueId());
		
		activateAbility(user, type);
	}
	
	@EventHandler
	public void onPlayerRightClickEntity(PlayerInteractEntityEvent event) {
		if (event.isCancelled()) {
			return;
		}
		
		Player player = event.getPlayer();
		ActivationType type = player.isSneaking() ? ActivationType.RIGHT_CLICK_ENTITY_SNEAKING : ActivationType.RIGHT_CLICK_ENTITY;
		QuirkUser user = QuirkUser.from(player.getUniqueId());
		
		activateAbility(user, type);
	}
	
	@EventHandler
	public void onPlayerToggleSprint(PlayerToggleSprintEvent event) {
		if (event.isCancelled()) {
			return;
		}
		
		Player player = event.getPlayer();
		
		if (player.isSprinting()) {
			return;
		}
		
		ActivationType type = ActivationType.TOGGLE_SPRINT;
		QuirkUser user = QuirkUser.from(player.getUniqueId());
		
		activateAbility(user, type);
	}
	
	@EventHandler
	public void onPlayerOffhandToggle(PlayerSwapHandItemsEvent event) {
		if (event.isCancelled()) {
			return;
		}
		
		Player player = event.getPlayer();
		ActivationType type = ActivationType.OFFHAND_TRIGGER;
		QuirkUser user = QuirkUser.from(player.getUniqueId());
		
		activateAbility(user, type);
	}
	
	@EventHandler
	public void onPlayerRightClickBlock(PlayerInteractEvent event) {
		if (event.isCancelled()) {
			return;
		}
		
		Player player = event.getPlayer();
		ActivationType type = player.isSneaking() ? ActivationType.RIGHT_CLICK_BLOCK_SNEAKING : ActivationType.RIGHT_CLICK_BLOCK;
		QuirkUser user = QuirkUser.from(player.getUniqueId());
		
		activateAbility(user, type);
	}
	
	public void activateAbility(QuirkUser user, ActivationType type) {
		if (user == null) {
			return;
		}
		
		Quirk quirk = user.getQuirk();
		
		if (quirk == null) {
			return;
		}
		
		if (quirk.hasActivationType(type)) {
			quirk.createAbilityInstance(user, type);
		}
	}
}
