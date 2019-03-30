package me.simp.quirkademia.quirk.creation;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

import me.simp.quirkademia.ability.QuirkAbilityInfo;
import me.simp.quirkademia.quirk.Quirk;
import me.simp.quirkademia.quirk.QuirkType;
import me.simp.quirkademia.quirk.QuirkUser;
import me.simp.quirkademia.util.ActivationType;

public class CreationQuirk extends Quirk {

	public CreationQuirk() {
		super("Creation", QuirkType.EMITTER);
	}

	@Override
	public String getDescription() {
		return "Momo Yaoyorozu's quirk is Creation! She can convert the lipids in her body into other chemical compounds to create a wide variety of inanimate objects!";
	}

	@Override
	public Set<QuirkAbilityInfo> registerAbilities() {
		Set<QuirkAbilityInfo> register = new HashSet<>();
		register.add(new QuirkAbilityInfo(ActivationType.PASSIVE, BodyLipids.class, this, "Body Lipids", "This keeps track of the lipids in your body!", "Passively active"));
		register.add(new QuirkAbilityInfo(ActivationType.OFFHAND_TRIGGER_SNEAKING, Creation.class, this, "Creation", "Use your body's lipids to create items!", "Press the offhand trigger while sneaking"));
		return register;
	}

	@EventHandler
	public void onPlayerEat(PlayerItemConsumeEvent event) {
		Player player = event.getPlayer();
		QuirkUser user = plugin.getUserManager().getUser(player.getUniqueId());
		
		if (user == null) {
			return;
		}
		
		if (!user.hasQuirk(this)) {
			return;
		}
		
		if (!plugin.getAbilityManager().hasAbility(user, BodyLipids.class)) {
			return;
		}
		
		plugin.getAbilityManager().getAbility(user, BodyLipids.class).restoreLipids();
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (!(event.getWhoClicked() instanceof Player)) {
			return;
		}
		
		Player player = (Player) event.getWhoClicked();
		QuirkUser user = plugin.getUserManager().getUser(player.getUniqueId());
		
		if (user == null) {
			return;
		}
		
		if (!plugin.getAbilityManager().hasAbility(user, Creation.class)) {
			return;
		}
		
		if (event.getClickedInventory() == null) {
			return;
		}
		
		if (event.getSlot() == 0) {
			ItemStack item = event.getCurrentItem();
			
			if (item != null && item.getType() != Material.AIR) {
				plugin.getAbilityManager().getAbility(user, Creation.class).craftItem(item);
				event.setCancelled(true);
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
					event.getInventory().clear();
					plugin.getAbilityManager().remove(plugin.getAbilityManager().getAbility(user, Creation.class));
				}
			}
		}
	}
}
