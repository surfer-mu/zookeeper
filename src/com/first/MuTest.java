package com.first;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.List;

/**
 * Created by mu on 2017/10/28.
 */
public class MuTest {
    private int  sessionTimeOut=200000;
    private String server="192.168.89.2:2181";
    Watcher w=new Watcher() {
        @Override
        public void process(WatchedEvent event) {
            System.out.println(event.getPath()+","+event.toString());
            try {
                zk.getChildren("/", true);
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };
    static ZooKeeper zk=null;


    public void init() throws IOException {
        zk=new ZooKeeper(server,sessionTimeOut,w);
    }
    public void isexist() throws KeeperException, InterruptedException {
        System.out.println(zk.exists("/app2",false));
    }
    public void create() throws KeeperException, InterruptedException {
        zk.create("/hahsa66","haha66".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);

    }

    public void setValue() throws KeeperException, InterruptedException {
        zk.setData("/app1","java".getBytes(),-1);
    }
    private void getChild() throws KeeperException, InterruptedException {
        zk.getChildren("/", true);

        /*for (String s:list
             ) {
            System.out.println(s);
        }*/
        System.out.println("zoni ");
        Thread.sleep(Integer.MAX_VALUE);
    }

    public static void main(String[] args) throws Exception {
        MuTest muTest = new MuTest();
        muTest.init();
      //  muTest.create();
        //muTest.isexist();
        zk.delete("/hahsa66",-1);
        muTest.getChild();
       // muTest.setValue();

    }
}
