package me.simp.quirkademia.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import me.simp.quirkademia.QuirkPlugin;
import me.simp.quirkademia.configuration.ConfigType;
import me.simp.quirkademia.quirk.Quirk;
import me.simp.quirkademia.quirk.QuirkUser;
import me.simp.quirkademia.quirk.QuirkUser.QuirkUserStatus;
import me.simp.quirkademia.util.Cooldown;
import me.simp.quirkademia.util.StatusEffect;

public class UserManager implements Manager {
	
	private QuirkPlugin plugin;
	private Map<QuirkUser, Long> checks;
	private Map<UUID, QuirkUser> users;
	
	public UserManager(QuirkPlugin plugin) {
		this.plugin = plugin;
		this.checks = new HashMap<>();
		this.users = new HashMap<>();
		
		plugin.getManagersRunnable().registerManager(this);
	}
	
	@Override
	public void run() {
		for (QuirkUser user : users.values()) {
			Player player = Bukkit.getPlayer(user.getUniqueId());
			QuirkUserStatus status = user.getStatus();
			
			for (StatusEffect effect : status.getEffects()) {
				if (effect.getPotion() != null) {
					player.addPotionEffect(new PotionEffect(effect.getPotion(), 5, status.getPower(effect), true, false), true);
				}
			}
			
			if (checks.containsKey(user)) {
				if (System.currentTimeMillis() >= checks.get(user) + 1000) {
					int recharge = user.getStamina().getRecharge();
					
					user.getStamina().set(user.getStamina().get() + recharge);
					
					checks.put(user, System.currentTimeMillis());
				}
			}
			
			Iterator<String> keys = user.getCooldowns().keySet().iterator();
			while (keys.hasNext()) {
				Cooldown cd = user.getCooldowns().get(keys.next());
				
				if (cd.getRemaining() <= 0) {
					keys.remove();
				}
			}
		}
	}
	
	public Set<QuirkUser> getOnlineUsers() {
		return new HashSet<>(users.values());
	}
	
	public QuirkPlugin getPlugin() {
		return plugin;
	}
	
	public QuirkUser getUser(UUID uuid) {
		return login(uuid);
	}
	
	public QuirkUser login(UUID uuid) {
		if (users.containsKey(uuid)) {
			return users.get(uuid);
		}
		
		Map<String, String> storage = QuirkPlugin.get().getStorageManager().get().load(uuid);
		
		if (storage.isEmpty()) {
			return new QuirkUser(uuid, null, new HashMap<>());
		}
		
		Quirk quirk = plugin.getQuirkManager().getQuirk(storage.get("quirk"));
		
		if (quirk == null && plugin.getConfigs().getConfiguration(ConfigType.PROPERTIES).getBoolean("AutoAssign.Enabled")) {
			quirk = randomAssign();
		}
		
		Map<String, Cooldown> cooldowns = new HashMap<>();
		
		if (plugin.getConfigs().getConfiguration(ConfigType.PROPERTIES).getBoolean("Storage.SaveCooldowns")) {
			for (String key : storage.keySet()) {
				if (key.startsWith("cooldown.")) {
					cooldowns.put(key.substring(9), new Cooldown(System.currentTimeMillis(), Long.valueOf(storage.get(key))));
				}
			}
		}
		
		QuirkUser user = new QuirkUser(uuid, quirk, cooldowns);
		
		checks.put(user, System.currentTimeMillis());
		users.put(uuid, user);
		
		return user;
	}
	
	public void logout(UUID uuid) {
		QuirkUser user = getUser(uuid);
		
		if (user != null) {
			QuirkPlugin.get().getStorageManager().get().store(user);
			
			user.getStamina().getBar().destroy();
			users.remove(uuid);
			checks.remove(user);
		}
	}
	
	private Quirk randomAssign() {
		Random r = new Random();
		List<Quirk> list = new ArrayList<>();
		
		for (Quirk quirk : plugin.getQuirkManager().getQuirks()) {
			int rarity = plugin.getConfigs().getConfiguration(ConfigType.QUIRKS).getInt(quirk.getName().replace(" ", "") + ".AssignRarity");
			
			if (rarity <= 0) {
				rarity = 1;
			}
			
			if (rarity > 20) {
				rarity = 20;
			}
			
			for (int i = 0; i < rarity; i++) {
				list.add(quirk);
			}
		}
		
		return list.get(r.nextInt(list.size()));
	}
}
