package me.simp.quirkademia.quirk;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class QuirkUser {
	
	private static final Map<UUID, QuirkUser> USERS = new HashMap<>();
	
	private UUID uuid;
	private Quirk quirk;
	private QuirkStamina stamina;
	
	public QuirkUser(UUID uuid, Quirk quirk) {
		this.uuid = uuid;
		this.quirk = quirk;
		this.stamina = new QuirkStamina();
		
		USERS.put(uuid, this);
	}
	
	public UUID getUniqueId() {
		return uuid;
	}
	
	public Quirk getQuirk() {
		return quirk;
	}
	
	public QuirkStamina getStamina() {
		return stamina;
	}
	
	public static QuirkUser from(UUID uuid) {
		if (USERS.containsKey(uuid)) {
			return USERS.get(uuid);
		} else {
			return load(uuid);
		}
	}
	
	public static QuirkUser load(UUID uuid) {
		//database stuff later
		return null;
	}
}
