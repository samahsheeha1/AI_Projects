
package fruit;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JTextArea;

 public class NeuralNetwork {
     int inputSize ; // sweetness and color
    int hiddenSize ; // number of neurons in the hidden layer
    int outputSize; 
    double[][] weights1;
    double[] biases1 ;
    double[][] weights2 ;
    double[] biases2 ;
    double goalError ;
    int epochs;
    double learningRate;
    ActivationFunction hiddenActivation;
    ActivationFunction outputActivation ;
    JTextArea outputArea= new JTextArea();
;

    NeuralNetwork(int inputsize, int hiddenSize, int outputsize, double learningRate, ActivationFunction hiddenActivation, ActivationFunction outputActivation, int epochs, double goalError) {
    this.inputSize=inputsize;
    this.outputSize=outputsize;
        this.hiddenSize = hiddenSize;
        this.learningRate = learningRate;
        this.hiddenActivation = hiddenActivation;
        this.outputActivation = outputActivation;
        this.goalError=goalError;
          this.epochs=epochs;
    
    }

    NeuralNetwork() {
    }
    public  double evaluatePerformance(NeuralNetwork network, List<FruitData> dataset) {
        int correctPredictions = 0;
        for (FruitData data : dataset) {
            String actualFruit = data.type;
            String predictedFruit = network.predict(data.sweetness, data.color);


            if (actualFruit.equals(predictedFruit)) {
                correctPredictions++;
            }
        }
        

        double accuracy = ((double) correctPredictions / dataset.size() )*100 ;
        return accuracy;
        
    }


   public void train(List<FruitData> dataset) {
     weights1 = new double[hiddenSize][inputSize];
     biases1 = new double[hiddenSize];
     weights2 = new double[outputSize][hiddenSize];
   biases2 = new double[outputSize];
  

   double x =2;
    Random random = new Random();

    
    for (int i = 0; i < hiddenSize; i++) {
        for (int j = 0; j < inputSize; j++) {
            weights1[i][j] =(random.nextDouble() * 4.8 / x) - (2.4 / x);
        }
        biases1[i] = (random.nextDouble() * 4.8 / x) - (2.4 / x);
    }
    for (int i = 0; i < outputSize; i++) {
        for (int j = 0; j < hiddenSize; j++) {
            weights2[i][j] = (random.nextDouble() * 4.8 / x) - (2.4 / x);
        }
        biases2[i] = (random.nextDouble() * 4.8 / x) - (2.4 / x);
    }

    // Training algorithm: Gradient descent
    long startTime = System.currentTimeMillis(); // Start time

    double totalError;
    for (int epoch = 0; epoch < epochs; epoch++) {
         totalError = 0.0;
        for (FruitData data : dataset) {
            // Forward propagation
            double[] input = new double[]{data.sweetness, (double) data.color};
            double[] hidden = new double[hiddenSize];
            for (int i = 0; i < hiddenSize; i++) {
                double netInput = 0;
                for (int j = 0; j < inputSize; j++) {
                    netInput += input[j] * weights1[i][j];
                }
                netInput += biases1[i];
                hidden[i] = hiddenActivation.apply(netInput);
            }
            double[] output = new double[outputSize];
            for (int i = 0; i < outputSize; i++) {
                double netInput = 0;
                for (int j = 0; j < hiddenSize; j++) {
                    netInput += hidden[j] * weights2[i][j];
                }
                netInput += biases2[i];
                output[i] = outputActivation.apply(netInput);
            }

            // Backpropagation
            double[] expectedOutput = new double[outputSize];

            
    if (data.type.equals("apple")) {
        expectedOutput[0] = 1; // Apple
    
} else if (data.type.equals("orange")) {
        expectedOutput[1] = 1; // Orange
    
} else if (data.type.equals("banana")) {
        expectedOutput[2] = 1; // Banana
    
}  

    

            
            
            double[] Delta = new double[outputSize];
            double[] error = new double[outputSize];

            for (int i = 0; i < outputSize; i++) {
                error[i]=output[i] - expectedOutput[i];
                Delta[i] = error[i]*outputActivation.derivative(output[i]);
            }
            double[] hiddenError = new double[hiddenSize];
            for (int i = 0; i < hiddenSize; i++) {
                double sum = 0;
                for (int j = 0; j < outputSize; j++) {
                    sum += Delta[j] * weights2[j][i];
                }
                hiddenError[i] = sum * hiddenActivation.derivative(hidden[i]);
            }

            // Update weights and biases
            for (int i = 0; i < outputSize; i++) {
                for (int j = 0; j < hiddenSize; j++) {
                    weights2[i][j] -= learningRate * Delta[i] * hidden[j];
                }
                biases2[i] -= learningRate * Delta[i];
            }
            for (int i = 0; i < hiddenSize; i++) {
                for (int j = 0; j < inputSize; j++) {
                    weights1[i][j] -= learningRate * hiddenError[i]* input[j];
                }
                biases1[i] -= learningRate * hiddenError[i];
            }
            for (int i = 0; i < outputSize; i++) {
                totalError +=  Math.pow(error[i], 2);
            }
        }
        

        outputArea.append("Epoch " + (epoch + 1) + ": Loss = " + totalError + "\n"); 


        // Check if total Delta is below the goal
        if (totalError < goalError) {
          outputArea.append("Training stopped. Total error reached the goal." + "\n");
            break;
        
        }
    }
        long endTime = System.currentTimeMillis(); // End time
    long trainingTime = endTime - startTime; // Calculate training time

    outputArea.append("Training completed in " + trainingTime + " milliseconds.\n");

}

    public String predict(double sweetness, int color) {
       
    double[] input = new double[]{sweetness, (double) color};
    double[] hidden = new double[hiddenSize];
    
   

    for (int i = 0; i < hiddenSize; i++) {
        double netInput = 0;
        for (int j = 0; j < inputSize; j++) {
            netInput += input[j] * weights1[i][j];
        }
        netInput += biases1[i];
        hidden[i] = hiddenActivation.apply(netInput);
    }

    double[] output = new double[outputSize];
    for (int i = 0; i < outputSize; i++) {
        double netInput = 0;
        for (int j = 0; j < hiddenSize; j++) {
            netInput += hidden[j] * weights2[i][j];
        }
        netInput += biases2[i];
        output[i] = outputActivation.apply(netInput);
    }

    int predictedClass = 0;
    double maxProbability = 0;
    for (int i = 0; i < outputSize; i++) {
        if (output[i] > maxProbability) {
            maxProbability = output[i];
            predictedClass = i;
        }
    }
    


       if (predictedClass == 0 ) {
        return "apple";
    } else if (predictedClass == 1 ) {
        return "orange";
    } else if (predictedClass == 2 ) {
        return "banana";
    } else {
        return "Unknown"; // Return "Unknown" if the data does not match any criteria
    }

}
}

