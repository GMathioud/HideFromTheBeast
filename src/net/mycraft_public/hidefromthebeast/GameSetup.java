package net.mycraft_public.hidefromthebeast;

import java.util.Iterator;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class GameSetup{
	private Variables game;
	private Main main;
	
	private static GameSetup instance = new GameSetup();

	private GameSetup(){

	}

	public static GameSetup getInstance(){
		return instance;
	}

	
	public void ArmorSetup(){
		ItemStack helmet = new ItemStack(Material.GOLD_HELMET);
		ItemStack chestplate = new ItemStack(Material.GOLD_CHESTPLATE);
		ItemStack legs = new ItemStack(Material.GOLD_LEGGINGS);
		ItemStack boots = new ItemStack(Material.GOLD_BOOTS);
    	PlayerInventory setup = game.getBeast().getInventory();
    	setup.setHelmet(helmet);
    	setup.setChestplate(chestplate);
    	setup.setLeggings(legs);
    	setup.setBoots(boots);
	}
	
	public void ArmorRemove(){
		PlayerInventory setup = game.getBeast().getInventory();
    	setup.setHelmet(null);
    	setup.setChestplate(null);
    	setup.setLeggings(null);
    	setup.setBoots(null);
	}
	public void Stop(){
		game.setGameState(false);
        Iterator<Player> it = game.getPlayerList().iterator();
        while(it.hasNext())
        {
        	Player pl = it.next();
        	pl.setGameMode(GameMode.SURVIVAL);
        	pl.teleport(pl.getWorld().getSpawnLocation());
        }
        game.getPlayerList().clear();
        Bukkit.broadcastMessage(game.getPrefix() + "Game stopped. All players were moved to spawn.");
	}
	
	public void ChooseTheBeast(){
		Random rand = new Random();
    	int i = rand.nextInt(game.getPlayerList().size());
    	Player beastRes = main.getServer().getOnlinePlayers()[i];
    	game.setBeast(beastRes);
	}
}
