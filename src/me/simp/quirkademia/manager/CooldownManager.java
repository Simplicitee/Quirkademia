package me.simp.quirkademia.manager;

import java.util.HashSet;
import java.util.Set;

import me.simp.quirkademia.QuirkPlugin;
import me.simp.quirkademia.quirk.QuirkUser;
import me.simp.quirkademia.util.Cooldown;

public class CooldownManager implements Manager {

	public CooldownManager(QuirkPlugin plugin) {
		plugin.getManagersRunnable().registerManager(this);
	}
	
	@Override
	public void run() {
		for (QuirkUser user : QuirkUser.getOnlineUsers()) {
			Set<String> remove = new HashSet<>();
			
			for (String abil : user.getCooldowns().keySet()) {
				Cooldown cd = user.getCooldowns().get(abil);
				
				if (cd.getRemaining() <= 0) {
					remove.add(abil);
				}
			}
			
			for (String s : remove) {
				user.getCooldowns().remove(s);
			}
		}
	}

}
