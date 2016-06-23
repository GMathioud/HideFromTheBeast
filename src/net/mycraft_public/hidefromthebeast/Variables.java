package net.mycraft_public.hidefromthebeast;

import java.util.HashSet;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
//import org.bukkit.scoreboard.Scoreboard;

public class Variables{
	public int hftbPlayerCount;
	public HashSet<Player> playerlist;
	
	private Main main;
	private String prefix = "" + ChatColor.GRAY + ChatColor.BOLD + "[" + ChatColor.GOLD + ChatColor.BOLD + "HideFromTheBeast" + ChatColor.GRAY + ChatColor.BOLD + "]" + ChatColor.WHITE + " ";
	private String denyconsole = ChatColor.RED + "Error: Not usable by console.";
	private String messageNoPerms = prefix + "You are not allowed to use this command.";
	private boolean gameState;
	private Location defspawn;
	private Location beastspawn;
	private Player beast;
	//TODO: private Scoreboard sb;
	//TODO: private Objective ob;
	//TODO: private Score sc;
	
	private static Variables instance = new Variables();

	private Variables(){

	}

	public static Variables getInstance(){
		return instance;
	}

	//The title [HideFromTheBeast].
	/**
	 * The prefix of every message that HFTB sends.
	 */
	public String getPrefix(){
		return prefix;
	}
	
	//Not usable by console message.
	/**
	 * Returns a deny message when the command is being used on the console.
	 */
	public String noConsoleUseMessage(){
		return denyconsole;
	}
	
	//No Permissions Message.
	/**
	 * Returns a message when the command executor has no command permissions.
	 */
	public String messageNoPerms(){
		return messageNoPerms;
	}
	
	//Add the player on the game's playerlist.
	/**
	 * Adds the specified player to the game's playerlist.
	 */
	public HashSet<Player> addPlayer(Player player){
	    playerlist.add(player);
		return null;
	}
	
	//Remove the player from the game's playerlist.
	/**
	 * Removes the player from the game's playerlist.
	 */
	public HashSet<Player> removePlayer(Player player){
		playerlist.remove(player);
		return null;
	}
	
	//Get the game's players.
	/**
	  * Returns the players that are playing the game.
	  */
	public HashSet<Player> getPlayerList(){
		if (playerlist.isEmpty())
		{
			return null;
		}
		return playerlist;
	}
	
	//Set the beast's spawn location.
	/**
	 * Sets the beast's spawn location.
	 */
	public Location setBeastSpawn(Location loc){
		return beastspawn = main.player.getLocation();
	}
	
	//Get the beast's spawn location.
	/**
	 * Returns the beast's spawn location.
	 */
	public Location getBeastSpawnLocation(){
		if (beastspawn == null)
		{
			main.player.sendMessage(prefix + "Beast's spawn is not set.");
			return null;
		}
		return beastspawn;
	}
	
	//Default Spawn Location.
	/**
	 * Sets the location that the players will spawn when they join the game.
	 */
	public Location setDefSpawn(Location loc){
		return defspawn = main.player.getLocation();
	}
	
	/**
	 * Returns the location that the players are spawning when they join the game.
	 */
	public Location getDefSpawnLocation(){
		if (defspawn == null){
			main.player.sendMessage(prefix + "Default spawn is not set.");
			return null;
		}
		return defspawn;
	}
	
	//Get the number of players who have joined the game.
	/**
	 * Returns the amount of players that are in an active HideFromTheBeast game.
	 */
	public int getPlayerCount(){
		return hftbPlayerCount;
	}
	
	//Dat Beast!
	/**
	 * Returns the name of the current beast.
	 */
	public Player getBeast(){
		if (beast == null)
		{
			main.player.sendMessage(prefix + "There is no beast set.");
			return null;
		}
		return beast;
	}
	
	/**
	 * Sets the beast.
	 */
	public Player setBeast(Player thebeast){
		return beast = thebeast;
	}
	
	//Game Stats.
	/**
	  * Returns if the game is active or not.
	  */
	public boolean getGameState(){
		return gameState;
	}
	
	/**
	 * Sets the state of the game. (Active or Inactive)
	 */
	public boolean setGameState(boolean bool){
		if (bool == true && gameState == true)
		{
			main.player.sendMessage(prefix + "Game is already active");
			return true;
		}
		return false;
	}
}