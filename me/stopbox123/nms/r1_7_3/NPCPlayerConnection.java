package me.stopbox123.nms.r1_7_3;

import net.minecraft.server.v1_7_R3.EntityPlayer;
import net.minecraft.server.v1_7_R3.EnumProtocol;
import net.minecraft.server.v1_7_R3.IChatBaseComponent;
import net.minecraft.server.v1_7_R3.NetworkManager;
import net.minecraft.server.v1_7_R3.Packet;
import net.minecraft.server.v1_7_R3.PacketPlayInAbilities;
import net.minecraft.server.v1_7_R3.PacketPlayInArmAnimation;
import net.minecraft.server.v1_7_R3.PacketPlayInBlockDig;
import net.minecraft.server.v1_7_R3.PacketPlayInBlockPlace;
import net.minecraft.server.v1_7_R3.PacketPlayInChat;
import net.minecraft.server.v1_7_R3.PacketPlayInClientCommand;
import net.minecraft.server.v1_7_R3.PacketPlayInCloseWindow;
import net.minecraft.server.v1_7_R3.PacketPlayInCustomPayload;
import net.minecraft.server.v1_7_R3.PacketPlayInEnchantItem;
import net.minecraft.server.v1_7_R3.PacketPlayInEntityAction;
import net.minecraft.server.v1_7_R3.PacketPlayInFlying;
import net.minecraft.server.v1_7_R3.PacketPlayInKeepAlive;
import net.minecraft.server.v1_7_R3.PacketPlayInSetCreativeSlot;
import net.minecraft.server.v1_7_R3.PacketPlayInSettings;
import net.minecraft.server.v1_7_R3.PacketPlayInSteerVehicle;
import net.minecraft.server.v1_7_R3.PacketPlayInTabComplete;
import net.minecraft.server.v1_7_R3.PacketPlayInTransaction;
import net.minecraft.server.v1_7_R3.PacketPlayInUpdateSign;
import net.minecraft.server.v1_7_R3.PacketPlayInUseEntity;
import net.minecraft.server.v1_7_R3.PacketPlayInWindowClick;
import net.minecraft.server.v1_7_R3.PlayerConnection;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_7_R3.CraftServer;

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
