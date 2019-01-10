package me.simp.quirkademia.event;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import me.simp.quirkademia.ability.QuirkAbility;

public class QuirkAbilityDamageEntityEvent extends Event implements Cancellable {

	private static final HandlerList HANDLERS = new HandlerList();
	
	private boolean cancel;
	
	private LivingEntity liveEntity;
	private Player playerSource;
	private QuirkAbility ability;
	private double damage;
	
	public QuirkAbilityDamageEntityEvent(LivingEntity liveEntity, Player playerSource, QuirkAbility ability, double damage) {
		this.liveEntity = liveEntity;
		this.playerSource = playerSource;
		this.ability = ability;
		this.damage = damage;
		this.cancel = false;
	}
	
	public LivingEntity getDamaged() {
		return liveEntity;
	}
	
	public Player getDamager() {
		return playerSource;
	}
	
	public QuirkAbility getAbility() {
		return ability;
	}
	
	public double getDamage() {
		return damage;
	}
	
	public void setDamage(double damage) {
		this.damage = damage;
	}
	
	@Override
	public boolean isCancelled() {
		return cancel;
	}

	@Override
	public void setCancelled(boolean cancel) {
		this.cancel = cancel;
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}
	
	public static HandlerList getHandlerList() {
		return HANDLERS;
	}

}
