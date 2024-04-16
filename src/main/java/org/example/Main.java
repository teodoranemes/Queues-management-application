package org.example;

import View.SimulationFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            SwingUtilities.invokeLater(() -> {


                SimulationFrame frame = new SimulationFrame();
                JFrame mainWindow = new JFrame("Simulare");
                mainWindow.setContentPane(frame.getPanel1());
                mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                mainWindow.pack();
                mainWindow.setVisible(true);

            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
