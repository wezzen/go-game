package com.github.wezzen.go.client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {

    private static final int SERVER_PORT = 9999;

    public static void main(String[] args) throws IOException {
        try (final Socket socket = new Socket("localhost", SERVER_PORT)) {
            try (final DataOutputStream dos = new DataOutputStream(socket.getOutputStream())) {
                dos.writeUTF("FUCK");
            }
        }
    }

}
