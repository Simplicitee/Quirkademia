package me.simp.quirkademia.util;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;

import me.simp.quirkademia.ability.QuirkAbility;

public class BlockRegen {

	private Block block;
	private BlockState old;
	private Material newType;
	private QuirkAbility creator;
	private long regenTime, createTime;
	
	public BlockRegen(Block block, Material newType, QuirkAbility creator, long regenTime) {
		this(block, block.getState(), newType, creator, regenTime);
	}
	
	public BlockRegen(Block block, BlockState old, Material newType, QuirkAbility creator, long regenTime) {
		this.block = block;
		this.old = old;
		this.newType = newType;
		this.creator = creator;
		this.regenTime = regenTime;
		this.createTime = System.currentTimeMillis();
	}
	
	public Block getBlock() {
		return block;
	}
	
	public Material getType() {
		return newType;
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
