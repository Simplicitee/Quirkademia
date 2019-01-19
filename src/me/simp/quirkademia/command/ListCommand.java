package me.simp.quirkademia.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;

import me.simp.quirkademia.quirk.AddonQuirk;
import me.simp.quirkademia.quirk.Quirk;

public class ListCommand extends QuirkCommand {

	public ListCommand() {
		super("list", "List the available quirks", "/quirk list", new String[] {"list", "l"});
	}

	@Override
	public void execute(CommandSender sender, List<String> args) {
		if (!hasPermission(sender, false)) {
			return;
		} else if (!acceptableLength(sender, args.size(), 0, 0)) {
			return;
		}
		
		for (Quirk quirk : plugin.getQuirkManager().getQuirks()) {
			sender.sendMessage(quirk.getChatColor() + quirk.getName() + ((quirk instanceof AddonQuirk) ? "(Addon)" : ""));
		}
	}

	@Override
	public List<String> completer(CommandSender sender, List<String> args) {
		return new ArrayList<>();
	}

}
