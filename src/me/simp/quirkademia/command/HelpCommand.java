package me.simp.quirkademia.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import me.simp.quirkademia.ability.QuirkAbilityInfo;
import me.simp.quirkademia.quirk.AddonQuirk;
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
		Quirk quirk = plugin.getQuirkManager().getQuirk(topic.replace("-", " ").replace("_", " "));
		QuirkAbilityInfo info = plugin.getAbilityManager().getAbilityInfo(topic.replace("-", " ").replace("_", " "));
		QuirkCommand cmd = plugin.getCommands().getCommand(topic);
		
		if (quirk != null) {
			sender.sendMessage(quirk.getChatColor() + quirk.getName());
			sender.sendMessage(ChatColor.YELLOW + quirk.getDescription());
			if (quirk instanceof AddonQuirk) {
				AddonQuirk addon = (AddonQuirk) quirk;
				sender.sendMessage(ChatColor.YELLOW + "Addon, made by " + ChatColor.WHITE + addon.getAuthor());
				sender.sendMessage(ChatColor.YELLOW + "Version: " + addon.getVersion());
			}
		} else if (info != null) {
			quirk = info.getQuirk();
			sender.sendMessage(quirk.getChatColor() + info.getName());
			sender.sendMessage(ChatColor.YELLOW + "Quirk: " + quirk.getChatColor() + quirk.getName());
			sender.sendMessage(ChatColor.WHITE + info.getDescription());
			sender.sendMessage(ChatColor.YELLOW + "How to use: " + ChatColor.WHITE + info.getInstruction());
		} else if (cmd != null) {
			sender.sendMessage(ChatColor.YELLOW + "Command: " + ChatColor.WHITE + cmd.getName());
			
			StringBuilder builder = new StringBuilder();
			Iterator<String> iter = Arrays.asList(cmd.getAliases()).iterator();
			
			while (iter.hasNext()) {
				String alias = iter.next();
				if (iter.hasNext()) {
					alias = alias + ", ";
				}
				builder.append(alias);
			}
			
			sender.sendMessage(ChatColor.YELLOW + "Aliases: [" + ChatColor.WHITE + builder.toString() + ChatColor.YELLOW + "]");
			sender.sendMessage(ChatColor.YELLOW + "Usage: " + ChatColor.WHITE + cmd.getProperUse());
			sender.sendMessage(cmd.getDescription());
		} else {
			sender.sendMessage("Unknown help topic!");
		}
	}

	@Override
	public List<String> completer(CommandSender sender, List<String> args) {
		List<String> completions = new ArrayList<>();
		for (Quirk quirk : plugin.getQuirkManager().getQuirks()) {
			completions.add(quirk.getName().replace(" ", "-").toLowerCase());
		}
		
		for (QuirkCommand cmd : plugin.getCommands().list()) {
			completions.add(cmd.getName().toLowerCase());
		}
		
		return completions;
	}

}
