package defineoutside.network;

import java.io.Serializable;
import java.util.UUID;

public class DataQueue implements Serializable {
    UUID playerUUID;
    String gameType;

    public DataQueue(UUID uuid, String gameType) {
        this.playerUUID = uuid;
        this.gameType = gameType;
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public String getGameType() {
        return gameType;
    }
}
