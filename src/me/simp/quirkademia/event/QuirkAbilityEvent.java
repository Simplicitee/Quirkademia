package me.simp.quirkademia.event;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import me.simp.quirkademia.ability.QuirkAbility;
import me.simp.quirkademia.quirk.QuirkUser;

public class QuirkAbilityEvent extends Event implements Cancellable {
	
	private static final HandlerList HANDLERS = new HandlerList();
	
	private boolean cancel;
	private QuirkUser user;
	private QuirkAbility ability;
	private QuirkAbilityEventType type;
	
	public QuirkAbilityEvent(QuirkUser user, QuirkAbility ability, QuirkAbilityEventType type) {
		this.user = user;
		this.ability = ability;
		this.type = type;
	}
	
	public QuirkAbility getAbility() {
		return ability;
	}
	
	public QuirkAbilityEventType getType() {
		return type;
	}
	
	public QuirkUser getUser() {
		return user;
	}
	
	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}

	public static HandlerList getHanderList() {
		return HANDLERS;
	}

	@Override
	public boolean isCancelled() {
		return cancel;
	}

	@Override
	public void setCancelled(boolean cancel) {
		this.cancel = cancel;
	}
	
	public static enum QuirkAbilityEventType {
		START, PROGRESS, END;
	}
}
