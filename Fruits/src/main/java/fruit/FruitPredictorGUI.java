/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fruit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class FruitPredictorGUI {

    private JFrame frame;
    private JTextField sweetnessField, colorField, hiddenNeuronsField, learningRateField, maxEpochsField, goalErrorField;
    private JComboBox<String> hiddenActivationFunctionBox, outputActivationFunctionBox;
    public  JTextArea outputArea;
    private NeuralNetwork network;
    private List<FruitData> dataset;
    public  JLabel accuracy = new JLabel();


    public FruitPredictorGUI() {
        initialize();
        dataset = new ArrayList<>();
        dataset=readFile();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 460, 360);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        frame.getContentPane().add(panel);
        panel.setLayout(new GridLayout(0, 2, 2, 2));

         JLabel label=new JLabel("   Sweetness:");
        panel.add(label);
        sweetnessField = new JTextField();
        panel.add(sweetnessField);

        panel.add(new JLabel("   Color:"));
        colorField = new JTextField();
        panel.add(colorField);

        panel.add(new JLabel("   Hidden Neurons:"));
        hiddenNeuronsField = new JTextField();
        panel.add(hiddenNeuronsField);

        panel.add(new JLabel("   Hidden Layer Activation Function:"));
        hiddenActivationFunctionBox = new JComboBox<>(new String[]{"Tanh", "Sigmoid", "ReLU"});
        panel.add(hiddenActivationFunctionBox);

        panel.add(new JLabel("   Output Layer Activation Function:"));
        outputActivationFunctionBox = new JComboBox<>(new String[]{"Softmax", "Sigmoid"});
        panel.add(outputActivationFunctionBox);

        panel.add(new JLabel("  Learning Rate:"));
        learningRateField = new JTextField();
        panel.add(learningRateField);

        panel.add(new JLabel("   Max Epochs:"));
        maxEpochsField = new JTextField();
        panel.add(maxEpochsField);

        panel.add(new JLabel("   Goal Error:"));
        goalErrorField = new JTextField();
        panel.add(goalErrorField);
 
       accuracy.setText("   Accuracy:");
       panel.add(accuracy);
      panel.add(new JLabel());

        JButton trainButton = new JButton("Train Network");
        trainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                trainNetwork();
            }
        });
        panel.add(trainButton);

        JButton predictButton = new JButton("Predict");

        predictButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                predictFruit();
            }
        });
        panel.add(predictButton);
        panel.setBackground(new Color(255,255,102));
        

        outputArea = new JTextArea();
        outputArea.setBackground(new Color(255,255,102));
        frame.getContentPane().add(new JScrollPane(outputArea));
        frame.getContentPane().setBackground(new Color(255,255,102));
        frame.setVisible(true);
    }

    private void trainNetwork() {
        int hiddenNeurons = Integer.parseInt(hiddenNeuronsField.getText());
        String hiddenActivationFunction = hiddenActivationFunctionBox.getSelectedItem().toString();
        String outputActivationFunction = outputActivationFunctionBox.getSelectedItem().toString();
        double learningRate = Double.parseDouble(learningRateField.getText());
        int maxEpochs = Integer.parseInt(maxEpochsField.getText());
        double goalError = Double.parseDouble(goalErrorField.getText());

        ActivationFunction hiddenActivation = createActivationFunction(hiddenActivationFunction);
        ActivationFunction outputActivation = createActivationFunction(outputActivationFunction);

        network = new NeuralNetwork(2, hiddenNeurons, 3, learningRate, hiddenActivation, outputActivation, maxEpochs, goalError);


       
        network.train(dataset);
        outputArea.setText("Network trained.\n");
     double Accuracy = network.evaluatePerformance(network, dataset);
     accuracy.setText("   Accuracy: "+String.valueOf(Accuracy+"%"));
    outputArea.setText("Network trained.\n");
    String s=network.outputArea.getText();
        outputArea.append(s+ "\n");
    
    }

    private ActivationFunction createActivationFunction(String functionName) {
        
        if ("Tanh".equals(functionName)) {
            return new Tanh();
        } else if ("Sigmoid".equals(functionName)) {
            return new Sigmoid();
        } else if ("ReLU".equals(functionName)) {
            return new ReLU();
        } else if ("Softmax".equals(functionName)) {
            return new Softmax();
        }
        return null; 
    }

    private void predictFruit() {
        double sweetness = Double.parseDouble(sweetnessField.getText());
        int color = Integer.parseInt(colorField.getText());
        String predictedFruit = network.predict(sweetness, color);
        outputArea.setText("Predicted fruit: " + predictedFruit + "\n");
    }
    
     private List<FruitData> readFile() {
           List<FruitData> dataset = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("fruitData.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                int sweetness = Integer.parseInt(values[0]);
                int color = Integer.parseInt(values[1]);
                String type = values[2];
                dataset.add(new FruitData(sweetness, color, type));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
           return dataset;
        
    }


   
}

