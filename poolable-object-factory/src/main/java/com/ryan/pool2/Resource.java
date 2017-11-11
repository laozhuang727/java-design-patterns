package com.ryan.pool2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Resource {
  private static int id;
  private int rid;
  private Socket socket = new Socket();;

  public Resource() {
    synchronized (this) {
      this.rid = id++;
    }
  }

  public void close() throws IOException {
    this.socket.close();

  }

  public static int getId() {
    return id;
  }

  public Socket getSocket() {
    return socket;
  }

  public int getRid() {
    return this.rid;
  }

  @Override
  public String toString() {
    return "id:" + this.rid;
  }

}