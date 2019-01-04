package me.simp.quirkademia.quirk.ability;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import me.simp.quirkademia.QuirkPlugin;
import me.simp.quirkademia.quirk.QuirkUser;

public class QuirkAbilityManager {

	private Set<QuirkAbility> instances;
	private Map<QuirkUser, Map<Class<? extends QuirkAbility>, PriorityQueue<QuirkAbility>>> userInstances;
	
	private QuirkPlugin plugin;
	
	public QuirkAbilityManager(QuirkPlugin plugin) {
		this.plugin = plugin;
		this.instances = new HashSet<>();
		this.userInstances = new HashMap<>();
	}
	
	public QuirkPlugin getPlugin() {
		return plugin;
	}
	
	/**
	 * Begins the progression for the ability, adding it to active instances
	 * @param ability {@link QuirkAbility} to begin progressing
	 * @return true if added to instances successfully
	 */
	public boolean start(QuirkAbility ability) {
		if (instances.add(ability)) {
			QuirkUser user = ability.getUser();
			PriorityQueue<QuirkAbility> register;
			
			if (!userInstances.containsKey(user)) {
				userInstances.put(user, new HashMap<>());
			}
			
			if (!userInstances.get(user).containsKey(ability.getClass())) {
				register = new PriorityQueue<>(50, new Comparator<QuirkAbility>() {

					@Override
					public int compare(QuirkAbility o1, QuirkAbility o2) {
						return (int) (o1.getInitializationTime() - o2.getInitializationTime());
					}
					
				});
				userInstances.get(user).put(ability.getClass(), register);
			} else {
				register = userInstances.get(user).get(ability.getClass());
			}
			
			if (register.add(ability)) {
				ability.onStart();
				return true;
			}
		}
		
		return false;
	}
	
	
	/**
	 * Removes the ability from instances, halting its progression
	 * @param ability {@link QuirkAbility} to remove from instances
	 * @return true if removed successfully
	 */
	public boolean remove(QuirkAbility ability) {
		if (instances.remove(ability)) {
			QuirkUser user = ability.getUser();
			
			if (userInstances.containsKey(user)) {
				if (userInstances.get(user).containsKey(ability.getClass())) {
					PriorityQueue<QuirkAbility> instances = userInstances.get(user).get(ability.getClass());
					if (instances.remove(ability)) {
						if (instances.isEmpty()) {
							userInstances.get(user).remove(ability.getClass());
						}
						
						if (userInstances.get(user).isEmpty()) {
							userInstances.remove(user);
						}
						
						ability.onRemove();
						
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	/**
	 * Progresses all ability instances if possible
	 */
	public void progressAll() {
		for (QuirkAbility ability : instances) {
			if (!ability.getPlayer().isOnline() || ability.getPlayer().isDead()) {
				remove(ability);
				continue;
			} 
			
			//TODO: add more checks here
			
			ability.progress();
		}
	}
	
	/**
	 * Returns whether the {@link QuirkUser} has an ability active for the given Class
	 * @param user {@link QuirkUser} to check for ability
	 * @param clazz Class of {@link QuirkAbility} to check for
	 * @return true if ability is present
	 */
	public boolean hasAbility(QuirkUser user, Class<? extends QuirkAbility> clazz) {
		if (user != null && clazz != null) {
			if (userInstances.containsKey(user)) {
				return userInstances.get(user).containsKey(clazz);
			}
		}
		
		return false;
	}
	
	/**
	 * Retrieves the oldest {@link QuirkAbility} from the {@link QuirkUser} and Class if present
	 * @param user {@link QuirkUser} to obtain ability from
	 * @param clazz Class of {@link QuirkAbility} to obtain
	 * @return oldest {@link QuirkAbility} of class clazz from user
	 */
	public QuirkAbility getAbility(QuirkUser user, Class<? extends QuirkAbility> clazz) {
		if (hasAbility(user, clazz)) {
			return userInstances.get(user).get(clazz).peek();
		}
		
		return null;
	}
}
