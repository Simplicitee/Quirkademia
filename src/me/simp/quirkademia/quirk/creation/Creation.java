package me.simp.quirkademia.quirk.creation;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.simp.quirkademia.ability.QuirkAbility;
import me.simp.quirkademia.quirk.QuirkUser;

public class Creation extends QuirkAbility {
	
	private BodyLipids passive;
	private Inventory station, old;

	public Creation(QuirkUser user) {
		super(user);
		
		if (!manager.hasAbility(user, BodyLipids.class)) {
			return;
		}
		
		passive = manager.getAbility(user, BodyLipids.class);
		station = newStation();
		old = plugin.getServer().createInventory(null, 36, "");
		old.setContents(player.getInventory().getStorageContents());
		
		player.getInventory().setStorageContents(station.getContents());
		player.updateInventory();
		player.openWorkbench(null, true);
		
		manager.start(this);
	}

	@Override
	public Location getLocation() {
		return player.getLocation();
	}

	@Override
	public boolean progress() {
		return true;
	}

	@Override
	public void onStart() {
		
	}

	@Override
	public void onRemove() {
		player.getInventory().setStorageContents(old.getContents());
		player.updateInventory();
	}
	
	public static enum LipidType {
		IRON("Iron", Material.IRON_INGOT),
		STICK("Stick", Material.STICK),
		LEATHER("Leather", Material.LEATHER);
		
		private String name;
		private Material m;
		
		private LipidType(String name, Material m) {
			this.name = name;
			this.m = m;
		}
		
		public String getName() {
			return name;
		}
		
		public Material getMaterial() {
			return m;
		}
	}
	
	public Inventory getStation() {
		return station;
	}
	
	public void craftItem(ItemStack item) {
		if (passive.useLipids(100)) {
			old.addItem(item);
			player.closeInventory();
			manager.remove(this);
		}
	}

	private Inventory newStation() {
		Inventory inv = plugin.getServer().createInventory(null, 27, "");
		
		inv.setItem(9, lipidItem(LipidType.STICK));
		inv.setItem(10, lipidItem(LipidType.IRON));
		inv.setItem(11, lipidItem(LipidType.LEATHER));
		
		return inv;
	}
	
	public ItemStack lipidItem(LipidType type) {
		ItemStack lipids = new ItemStack(type.getMaterial());
		ItemMeta im = lipids.getItemMeta();
		
		im.setDisplayName(ChatColor.LIGHT_PURPLE + "!> " + type.getName() + " Lipids <!");
		im.setLore(Arrays.asList("Use your body fat to create items with " + type.getName().toLowerCase() + "(s)!"));
		
		lipids.setItemMeta(im);
		lipids.setAmount(9);
		
		return lipids;
	}
}
