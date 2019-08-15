/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Network;

import java.util.ArrayList;

/**
 *
 * @author Mahmud
 */
public class Layer {

    private int LayerType;
    Neuron[] neurons;

    public Layer(int LayerType, Neuron[] neurons) {
        this.LayerType = LayerType;
        this.neurons = neurons;
    }

    public Neuron[] getNeurons() {
        return neurons;
    }

    public Neuron getNeuron(int index) {
        return neurons[index];
    }

    public void setNeurons(Neuron[] neurons) {
        this.neurons = neurons;
    }

//    public boolean addNeuron(Neuron neuron) {
//      
//        return neurons.add(neuron);
//    }

    public int getLayerType() {
        return LayerType;
    }

    public int size() {
        return neurons.length;
    }
}



