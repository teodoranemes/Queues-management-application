package Controller;

import Model.Server;
import Model.Task;
import View.SimulationFrame;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class SimulationManager implements Runnable {

    private int timeLimit;
    private int maxProcessingTime;
    private int minProcessingTime;
    private int maxArrivalTime;
    private int minArrivalTime;
    private int numberOfServers;
    private int numberOfClients;
    private SelectionPolicy selectionPolicy = SelectionPolicy.SHORTEST_TIME;
    private Scheduler scheduler;
    private List<Task> generatedTasks;
    private SimulationFrame frame;
    private String fileName = "simulation_results.txt";

    public SimulationManager(SimulationFrame frame) {
        this.frame = frame;
        frame.getStartSimulationButton().addActionListener(e -> startSimulation());
    }

    public void initializeSimulation() {
        try {
            timeLimit = Integer.parseInt(frame.getTextField1().getText());
            maxProcessingTime = Integer.parseInt(frame.getTextField3().getText());
            minProcessingTime = Integer.parseInt(frame.getTextField4().getText());
            maxArrivalTime = Integer.parseInt(frame.getTextField6().getText());
            minArrivalTime = Integer.parseInt(frame.getTextField7().getText());
            numberOfServers = Integer.parseInt(frame.getTextField5().getText());
            numberOfClients = Integer.parseInt(frame.getTextField2().getText());

            if (frame.getTimeStrategyRadioButton().isSelected()) {
                selectionPolicy = SelectionPolicy.SHORTEST_TIME;
            } else if (frame.getShortestQueueStrategyRadioButton().isSelected()) {
                selectionPolicy = SelectionPolicy.SHORTEST_QUEUE;
            }

            if (timeLimit <= 0 || maxProcessingTime <= 0 || minProcessingTime <= 0 ||
                    numberOfClients <= 0 || numberOfServers <= 0 || maxArrivalTime <= 0 || minArrivalTime < 0) {
                throw new IllegalArgumentException("Bound must be positive");
            }

            scheduler = new Scheduler(numberOfServers, maxProcessingTime);
            scheduler.changeStrategy(selectionPolicy);
            generateNRandomTasks();
            for (Server server : scheduler.getServers()) {
                Thread serverThread = new Thread(server);
                serverThread.start();
            }
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(frame.getPanel1(), "Date introduse incorecte: " + e.getMessage(), "Eroare", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void generateNRandomTasks() {
        generatedTasks = new ArrayList<>();
        Random random = new Random();
        for (int i = 1; i <= numberOfClients; i++) {
            int arrivalTime = random.nextInt(maxArrivalTime - minArrivalTime + 1) + minArrivalTime;
            int serviceTime = random.nextInt(maxProcessingTime - minProcessingTime + 1) + minProcessingTime;
            Task task = new Task(i, arrivalTime, serviceTime);
            generatedTasks.add(task);
        }
        generatedTasks.sort(Comparator.comparingInt(Task::getArrivalTime));
    }


    public void run() {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            int currentTime = 0;
            initializeSimulation();
            double totalWaitingTime = 0;
            double totalServiceTime = countServiceTime();
            int maxClientsInQueueTime = 0;
            int maxClientsInQueue = 0;


            while (currentTime <= timeLimit) {
                Iterator<Task> iterator = generatedTasks.iterator();
                while (iterator.hasNext()) {
                    Task task = iterator.next();
                    if (task.getArrivalTime() == currentTime) {
                        scheduler.dispatchTasks(task);
                        iterator.remove();
                    }
                }
                String schedulerInfo = String.valueOf(currentTime);
                String waitingClientsInfo = scheduler.printScheduler();
                writer.write("Waiting Clients:" + generatedTasks + "\n");
                writer.write(waitingClientsInfo);
                writer.write("\n");
                String serverQueuesInfo = "Waiting clients: " + generatedTasks;
                frame.getTextArea1().setText(schedulerInfo);
                frame.getTextArea2().setText(waitingClientsInfo);
                frame.getTextArea3().setText(serverQueuesInfo);


                for (Server server : scheduler.getServers()) {
                    Task[] tasks = server.getTasks();
                    for (Task task : tasks) {
                        totalWaitingTime += task.getWaitingTime(currentTime);
                    }
                }
                int clientsInQueue = countClientsInQueue(scheduler.getServers());
                if (clientsInQueue > maxClientsInQueue) {
                    maxClientsInQueue = clientsInQueue;
                    maxClientsInQueueTime = currentTime;
                }

                writer.write("Time: " + currentTime + "\n");


                currentTime++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


                double averageWaitingTime = totalWaitingTime / numberOfClients;
                double averageServiceTime =  totalServiceTime/ numberOfClients;
                frame.getTextArea4().setText(String.valueOf(averageWaitingTime));
                frame.getTextArea5().setText(String.valueOf(averageServiceTime));
                frame.getTextArea6().setText(String.valueOf(maxClientsInQueueTime));
                writer.write("Average Waiting Time: " + (totalWaitingTime / numberOfClients) + "\n");
                writer.write("Average Service Time: " + (totalServiceTime / numberOfClients) + "\n");
                writer.write("Max Clients In Queue Time: " + maxClientsInQueueTime + "\n");



        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private int countServiceTime(){

        int serviceTime = 0;
        for(Task t: generatedTasks)
            serviceTime += t.getServiceTime();
        return serviceTime;

    }

    private int countClientsInQueue(List<Server> servers) {
        int totalClients = 0;
        for (Server server : servers) {
            totalClients += server.getWaitingPeriod().get();
        }
        return totalClients;
    }

    public void startSimulation() {
        Thread simulationThread = new Thread(this);
        simulationThread.start();
    }

    public String toString()
    {
        return timeLimit + "" + maxProcessingTime + minProcessingTime + numberOfServers + numberOfClients;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SimulationFrame frame = new SimulationFrame();
            JFrame mainWindow = new JFrame("Simulation Manager");
            mainWindow.setContentPane(frame.getPanel1());
            mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainWindow.pack();
            mainWindow.setLocationRelativeTo(null);
            mainWindow.setVisible(true);
            SimulationManager s = new SimulationManager(frame);
            System.out.println(s);

        });
    }
}
