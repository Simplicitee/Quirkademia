package me.simp.quirkademia.event;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import me.simp.quirkademia.ability.QuirkAbility;

public class QuirkAbilityCollisionEvent extends Event implements Cancellable {

	private static final HandlerList HANDLERS = new HandlerList();
	
	private boolean cancelled;
	private QuirkAbility a, b;
	private boolean removeFirst, removeSecond;
	
	public QuirkAbilityCollisionEvent(QuirkAbility a, QuirkAbility b) {
		this.a = a;
		this.b = b;
		this.removeFirst = false;
		this.removeFirst = false;
	}
	
	public QuirkAbility getFirstCollider() {
		return a;
	}
	
	public QuirkAbility getSecondCollider() {
		return b;
	}
	
	public boolean getRemoveFirst() {
		return removeFirst;
	}
	
	public boolean getRemoveSecond() {
		return removeSecond;
	}
	
	public QuirkAbilityCollisionEvent setRemoveFirst(boolean remove) {
		this.removeFirst = remove;
		return this;
	}
	
	public QuirkAbilityCollisionEvent setRemoveSecond(boolean remove) {
		this.removeSecond = remove;
		return this;
	}
	
	public static HandlerList getHanderList() {
		return HANDLERS;
	}
	
	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancel) {
		this.cancelled = cancel;
	}

}
