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
import me.simp.quirkademia.quirk.FusedQuirk;
import me.simp.quirkademia.quirk.Quirk;
import me.simp.quirkademia.quirk.QuirkUser;
import me.simp.quirkademia.quirk.QuirkUser.QuirkUserStatus;
import me.simp.quirkademia.util.ActivationType;
import me.simp.quirkademia.util.Cooldown;
import me.simp.quirkademia.util.StatusEffect;

public class UserManager extends Manager {
	
	private Map<QuirkUser, Long> checks;
	private Map<UUID, QuirkUser> users;
	
	public UserManager(QuirkPlugin plugin) {
		super(plugin);
		
		this.checks = new HashMap<>();
		this.users = new HashMap<>();
	}
	
	@Override
	public void run() {
		for (QuirkUser user : users.values()) {
			if (user.getQuirk() == null) {
				continue;
			}
			
			Player player = Bukkit.getPlayer(user.getUniqueId());
			QuirkUserStatus status = user.getStatus();
			
			if (player.isOnline() && !player.isDead()) {
				user.createAbilityInstance(ActivationType.PASSIVE);
			}
			
			for (StatusEffect effect : status.getEffects()) {
				if (effect.getPotion() != null) {
					player.addPotionEffect(new PotionEffect(effect.getPotion(), 5, status.getPower(effect), true, false), true);
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
	
	public QuirkUser getUser(UUID uuid) {
		return login(uuid);
	}
	
	public QuirkUser login(UUID uuid) {
		if (users.containsKey(uuid)) {
			return users.get(uuid);
		}
		
		QuirkUser user;
		Map<String, String> storage = QuirkPlugin.get().getStorageManager().get().load(uuid);
		
		if (storage.isEmpty()) {
			Quirk quirk = null;
			
			if (plugin.getConfigs().getConfiguration(ConfigType.PROPERTIES).getBoolean("AutoAssign.Enabled")) {
				quirk = randomAssign();
			}
			
			user = new QuirkUser(uuid, quirk);
		} else {
			Quirk quirk = plugin.getQuirkManager().getQuirk(storage.get("quirk"));
			
			if (quirk == null && plugin.getConfigs().getConfiguration(ConfigType.PROPERTIES).getBoolean("AutoAssign.Enabled")) {
				quirk = randomAssign();
			}
			
			user = new QuirkUser(uuid, quirk);
			
			for (int i = 0; i < 9; i++) {
				String key = "Slot" + (i + 1);
				if (storage.containsKey(key)) {
					user.setBind(i, plugin.getQuirkManager().getQuirk(storage.get(key)));
				}
			}
			
			if (plugin.getConfigs().getConfiguration(ConfigType.PROPERTIES).getBoolean("Storage.SaveCooldowns")) {
				for (String key : storage.keySet()) {
					if (key.startsWith("cooldown.")) {
						user.addCooldown(key.substring(10), Long.valueOf(storage.get(key)));
					}
				}
			}
		}
		
		checks.put(user, System.currentTimeMillis());
		users.put(uuid, user);
		
		return user;
	}
	
	public void logout(UUID uuid) {
		QuirkUser user = getUser(uuid);
		
		if (user != null) {
			QuirkPlugin.get().getStorageManager().get().store(user);
			
			users.remove(uuid);
			checks.remove(user);
		}
	}
	
	private Quirk randomAssign() {
		Random r = new Random();
		List<Quirk> list = new ArrayList<>();
		
		for (Quirk quirk : plugin.getQuirkManager().getQuirks()) {
			if (quirk instanceof FusedQuirk && !plugin.getConfigs().getConfiguration(ConfigType.PROPERTIES).getBoolean("AutoAssign.Fusions")) {
				continue;
			}
			
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
