package me.simp.quirkademia.command;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import me.simp.quirkademia.ability.QuirkAbilityInfo;
import me.simp.quirkademia.quirk.Quirk;

public class HelpCommand extends QuirkCommand {

	public HelpCommand() {
		super("help", "Get help with a quirk-related topic", "/quirk help <quirk/ability>", new String[] {"help", "h", "?", "info", "i"});
	}

	@Override
	public void execute(CommandSender sender, List<String> args) {
		if (!hasPermission(sender, false)) {
			return;
		} else if (!acceptableLength(sender, args.size(), 1, 1)) {
			return;
		}
		
		String topic = args.get(0).toLowerCase();
		Quirk quirk = Quirk.get(topic.replace("-", " "));
		QuirkAbilityInfo info = plugin.getAbilityManager().getAbilityInfo(topic.replace("-", " "));
		
		if (quirk != null) {
			sender.sendMessage(quirk.getChatColor() + quirk.getName());
			sender.sendMessage(ChatColor.YELLOW + quirk.getDescription());
		} else if (info != null) {
			quirk = info.getQuirk();
			sender.sendMessage(quirk.getChatColor() + info.getName());
			sender.sendMessage(ChatColor.YELLOW + "Quirk: " + quirk.getChatColor() + quirk.getName());
			sender.sendMessage(ChatColor.WHITE + info.getDescription());
			sender.sendMessage(ChatColor.YELLOW + "How to use: " + ChatColor.WHITE + info.getInstruction());
		} else {
			sender.sendMessage("Unknown help topic!");
			return;
		}
	}

}
