package defineoutside.network;

import java.io.Serializable;
import java.util.UUID;

public class PlayerTransferData implements Serializable {
    UUID playerUUID;
    String subServer;
    public PlayerTransferData(UUID playerUUID, String subServerName) {
        this.playerUUID = playerUUID;
        this.subServer = subServerName;
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public String getSubServer() {
        return subServer;
    }
}
