package defineoutside.network;

import java.io.Serializable;
import java.util.UUID;

public class ServerAddedInfo implements Serializable {
    UUID serverUUID;
    String subName;
    public ServerAddedInfo(UUID serverUUID, String subName) {
        this.serverUUID = serverUUID;
        this.subName = subName;
    }

    public UUID getServerUUID() {
        return serverUUID;
    }

    public String getSubName() {
        return subName;
    }
}
