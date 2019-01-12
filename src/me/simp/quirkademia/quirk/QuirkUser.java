package me.simp.quirkademia.quirk;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.potion.PotionEffectType;

import me.simp.quirkademia.QuirkPlugin;
import me.simp.quirkademia.ability.AbilityBoard;
import me.simp.quirkademia.configuration.ConfigType;
import me.simp.quirkademia.util.Cooldown;

public class QuirkUser {
	
	private static final Map<UUID, QuirkUser> USERS = new HashMap<>();
	
	private UUID uuid;
	private Quirk quirk;
	private QuirkStamina stamina;
	private QuirkUserStatus status;
	private AbilityBoard board;
	private boolean disabled;
	private Map<String, Cooldown> cooldowns;
	
	public QuirkUser(UUID uuid, Quirk quirk, Map<String, Cooldown> map) {
		this.uuid = uuid;
		this.quirk = quirk;
		if (quirk != null) {
			this.stamina = new QuirkStamina(this);
			this.board = new AbilityBoard(this);
		}
		this.status = new QuirkUserStatus();
		this.disabled = false;
		this.cooldowns = map;
		
		USERS.put(uuid, this);
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
		
		if (this.stamina != null && this.stamina.getBar() != null) {
			this.stamina.getBar().destroy();
		}
		
		this.stamina = new QuirkStamina(this);
		
		if (this.board != null) {
			this.board.destroy();
		}
		
		this.board = new AbilityBoard(this);
		
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
		
		Map<String, String> storage = QuirkPlugin.get().getStorageManager().get().load(uuid);
		if (storage.isEmpty()) {
			return new QuirkUser(uuid, null, new HashMap<>());
		}
		
		Quirk quirk = Quirk.get(storage.get("quirk"));
		Map<String, Cooldown> cooldowns = new HashMap<>();
		
		if (QuirkPlugin.get().getConfigs().getConfiguration(ConfigType.PROPERTIES).getBoolean("Storage.SaveCooldowns")) {
			for (String key : storage.keySet()) {
				if (key.startsWith("cooldown.")) {
					cooldowns.put(key.substring(9), new Cooldown(System.currentTimeMillis(), Long.valueOf(storage.get(key))));
				}
			}
		}
		
		return new QuirkUser(uuid, quirk, cooldowns);
	}
	
	public static void logout(UUID uuid) {
		QuirkUser user = from(uuid);
		
		if (user != null) {
			QuirkPlugin.get().getStorageManager().get().store(user);
			
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
