package me.z3ndovo.applesplus;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CancelPlacement implements Listener{
	
	FileConfiguration config = Core.plugin.getConfig();
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onSkullPlace(BlockPlaceEvent e) {
  		Player p = e.getPlayer();
		Block block = e.getBlock();
		ItemStack item = p.getItemInHand();
		if (block.getType() == Material.SKULL) {
		List<String> lore = item.getItemMeta().getLore();
		if (lore != null) {
		for (String key : config.getConfigurationSection("apples").getKeys(false)) {
			if (lore.contains(ChatColor.translateAlternateColorCodes('&', config.getString("apples." + key + ".ilore")))) {
				e.setCancelled(true);
				for(String effectString : config.getStringList("apples." + key + ".effects")) {
					String[] values = effectString.split(":");
					PotionEffectType type;
					int duration = 30;
					int amplifier = 1;
					
					type = PotionEffectType.getByName(values[0]);
					if (type == null) {
						System.err.println(effectString + "is not a valid effect.");
						continue;
					}
					if (values.length > 1)
						duration = Integer.parseInt(values[1]);
					if (values.length > 2)
						amplifier = Integer.parseInt(values[2]);
				    if(p.hasPotionEffect(type))
				        p.removePotionEffect(type);
				    PotionEffect effect = type.createEffect(duration *20, amplifier);
				    p.addPotionEffect(effect);
				}
				    if (item.getAmount() > 1) item.setAmount(item.getAmount() - 1);
				    else ((Player) p).getInventory().clear(((Player) p).getInventory().getHeldItemSlot());
				    p.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("apples." + key + ".messageAfterConsumption")));
				    p.playSound(p.getLocation(), Sound.EAT, 2.0f, 2.0f);
				}
			}
		} else {
			e.setCancelled(false);
			return;
		}
	}
	}
}
