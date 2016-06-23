package net.mycraft_public.hidefromthebeast;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockListener implements Listener{
	private Variables game;
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event){
		Player player = event.getPlayer();
		if (game.getGameState())
		{
		    if (game.getPlayerList().contains(player))
	        {
	    		event.setCancelled(true);
	    	}
	   	}
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event){
		Player player = event.getPlayer();
		if (game.getGameState())
		{
	    	if (game.getPlayerList().contains(player))
	    	{
			    event.setCancelled(true);
        	}
		}
	}
}
