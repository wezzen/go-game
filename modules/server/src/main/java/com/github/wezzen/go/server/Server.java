package com.github.wezzen.go.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static final int PORT = 9999;

    public static void main(String[] args) throws IOException {
        try (final ServerSocket ss = new ServerSocket(PORT)) {
            while (!ss.isClosed()) {
                final Socket client = ss.accept();
                new Thread(new ClientHandler(client)).start();
            }
        }
    }

}
