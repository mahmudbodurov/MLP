/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mlp_rev.pkg2;

import Network.Network;
import com.Network.Functions.SigmoidFunction;
import com.Network.utility.ImageProcessingBW;
import java.util.Scanner;

/**
 *
 * @author Mahmud
 */
public class MLP_rev2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int size = 16;
        boolean isRunning = true;
        double error = 0.0;
        double accouracy = 0.01;
        int nimagesxpatt = 89;//89;//folder inside image size 88image counting start at 1 
        int npatt = 10;//pattern size--folders size
        int dir = 1;
        int cpatt = 1;
        int maxit = 5000;//iteration count
        double last_error = 10;
        int[] layer = {size * size, size, npatt};
        Network net = new Network(layer, 0.7, new SigmoidFunction());
//        net = Network.load("C:\\Users\\Mahmud\\Documents\\NetBeansProjects\\MLP_rev-2\\data");
        /* Learning */
        //<editor-fold defaultstate="collapsed" desc="for-loop learning">
        for (int i = 0; i < maxit; i++) {
            for (int k = 1; k < nimagesxpatt; k++) {
                for (int j = 1; j < npatt + 1; j++) {
                    String pattern = "E:\\Documents\\NetBeansProjects\\NNetworkDesigner\\img/patterns/" + j + "/" + k + ".png";
                    double[] inputs = ImageProcessingBW.loadImage(pattern, size, size);

                    if (inputs == null) {
                        System.out.println("Cant find " + pattern);
                        continue;

                    }
                    double[] output = new double[npatt];

                    for (int l = 0; l < npatt; l++) {
                        output[l] = 0.0;
                    }

                    output[j - 1] = 1.0;

                    // Training
                    error = net.backPropagate(inputs, output);
                    if (error <= 0.0000005) {
                        isRunning = false;
                    }
//                        if (i % 100 == 0) {

                    System.out.println("Error is " + error + " (" + i + " " + j + " " + k + " ) and Learning rate = " + net.getLearningRate());
//                        }
                }
            }
            System.out.println("iteration " + i);
            if (i % 50 == 0) {
//                    net.setLearningRate(net.getLearningRate() / 1.05d);

                System.out.println(net.save("E:\\Documents\\NetBeansProjects\\MLP_rev-2\\data"));
            }
            if (!isRunning) {
                break;
            }
            //</editor-fold>
        }

        System.out.println(
                "Learning completed!");

        /* Test */
        int correct = 0;
        String pattern = "E:\\Documents\\NetBeansProjects\\NNetworkDesigner\\img/patterns/" + 2 + "/" + 8 + ".png";

        double[] inputs = ImageProcessingBW.loadImage(pattern, size, size);
        double[] output = net.execute(inputs);

        int max = 0;
        for (int i = 1;
                i < npatt;
                i++) {
            if (output[i] > output[max]) {
                max = i;
            }
        }

        System.out.println(
                "Il valore massimo e' " + output[max] + " pattern " + (max + 1));
        Scanner scanner = new Scanner(System.in);

        while (true) {

            String path = scanner.nextLine();
            if (path.equals("exit")) {
                break;
            }
            inputs = ImageProcessingBW.loadImage(path, size, size);
            output = net.execute(inputs);

            max = 0;
            for (int i = 1; i < npatt; i++) {
                if (output[i] > output[max]) {
                    max = i;
                }
            }
            System.out.println("Il valore massimo e' " + output[max] + " pattern " + (max + 1));

        }
    }

}


