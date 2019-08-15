/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Network;

import java.util.Random;

/**
 *
 * @author Mahmud
 */
public class Neuron {

    String name;
    public final int id;
    public double[] prevConnection;
    public double output;
    public double Bias;
    public double Delta;
    int LayerID;

    public Neuron(int PrevLayerSize, int LayerID, String Name) {
        name = Name;
        id = Vars.getNext();

        this.LayerID = LayerID;
        Bias = 1;//Math.random() / 10000000000000.0;
        Delta = Math.random() / 10000000000000.0;
        if (LayerID != 0) {
            Random rand = new Random();
            prevConnection = new double[PrevLayerSize];
            for (int i = 0; i < PrevLayerSize; i++) {
                prevConnection[i] = rand.nextDouble();
            }
        }
    }

}







