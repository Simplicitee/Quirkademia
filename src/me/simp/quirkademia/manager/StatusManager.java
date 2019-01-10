package me.simp.quirkademia.manager;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import me.simp.quirkademia.QuirkPlugin;
import me.simp.quirkademia.quirk.QuirkUser;
import me.simp.quirkademia.quirk.QuirkUser.QuirkUserStatus;
import me.simp.quirkademia.quirk.QuirkUser.StatusEffect;

public class StatusManager implements Manager {
	
	private Map<QuirkUser, Long> checks;
	
	public StatusManager(QuirkPlugin plugin) {
		checks = new HashMap<>();
		plugin.getManagersRunnable().registerManager(this);
	}

	@Override
	public void run() {
		for (QuirkUser user : QuirkUser.getOnlineUsers()) {
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
		}
	}
	
	public void register(QuirkUser user) {
		checks.put(user, System.currentTimeMillis());
	}
}
