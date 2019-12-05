package me.simp.quirkademia.quirk;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.simp.quirkademia.QuirkPlugin;
import me.simp.quirkademia.ability.AbilityBoard;
import me.simp.quirkademia.ability.QuirkAbility;
import me.simp.quirkademia.ability.QuirkAbilityInfo;
import me.simp.quirkademia.object.BindsBoard;
import me.simp.quirkademia.util.ActivationType;
import me.simp.quirkademia.util.Cooldown;
import me.simp.quirkademia.util.DisplayBoard;
import me.simp.quirkademia.util.StatusEffect;

public class QuirkUser {
	
	private UUID uuid;
	private Quirk quirk;
	private QuirkUserStatus status;
	private DisplayBoard board;
	private boolean disabled;
	private Map<String, Cooldown> cooldowns;
	private Map<Integer, Quirk> binds;
	
	public QuirkUser(UUID uuid, Quirk quirk) {
		this.uuid = uuid;
		this.quirk = quirk;
		
		if (quirk != null) {
			if (quirk instanceof FusedQuirk) {
				this.board = new BindsBoard(this);
			} else {
				this.board = new AbilityBoard(this);
			}
		}
		
		initializePassives();
		
		this.status = new QuirkUserStatus();
		this.disabled = false;
		this.cooldowns = new HashMap<>();
		this.binds = new HashMap<>();
	}
	
	public UUID getUniqueId() {
		return uuid;
	}
	
	public Quirk getQuirk() {
		return quirk;
	}
	
	public QuirkUser setQuirk(Quirk quirk) {
		if (quirk == null) {
			return this;
		}
		
		this.quirk = quirk;
		
		if (this.board != null) {
			this.board.destroy();
		}
		
		if (quirk instanceof FusedQuirk) {
			this.board = new BindsBoard(this);
		} else {
			this.board = new AbilityBoard(this);
		}
		
		this.binds.clear();
		
		return this;
	}
	
	public QuirkUserStatus getStatus() {
		return status;
	}
	
	public QuirkUser disableQuirk() {
		this.disabled = true;
		return this;
	}
	
	public QuirkUser enableQuirk() {
		this.disabled = false;
		return this;
	}
	
	public boolean isQuirkDisabled() {
		return disabled;
	}
	
	public boolean hasCooldown(String name) {
		return cooldowns.containsKey(name);
	}
	
	public QuirkUser addCooldown(String name, long cooldown) {
		if (hasCooldown(name)) {
			return this;
		}
		
		Cooldown cd = new Cooldown(System.currentTimeMillis(), cooldown);
		cooldowns.put(name, cd);
		return this;
	}
	
	public Map<String, Cooldown> getCooldowns() {
		return cooldowns;
	}
	
	public boolean hasBind(int slot) {
		if (binds == null || binds.isEmpty()) {
			return false;
		}
		
		return binds.containsKey(slot);
	}
	
	public Quirk getBind(int slot) {
		if (hasBind(slot)) {
			return binds.get(slot);
		}
		
		return null;
	}
	
	public QuirkUser setBind(int slot, Quirk quirk) {
		if (slot < 0 || slot > 9) {
			throw new IllegalArgumentException("Slot number must be within range 0 - 9 inclusively.");
		}
		
		binds.put(slot, quirk);
		return this;
	}
	
	public QuirkUser clearBind(int slot) {
		binds.remove(slot);
		return this;
	}
	
	public QuirkUser clearBinds() {
		binds.clear();
		return this;
	}
	
	public QuirkAbility createAbilityInstance(ActivationType type) {
		Quirk active = quirk;
		if (quirk instanceof FusedQuirk) {
			Player p = Bukkit.getPlayer(uuid);
			active = getBind(p.getInventory().getHeldItemSlot());
		}
		
		return active.activateAbility(this, type);
	}
	
	public void initializePassives() {
		if (quirk instanceof FusedQuirk) {
			for (Quirk q : ((FusedQuirk) quirk).getQuirks()) {
				if (!q.hasActivationType(ActivationType.PASSIVE)) {
					continue;
				}
				
				QuirkAbilityInfo info = q.getAbilities().get(ActivationType.PASSIVE);
				
				if (QuirkPlugin.get().getAbilityManager().hasAbility(this, info.getProvider())) {
					continue;
				}
				
				if (!canUseAbility(info)) {
					continue;
				}
				
				try {
					Constructor<?> construct = info.getProvider().getConstructor(QuirkUser.class);
					construct.newInstance(this);
				} catch (Exception e) {}
			}
		} else {
			quirk.activateAbility(this, ActivationType.PASSIVE);
		}
	}
	
	public boolean hasQuirk(Quirk check) {
		if (quirk instanceof FusedQuirk) {
			if (((FusedQuirk) quirk).getQuirks().contains(check)) {
				return true;
			}
		}
		
		return quirk.equals(check);
	}
	
	public boolean canUseAbility(final QuirkAbilityInfo info) {
		if (info == null) {
			return false;
		} else if (isQuirkDisabled()) {
			return false;
		} else if (getStatus().has(StatusEffect.QUIRK_ERASED)) {
			return false;
		} else if (!hasQuirk(info.getQuirk())) {
			return false;
		}
		
		return true;
	}
	
	public class QuirkUserStatus {
		
		private Map<StatusEffect, Integer> active;
		
		private QuirkUserStatus() {
			active = new HashMap<>();
		}
		
		public boolean has(StatusEffect effect) {
			return active.containsKey(effect);
		}
		
		public void add(StatusEffect effect, int power) {
			if (has(effect) && power < active.get(effect)) {
				return;
			}
			
			active.put(effect, power);
		}
		
		public void remove(StatusEffect effect) {
			active.remove(effect);
		}
		
		public int getPower(StatusEffect effect) {
			return has(effect) ? active.get(effect) : 0;
		}
		
		public Set<StatusEffect> getEffects() {
			return active.keySet();
		}
	}
}
