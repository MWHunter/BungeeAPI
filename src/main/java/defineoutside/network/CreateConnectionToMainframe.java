package defineoutside.network;

import java.io.*;
import java.net.Socket;

public class CreateConnectionToMainframe {
    NetworkInfo connectToMainframe(String host, Integer port, String hostName) throws IOException {

        Socket s = new Socket(host, port);
        DataInputStream dis = new DataInputStream(s.getInputStream());
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());
        //ObjectInputStream objectInputStream = new ObjectInputStream(s.getInputStream());

        String msg = dis.readUTF();

        // Authenticating
        if (msg.equals("CRva7SfCPaiBrS7cZh6bNXuupO0qnfOYrgOCZceQFWcFjbiksI1mgcUyhO31AZtz10k6Kj8Ji5XQ0pMObC2BXEKg2XptcVjFdGf")) {
            dos.writeUTF("4v2NZ8RTar54k4PoYEsnjxpL0IObNMgediJQP65QwUwmm9hBw1hQCJvxcSo6tIDwiHY2RkYzmVMWIpN8Oe4rrmPxVum2PBwBnL6");
            dos.writeUTF(hostName);
        } // Authentication complete else
        else {
            return null;
        }

        return new NetworkInfo(s.getInputStream(), s.getOutputStream());
    }
}
