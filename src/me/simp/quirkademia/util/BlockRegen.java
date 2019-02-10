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
	private boolean flowOut, flowIn, affectLevels;
	
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
		this.flowOut = false;
		this.flowIn = false;
		this.affectLevels = false;
		
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
	
	public boolean canAffectLevels() {
		return affectLevels;
	}
	
	public boolean canFlowIn() {
		return flowIn;
	}
	
	public boolean canFlowOut() {
		return flowOut;
	}
	
	public void revert() {
		block.setType(old.getType());
	}
	
	public BlockRegen setAffectLevels(boolean affectLevels) {
		this.affectLevels = affectLevels;
		return this;
	}
	
	public BlockRegen setFlowIn(boolean flowIn) {
		this.flowIn = flowIn;
		return this;
	}
	
	public BlockRegen setFlowOut(boolean flowOut) {
		this.flowOut = flowOut;
		return this;
	}
}
