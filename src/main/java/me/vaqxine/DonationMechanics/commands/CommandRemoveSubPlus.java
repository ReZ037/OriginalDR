package me.vaqxine.DonationMechanics.commands;

import me.vaqxine.Main;
import me.vaqxine.DonationMechanics.DonationMechanics;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandRemoveSubPlus implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player ps = null;
		if(sender instanceof Player){
			ps = (Player)sender;
			if(!(ps.isOp())){
				return true;
			}
		}
		String p_name = args[0];
		int user_id = DonationMechanics.getForumUserID(p_name);
		String current_rank = DonationMechanics.getRank(p_name);
		
		if(!current_rank.equalsIgnoreCase("sub+")){
			boolean plus = true;
			DonationMechanics.removeSubscriber(user_id, plus);
			return true;
		}
		
		DonationMechanics.sendPacketCrossServer("[rank_map]" + p_name + ":" + "default", -1, true);
		
		if(user_id == -1){
			Main.log.info("[DonationMechanics] Set user " + p_name + " to DEFAULT, however they didn't have a forum account!");
			if(ps != null){
				ps.sendMessage(ChatColor.RED + "The user " + p_name + " does not have a forum account yet. Cannot set DEFAULT status.");
			}
			return true;
		}

		boolean plus = true;
		DonationMechanics.removeSubscriber(user_id, plus);
		DonationMechanics.addSubscriberDays(p_name, 0, true);
		DonationMechanics.setRank(p_name, "default");
		if(ps != null){
			ps.sendMessage(ChatColor.GREEN + "Set " + p_name + " to DEFAULT.");
			ps.sendMessage(ChatColor.GRAY + "FORUM USER_ID: " + user_id);
		}
		Main.log.info("[DonationMechanics] Set user " + p_name + " to DEFAULT. user_id = " + user_id);
		return true;
	}
	
}