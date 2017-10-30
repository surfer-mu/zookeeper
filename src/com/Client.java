package com;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by mu on 2017/10/29.
 */
public class Client {
    private int sessionTimeOut=20000;
    private String connectString="192.168.89.2:2181,192.168.89.3:2181,192.168.89.4:2181";
    private String parentNode="/servers";
    private volatile List servers;
    ZooKeeper zooKeeper=null;
    public void connect() throws IOException {
        zooKeeper=new ZooKeeper(connectString, sessionTimeOut, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println(event.getPath()+" is broken");
                try {
                    getServers();
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
    }
    public void getServers() throws KeeperException, InterruptedException {
        List<String> list = zooKeeper.getChildren(parentNode, true);
        List servers=new ArrayList();
        for (String s:list
             ) {
            byte[] data = zooKeeper.getData(parentNode + "/" + s, false, null);
            servers.add(new String(data));
        }

        this.servers=servers;

        System.out.println(servers);
    }
    public void handleBussiness() throws InterruptedException {
        System.out.println("client is work");
        Thread.sleep(Integer.MAX_VALUE);
    }
    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        Client client = new Client();
        client.connect();
        client.getServers();
        client.handleBussiness();
    }
}
