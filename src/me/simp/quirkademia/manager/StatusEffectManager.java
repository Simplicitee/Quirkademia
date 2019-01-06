package me.simp.quirkademia.manager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import me.simp.quirkademia.QuirkPlugin;
import me.simp.quirkademia.quirk.QuirkUser;
import me.simp.quirkademia.quirk.QuirkUser.QuirkUserStatus;
import me.simp.quirkademia.quirk.QuirkUser.StatusEffect;

public class StatusEffectManager implements Manager {
	
	public StatusEffectManager(QuirkPlugin plugin) {
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
		}
	}
}
