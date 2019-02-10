package me.simp.quirkademia.manager;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.FluidLevelChangeEvent;

import me.simp.quirkademia.QuirkPlugin;
import me.simp.quirkademia.ability.QuirkAbility;
import me.simp.quirkademia.configuration.ConfigType;
import me.simp.quirkademia.util.BlockRegen;

public class RegenManager extends Manager {

	private QuirkPlugin plugin;
	private Map<Block, PriorityQueue<BlockRegen>> blocks;
	
	public RegenManager(QuirkPlugin plugin) {
		super(plugin);
		
		this.plugin = plugin;
		this.blocks = new HashMap<>();
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

	public void close() {
		for (Block b : blocks.keySet()) {
			PriorityQueue<BlockRegen> queue = blocks.get(b);
			
			while (!queue.isEmpty()) {
				queue.poll().revert();
			}
		}
	}
	
	public BlockRegen getBlockRegen(Block block) {
		if (blocks.containsKey(block)) {
			return blocks.get(block).peek();
		}
		
		return null;
	}
	
	public Queue<BlockRegen> getAllBlockRegensAtBlock(Block block) {
		if (blocks.containsKey(block)) {
			return new LinkedList<>(blocks.get(block));
		}
		
		return null;
	}
	
	public BlockRegen regenerator(Block block, Material newType, QuirkAbility creator) {
		return regenerator(block, newType.createBlockData(), creator);
	}
	
	public BlockRegen regenerator(Block block, BlockData newData, QuirkAbility creator) {
		return regenerator(block, newData, creator, plugin.getConfigs().getConfiguration(ConfigType.PROPERTIES).getLong("BlockRegen.RegenTime"));
	}
	
	public BlockRegen regenerator(Block block, BlockData newData, QuirkAbility creator, long regenTime) {
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
			
			regen = new BlockRegen(block, newData, creator, regenTime);
		} else {
			queue = blocks.get(block);
			
			BlockState original = queue.peek().getOldState();
			
			regen = new BlockRegen(block, original, newData, creator, regenTime);
		}
		
		queue.add(regen);
		
		return regen;
	}
	
	public void revert(BlockRegen regen) {
		for (Block b : blocks.keySet()) {
			if (blocks.get(b).contains(regen)) {
				blocks.get(b).remove(regen);
				return;
			}
		}
		
		regen.revert();
	}
	
	@EventHandler
	public void blockFromTo(BlockFromToEvent event) {
		if (event.isCancelled()) {
			return;
		}
		
		BlockRegen regenFrom = getBlockRegen(event.getBlock());
		BlockRegen regenTo = getBlockRegen(event.getToBlock());
		
		if (regenFrom != null) {
			event.setCancelled(regenFrom.canFlowOut());
		}
		
		if (!event.isCancelled() && regenTo != null) {
			event.setCancelled(regenTo.canFlowOut());
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void fluidLevelChange(FluidLevelChangeEvent event) {
		if (event.isCancelled()) {
			return;
		}
		
		BlockRegen regen = getBlockRegen(event.getBlock());
		
		if (regen != null) {
			event.setCancelled(regen.canAffectLevels());
		}
	}
}
