package me.simp.quirkademia.manager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.simp.quirkademia.QuirkPlugin;
import me.simp.quirkademia.ability.QuirkAbilityInfo;
import me.simp.quirkademia.quirk.QuirkUser;
import me.simp.quirkademia.util.ActivationType;

public class PassiveManager implements Manager {
	
	public QuirkPlugin plugin;
	
	public PassiveManager(QuirkPlugin plugin) {
		this.plugin = plugin;
		
		plugin.getManagersRunnable().registerManager(this);
	}

	@Override
	public void run() {
		for (QuirkUser user : QuirkUser.getOnlineUsers()) {
			if (user.getQuirk().hasActivationType(ActivationType.PASSIVE)) {
				QuirkAbilityInfo info = user.getQuirk().getAbilities().get(ActivationType.PASSIVE);
				
				if (!plugin.getAbilityManager().hasAbility(user, info.getAbilityClass())) {
					Player player = Bukkit.getPlayer(user.getUniqueId());
					
					if (player == null) {
						QuirkUser.logout(user.getUniqueId());
						continue;
					}
					
					if (player.isOnline() && !player.isDead()) {
						user.getQuirk().createAbilityInstance(user, ActivationType.PASSIVE);
					}
				}
			}
		}
	}

}
