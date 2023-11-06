package com.github.wezzen.go.server;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler implements Runnable, Closeable {

    private final Socket client;

    private final DataInputStream in;

    private final DataOutputStream out;

    public ClientHandler(final Socket client) throws IOException {
        this.client = client;
        in = new DataInputStream(client.getInputStream());
        out = new DataOutputStream(client.getOutputStream());
    }

    @Override
    public void run() {
        try {
            final String msg = in.readUTF();
            System.out.println("Got message from client: " + msg);
        } catch (final IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void close() throws IOException {
        in.close();
        out.close();
    }
}
