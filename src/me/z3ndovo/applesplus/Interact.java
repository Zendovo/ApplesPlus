package me.z3ndovo.applesplus;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class Interact implements Listener{
	
	FileConfiguration config = Core.plugin.getConfig();
	
	@EventHandler
	public void onConsume(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		ItemStack item = p.getItemInHand();
		if ((item.getType() != Material.AIR) && item.getItemMeta().hasLore()) {
		if (item.getType() == (Material.SKULL_ITEM)) {
			for (String key : config.getConfigurationSection("apples").getKeys(false)) {
				p.sendMessage(config.getString("apples." + key + ".lore"));
			}
		}
	}
}
}
