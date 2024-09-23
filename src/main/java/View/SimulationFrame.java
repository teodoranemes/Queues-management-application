package View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class SimulationFrame {
    private JPanel panel1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextArea textArea1;
    private JTextArea textArea2;
    private JTextArea textArea3;
    private JButton startSimulationButton;
    private JRadioButton timeStrategyRadioButton;
    private JRadioButton shortestQueueStrategyRadioButton;
    private JTextField textField6;
    private JTextField textField7;
    private JTextArea textArea4;
    private JTextArea textArea5;
    private JTextArea textArea6;

    public SimulationFrame() {
        startSimulationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
    }

    public JPanel getPanel1() {
        return panel1;
    }

    public JTextField getTextField1() {
        return textField1;
    }

    public JTextField getTextField2() {
        return textField2;
    }

    public JTextField getTextField3() {
        return textField3;
    }

    public JTextField getTextField4() {
        return textField4;
    }

    public JTextField getTextField5() {
        return textField5;
    }

    public JTextField getTextField6() {
        return textField6;
    }

    public JTextField getTextField7() {
        return textField7;
    }

    public JTextArea getTextArea1() {
        return textArea1;
    }

    public JTextArea getTextArea2() {
        return textArea2;
    }

    public JTextArea getTextArea3() {
        return textArea3;
    }

    public JTextArea getTextArea4() {
        return textArea4;
    }

    public JTextArea getTextArea5() {
        return textArea5;
    }

    public JTextArea getTextArea6() {
        return textArea6;
    }

    public JButton getStartSimulationButton() {
        return startSimulationButton;
    }

    public JRadioButton getTimeStrategyRadioButton() {
        return timeStrategyRadioButton;
    }

    public JRadioButton getShortestQueueStrategyRadioButton() {
        return shortestQueueStrategyRadioButton;
    }
}
