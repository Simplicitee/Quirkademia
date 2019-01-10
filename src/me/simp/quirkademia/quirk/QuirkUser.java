package me.simp.quirkademia.quirk;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.potion.PotionEffectType;

import me.simp.quirkademia.quirk.oneforall.OneForAllQuirk;
import me.simp.quirkademia.util.Cooldown;

public class QuirkUser {
	
	private static final Map<UUID, QuirkUser> USERS = new HashMap<>();
	
	private UUID uuid;
	private Quirk quirk;
	private QuirkStamina stamina;
	private QuirkUserStatus status;
	private boolean disabled;
	private Map<String, Cooldown> cooldowns;
	
	public QuirkUser(UUID uuid, Quirk quirk) {
		this.uuid = uuid;
		this.quirk = quirk;
		this.stamina = new QuirkStamina(this);
		this.status = new QuirkUserStatus();
		this.disabled = false;
		this.cooldowns = new HashMap<>();
		
		USERS.put(uuid, this);
	}
	
	public UUID getUniqueId() {
		return uuid;
	}
	
	public Quirk getQuirk() {
		return quirk;
	}
	
	public QuirkUser setQuirk(Quirk quirk) {
		this.quirk = quirk;
		
		if (this.stamina.getBar() != null) {
			this.stamina.getBar().destroy();
		}
		
		this.stamina = new QuirkStamina(this);
		
		return this;
	}
	
	public QuirkStamina getStamina() {
		return stamina;
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
	
	public static QuirkUser from(UUID uuid) {
		if (USERS.containsKey(uuid)) {
			return USERS.get(uuid);
		}
		
		return null;
	}
	
	public static Set<QuirkUser> getOnlineUsers() {
		Set<QuirkUser> users = new HashSet<>();
		
		for (QuirkUser user : USERS.values()) {
			users.add(user);
		}
		
		return users;
	}
	
	public static QuirkUser login(UUID uuid) {
		if (USERS.containsKey(uuid)) {
			return USERS.get(uuid);
		}
		
		Quirk quirk = Quirk.get(OneForAllQuirk.class);
		
		//TODO: database loading stuff 
		
		return new QuirkUser(uuid, quirk);
	}
	
	public static void logout(UUID uuid) {
		QuirkUser user = from(uuid);
		
		if (user != null) {
			//TODO: database saving stuff
			
			user.getStamina().getBar().destroy();
			USERS.remove(uuid);
		}
	}
		
	public static enum StatusEffect {
		INCREASED_STRENGTH(PotionEffectType.INCREASE_DAMAGE),
		INCREASED_ENDURANCE(PotionEffectType.DAMAGE_RESISTANCE),
		INCREASED_SPEED(PotionEffectType.SPEED), 
		INCREASED_JUMP(PotionEffectType.JUMP), 
		QUIRK_ERASED(null), 
		PARALYZED(PotionEffectType.SLOW), 
		HEALING(PotionEffectType.REGENERATION),
		INVISIBLE(PotionEffectType.INVISIBILITY);
		
		public PotionEffectType type;
		
		private StatusEffect(PotionEffectType type) {
			this.type = type;
		}
		
		public PotionEffectType getPotion() {
			return type;
		}
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
