package me.z3ndovo.applesplus;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

public class Core extends JavaPlugin{
	
	public static Core plugin;
	
	public void onEnable() {
		saveDefaultConfig();
		plugin = this;
		getLogger().info("ApplesPlus by z3ndovo enabled!");
		getServer().getPluginManager().registerEvents(new Interface(), this);
		getServer().getPluginManager().registerEvents(new CancelPlacement(), this);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String Label, String[] args) {
		
		if (args.length < 3) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cApples &8> &4Invalid usage! &7Use: &e/giveapple {player} {type} {amount}"));
			return false;
			}
		
		Player targetPlayer = this.getServer().getPlayer(args[0]);
		String type = args[1];
		
		try{
	    	@SuppressWarnings("unused")
			int test = Integer.parseInt(args[2]);
	    } catch(NumberFormatException e) {
	        targetPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cApples &8> &4Invalid usage! &7Use: &e/giveapple {player} {type} {amount}"));
	        return false;
	    }
		
		int amount = Integer.parseInt(args[2]);
		
		if (sender.hasPermission("apples.give")) {
			targetPlayer.getInventory().addItem(getSkull((getConfig().getString("apples." + type + ".url")), getConfig().getString("apples." + type + ".name"), getConfig().getStringList("apples." + type + ".lore"), amount, getConfig().getString("apples." + type + ".fake-uuid")));
		} else {
			sender.sendMessage("You do not have the permission to use this command.");
		}
		
		return false;
	}
	
		public ItemStack getSkull(String url, String name, List<String> slore, int amount, String uuid) {
		        ItemStack skull = new ItemStack(Material.SKULL_ITEM, amount, (short) 3);
		        if (url.isEmpty()) return skull;
		        
		        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
		        GameProfile profile = new GameProfile(UUID.fromString(uuid), null);
		        profile.getProperties().put("textures", new Property("textures", url	));
		        Field profileField = null;
		        
		        try {
		            profileField = skullMeta.getClass().getDeclaredField("profile");
		        } catch (NoSuchFieldException | SecurityException e) {
		            e.printStackTrace();
		        }
		        profileField.setAccessible(true);
		        try {
		            profileField.set(skullMeta, profile);
		        } catch (IllegalArgumentException | IllegalAccessException e) {
		            e.printStackTrace();
		        }
		        
		        List<String> lore = new ArrayList<>();
		        for(String s : slore) {
		        lore.add(ChatColor.translateAlternateColorCodes('&', s));
		        }
		        skullMeta.setLore(lore);
		        skullMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
				skull.setItemMeta(skullMeta);
		        
				return skull;
		    }

}
