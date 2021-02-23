package defineoutside.network;

import java.io.Serializable;
import java.net.InetAddress;

public class AddNonSubServer implements Serializable {
    public boolean add;
    public boolean lobby;
    public String name;
    public InetAddress inetAddress;
    public int port;

    public AddNonSubServer(boolean add, boolean lobby, String name, InetAddress inetAddress, int port) {
        this.add = add;
        this.lobby = lobby;
        this.name = name;
        this.inetAddress = inetAddress;
        this.port = port;
    }
}
