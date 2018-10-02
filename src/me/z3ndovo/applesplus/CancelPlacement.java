package me.z3ndovo.applesplus;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class CancelPlacement implements Listener{
	
	FileConfiguration config = Core.plugin.getConfig();
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onSkullPlace(BlockPlaceEvent e) {
/*        Player p = e.getPlayer();
        if(e.getBlock() == null){
            return;
        }

        Block block = e.getBlock();

        ItemStack item = p.getItemInHand();
        if(item == null){
            return;
        }

        if (block.getType() != Material.SKULL) {
            return;
        }

        if(item.getItemMeta() == null){
            return;
        }

        List<String> lore = item.getItemMeta().getLore();
        for (String key : config.getConfigurationSection("apples").getKeys(false)) {
            if (!lore.contains(ChatColor.translateAlternateColorCodes('&', config.getString("apples." + key + ".ilore")))) {
                e.setCancelled(true);;
            }
            
            e.setCancelled(true);
        }*/
  		Player p = e.getPlayer();
		Block block = e.getBlock();
		ItemStack item = p.getItemInHand();
		if (block.getType() == Material.SKULL) {
		try {
			item.getItemMeta().getLore();
		} catch (NullPointerException npe) {
			e.setCancelled(true);
			return;
		}
		}
		if (block.getType() == Material.SKULL) {
		List<String> lore = item.getItemMeta().getLore();
		if (lore != null) {
		for (String key : config.getConfigurationSection("apples").getKeys(false)) {
			if (lore.contains(ChatColor.translateAlternateColorCodes('&', config.getString("apples." + key + ".ilore")))) {
				e.setCancelled(true);
				}
			}
		} else {
			e.setCancelled(false);
			return;
		}
	}
	}
}
