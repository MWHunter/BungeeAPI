package org.abyssmc.servermanager;

import defineoutside.network.PlayerQueue;
import defineoutside.network.receivePlayerTransferAndCommands;
import net.md_5.bungee.api.Callback;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ServerManager extends Plugin implements Listener {
    public static String host = "127.0.0.1";
    public static List<String> lobbies = new ArrayList<>();
    Random random = new Random();

    public void onEnable() {
        receivePlayerTransferAndCommands receivePlayerTransferAndCommands = new receivePlayerTransferAndCommands();
        receivePlayerTransferAndCommands.setProxy(getProxy());
        PlayerQueue playerQueue = new PlayerQueue();
        playerQueue.setProxy(getProxy());


        getLogger().info("Coded by DefineOutside <3");
        getProxy().getPluginManager().registerListener(this, this);

        receivePlayerTransferAndCommands.ConnectToMainframe(host);
        playerQueue.ConnectToMainframe(host);

        getProxy().getScheduler().schedule(this, () -> {
            for (ServerInfo server : getProxy().getServers().values()) {
                server.ping(receiveCallback(server));
            }
        }, 15, 15, TimeUnit.SECONDS);
    }


    public Callback<ServerPing> receiveCallback(ServerInfo server) {
        return (result, error) -> {
            if (error != null) {
                getLogger().info("Removing dead lobby " + server.getName());

                lobbies.remove(server.getName());

                getProxy().getServers().remove(server.getName());
            }
        };
    }


    public void onDisable() {
        getLogger().info("Exiting!");
    }


    /*@EventHandler
    public void onPostLogin(PostLoginEvent event) {
        //PlayerQueue.queueData.add(new QueueData(event.getPlayer().getUniqueId(), "Lobby"));
        for (ServerInfo serverInfo : ProxyServer.getInstance().getServers().values()) {
            getProxy().getLogger().log(Level.WARNING, serverInfo.toString());
        }
        event.getPlayer().connect(ProxyServer.getInstance().getServerInfo("4894088d-9135-47cf-a8d8-68ac48a65d53"));
    }*/

    @EventHandler
    public void onPlayerConnectToServer(ServerConnectEvent event) {
        System.out.println("Reason: " + event.getReason().toString());
        if (event.getReason().equals(ServerConnectEvent.Reason.JOIN_PROXY)) {
            // TODO: Allow multiple hubs
            event.setTarget(lobbies.get(random.nextInt(lobbies.size())));
            System.out.println("Current lobbies " + lobbies.toString());
        }
    }
}
