package me.simp.quirkademia.quirk;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import me.simp.quirkademia.ability.QuirkAbilityInfo;

public class FusedQuirk extends Quirk {

	private Set<Quirk> quirks;
	
	public FusedQuirk(String name, Quirk...quirks) {
		super(name, QuirkType.FUSION);
		
		this.quirks = new HashSet<>();
		
		for (Quirk quirk : quirks) {
			this.quirks.add(quirk);
		}
	}
	
	public FusedQuirk addQuirk(Quirk quirk) {
		this.quirks.add(quirk);
		return this;
	}
	
	public FusedQuirk removeQuirk(Quirk quirk) {
		this.quirks.remove(quirk);
		return this;
	}
	
	public Set<Quirk> getQuirks() {
		return new HashSet<>(quirks);
	}
	
	public String getFusionList() {
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
		
		return builder.toString();
	}

	@Override
	public String getDescription() {
		return "A fusion of " + getFusionList();
	}

	@Override
	public Set<QuirkAbilityInfo> registerAbilities() {
		return Collections.emptySet();
	}
}
