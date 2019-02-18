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
	private Inventory station;
	private int counter;
	private int[] craftSlots = {3, 4, 5, 12, 13, 14, 21, 22, 23};

	public Creation(QuirkUser user) {
		super(user);
		
		if (!manager.hasAbility(user, BodyLipids.class)) {
			return;
		}
		
		passive = manager.getAbility(user, BodyLipids.class);
		station = newStation();
		counter = 0;
		
		player.openInventory(station);
		
		manager.start(this);
	}

	@Override
	public Location getLocation() {
		return player.getLocation();
	}

	@Override
	public boolean progress() {
		counter++;
		if (counter >= 19) {
			counter = 0;
			
			ItemStack item = checkForCraftableItem();
			
			if (item == null) {
				return true;
			}
			
			station.setItem(16, item);
			player.updateInventory();
		}
		
		return true;
	}

	@Override
	public void onStart() {
		
	}

	@Override
	public void onRemove() {
		
	}
	
	public static enum LipidType {
		IRON("Iron", Material.IRON_INGOT),
		STICK("Stick", Material.STICK);
		
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
		int lipids = 0;
		
		for (int i : craftSlots) {
			if (station.getItem(i) != null && station.getItem(i).getType() == Material.COOKIE) {
				lipids++;
			}
		}
		
		if (passive.useLipids(lipids * 10)) {
			ItemStack inHand = player.getInventory().getItemInMainHand();
			if (inHand != null) {
				player.getInventory().addItem(inHand);
			}
			
			player.getInventory().setItemInMainHand(item);
		}
	}

	private Inventory newStation() {
		Inventory inv = plugin.getServer().createInventory(null, 27, ChatColor.DARK_PURPLE + "Creation Station");
		
		for (int i = 0; i < 27; i++) {
			if (i % 9 < 3 || i % 9 > 5) {
				if (i == 9) { 
					inv.setItem(i, lipidItem(LipidType.STICK));
				} else if (i == 10) {
					inv.setItem(i, lipidItem(LipidType.IRON));
				} else if (i == 16) {
					inv.setItem(i, errorItem());
				} else {
					inv.setItem(i, organizerItem());
				}
			}
		}
		
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
	
	public ItemStack errorItem() {
		ItemStack error = new ItemStack(Material.BARRIER);
		ItemMeta im = error.getItemMeta();
		
		im.setDisplayName(ChatColor.RED + "!> No Item <!");
		im.setLore(Arrays.asList("There is no item to create given the current recipe!"));
		
		error.setItemMeta(im);
		
		return error;
	}
	
	public ItemStack organizerItem() {
		ItemStack organizer = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
		ItemMeta im = organizer.getItemMeta();
		
		im.setDisplayName(ChatColor.GRAY + "!> Organizer <!");
		im.setLore(Arrays.asList("This is used to organize the station!"));
		
		organizer.setItemMeta(im);
		
		return organizer;
	}
	
	public ItemStack checkForCraftableItem() {
		ItemStack[] order = new ItemStack[9];
		
		for (int i = 0; i < 9; i++) {
			order[i] = station.getItem(craftSlots[i]);
		}
		
		return plugin.getQuirkManager().getQuirk(CreationQuirk.class).getRecipeManager().getCraftableItem(order);
	}
}
