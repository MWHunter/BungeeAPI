package org.abyssmc.servermanager;

import defineoutside.network.PlayerQueue;
import defineoutside.network.QueueData;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class QueueListener extends Command {
    public QueueListener() {
        super("joinqueue", null);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args[0] != null) {
            PlayerQueue playerQueue = new PlayerQueue();
            playerQueue.addToQueue(new QueueData(((ProxiedPlayer)sender).getUniqueId(), args[0]));
        }
    }
}
