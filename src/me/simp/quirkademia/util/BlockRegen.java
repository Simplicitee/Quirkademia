package me.simp.quirkademia.util;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;

import me.simp.quirkademia.ability.QuirkAbility;

public class BlockRegen {

	private Block block;
	private BlockState old;
	private BlockData newData;
	private QuirkAbility creator;
	private long regenTime, createTime;
	
	public BlockRegen(Block block, BlockData newData, QuirkAbility creator, long regenTime) {
		this(block, block.getState(), newData, creator, regenTime);
	}
	
	public BlockRegen(Block block, BlockState old, BlockData newData, QuirkAbility creator, long regenTime) {
		this.block = block;
		this.old = old;
		this.newData = newData;
		this.creator = creator;
		this.regenTime = regenTime;
		this.createTime = System.currentTimeMillis();
		
		this.block.setBlockData(newData);
	}
	
	public Block getBlock() {
		return block;
	}
	
	public BlockData getData() {
		return newData;
	}
	
	public BlockState getOldState() {
		return old;
	}
	
	public QuirkAbility getCreator() {
		return creator;
	}
	
	public long getRegenTime() {
		return regenTime;
	}
	
	public long getCreationTime() {
		return createTime;
	}
	
	public void revert() {
		block.setType(old.getType());
	}
}
