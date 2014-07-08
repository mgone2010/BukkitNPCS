package me.stopbox123.nms;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

import me.stopbox123.commands.SpawnNPC;

public class Equip implements Listener{
	
	HashMap<Player, PlayerNPC> equiping = SpawnNPC.equiping;
	
	@EventHandler
	public void onClick(PlayerInteractEntityEvent e) {
		if (e.getRightClicked() == equiping.get(e.getPlayer()).getBukkitEntity()) {
			ItemStack item = e.getPlayer().getItemInHand();
			if (item.getType() != Material.AIR && !e.getPlayer().isSneaking()) {
			if (item.getType() == Material.LEATHER_HELMET || item.getType() == Material.IRON_HELMET || item.getType() == Material.CHAINMAIL_HELMET || item.getType() == Material.DIAMOND_HELMET || item.getType() == Material.GOLD_HELMET) {
				equiping.get(e.getPlayer()).getBukkitEntity().getInventory().setHelmet(item);
				equiping.get(e.getPlayer()).sendUpatePackets();
			} else if (item.getType() == Material.LEATHER_CHESTPLATE || item.getType() == Material.IRON_CHESTPLATE || item.getType() == Material.CHAINMAIL_CHESTPLATE || item.getType() == Material.DIAMOND_CHESTPLATE || item.getType() == Material.GOLD_CHESTPLATE) {
				equiping.get(e.getPlayer()).getBukkitEntity().getInventory().setChestplate(item);
				equiping.get(e.getPlayer()).sendUpatePackets();
			} else if (item.getType() == Material.LEATHER_LEGGINGS || item.getType() == Material.IRON_LEGGINGS || item.getType() == Material.CHAINMAIL_LEGGINGS || item.getType() == Material.DIAMOND_LEGGINGS || item.getType() == Material.GOLD_LEGGINGS) {
					equiping.get(e.getPlayer()).getBukkitEntity().getInventory().setLeggings(item);
					equiping.get(e.getPlayer()).sendUpatePackets();
			} else if (item.getType() == Material.LEATHER_BOOTS || item.getType() == Material.IRON_BOOTS || item.getType() == Material.CHAINMAIL_BOOTS || item.getType() == Material.DIAMOND_BOOTS || item.getType() == Material.GOLD_BOOTS) {
				equiping.get(e.getPlayer()).getBukkitEntity().getInventory().setBoots(item);
				equiping.get(e.getPlayer()).sendUpatePackets();
			} else {
				equiping.get(e.getPlayer()).getBukkitEntity().setItemInHand(item);
				equiping.get(e.getPlayer()).sendUpatePackets();
			}
			} else if (e.getPlayer().isSneaking()) {
				equiping.get(e.getPlayer()).getBukkitEntity().getInventory().setHelmet(new ItemStack(Material.AIR, 0));
				equiping.get(e.getPlayer()).getBukkitEntity().getInventory().setChestplate(new ItemStack(Material.AIR, 0));
				equiping.get(e.getPlayer()).getBukkitEntity().getInventory().setLeggings(new ItemStack(Material.AIR, 0));
				equiping.get(e.getPlayer()).getBukkitEntity().getInventory().setBoots(new ItemStack(Material.AIR, 0));
				equiping.get(e.getPlayer()).getBukkitEntity().setItemInHand(new ItemStack(Material.AIR, 0));
				equiping.get(e.getPlayer()).sendUpatePackets();
			}
		}
	}
}
