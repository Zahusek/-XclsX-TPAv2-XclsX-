package com.gmail.zahusek.tinyprotocolapi.essential;

import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.Plugin;

import com.gmail.zahusek.tinyprotocolapi.annotations.PacketHandler;
import com.gmail.zahusek.tinyprotocolapi.interfaces.PacketListener;
import com.gmail.zahusek.tinyprotocolapi.utility.Reflection;
import com.gmail.zahusek.tinyprotocolapi.utility.Reflection.*;
import com.google.common.collect.Lists;
import com.google.common.collect.MapMaker;
import com.mojang.authlib.GameProfile;

/**
 * @author Kristian
 * Modified by me. Patterned on PacketAPI (@BigTeddy98 ^^)
 * Orginal TPAPI - https://github.com/aadnk/ProtocolLib/tree/master/modules/TinyProtocol/src/main/java/com/comphenix/tinyprotocol
 **/

public class TinyProtocol extends Overlay {

	private static final AtomicInteger ID = new AtomicInteger(0);

	private static final MethodInvoker getPlayerHandle = Reflection.getMethod(
			"{obc}.entity.CraftPlayer", "getHandle");
	private static final FieldAccessor<Object> getConnection = Reflection
			.getField("{nms}.EntityPlayer", "playerConnection", Object.class);
	private static final FieldAccessor<Object> getManager = Reflection
			.getField("{nms}.PlayerConnection", "networkManager", Object.class);
	private static final FieldAccessor<Channel> getChannel = Reflection
			.getField("{nms}.NetworkManager", Channel.class, 0);

	private static final Class<Object> minecraftServerClass = Reflection
			.getUntypedClass("{nms}.MinecraftServer");
	private static final Class<Object> serverConnectionClass = Reflection
			.getUntypedClass("{nms}.ServerConnection");
	private static final FieldAccessor<Object> getMinecraftServer = Reflection
			.getField("{obc}.CraftServer", minecraftServerClass, 0);
	private static final FieldAccessor<Object> getServerConnection = Reflection
			.getField(minecraftServerClass, serverConnectionClass, 0);
	private static final MethodInvoker getNetworkMarkers = Reflection
			.getTypedMethod(serverConnectionClass, null, List.class,
					serverConnectionClass);

	private static final Class<?> PACKET_LOGIN_IN_START = Reflection
			.getMinecraftClass("PacketLoginInStart");
	private static final FieldAccessor<GameProfile> getGameProfile = Reflection
			.getField(PACKET_LOGIN_IN_START, GameProfile.class, 0);

	private Map<String, Channel> channelLookup = new MapMaker().weakValues()
			.makeMap();
	private Listener listener;

	private Set<Channel> uninjectedChannels = Collections
			.newSetFromMap(new MapMaker().weakKeys()
					.<Channel, Boolean> makeMap());

	private List<Object> networkManagers;

	private List<Channel> serverChannels = Lists.newArrayList();
	private ChannelInboundHandlerAdapter serverChannelHandler;
	private ChannelInitializer<Channel> beginInitProtocol;
	private ChannelInitializer<Channel> endInitProtocol;

	private String handlerName;

	protected volatile boolean closed;
	protected Plugin plugin;

	public TinyProtocol(Plugin plugin) {
		this.plugin = plugin;
		this.handlerName = getHandlerName();
		registerBukkitEvents();
		registerChannelHandler();
		registerPlayers(plugin);
	}

	private void createServerChannelHandler() {
		endInitProtocol = new ChannelInitializer<Channel>() {

			@Override
			protected void initChannel(Channel channel) throws Exception {
				try {
					synchronized (networkManagers) {
						if (!closed) {
							injectChannelInternal(channel);
						}
					}
				} catch (Exception e) {
					plugin.getLogger().log(Level.SEVERE,
							"Cannot inject incomming channel " + channel, e);
				}
			}

		};

		beginInitProtocol = new ChannelInitializer<Channel>() {

			@Override
			protected void initChannel(Channel channel) throws Exception {
				channel.pipeline().addLast(endInitProtocol);
			}

		};

		serverChannelHandler = new ChannelInboundHandlerAdapter() {

			@Override
			public void channelRead(ChannelHandlerContext ctx, Object msg)
					throws Exception {
				Channel channel = (Channel) msg;

				channel.pipeline().addFirst(beginInitProtocol);
				ctx.fireChannelRead(msg);
			}

		};
	}

	private void registerBukkitEvents() {
		listener = new Listener() {

			@EventHandler(priority = EventPriority.LOWEST)
			public final void onPlayerLogin(PlayerLoginEvent e) {
				if (closed)
					return;

				Channel channel = getChannel(e.getPlayer());

				if (!uninjectedChannels.contains(channel)) {
					injectPlayer(e.getPlayer());
				}
			}

			@EventHandler
			public final void onPluginDisable(PluginDisableEvent e) {
				if (e.getPlugin().equals(plugin)) {
					close();
				}
			}

		};

		plugin.getServer().getPluginManager().registerEvents(listener, plugin);
	}

	@SuppressWarnings("unchecked")
	private void registerChannelHandler() {
		Object mcServer = getMinecraftServer.get(Bukkit.getServer());
		Object serverConnection = getServerConnection.get(mcServer);
		boolean looking = true;

		networkManagers = (List<Object>) getNetworkMarkers.invoke(null,
				serverConnection);
		createServerChannelHandler();

		for (int i = 0; looking; i++) {
			List<Object> list = Reflection.getField(
					serverConnection.getClass(), List.class, i).get(
					serverConnection);

			for (Object item : list) {
				if (!ChannelFuture.class.isInstance(item))
					break;

				Channel serverChannel = ((ChannelFuture) item).channel();

				serverChannels.add(serverChannel);
				serverChannel.pipeline().addFirst(serverChannelHandler);
				looking = false;
			}
		}
	}

	private void unregisterChannelHandler() {
		if (serverChannelHandler == null)
			return;

		for (Channel serverChannel : serverChannels) {
			final ChannelPipeline pipeline = serverChannel.pipeline();

			serverChannel.eventLoop().execute(new Runnable() {

				@Override
				public void run() {
					try {
						pipeline.remove(serverChannelHandler);
					} catch (NoSuchElementException e) {
					}
				}

			});
		}
	}

	private void registerPlayers(Plugin plugin) {
		for (Player player : plugin.getServer().getOnlinePlayers()) {
			injectPlayer(player);
		}
	}

	public void sendPacket(Player player, Object packet) {
		sendPacket(getChannel(player), packet);
	}

	private void sendPacket(Channel channel, Object packet) {
		channel.pipeline().writeAndFlush(packet);
	}

	public void receivePacket(Player player, Object packet) {
		receivePacket(getChannel(player), packet);
	}

	private void receivePacket(Channel channel, Object packet) {
		channel.pipeline().context("encoder").fireChannelRead(packet);
	}

	protected String getHandlerName() {
		return "TinyProtocol(" + plugin.getName() + ")" + ID.incrementAndGet();
	}

	public void injectPlayer(Player player) {
		injectChannelInternal(getChannel(player)).player = player;
	}

	public void injectChannel(Channel channel) {
		injectChannelInternal(channel);
	}

	private PacketInterceptor injectChannelInternal(Channel channel) {
		try {
			PacketInterceptor interceptor = (PacketInterceptor) channel
					.pipeline().get(handlerName);

			if (interceptor == null) {
				interceptor = new PacketInterceptor();
				channel.pipeline().addBefore("packet_handler", handlerName,
						interceptor);
				uninjectedChannels.remove(channel);
			}

			return interceptor;
		} catch (IllegalArgumentException e) {
			return (PacketInterceptor) channel.pipeline().get(handlerName);
		}
	}

	public Channel getChannel(Player player) {
		Channel channel = channelLookup.get(player.getName());

		if (channel == null) {
			Object connection = getConnection.get(getPlayerHandle
					.invoke(player));
			Object manager = getManager.get(connection);

			channelLookup.put(player.getName(),
					channel = getChannel.get(manager));
		}

		return channel;
	}

	private void uninjectPlayer(Player player) {
		uninjectChannel(getChannel(player));
	}

	private void uninjectChannel(final Channel channel) {
		if (!closed) {
			uninjectedChannels.add(channel);
		}

		channel.eventLoop().execute(new Runnable() {

			@Override
			public void run() {
				channel.pipeline().remove(handlerName);
			}

		});
	}

	public boolean hasInjected(Player player) {
		return hasInjected(getChannel(player));
	}

	private boolean hasInjected(Channel channel) {
		return channel.pipeline().get(handlerName) != null;
	}

	private final void close() {
		if (!closed) {
			closed = true;

			for (Player player : plugin.getServer().getOnlinePlayers()) {
				uninjectPlayer(player);
			}

			HandlerList.unregisterAll(listener);
			unregisterChannelHandler();
		}
	}

	private final class PacketInterceptor extends ChannelDuplexHandler {
		public volatile Player player;

		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg)
				throws Exception {
			final Channel channel = ctx.channel();
			handleLoginStart(channel, msg);

			for (Entry<PacketListener, List<Method>> listener : IN.entrySet()) {
				for (Method method : listener.getValue()) {
					PacketHandler ann = method
							.getAnnotation(PacketHandler.class);
					if (!ann.packet().getName()
							.equals(msg.getClass().getSimpleName()))
						continue;

					PacketEvent evt = new PacketEvent(player, msg);
					method.setAccessible(true);
					method.invoke(listener.getKey(), evt);
					method.setAccessible(false);
					msg = evt.getPacket();
				}
			}
			if (msg != null) {
				super.channelRead(ctx, msg);
			}
		}

		@Override
		public void write(ChannelHandlerContext ctx, Object msg,
				ChannelPromise promise) throws Exception {
			for (Entry<PacketListener, List<Method>> listener : OUT.entrySet()) {
				for (Method method : listener.getValue()) {
					
					PacketHandler ann = method.getAnnotation(PacketHandler.class);
					if (!ann.packet().getName().equals(msg.getClass().getSimpleName()))
						continue;
					PacketEvent evt = new PacketEvent(player, msg);
					method.setAccessible(true);
					method.invoke(listener.getKey(), evt);
					msg = evt.getPacket();
				}
			}

			if (msg != null) {
				super.write(ctx, msg, promise);
			}
		}

		private void handleLoginStart(Channel channel, Object packet) {
			if (PACKET_LOGIN_IN_START.isInstance(packet)) {
				GameProfile profile = getGameProfile.get(packet);
				channelLookup.put(profile.getName(), channel);
			}
		}
	}
}
