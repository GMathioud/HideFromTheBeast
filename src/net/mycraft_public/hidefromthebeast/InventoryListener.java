package net.mycraft_public.hidefromthebeast;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryListener implements Listener{
	private Variables game;
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event){
		Player player = (Player) event.getWhoClicked();
	    if (game.getGameState())
	    {
	    	if (player == game.getBeast())
	    	{
	    		event.setCancelled(true);
	    	}
	    }
	}
}
