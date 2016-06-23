package net.mycraft_public.hidefromthebeast;

//TODO: import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
//import org.bukkit.scoreboard.DisplaySlot;
//import org.bukkit.scoreboard.Objective;
//import org.bukkit.scoreboard.Score;
//TODO: import org.bukkit.scoreboard.Scoreboard;
//TODO: import org.bukkit.scheduler.BukkitTask;

public class Main extends JavaPlugin{
	
	private Logger logger;
	private Variables game;
	private GameSetup setup;
	public Player player;
	
	private static Main instance = new Main();
	
	private Main(){
		
	}
	
	public static Main getInstance(){
		return instance;
	}
	
	@Override
	public void onEnable() {
		logger = getLogger();
		game.setGameState(false);
		PluginManager manager = getServer().getPluginManager();
		manager.registerEvents(new PlayerListener(), this);
		manager.registerEvents(new BlockListener(), this);
		manager.registerEvents(new InventoryListener(), this);
		game.playerlist = new HashSet<Player>();
		//sb = Bukkit.getServer().getScoreboardManager().getNewScoreboard();
		//ob = sb.registerNewObjective("HFTBscrbrd", "dummy");
		//ob.setDisplayName("HideFromTheBeast");
		//ob.setDisplaySlot(DisplaySlot.SIDEBAR);
		logger.info(this.getDescription().getVersion() + " has been enabled!");
	}
	
	@Override
	public void onDisable() {
		logger.info(this.getDescription().getVersion() + " has been disabled!");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandlabel, String[] args) {
		if (commandlabel.equalsIgnoreCase("hftb") || commandlabel.equalsIgnoreCase("hidefromthebeast"))
		{
			if (args.length == 0)
			{
				sender.sendMessage(ChatColor.GOLD + "==========[" + ChatColor.RED + "Hide From The Beast" + ChatColor.GOLD + "]==========");
				sender.sendMessage(ChatColor.GREEN + "- /hftb join        Joins an active game.");
				sender.sendMessage(ChatColor.GREEN + "- /hftb leave       Leave an active game.");
				sender.sendMessage(ChatColor.GREEN + "- /hftb start       Start a hftb game");
				sender.sendMessage(ChatColor.GREEN + "- /hftb stop        Stop an active game.");
				sender.sendMessage(ChatColor.GREEN + "- /hftb status      Checks for an active game");
				sender.sendMessage(ChatColor.GREEN + "- /hftb defspawn    Sets the default player spawn");
				sender.sendMessage(ChatColor.GREEN + "- /hftb beastspawn  Sets the beast's spawn");
				return true;
			}
			//               //
			// JOIN THE GAME //
			//               //
			if (args.length == 1 && args[0].equalsIgnoreCase("join"))
			{
				if (!(sender instanceof Player))
				{
					sender.sendMessage(game.noConsoleUseMessage());
					return true;
				}
				player = (Player) sender;
		    	if (!(player.hasPermission("hftb.play")))
		    	{
		    		player.sendMessage(game.messageNoPerms());
		    		return true;
		    	}
				if (game.getGameState())
				{
					player.sendMessage(game.getPrefix() + "Cannot join: Game has started.");
					return true;
				}
				if (game.getDefSpawnLocation() == null && game.getBeastSpawnLocation() == null)
				{
					player.sendMessage(game.getPrefix() + "Player/Beast spawn points not set.");
					return true;
				}
				PlayerInventory inv = player.getInventory();
				if (inv.getContents() != null || inv.getArmorContents() != null)
				{
					player.sendMessage(game.getPrefix() + "You have to empty your inventory first.");
					return true;
				}
				if (game.getPlayerList().contains(player))
				{
					player.sendMessage(game.getPrefix() + "You're on an active game.");
					return true;
				}
				game.addPlayer(player);
				game.hftbPlayerCount++;
				player.teleport(game.getDefSpawnLocation());
				player.setGameMode(GameMode.ADVENTURE);
				player.sendMessage(game.getPrefix() + "Successfully joined.");
				return true;
			}
			//                //
			// LEAVE THE GAME //
			//                //
			else if (args.length == 1 && args[0].equalsIgnoreCase("leave"))
			{
				if (!(sender instanceof Player))
			    {
				    sender.sendMessage(game.noConsoleUseMessage());
				    return true;
			    }
			    player = (Player) sender;
		    	if (!(player.hasPermission("hftb.play")))
		    	{
		    		player.sendMessage(game.messageNoPerms());
		    		return true;
		    	}
			    if (!game.getPlayerList().contains(player))
			    {
				    player.sendMessage(game.getPrefix() + "You're not in an active game.");
			     	return true;
			    }
			    if (game.getPlayerCount() < 2)
			    {
			    	setup.Stop();
			    	Iterator<Player> it = game.getPlayerList().iterator();
			    	while(it.hasNext())
			    	{
			    		Player pl = it.next();
			    		pl.sendMessage(game.getPrefix() + "Players were less than 2. Game stopped.");
			    		pl.teleport(player.getWorld().getSpawnLocation());
					    pl.setGameMode(GameMode.SURVIVAL);
					    game.getPlayerList().clear();
			    	}
			    	return true;
			    }
			    game.getPlayerList().remove(player);
			    player.sendMessage(game.getPrefix() + "Successfully left.");
			    player.teleport(player.getWorld().getSpawnLocation());
			    player.setGameMode(GameMode.SURVIVAL);
			    return true;
			}
			//                //
			// START THE GAME //
			//                //
			else if (args.length == 1 && args[0].equalsIgnoreCase("start"))
		    {
		    	if (!(sender.hasPermission("hftb.admin")))
		    	{
		    		sender.sendMessage(game.messageNoPerms());
		    		return true;
		    	}
		    	if (game.getGameState())
		    	{
		    		sender.sendMessage(game.getPrefix() + "Game has already started.");
		    		return true;
		    	}
				if (game.getDefSpawnLocation() == null && game.getBeastSpawnLocation() == null)
				{
					sender.sendMessage(game.getPrefix() + "Player/Beast spawn points not set.");
					return true;
				}
		        if (game.getPlayerCount() < 2)
		    	{
		    		sender.sendMessage(game.getPrefix() + "The game can't start: Players must be more than 2");
		    		return true;
		    	}
		    	game.setGameState(true);
		    	setup.ChooseTheBeast();
		    	game.getBeast().teleport(game.getBeastSpawnLocation());
		    	setup.ArmorSetup();
		    	Iterator<Player> itr = game.getPlayerList().iterator();
		    	while(itr.hasNext())
		    	{
		    		Player getStats = itr.next();
		    		//Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Events(), 20)
			    	getStats.sendMessage(game.getPrefix() + "Game Started.");
		    		//getStats.setScoreboard(sb);
		    		//sc = ob.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "" + getStats));
		    		getStats.sendMessage(game.getPrefix() + "The beast was chosen! Hide From " + game.getBeast().getName() + "!");
		    	}
		    	return true;
		    }
			//               //
			// STOP THE GAME //
			//               //
			else if (args.length == 1 && args[0].equalsIgnoreCase("stop"))
		    {
		    	if (!(sender.hasPermission("hftb.admin")))
		    	{
		    		sender.sendMessage(game.messageNoPerms());
		    		return true;
		    	}
		    	if (!(game.getGameState()))
		    	{
		    		sender.sendMessage(game.getPrefix() + "Game is not running.");
		    		return true;
		    	}
		    	setup.Stop();
		        return true;
		    }
			//                    //
			// STATUS OF THE GAME //
			//                    //
		    else if (args.length == 1 && args[0].equalsIgnoreCase("status"))
		    {
		    	if (!(sender.hasPermission("hftb.status")))
		    	{
		    		sender.sendMessage(game.messageNoPerms());
		    		return true;
		    	}
		    	if (!(game.getGameState()))
		    	{
		    		sender.sendMessage(game.getPrefix() + "Game status:" + ChatColor.RED + " Inactive.");
		    		return true;
		    	}
		    	sender.sendMessage(game.getPrefix() + "Game status:" + ChatColor.GREEN + " Active.");
		    	sender.sendMessage(game.getPrefix() + "Users playing: " + ChatColor.GREEN + game.getPlayerCount());
		    	return true;
		    }
			//               //
			// DEFAULT SPAWN //
			//               //
		    else if (args.length == 1 && args[0].equalsIgnoreCase("defspawn"))
		    {
		    	if (!(sender instanceof Player))
		    	{
		    		sender.sendMessage(game.noConsoleUseMessage());
		    		return true;
		    	}
		    	player = (Player) sender;
		    	if (!(player.hasPermission("hftb.admin")))
		    	{
		    		player.sendMessage(game.messageNoPerms());
		    		return true;
		    	}
		    	game.setDefSpawn(player.getLocation());
		    	player.sendMessage(game.getPrefix() + "Default Spawn set.");
		    	return true;
		    }
			//             //
			// BEAST SPAWN //
			//             //
		    else if (args.length == 1 && args[0].equalsIgnoreCase("beastspawn"))
		    {
		    	if (!(sender instanceof Player))
		    	{
		    		sender.sendMessage(game.noConsoleUseMessage());
		    		return true;
		    	}
		    	player = (Player) sender;
		    	if (!(player.hasPermission("hftb.admin")))
		    	{
		    		player.sendMessage(game.messageNoPerms());
		    		return true;
		    	}
		    	game.setBeastSpawn(player.getLocation());
		    	player.sendMessage(game.getPrefix() + "Beast's Spawn set.");
		    	return true;
		    }
		    else
		    {
		    	sender.sendMessage(ChatColor.RED + "Command not found. Try " + ChatColor.GREEN + "/hftb");
		    	return true;
		    }
		}
		return false;
	}
}