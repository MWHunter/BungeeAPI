package defineoutside.network;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import org.abyssmc.servermanager.ServerManager;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.util.logging.Level;

public class receivePlayerTransferAndCommands {
    static NetworkInfo networkInfo;
    static ProxyServer proxyServer;

    CreateConnectionToMainframe createConnectionToMainframe = new CreateConnectionToMainframe();

    //static HashSet<String> hubServers = new HashSet<>();

    public void ConnectToMainframe(String host) {
        Runnable connect = () -> {
            while (true) {
                try {
                    networkInfo = createConnectionToMainframe.connectToMainframe(host, 27469, "MainBungee#Input");
                    proxyServer.getLogger().log(Level.INFO, "Connected to the central server");


                } catch (IOException e) {
                    e.printStackTrace();
                    // it restarts later
                }

                while (true) {
                    try {
                        // Now listen for players being sent
                        ObjectInputStream objectInputStream = new ObjectInputStream(networkInfo.getInputStream());

                        Object received = objectInputStream.readObject();

                        proxyServer.getLogger().log(Level.SEVERE, received.getClass().toString() + (received instanceof PlayerTransferData));

                        if (received instanceof ServerCommand) {
                            ServerCommand serverCommand = (ServerCommand) received;

                            proxyServer.getPluginManager().dispatchCommand(proxyServer.getConsole(), serverCommand.getCommand());
                        }

                        if (received instanceof AddNonSubServer) {
                            AddNonSubServer nonSubServer = (AddNonSubServer) received;

                            if (nonSubServer.add) {
                                ServerInfo info = ProxyServer.getInstance().constructServerInfo(nonSubServer.name, new InetSocketAddress(nonSubServer.inetAddress, nonSubServer.port), "AbyssMC", false);
                                ProxyServer.getInstance().getServers().put(nonSubServer.name, info);

                                if (nonSubServer.lobby) { // Bungee responsible for choosing lobbies to allow players to login almost instantly

                                    ServerManager.lobbies.add(info.getName());
                                }
                            } else {
                                ServerManager.lobbies.remove(nonSubServer.name);
                                ProxyServer.getInstance().getServers().remove(nonSubServer.name);

                                proxyServer.getLogger().log(Level.INFO, "Removed lobby " + nonSubServer.name);
                            }
                            //ProxyServer.getInstance().addServer(nonSubServer.name, nonSubServer.inetAddress, nonSubServer.port, "AbyssMC", false, false);
                        }

                        // I don't know why this is needed
                        // TODO: Why does received instanceof HubAddOrRemove not work
                        if (received instanceof PlayerTransferData) {
                            PlayerTransferData playerTransferData = (PlayerTransferData) received;
                            proxyServer.getLogger().log(Level.SEVERE, "Player UUID is " + playerTransferData.getPlayerUUID().toString());
                            proxyServer.getLogger().log(Level.SEVERE, "Server sending to is " + playerTransferData.getSubServer());

                            proxyServer.getPlayer(playerTransferData.getPlayerUUID()).connect(proxyServer.getServerInfo(playerTransferData.getSubServer()));
                        }

                    } catch (Exception e) {

                        e.printStackTrace();
                        proxyServer.getLogger().log(Level.WARNING, "Disconnected from the central server");

                        // Loop again!
                        try {
                            Thread.sleep(10000);
                        } catch (InterruptedException ex) {
                            // nothing
                        }

                        break;
                    }
                }
            }

        };

        Thread thread = new Thread(connect);
        thread.start();
    }

    public void setProxy(ProxyServer proxy) {
        proxyServer = proxy;
    }
}
