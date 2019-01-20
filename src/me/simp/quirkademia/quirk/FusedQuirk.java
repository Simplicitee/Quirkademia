package me.simp.quirkademia.quirk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import org.bukkit.Location;

import me.simp.quirkademia.ability.QuirkAbility;
import me.simp.quirkademia.ability.QuirkAbilityInfo;
import me.simp.quirkademia.util.ActivationType;

public class FusedQuirk extends Quirk {

	private List<Quirk> quirks;
	private Queue<Quirk> cycle;
	private Quirk selected;
	
	public FusedQuirk(String name, Quirk...quirks) {
		super(name, QuirkType.FUSION);
		
		this.quirks = Arrays.asList(quirks);
		this.cycle = new LinkedList<>();
		
		for (Quirk quirk : quirks) {
			this.cycle.add(quirk);
		}
		
		this.selected = this.cycle.poll();
		this.cycle.add(selected);
	}
	
	public List<Quirk> getQuirks() {
		return new ArrayList<>(quirks);
	}
	
	public Quirk getSelected() {
		return selected;
	}
	
	@Override
	public QuirkAbility createAbilityInstance(QuirkUser user, ActivationType type) {
		return selected.createAbilityInstance(user, type);
	}

	@Override
	public String getDescription() {
		StringBuilder builder = new StringBuilder();
		Iterator<Quirk> iter = quirks.iterator();
		
		while (iter.hasNext()) {
			Quirk quirk = iter.next();
			
			if (!iter.hasNext()) {
				builder.append("and " + quirk.getName());
			} else {
				builder.append(quirk.getName() + ", ");
			}
		}
		
		return "A fusion of " + builder.toString();
	}

	@Override
	public Set<QuirkAbilityInfo> registerAbilities() {
		Set<QuirkAbilityInfo> register = new HashSet<>();
		//register.add(new QuirkAbilityInfo(ActivationType.MANUAL, FusionCycler.class, this, "After using an ability, your fused quirks cycle and you "))
		return null;
	}
}
