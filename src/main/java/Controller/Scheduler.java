package Controller;

import Model.Server;
import Model.Task;

import java.util.ArrayList;
import java.util.List;

public class Scheduler {

    private List<Server> servers;
    private int maxNoServers;
    private int maxTasksPerServer;
    private Strategy strategy;

    public Scheduler(int maxNoServers, int maxTasksPerServer){

        this.maxNoServers = maxNoServers;
        this.maxTasksPerServer = maxTasksPerServer;
        this.servers = new ArrayList<>();
        for(int i = 0; i < maxNoServers; i++){
            Server s = new Server();
            servers.add(s);
            Thread t = new Thread(s);
            t.start();
        }
    }

    public void changeStrategy(SelectionPolicy policy){

        if(policy == SelectionPolicy.SHORTEST_QUEUE)
            strategy = new ShortestQueueStrategy();

        if(policy == SelectionPolicy.SHORTEST_TIME)
            strategy = new TimeStrategy();
    }

    public void dispatchTasks(Task t){
        strategy.addTask(servers, t);
    }

    public List<Server> getServers(){
        return servers;
    }

    public String printScheduler(){

        String totalString = "";
        int nrServer=0;
        for(Server server: servers)
        {
            nrServer++;
            String serverPrint = "Queue " + nrServer + " "  + server.printTasks();
            totalString += serverPrint;
            totalString += "\n";

        }
        return totalString;
    }



}
