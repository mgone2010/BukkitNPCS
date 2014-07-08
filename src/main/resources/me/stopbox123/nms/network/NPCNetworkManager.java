package me.stopbox123.nms.network;

import java.lang.reflect.Field;

import net.minecraft.server.v1_7_R3.NetworkManager;

public class NPCNetworkManager extends NetworkManager {

	public NPCNetworkManager() throws Exception {
		super(false);
		this.swapFields();
	}

	protected void swapFields() throws Exception {
		Field channel = NetworkManager.class.getDeclaredField("m");
		Field address = NetworkManager.class.getDeclaredField("n");

		channel.setAccessible(true);
		address.setAccessible(true);

		channel.set(this, new NullChannel(null));
		address.set(this, null);
	}

}
