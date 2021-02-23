package defineoutside.network;

import java.io.Serializable;
import java.util.UUID;

public class QueueData implements Serializable {
    UUID player;
    String server;
    public QueueData(UUID player, String server) {
        this.player = player;
        this.server = server;
    }

    public UUID getPlayer() {
        return player;
    }

    public String getServer() {
        return server;
    }
}
