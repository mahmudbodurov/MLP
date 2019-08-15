/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Network;

import com.Network.Functions.ActivationFunction;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 *
 * @author Mahmud
 */
public class Network implements Serializable, Cloneable {

    private static final long serialVersionUID = 2783104235278450127L;

    protected ActivationFunction activationFunction;
    protected double LearningRate;
    protected int inputCount;
    protected int outputCount;
    protected int LayerCount;
    public Layer[] layers;

    public Network(int layer[], double LearningRate, ActivationFunction activationFunction) {
        layers = new Layer[layer.length];
        Neuron[] inputs = new Neuron[layer[0]];
        //input layer
        for (int j = 0; j < inputs.length; j++) {
            inputs[j] = new Neuron(0, 0, "");
            layers[0] = new Layer(0, inputs);
        }
        for (int i = 1; i < layer.length; i++) {
            Neuron[] neurons = new Neuron[layer[i]];
            int prevLayerSize = layer[i - 1];
            for (int j = 0; j < neurons.length; j++) {
                neurons[j] = new Neuron(prevLayerSize, i, "");
            }
            layers[i] = new Layer(i, neurons);
        }
        this.LearningRate = LearningRate;
        this.activationFunction = activationFunction;
        inputCount = layer[0];
        outputCount = layer[layer.length - 1];
        LayerCount = layer.length;
    }

    public double getExecuteMax(double[] input) {
        double[] output = execute(input);

        int max = 0;
        for (int i = 1; i < output.length; i++) {
            if (output[i] > output[max]) {
                max = i;
            }
        }
        return output[max];
    }

    public double[] execute(double[] input) {
        Neuron[] inputs = layers[0].neurons;
        for (int i = 0; i < inputs.length; i++) {
            inputs[i].output = input[i];
        }
        //Layers 2
        for (int i = 1; i < LayerCount - 1; i++) {

            for (int j = 0; j < layers[i].size(); j++) {
                double new_value = 0.0;
                for (int k = 0; k < layers[i - 1].size(); k++) {
                    new_value += layers[i - 1].neurons[k].output * layers[i].neurons[j].prevConnection[k];
                }

                new_value += layers[i].neurons[j].Bias;
                layers[i].neurons[j].output = new_value;//activationFunction.evulate(new_value);
            }
        }
        //for output
        for (int j = 0; j < layers[layers.length - 1].size(); j++) {
            double new_value = 0.0;
            for (int k = 0; k < layers[layers.length - 2].size(); k++) {
                new_value += layers[layers.length - 2].neurons[k].output * layers[layers.length - 1].neurons[j].prevConnection[k];
            }

            new_value += layers[layers.length - 1].neurons[j].Bias;
            layers[layers.length - 1].neurons[j].output = activationFunction.evulate(new_value);
        }
        double output[] = new double[outputCount];
        for (int i = 0; i < outputCount; i++) {

            output[i] = layers[layers.length - 1].neurons[i].output;
        }
        return output;
    }

    public int getInputLayerSize() {
        return layers[0].neurons.length;
    }
//geriyə  izləmə alqoritmi

    public double backPropagate(double[] input, double[] output) {
        double new_output[] = execute(input);
        double error;
        /* doutput = correct output (output) */
        // Calcoliamo l'errore dell'output
        for (int i = 0; i < layers[layers.length - 1].size(); i++) {
            error = output[i] - new_output[i];
            layers[layers.length - 1].neurons[i].Delta = error * activationFunction.evaluteDerivate(new_output[i]);
        }

        for (int k = layers.length - 2; k >= 0; k--) {
            //Mevcut katmanın hatasını hesaplayın ve deltaları yeniden hesaplayın
            for (int i = 0; i < layers[k].size(); i++) {
                error = 0.0;
                for (int j = 0; j < layers[k + 1].size(); j++) {
                    error += layers[k + 1].neurons[j].Delta * layers[k + 1].neurons[j].prevConnection[i];
                }

                layers[k].neurons[i].Delta = error * activationFunction.evaluteDerivate(layers[k].neurons[i].output);
            }

            //System.out.println(" Bir sonraki katmanın ağırlıklarını güncelle");
            // Bir sonraki katmanın ağırlıklarını güncelle
            for (int i = 0; i < layers[k + 1].size(); i++) {
                for (int j = 0; j < layers[k].size(); j++) {
                    layers[k + 1].neurons[i].prevConnection[j] += LearningRate * layers[k + 1].neurons[i].Delta
                            * layers[k].neurons[j].output;
                }
                layers[k + 1].neurons[i].Bias += LearningRate * layers[k + 1].neurons[i].Delta;
            }
        }

// hatayı hesaplayalım
        error = 0.0;

        for (int i = 0; i < output.length; i++) {
            error += Math.abs(new_output[i] - output[i]);

            //System.out.println(output[i]+" "+new_output[i]);
        }
        error = error / output.length;
        return error;
    }

    public boolean save(String path) {
        try {
            FileOutputStream fout = new FileOutputStream(path);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(this);
            oos.close();
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public static Network load(String path) {
        try {
            Network net;

            FileInputStream fin = new FileInputStream(path);
            ObjectInputStream oos = new ObjectInputStream(fin);
            net = (Network) oos.readObject();
            oos.close();

            return net;
        } catch (Exception e) {
            return null;
        }
    }

    public void setLearningRate(double LearningRate) {
        this.LearningRate = LearningRate;
    }

    public double getLearningRate() {
        return LearningRate;
    }

}


/*input summing
    protected double inputSumming() {

        double sum = 0;
        Layer PrevLayer = layers[LayerID - 1];
        for (int i = 0; i < PrevLayer.size(); i++) {
            sum += PrevLayer.getNeuron(i).outputValue() * prevConnection[i];
        }

        return sum;///prev layer neurons output sum
    }

    public double outputValue() {
        if (LayerID == 0) {
            return output;
        }
        output = Vars.activationFunction.evulate(inputSumming());
        if (LayerID != layers.length - 1) {
            output += Bias;
        }
        System.out.println("Neuron id="+id+" Layer id="+LayerID);
        return output;
    }

 */


