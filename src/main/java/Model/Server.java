package Model;

import Controller.Scheduler;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable {

    private BlockingQueue<Task> tasks;
    private AtomicInteger waitingPeriod;

    public Server(){

        this.tasks = new LinkedBlockingQueue<>();
        this.waitingPeriod = new AtomicInteger(0);
    }

    public void addTask(Task newTask){

        tasks.add(newTask);
        waitingPeriod.incrementAndGet();
    }

    public void run() {
        while (true) {
            try {
                Task t = tasks.peek();
                if (t != null) {
                    int x = t.getServiceTime();
                    Thread.sleep(x * 1000);
                    t.decrementServiceTime();
                    waitingPeriod.decrementAndGet();
                    t = tasks.take();
                    t.setCompletionTime((int) System.currentTimeMillis());
                }

            } catch (InterruptedException e) {
                System.out.println("Task could not be added to the queue");
            }
        }
    }

    public Task[] getTasks(){
        return tasks.toArray(new Task[tasks.size()]);
    }

    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }

    public String printTasks() {

        String totalString = "";
        for (Task task : tasks) {

            totalString += task;
        }
        return totalString;
    }
}
