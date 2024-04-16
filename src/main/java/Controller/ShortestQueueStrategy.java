package Controller;

import Model.Server;
import Model.Task;

import java.util.List;

public class ShortestQueueStrategy implements Strategy {

    public void addTask(List<Server> servers, Task t) {

        Server minQueueServer = servers.getFirst();
        for (Server server : servers) {
            if (server.getTasks().length < minQueueServer.getTasks().length) {
                minQueueServer = server;
            }
        }
        minQueueServer.addTask(t);
    }
}
