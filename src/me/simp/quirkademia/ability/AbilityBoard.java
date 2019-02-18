package me.simp.quirkademia.ability;

import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;

import me.simp.quirkademia.quirk.QuirkUser;
import me.simp.quirkademia.util.ActivationType;
import me.simp.quirkademia.util.DisplayBoard;

public class AbilityBoard extends DisplayBoard {
	
	public AbilityBoard(QuirkUser user) {
		super(user);
		
		this.update();
	}
	public void update() {
		Objective o = board.registerNewObjective("abilities", "dummy", "Quirk Abilities");
		o.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		int i = 0;
		for (ActivationType type : user.getQuirk().getAbilities().keySet()) {
			QuirkAbilityInfo info = user.getQuirk().getAbilities().get(type);
			Score s = o.getScore(info.getName());
			s.setScore(i);
			i++;
		}
		
		player.setScoreboard(board);
	}
}
