/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mlp_rev.pkg2;

import Data.data;
import static Data.data.net;
import Network.Layer;
import Network.Network;
import Network.Neuron;
import com.Network.Functions.SigmoidFunction;
import com.Network.utility.ImageProcessingBW;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author Mahmud
 */
public class testMLP {

    public static void main(String[] args) {
        int[] layer = {3, 4, 4, 3};
        Network net = new Network(layer, 0.6, new SigmoidFunction());
        boolean isRunning = true;
        double error = 0.0;
        int maxit = 500000;//iteration count
//        net = Network.load("C:\\Users\\Mahmud\\Documents\\NetBeansProjects\\MLP_rev-2\\data");

        long time = System.currentTimeMillis();
        /* Learning */
        //<editor-fold defaultstate="collapsed" desc="for-loop learning">
        for (int i = 0; i < maxit; i++) {
            for (int j = 0; j < data.x.length; j++) {
                double output[] = data.y[j];                  //verilmeli cavab

                // Training
                error = net.backPropagate(data.x[j], output);
                System.out.println("error is " + error);
            }
            if (System.currentTimeMillis() - time >= 40000) {
                break;
            }
        }
        //</editor-fold>

        System.out.println("Learning completed!");

        /* Test */
        int correct = 0;
        for (int i = 0; i < data.x.length; i++) {
            double[] inputs = data.x[i];
            double[] output = net.execute(inputs);

            System.out.println("input is " + Arrays.toString(inputs));
            System.out.println("output is " + Arrays.toString(output));
            System.out.println("++++++++++++++++++++++++++++++++++++++");
        }
        for (int i = 1; i < net.layers.length; i++) {
            Layer lay = net.layers[i];
            Neuron[] neurons = lay.getNeurons();
            for (int j = 0; j < neurons.length; j++) {
                System.out.println(Arrays.toString(neurons[j].prevConnection));
            }
        }

    }
}


