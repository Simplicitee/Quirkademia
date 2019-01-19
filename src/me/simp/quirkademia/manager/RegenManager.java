package me.simp.quirkademia.manager;

import java.util.Comparator;
import java.util.Map;
import java.util.PriorityQueue;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;

import me.simp.quirkademia.QuirkPlugin;
import me.simp.quirkademia.ability.QuirkAbility;
import me.simp.quirkademia.configuration.ConfigType;
import me.simp.quirkademia.util.BlockRegen;

public class RegenManager implements Manager {

	private QuirkPlugin plugin;
	private Map<Block, PriorityQueue<BlockRegen>> blocks;
	
	public RegenManager(QuirkPlugin plugin) {
		this.plugin = plugin;
		
		plugin.getManagersRunnable().registerManager(this);
	}

	@Override
	public void run() {
		for (Block b : blocks.keySet()) {
			PriorityQueue<BlockRegen> queue = blocks.get(b);
			
			while (!queue.isEmpty()) {
				BlockRegen regen = queue.peek();
				
				if (System.currentTimeMillis() > regen.getCreationTime() + regen.getRegenTime()) {
					regen.revert();
					queue.poll();
				} else {
					break;
				}
			}
		}
	}
	
	public BlockRegen regenerator(Block block, Material newType, QuirkAbility creator) {
		return regenerator(block, newType, creator, plugin.getConfigs().getConfiguration(ConfigType.PROPERTIES).getLong("BlockRegen.RegenTime"));
	}
	
	public BlockRegen regenerator(Block block, Material newType, QuirkAbility creator, long regenTime) {
		PriorityQueue<BlockRegen> queue;
		BlockRegen regen;
		
		if (!blocks.containsKey(block)) {
			queue = new PriorityQueue<>(10, new Comparator<BlockRegen>() {

				@Override
				public int compare(BlockRegen o1, BlockRegen o2) {
					return (int) (o1.getCreationTime() - o2.getCreationTime());
				}
				
			});
			
			blocks.put(block, queue);
			
			regen = new BlockRegen(block, newType, creator, regenTime);
		} else {
			queue = blocks.get(block);
			
			BlockState original = queue.peek().getOldState();
			
			regen = new BlockRegen(block, original, newType, creator, regenTime);
		}
		
		queue.add(regen);
		
		return regen;
	}
}
