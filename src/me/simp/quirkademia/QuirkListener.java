package me.simp.quirkademia;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;

import me.simp.quirkademia.configuration.ConfigType;
import me.simp.quirkademia.quirk.Quirk;
import me.simp.quirkademia.quirk.QuirkUser;
import me.simp.quirkademia.util.ActivationType;
import net.md_5.bungee.api.ChatColor;

public class QuirkListener implements Listener {
	
	private QuirkPlugin plugin;
	
	public QuirkListener(QuirkPlugin plugin) {
		this.plugin = plugin;
		this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
	}
	
	public QuirkPlugin getPlugin() {
		return plugin;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerLoginEvent event) {
		QuirkUser.login(event.getPlayer().getUniqueId());
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		QuirkUser.logout(event.getPlayer().getUniqueId());
	}
	
	@EventHandler
	public void onPlayerKick(PlayerKickEvent event) {
		QuirkUser.logout(event.getPlayer().getUniqueId());
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		if (event.isCancelled()) {
			return;
		}
		
		Player player = event.getPlayer();
		QuirkUser user = QuirkUser.from(player.getUniqueId());
		String format = plugin.getConfigs().get(ConfigType.CHAT).get().getString("Chat.Format");
		
		format = format.replace("{quirkcolor}", "" + user.getQuirk().getChatColor());
		format = format.replace("{quirk}", user.getQuirk().getName());
		format = format.replace("{player}", "%1$2s");
		format = format.replace("{message}", "%1$2s");
		
		event.setFormat(ChatColor.translateAlternateColorCodes('&', format));
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerSneak(PlayerToggleSneakEvent event) {
		if (event.isCancelled()) {
			return;
		}
		
		Player player = event.getPlayer();
		ActivationType type = player.isSneaking() ? ActivationType.SNEAK_UP : ActivationType.SNEAK_DOWN;
		QuirkUser user = QuirkUser.from(player.getUniqueId());
		
		activateAbility(user, type);
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerClick(PlayerAnimationEvent event) {
		if (event.isCancelled()) {
			return;
		}
		
		Player player = event.getPlayer();
		ActivationType type = player.isSneaking() ? ActivationType.LEFT_CLICK_SNEAKING : ActivationType.LEFT_CLICK;
		QuirkUser user = QuirkUser.from(player.getUniqueId());
		
		activateAbility(user, type);
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerRightClickEntity(PlayerInteractEntityEvent event) {
		if (event.isCancelled()) {
			return;
		}
		
		Player player = event.getPlayer();
		ActivationType type = player.isSneaking() ? ActivationType.RIGHT_CLICK_ENTITY_SNEAKING : ActivationType.RIGHT_CLICK_ENTITY;
		QuirkUser user = QuirkUser.from(player.getUniqueId());
		
		activateAbility(user, type);
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
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
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerOffhandToggle(PlayerSwapHandItemsEvent event) {
		if (event.isCancelled()) {
			return;
		}
		
		Player player = event.getPlayer();
		ActivationType type = player.isSneaking() ? ActivationType.OFFHAND_TRIGGER_SNEAKING : player.isSprinting() ? ActivationType.OFFHAND_TRIGGER_SPRINTING : ActivationType.OFFHAND_TRIGGER;
		QuirkUser user = QuirkUser.from(player.getUniqueId());
		
		activateAbility(user, type);
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
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
