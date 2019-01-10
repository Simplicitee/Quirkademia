package me.simp.quirkademia.manager;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import me.simp.quirkademia.QuirkPlugin;
import me.simp.quirkademia.ability.QuirkAbility;
import me.simp.quirkademia.ability.QuirkAbilityInfo;
import me.simp.quirkademia.event.QuirkAbilityEvent;
import me.simp.quirkademia.event.QuirkAbilityEvent.QuirkAbilityEventType;
import me.simp.quirkademia.quirk.QuirkUser;
import me.simp.quirkademia.quirk.QuirkUser.StatusEffect;

public class QuirkAbilityManager implements Manager {

	private QuirkPlugin plugin;
	private Set<QuirkAbility> instances;
	private Map<QuirkUser, Map<Class<? extends QuirkAbility>, PriorityQueue<QuirkAbility>>> userInstances;
	private Map<Class<? extends QuirkAbility>, QuirkAbilityInfo> abilityInfos;
	private Map<String, QuirkAbilityInfo> abilityInfosNames;
	
	public QuirkAbilityManager(QuirkPlugin plugin) {
		this.plugin = plugin;
		this.instances = new HashSet<>();
		this.userInstances = new HashMap<>();
		this.abilityInfos = new HashMap<>();
		this.abilityInfosNames = new HashMap<>();
		
		this.plugin.getManagersRunnable().registerManager(this);
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
		if (ability.getUser().getStatus().has(StatusEffect.QUIRK_ERASED)) {
			return false;
		} else if (ability.getUser().isQuirkDisabled()) {
			return false;
		}
		
		QuirkAbilityEvent event = new QuirkAbilityEvent(ability.getUser(), ability, QuirkAbilityEventType.START);
		plugin.getServer().getPluginManager().callEvent(event);
		
		if (event.isCancelled()) {
			return false;
		}
		
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
		QuirkAbilityEvent event = new QuirkAbilityEvent(ability.getUser(), ability, QuirkAbilityEventType.END);
		plugin.getServer().getPluginManager().callEvent(event);
		
		if (event.isCancelled()) {
			return false;
		}
		
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
		Set<QuirkAbility> remove = new HashSet<>();
		for (QuirkAbility ability : instances) {
			if (!ability.getPlayer().isOnline() || ability.getPlayer().isDead()) {
				remove.add(ability);
				continue;
			} else if (!ability.getUser().getQuirk().equals(getAbilityInfo(ability.getClass()).getQuirk())) {
				remove.add(ability);
				continue;
			} else if (ability.getUser().getStatus().has(StatusEffect.QUIRK_ERASED)) {
				remove.add(ability);
				continue;
			} else if (ability.getUser().isQuirkDisabled()) {
				remove.add(ability);
				continue;
			}
			
			//TODO: add more checks here probably
			
			QuirkAbilityEvent event = new QuirkAbilityEvent(ability.getUser(), ability, QuirkAbilityEventType.PROGRESS);
			plugin.getServer().getPluginManager().callEvent(event);
			
			if (event.isCancelled()) {
				remove.add(ability);
			} else if (!ability.progress()) {
				remove.add(ability);
			}
		}
		
		for (QuirkAbility abil : remove) {
			remove(abil);
		}
		
		remove.clear();
	}
	
	public void removeAllFromUser(QuirkUser user) {
		if (userInstances.containsKey(user)) {
			for (Class<? extends QuirkAbility> clazz : userInstances.get(user).keySet()) {
				Iterator<QuirkAbility> abils = userInstances.get(user).get(clazz).iterator();
				
				while (abils.hasNext()) {
					QuirkAbility abil = abils.next();
					abils.remove();
					remove(abil);
				}
			}
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
	public <T extends QuirkAbility> T getAbility(QuirkUser user, Class<T> clazz) {
		if (hasAbility(user, clazz)) {
			return clazz.cast(userInstances.get(user).get(clazz).peek());
		}
		
		return null;
	}
	
	public QuirkAbilityInfo getAbilityInfo(Class<? extends QuirkAbility> clazz) {
		if (abilityInfos.containsKey(clazz)) {
			return abilityInfos.get(clazz);
		}
		
		return null;
	}
	
	public QuirkAbilityInfo getAbilityInfo(String name) {
		name = name.toLowerCase();
		if (abilityInfosNames.containsKey(name)) {
			return abilityInfosNames.get(name);
		}
		
		return null;
	}
	
	public QuirkAbilityInfo registerInfo(QuirkAbilityInfo info) {
		if (!abilityInfos.containsKey(info.getAbilityClass())) {
			abilityInfos.put(info.getAbilityClass(), info);
		}
		
		if (!abilityInfosNames.containsKey(info.getName().toLowerCase())) {
			abilityInfosNames.put(info.getName().toLowerCase(), info);
		}
		
		plugin.getServer().getPluginManager().registerEvents(info, plugin);
		
		return info;
	}

	@Override
	public void run() {
		progressAll();
	}
}
