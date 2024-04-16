package Controller;

import Model.Server;
import Model.Task;

import java.util.List;

public class TimeStrategy implements Strategy {

    public void addTask(List<Server> servers, Task t) {

        Server minWaitingServer = servers.getFirst();
        for (Server s : servers) {
            if (s.getWaitingPeriod().get() < minWaitingServer.getWaitingPeriod().get()) {
                minWaitingServer = s;
            }
        }
        minWaitingServer.addTask(t);
    }
}
