package com.first;

import org.apache.zookeeper.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * Created by mu on 2017/10/28.
 */
public class ZookeeperConnect {
    private String connectionString ="192.168.89.2:2181";
    private int sessionTimeout=2000;

    private Watcher w = new Watcher() {
        /**
         * Watched事件
         */
        public void process(WatchedEvent event) {
            System.out.println("WatchedEvent >>> " + event.toString());
            getChild();
        }
    };
    static ZooKeeper zk=null;

    public void init() throws IOException {
        zk = new ZooKeeper("192.168.89.2:2181", 20000, this.w);
    }

    public void create() throws KeeperException, InterruptedException {
        String s = zk.create("/mu", "myData2".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(s);
    }

    public void getData() throws KeeperException, InterruptedException {
        System.out.println(zk);
        byte[] data = zk.getData("/", false, null);
        System.out.println(new String(data));
    }

    public void isexist() throws KeeperException, InterruptedException {
        System.out.println(zk);
        System.out.println(zk.exists("/app1",false));
    }

    public void getChild()  {
        try {
            List<String> children = zk.getChildren("/", true);
            for (String s:children
                 ) {
                System.out.println(s);
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        ZookeeperConnect z = new ZookeeperConnect();

        z.init();
        z.create();
        z.isexist();
        z.getData();
        z.getChild();
    }
}
