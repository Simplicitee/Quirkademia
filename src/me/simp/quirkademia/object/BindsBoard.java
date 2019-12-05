package me.simp.quirkademia.object;

import org.bukkit.ChatColor;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;

import me.simp.quirkademia.quirk.Quirk;
import me.simp.quirkademia.quirk.QuirkUser;
import me.simp.quirkademia.util.DisplayBoard;

public class BindsBoard extends DisplayBoard {

	public BindsBoard(QuirkUser user) {
		super(user);
		
		this.update();
	}

	@Override
	public void update() {
		Objective o = board.registerNewObjective("quirk binds", "dummy", "Quirk Binds");
		
		o.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		for (int i = 0; i < 9; i++) {
			Quirk q = user.getBind(i);
			String display = ChatColor.GRAY + "!> Unbound (" + (i + 1) + ") <!";
			
			if (q != null) {
				display = q.getDisplayName();
			}
			
			o.getScore(display).setScore(-(i + 1));
		}
		
		player.setScoreboard(board);
	}
}
