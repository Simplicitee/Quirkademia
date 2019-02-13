package me.simp.quirkademia.quirk.creation;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.simp.quirkademia.QuirkPlugin;
import me.simp.quirkademia.manager.Manager;

public class CreationRecipeManager extends Manager {
	
	private Set<CreationRecipe> recipes;

	public CreationRecipeManager(QuirkPlugin plugin) {
		super(plugin);
		
		this.recipes = new HashSet<>();
		this.init();
	}
	
	public void init() {
		register(new Material[] {Material.AIR, Material.COOKIE, Material.AIR, Material.AIR, Material.COOKIE, Material.AIR, Material.AIR, Material.COOKIE, Material.AIR}, new ItemStack(Material.IRON_SWORD));
	}

	public class CreationRecipe {

		private Material[] materialOrder;
		private ItemStack craft;
		
		private CreationRecipe(Material[] materialOrder, ItemStack craft) {
			this.materialOrder = materialOrder;
			this.craft = craft;
		}
		
		public Material[] getMaterialOrder() {
			return materialOrder;
		}
		
		public ItemStack getCrafted() {
			return craft;
		}
	}
	
	public ItemStack getCraftableItem(ItemStack[] order) {
		Material[] materialOrder = new Material[order.length];
		
		for (int i = 0; i < order.length; i++) {
			if (order[i] == null) {
				materialOrder[i] = Material.AIR;
			} else {
				materialOrder[i] = order[i].getType();
			}
		}
		
		ItemStack craftable = null;
		
		loop: for (CreationRecipe recipe : recipes) {
			for (int i = 0; i < 9; i++) {
				if (recipe.getMaterialOrder()[i] != materialOrder[i]) {
					continue loop;
				}
			}
			
			craftable = recipe.getCrafted();
			break;
		}
		
		return craftable;
	}
	
	public CreationRecipe register(Material[] materialOrder, ItemStack craft) {
		for (int i = 0; i < materialOrder.length; i++) {
			if (materialOrder[i] == null) {
				materialOrder[i] = Material.AIR;
			}
		}
		
		CreationRecipe recipe = new CreationRecipe(materialOrder, craft);
		
		recipes.add(recipe);
		
		return recipe;
	}

	@Override
	public void run() {}
}
