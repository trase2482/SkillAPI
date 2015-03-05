package com.sucy.skill.cmd;

import com.rit.sucy.commands.CommandManager;
import com.rit.sucy.commands.ConfigurableCommand;
import com.rit.sucy.commands.IFunction;
import com.rit.sucy.config.Filter;
import com.rit.sucy.version.VersionManager;
import com.sucy.skill.SkillAPI;
import com.sucy.skill.api.classes.RPGClass;
import com.sucy.skill.api.player.PlayerAccounts;
import com.sucy.skill.api.player.PlayerData;
import com.sucy.skill.language.RPGFilter;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * A command that allows a player or the console to forcefully
 * reset an account of another player
 */
public class CmdForceResetAll implements IFunction
{
    private static final String NOT_PLAYER  = "not-player";
    private static final String RESET       = "reset";
    private static final String RECEIVER    = "receiver";

    /**
     * Runs the command
     *
     * @param cmd    command that was executed
     * @param plugin plugin reference
     * @param sender sender of the command
     * @param args   argument list
     */
    @Override
    public void execute(ConfigurableCommand cmd, Plugin plugin, CommandSender sender, String[] args)
    {
        // Must have both args provided
        if (args.length < 1)
        {
            cmd.displayHelp(sender);
            return;
        }

        // Must be a valid player
        OfflinePlayer target = VersionManager.getOfflinePlayer(args[0]);
        if (target == null || !target.hasPlayedBefore())
        {
            cmd.sendMessage(sender, NOT_PLAYER, ChatColor.RED + "That is not a valid player name");
            return;
        }

        // Clear the data
        PlayerAccounts data = SkillAPI.getPlayerAccountData(target);
        for (PlayerData account : data.getAllData().values())
        {
            account.resetAll();
        }

        // Messages
        if (target != sender)
        {
            cmd.sendMessage(sender, RESET, ChatColor.GOLD + "{player}'s" + ChatColor.DARK_GREEN + " accounts have all been reset", Filter.PLAYER.setReplacement(target.getName()));
        }
        if (target.isOnline())
        {
            cmd.sendMessage((Player)target, RECEIVER, ChatColor.RED + "All of your class data has been forcefully reset");
        }
    }
}
