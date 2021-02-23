package defineoutside.network;

import java.io.Serializable;

public class HubAddOrRemove implements Serializable {
    boolean add;
    String serverName;

    public HubAddOrRemove(boolean add, String serverName) {
        this.add = add;
        this.serverName = serverName;
    }

    public boolean isAdd() {
        return add;
    }

    public String getServerName() {
        return serverName;
    }
}
