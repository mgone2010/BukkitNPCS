package me.stopbox123.nms.r1_7_4;

import net.minecraft.server.v1_7_R4.EntityPlayer;
import net.minecraft.server.v1_7_R4.EnumProtocol;
import net.minecraft.server.v1_7_R4.IChatBaseComponent;
import net.minecraft.server.v1_7_R4.NetworkManager;
import net.minecraft.server.v1_7_R4.Packet;
import net.minecraft.server.v1_7_R4.PacketPlayInAbilities;
import net.minecraft.server.v1_7_R4.PacketPlayInArmAnimation;
import net.minecraft.server.v1_7_R4.PacketPlayInBlockDig;
import net.minecraft.server.v1_7_R4.PacketPlayInBlockPlace;
import net.minecraft.server.v1_7_R4.PacketPlayInChat;
import net.minecraft.server.v1_7_R4.PacketPlayInClientCommand;
import net.minecraft.server.v1_7_R4.PacketPlayInCloseWindow;
import net.minecraft.server.v1_7_R4.PacketPlayInCustomPayload;
import net.minecraft.server.v1_7_R4.PacketPlayInEnchantItem;
import net.minecraft.server.v1_7_R4.PacketPlayInEntityAction;
import net.minecraft.server.v1_7_R4.PacketPlayInFlying;
import net.minecraft.server.v1_7_R4.PacketPlayInKeepAlive;
import net.minecraft.server.v1_7_R4.PacketPlayInSetCreativeSlot;
import net.minecraft.server.v1_7_R4.PacketPlayInSettings;
import net.minecraft.server.v1_7_R4.PacketPlayInSteerVehicle;
import net.minecraft.server.v1_7_R4.PacketPlayInTabComplete;
import net.minecraft.server.v1_7_R4.PacketPlayInTransaction;
import net.minecraft.server.v1_7_R4.PacketPlayInUpdateSign;
import net.minecraft.server.v1_7_R4.PacketPlayInUseEntity;
import net.minecraft.server.v1_7_R4.PacketPlayInWindowClick;
import net.minecraft.server.v1_7_R4.PlayerConnection;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_7_R4.CraftServer;

public class NPCPlayerConnection extends PlayerConnection {

	public NPCPlayerConnection(NetworkManager networkManager, EntityPlayer entityPlayer) {
		super(((CraftServer) Bukkit.getServer()).getServer(), networkManager, entityPlayer);
	}
	
	@Override
	public void disconnect(String s) { }
	
	@Override
	public void a(PacketPlayInSteerVehicle packetPlayInSteerVehicle) { }
	
	@Override
	public void a(PacketPlayInFlying packetPlayInFlying) { }
	
	@Override
	public void a(PacketPlayInBlockDig packetPlayInBlockDig) { }
	
	@Override
	public void a(PacketPlayInBlockPlace packetPlayInBlockPlace) { }
	
	@Override
	public void a(IChatBaseComponent iChatBaseComponent) { }
	
	@Override
	public void sendPacket(Packet packet) { }
	
	@Override
	public void a(PacketPlayInChat packetPlayInChat) { }
	
	@Override
	public void a(PacketPlayInArmAnimation packetPlayInArmAnimation) { }
	
	@Override
	public void a(PacketPlayInEntityAction playInEntityAction) { }
	
	@Override
	public void a(PacketPlayInUseEntity packetPlayInUseEntity) { }
	
	@Override
	public void a(PacketPlayInClientCommand packetPlayInClientCommand) { }
	
	@Override
	public void a(PacketPlayInCloseWindow packetPlayInCloseWindow) { }
	
	@Override
	public void a(PacketPlayInWindowClick packetPlayInWindowClick) { }
	
	@Override
	public void a(PacketPlayInEnchantItem packetPlayInEnchantItem) { }
	
	@Override
	public void a(PacketPlayInSetCreativeSlot playInSetCreativeSlot) { }
	
	@Override
	public void a(PacketPlayInTransaction packetPlayInTransaction) { }
	
	@Override
	public void a(PacketPlayInUpdateSign packetPlayInUpdateSign) { }
	
	@Override
	public void a(PacketPlayInKeepAlive playInKeepAlive) { }
	
	@Override
	public void a(PacketPlayInAbilities packetPlayInAbilities) { }
	
	@Override
	public void a(PacketPlayInTabComplete packetPlayInTabComplete) { }
	
	@Override
	public void a(PacketPlayInSettings packetPlayInSettings) { }
	
	@Override
	public void a(PacketPlayInCustomPayload packetPlayInCustomPayload) { }
	
	@Override
	public void a(EnumProtocol enumProtocol, EnumProtocol enumProtocol2) { }
}
