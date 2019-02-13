package me.simp.quirkademia.manager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

import me.simp.quirkademia.QuirkPlugin;
import me.simp.quirkademia.quirk.QuirkUser;

/**
 * Tracks the selected entity / block when a user right clicks them
 * @author simp
 *
 */
public class SelectionManager extends Manager {

	private Map<QuirkUser, Selection<?>> selections;
	
	public SelectionManager(QuirkPlugin plugin) {
		super(plugin);
		
		selections = new HashMap<>();
	}

	public void update(QuirkUser user, Entity entity) {
		if (entity != null) {
			selections.put(user, new Selection<Entity>(user, entity));
		}
	}
	
	public void update(QuirkUser user, Block block) {
		if (block != null) {
			selections.put(user, new Selection<Block>(user, block));
		}
	}
	
	public Selection<?> getSelection(QuirkUser user) {
		if (selections.containsKey(user)) {
			Selection<?> target = selections.get(user);
			
			selections.remove(user);
			
			return target;
		}
		
		return null;
	}
	
	public Entity getSelectedEntity(QuirkUser user) {
		if (selections.containsKey(user)) {
			Object target = selections.get(user).getTarget();
			
			if (target instanceof Entity) {
				selections.remove(user);
				
				return (Entity) target;
			}
		}
		
		return null;
	}
	
	public Block getSelectedBlock(QuirkUser user) {
		if (selections.containsKey(user)) {
			Object target = selections.get(user).getTarget();
			
			if (target instanceof Block) {
				selections.remove(user);
				
				return (Block) target;
			}
		}
		
		return null;
	}

	@Override
	public void run() {
		Iterator<QuirkUser> users = selections.keySet().iterator();
		while (users.hasNext()) {
			QuirkUser user = users.next();
			Location loc = null;
			Selection<?> selection = selections.get(user);
			
			if (System.currentTimeMillis() >= selection.getTime() + 7000) {
				users.remove();
				continue;
			}
			
			Object target = selection.getTarget();
			
			if (target instanceof Entity) {
				if (((Entity) target).isDead()) {
					users.remove();
					continue;
				}
				
				loc = ((Entity) target).getLocation();
			} else if (target instanceof Block) {
				loc = ((Block) target).getLocation();
			}
			
			if (loc == null) {
				users.remove();
				continue;
			}
			
			if (!loc.getWorld().equals(Bukkit.getPlayer(user.getUniqueId()).getWorld())) {
				users.remove();
				continue;
			}
		}
	}
	
	public class Selection<T> {
		
		private T target;
		private QuirkUser user;
		private long time;
		
		public Selection(QuirkUser user, T target) {
			this.user = user;
			this.target = target;
			this.time = System.currentTimeMillis();
		}
		
		public T getTarget() {
			return target;
		}
		
		public QuirkUser getUser() {
			return user;
		}
		
		public long getTime() {
			return time;
		}
	}
}
