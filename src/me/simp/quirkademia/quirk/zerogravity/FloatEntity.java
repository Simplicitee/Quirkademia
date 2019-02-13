package me.simp.quirkademia.quirk.zerogravity;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import me.simp.quirkademia.ability.QuirkAbility;
import me.simp.quirkademia.quirk.QuirkUser;

public class FloatEntity extends QuirkAbility {

	public FloatEntity(QuirkUser user) {
		super(user);
		
		Entity e = plugin.getSelectionManager().getSelectedEntity(user);
		
		if (e == null) {
			return;
		} else if (!(e instanceof LivingEntity) || !e.hasGravity()) {
			return;
		}
		
		if (manager.hasAbility(user, Floaty.class)) {
			manager.getAbility(user, Floaty.class).makeFloat(e, (int) (e.getBoundingBox().getVolume() * 10));
		}
	}

	@Override
	public Location getLocation() {
		return null;
	}

	@Override
	public boolean progress() {
		return false;
	}

	@Override
	public void onStart() {
		
	}

	@Override
	public void onRemove() {
		
	}

}
