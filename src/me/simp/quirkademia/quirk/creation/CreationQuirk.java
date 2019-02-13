package me.simp.quirkademia.quirk.creation;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.simp.quirkademia.ability.QuirkAbilityInfo;
import me.simp.quirkademia.quirk.Quirk;
import me.simp.quirkademia.quirk.QuirkType;
import me.simp.quirkademia.quirk.QuirkUser;
import me.simp.quirkademia.util.ActivationType;

public class CreationQuirk extends Quirk {
	
	private CreationRecipeManager recipes;

	public CreationQuirk() {
		super("Creation", QuirkType.EMITTER);
		
		recipes = new CreationRecipeManager(plugin);
	}
	
	public CreationRecipeManager getRecipeManager() {
		return recipes;
	}

	@Override
	public String getDescription() {
		return "";
	}

	@Override
	public Set<QuirkAbilityInfo> registerAbilities() {
		Set<QuirkAbilityInfo> register = new HashSet<>();
		register.add(new QuirkAbilityInfo(ActivationType.OFFHAND_TRIGGER_SNEAKING, Creation.class, this, "Creation", "Use your body's lipids to create items!", "Press the offhand trigger while sneaking"));
		return register;
	}

	@EventHandler
	public void onPlayerEat(PlayerItemConsumeEvent event) {
		Player player = event.getPlayer();
		QuirkUser user = plugin.getUserManager().getUser(player.getUniqueId());
		
		if (user != null) {
			if (user.hasQuirk(this)) {
				user.getStamina().setValue(user.getStamina().getValue() + 100);
			}
		}
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getWhoClicked() instanceof Player) {
			Player player = (Player) event.getWhoClicked();
			QuirkUser user = plugin.getUserManager().getUser(player.getUniqueId());
			
			if (user != null) {
				if (plugin.getAbilityManager().hasAbility(user, Creation.class)) {
					Creation abil = plugin.getAbilityManager().getAbility(user, Creation.class);
					
					if (event.getClickedInventory() == null) {
						return;
					}
					
					if (!event.getInventory().equals(abil.getStation())) {
						plugin.getAbilityManager().remove(abil);
						return;
					}
					
					if (!event.getClickedInventory().equals(event.getInventory())) {
						event.setCancelled(true);
					} else {
						if (event.getSlot() % 9 < 3 || event.getSlot() % 9 > 5) {
							if (event.getSlot() == 16) {
								if (event.getCurrentItem().isSimilar(abil.errorItem())) {
									event.setCancelled(true);
								} else {
									new BukkitRunnable() {

										@Override
										public void run() {
											abil.craftItem(event.getCurrentItem());
										}
										
									}.runTaskLater(plugin, 1);
								}
							} else if (event.getSlot() != 10) {
								event.setCancelled(true);
							}
						}
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		if (event.getPlayer() instanceof Player) {
			Player player = (Player) event.getPlayer();
			QuirkUser user = plugin.getUserManager().getUser(player.getUniqueId());
			
			if (user != null) {
				if (plugin.getAbilityManager().hasAbility(user, Creation.class)) {
					plugin.getAbilityManager().remove(plugin.getAbilityManager().getAbility(user, Creation.class));
				}
			}
		}
	}
}
