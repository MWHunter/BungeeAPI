package defineoutside.network;

import java.io.Serializable;

public class ServerCommand implements Serializable {
    String command;
    public ServerCommand(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
