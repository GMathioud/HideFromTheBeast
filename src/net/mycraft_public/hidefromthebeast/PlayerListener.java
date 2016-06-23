package net.mycraft_public.hidefromthebeast;

import java.util.Iterator;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener{
	private Variables game;
	private GameSetup setup;
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event){
		Player caught = (Player) event.getEntity();
		Player damager = (Player) event.getDamager();
		if (game.getGameState())
		{
	    	if (damager == game.getBeast())
		    {
		    	caught.getWorld().strikeLightningEffect(caught.getLocation());
		        caught.teleport(game.getBeastSpawnLocation());
		        //sc = ob.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "" + caught));
	    		//sc.setScore(sc.getScore() +1);
	    		setup.ArmorRemove();
		    	game.setBeast(caught);
	    		setup.ArmorSetup();
		        Iterator<Player> it = game.getPlayerList().iterator();
		        while(it.hasNext())
	    	    {
	    	    	Player pl = it.next();
		        	pl.sendMessage(game.getPrefix() + caught.getName() + " was eaten by " + caught.getName() + "! " + caught.getName() + " is the beast now.");
		        }
	    	    caught.sendMessage(game.getPrefix() + "You are the beast now! Eat 'em!");
	    	    caught.setHealth(20);
	    	}
    	}
	}
	
	/*@EventHandler
	public void onPlayerMove(PlayerMoveEvent event){
		Player player = event.getPlayer();
		if (!isActive)
		{
			if (playerlist.contains(player))
			{
			    player.teleport(defspawn);
			}
		}
	}*/
	
	@EventHandler
	public void onFoodLevelChange(FoodLevelChangeEvent event)
	{
		if (game.getGameState())
		{
		    if (event.getEntity() instanceof Player)
	    	{
		    	Player player = (Player) event.getEntity();
	    		if (game.getPlayerList().contains(player))
	    		{
	    			event.setCancelled(true);
	    		}
	    	}
		}
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		Player player = event.getPlayer();
		if (game.getGameState())
		{
			if (game.getPlayerList().contains(player))
			{
				game.removePlayer(player);
				game.hftbPlayerCount--;
			}
		}
	}
}
