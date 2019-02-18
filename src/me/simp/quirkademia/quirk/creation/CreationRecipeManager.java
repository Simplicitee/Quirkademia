package me.simp.quirkademia.quirk.creation;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

import me.simp.quirkademia.QuirkPlugin;
import me.simp.quirkademia.manager.Manager;

public class CreationRecipeManager extends Manager {
	
	private Set<CreationRecipe> recipes;

	public CreationRecipeManager(QuirkPlugin plugin) {
		super(plugin);
		
		this.recipes = new HashSet<>();
	}
	
	public void init() {
		for (Material m : Material.values()) {
			if (!m.isBlock() && !m.isEdible() && !m.isFuel() && m.isItem()) {
				Recipe r = plugin.getServer().getRecipesFor(new ItemStack(m)).get(0);
				
				if (r instanceof ShapedRecipe) {
					ShapedRecipe sr = (ShapedRecipe) r;
					ItemStack[] order = new ItemStack[9];
					
					int i = 0;
					for (int j = 0; j < 3; j++) {
						for (int k = 0; k < 3; k++) {
							if (sr.getShape()[j].length() > k) {
								order[i] = sr.getIngredientMap().get(sr.getShape()[j].charAt(k));
							} else {
								order[i] = null;
							}
							i++;
						}
					}
					
					register(order, sr.getResult());
				}
			}
		}
	}

	public class CreationRecipe {

		private ItemStack[] order;
		private ItemStack craft;
		
		private CreationRecipe(ItemStack[] order, ItemStack craft) {
			this.order = order;
			this.craft = craft;
		}
		
		public ItemStack[] getIngredients() {
			return order;
		}
		
		public ItemStack getCrafted() {
			return craft;
		}
	}
	
	public ItemStack getCraftableItem(ItemStack[] order) {
		ItemStack craftable = null;
		
		loop: for (CreationRecipe recipe : recipes) {
			for (int i = 0; i < 9; i++) {
				ItemStack ingredient = recipe.getIngredients()[i];
				if (ingredient == null && order[i] != null) {
					continue loop;
				} else if (ingredient != null && order[i] == null) {
					continue loop;
				} else if (ingredient == null && order[i] == null) {
					continue;
				} else if (ingredient.isSimilar(order[i]) && ingredient.getAmount() == order[i].getAmount()) {
					continue;
				} else {
					continue loop;
				}
			}
			
			craftable = recipe.getCrafted();
			break;
		}
		
		return craftable;
	}
	
	public CreationRecipe register(ItemStack[] order, ItemStack craft) {
		CreationRecipe recipe = new CreationRecipe(order, craft);
		
		recipes.add(recipe);
		
		return recipe;
	}

	@Override
	public void run() {}
}
