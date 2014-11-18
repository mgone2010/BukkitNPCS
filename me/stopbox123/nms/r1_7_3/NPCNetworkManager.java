package me.stopbox123.nms.r1_7_3;

import java.lang.reflect.Field;
import java.net.SocketAddress;

import me.stopbox123.nms.unversioned.NPCChannel;
import net.minecraft.server.v1_7_R3.NetworkManager;
import net.minecraft.util.io.netty.channel.Channel;
import net.minecraft.util.io.netty.util.AttributeKey;

public class NPCNetworkManager extends NetworkManager {
	
	@SuppressWarnings("unchecked")
	public NPCNetworkManager() {
		super(false);
		
		try {
			Field channel = NetworkManager.class.getDeclaredField("m");
			Field address = NetworkManager.class.getDeclaredField("n");
			
			channel.setAccessible(true);
			address.setAccessible(true);
			
			Channel parent = new NPCChannel(null);
			
			try {
				Field protocolVersion = NetworkManager.class.getDeclaredField("protocolVersion");
				parent.attr(((AttributeKey<Integer>) protocolVersion.get(null))).set(47);
			} catch (NoSuchFieldException e) { }
			
			channel.set(this, parent);
			address.set(this, new SocketAddress() {
				private static final long serialVersionUID = 3609305649906536413L;
			});
		} catch (Exception e) { }
	}

}
