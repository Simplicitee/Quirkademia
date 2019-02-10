package me.simp.quirkademia.manager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.metadata.Metadatable;

import me.simp.quirkademia.QuirkPlugin;
import me.simp.quirkademia.quirk.QuirkUser;

public class SelectionManager extends Manager {

	private Map<QuirkUser, Selection<? extends Metadatable>> selections;
	
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
	
	public Selection<? extends Metadatable> getSelection(QuirkUser user) {
		if (selections.containsKey(user)) {
			return selections.get(user);
		}
		
		return null;
	}
	
	public Entity getSelectedEntity(QuirkUser user) {
		if (selections.containsKey(user)) {
			Selection<?> target = selections.get(user);
			
			if (target instanceof Entity) {
				return (Entity) target;
			}
		}
		
		return null;
	}
	
	public Block getSelectedBlock(QuirkUser user) {
		if (selections.containsKey(user)) {
			Object target = selections.get(user);
			
			if (target instanceof Block) {
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
			Object target = selections.get(user);
			
			if (target instanceof Entity) {
				if (((Entity) target).isDead()) {
					users.remove();
					continue;
				}
				
				loc = ((Entity) target).getLocation();
			} else {
				loc = ((Block) target).getLocation();
			}
			
			if (!loc.getWorld().equals(Bukkit.getPlayer(user.getUniqueId()).getWorld())) {
				users.remove();
				continue;
			}
		}
	}
	
	public class Selection<T> {
		
		public T target;
		public QuirkUser user;
		
		public Selection(QuirkUser user, T target) {
			this.user = user;
			this.target = target;
		}
		
		public T getTarget() {
			return target;
		}
		
		public QuirkUser getUser() {
			return user;
		}
	}
}
