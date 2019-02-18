package me.simp.quirkademia.quirk.zerogravity;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.FallingBlock;

import me.simp.quirkademia.ability.QuirkAbility;
import me.simp.quirkademia.quirk.QuirkUser;

public class FloatBlock extends QuirkAbility {

	public FloatBlock(QuirkUser user) {
		super(user);
		
		Block b = plugin.getSelectionManager().getSelectedBlock(user);
		
		if (b == null) {
			return;
		} else if (b.getType().isInteractable() || b.isPassable()) {
			return;
		} else if (!methods.isAir(b.getRelative(BlockFace.UP))) {
			return;
		}
		
		if (manager.hasAbility(user, Floaty.class)) {
			BlockState state = b.getState();
			
			if (state.getType().getHardness() < 0) {
				return;
			}
			
			b.setType(Material.AIR);
			
			Floaty passive = manager.getAbility(user, Floaty.class);
			int weight = (int) state.getType().getHardness() * 50;
			
			if (!passive.canFloat(weight)) {
				return;
			}
			
			FallingBlock fb = b.getWorld().spawnFallingBlock(b.getLocation().clone().add(0.5, 0.3, 0.5), state.getBlockData());
			fb.setGravity(false);
			
			passive.makeFloat(fb, weight);
		}
	}

	@Override
	public Location getLocation() {
		return null;
	}

	@Override
	public boolean progress() {
		return false;
	}

	@Override
	public void onStart() {
		
	}

	@Override
	public void onRemove() {
		
	}

}
