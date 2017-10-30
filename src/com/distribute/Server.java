package com.distribute;

import org.apache.zookeeper.*;

import java.io.IOException;

/**
 * Created by mu on 2017/10/29.
 */
public class Server {
    private int sessionTimeOut=20000;
    private String connectString="192.168.89.2:2181,192.168.89.3:2181,192.168.89.4:2181";
    private String parentNode="/servers";
    ZooKeeper zooKeeper=null;
    public void connect() throws IOException {
        zooKeeper=new ZooKeeper(connectString, sessionTimeOut, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println(event.getPath()+","+event.toString());
                try {
                    zooKeeper.getChildren("/",true);
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void registerServer(String hostName) throws KeeperException, InterruptedException {
        zooKeeper.create(parentNode+"/server",hostName.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println(hostName+"is online");
    }
    public void handleBussiness(String name) throws KeeperException, InterruptedException {
        System.out.println(name+"is work");
        Thread.sleep(Integer.MAX_VALUE);
    }

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        Server server = new Server();
        server.connect();
        server.registerServer(args[0]);
        server.handleBussiness(args[0]);
    }
}
