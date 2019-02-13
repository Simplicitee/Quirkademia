package me.simp.quirkademia.manager;

import java.util.ArrayList;
import java.util.List;

import me.simp.quirkademia.QuirkPlugin;
import me.simp.quirkademia.ability.Collidable;
import me.simp.quirkademia.ability.QuirkAbility;
import me.simp.quirkademia.event.QuirkAbilityCollisionEvent;

public class CollisionManager extends Manager {
	
	private List<QuirkAbility> collisions;

	public CollisionManager(QuirkPlugin plugin) {
		super(plugin);
		
		collisions = new ArrayList<>();
	}

	@Override
	public void run() {
		if (collisions.size() <= 1) {
			return;
		}
		
		for (int i = collisions.size() - 1; i >= 0; i--) {
			QuirkAbility a = collisions.get(i);
			
			if (a.getLocation() == null) {
				continue;
			} else if (!(a instanceof Collidable)) {
				continue;
			}
			
			for (int j = 0; j < i; j++) {
				QuirkAbility b = collisions.get(j);
				
				if (a.equals(b)) {
					continue;
				} else if (b.getLocation() == null) {
					continue;
				} else if (!(b instanceof Collidable)) {
					continue;
				}
				
				if (!a.getLocation().getWorld().equals(b.getLocation().getWorld())) {
					continue;
				}
				
				QuirkAbilityCollisionEvent event = new QuirkAbilityCollisionEvent(a, b);
				plugin.getServer().getPluginManager().callEvent(event);
				
				if (event.isCancelled()) {
					continue;
				}
				
				Collidable ac = (Collidable) a;
				Collidable bc = (Collidable) b;
				
				double radius = Math.max(ac.getRadius(), bc.getRadius());
				
				if (a.getLocation().distanceSquared(b.getLocation()) <= radius * radius) {
					if (ac.onCollision(b) || event.getRemoveFirst()) {
						i--;
						collisions.remove(i);
						plugin.getAbilityManager().remove(a);
					}
					
					if (bc.onCollision(a) || event.getRemoveSecond()) {
						i--;
						collisions.remove(j);
						plugin.getAbilityManager().remove(b);
						j--;
					}
					
				}
			}
		}
	}

}
