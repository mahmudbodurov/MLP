/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mlp_rev.pkg2;

import Constant.Const;
import Constant.Functions;
import Constant.Range;
import Network.Layer;
import Network.Neuron;
import genetics.Population;
import Data.data;
import static Data.data.net;
import static Data.data.*;
import com.Network.utility.ImageProcessingBW;
import genetics.Chromosome;
import java.util.Arrays;

/**
 *
 * @author Mahmud
 */
public class test {

    static int dt;

    //ga ve linear regression birlesdirmek
    public static void main(String[] args) {
        int length = 0;
//        for (int k = 1; k <2; k++) {
//            for (int j = 1; j < npatt + 1; j++) {
//                String pattern = "E:\\Documents\\NetBeansProjects\\NNetworkDesigner\\img\\patterns\\" + j + "/" + 1 + ".png";
//                double[] inputs = ImageProcessingBW.loadImage(pattern, size, size);
//
//                if (inputs == null) {
//                    System.out.println("Cant find " + pattern);
//                    continue;
//                }
//
//                double[] output = new double[npatt];
//
//                for (int l = 0; l < npatt; l++) {
//                    output[l] = 0.0;
//                }
//                output[j - 1] = 1.0;
//                data.x[length] = inputs;
//                data.y[length] = output;
//                length++;
//            }
//
//        }
//        System.out.println("length = " + length);

        //funksiyani daxil edirik
        Const.setFunction(new Functions<Double, Double>() {

            @Override
            public Double compute(Double... x) {
                //neyron sebeke burda olacaq
                Layer[] layers = net.layers;
                int pos = 0;
                for (int i = 1; i < layers.length; i++) {
                    Neuron[] neurons = layers[i].getNeurons();
                    int prevLayerSize = layers[i - 1].size();
                    for (int j = 0; j < neurons.length; j++) {
                        for (int k = 0; k < prevLayerSize; k++) {
                            neurons[j].prevConnection[k] = x[pos];//i * k + j * prevLayerSize
                            pos++;
//                            System.out.print(neurons[j].prevConnection[k] + "\t");
                        }
//                        neurons[j].Bias = x[pos];
//                        pos++;
//                        System.out.println();
                    }
                }

//                 hatayı hesaplayalım
                double error = 0.0;
                //-----------------------------
                for (int i = 0; i < data.x.length; i++) {
                    //String pattern = "E:\\Documents\\NetBeansProjects\\NNetworkDesigner\\img\\patterns\\" + j + "/" + k + ".png";
                    double[] inputs = data.x[i];//ImageProcessingBW.loadImage(pattern, size, size);

                    if (inputs == null) {
                        // System.out.println("Cant find " + pattern);
                        continue;
                    }
                    double[] output = data.y[i];//new double[npatt];
//                    position++;

                    double new_error = 0;
                    double[] new_output = net.execute(inputs);
                    for (int j = 0; j < new_output.length; j++) {
                        new_error += Math.abs(new_output[j] - output[j]);
                    }
                    error += new_error / new_output.length;
                }
                error = error / data.x.length;
                //----------------------------

//                for (int j = 0; j < data.x.length; j++) {
//                    double[] new_output = net.execute(data.x[j]);
//                    for (int i = 0; i < new_output.length; i++) {
//                        error += Math.abs(new_output[i] - data.y[j][i]);
//
//                        //System.out.println(output[i]+" "+new_output[i]);
//                    }
//                    error += error / new_output.length;
//                }
//                error = error / data.x.length;
                return error;//(error_total / (data.y[0].length)) / data.x.length;
            }
        }
        );
        //min ve ya max  optimal noqteni qeyd edirik
        Const.setSearch(Const.MIN);
        //her deyisenin oz araligini veririk

        Const.setRanges(
                new Range[]{new Range(0, 1)
                }
        );
//        Const.setConditions((Double[] x) -> {
//            double error_total = 0;
//            int count = 0;
//            for (int i = 0; i < data.x.length; i++) {
//                double[] new_output = net.execute(data.x[i]);
//                count = new_output.length;
//                for (int j = 0; j < new_output.length; j++) {
//                    error_total += Math.abs(new_output[j] - data.y[i][j]);
//                    //System.out.println(output[i]+" "+new_output[i]);
//                }
//            }
//            return (error_total / data.x.length) / count < 0.50; //To change body of generated lambdas, choose Tools | Templates.
//        });

        Const.setVar_count(3 * 6 + 6 * 6 + 6 * 3);// 13824 + 576 + 864
        //populyasiya yaradilir
        Population p = new Population();
        //dovr basladilir
//        for (int i = 0; i < data.x.length; i++) {
//            System.out.println("data " + i);
//            dt = i;

        int generation = 0;

        long time = System.currentTimeMillis();
        while (generation < 400_000) {
            if (2*generation % 3 == 0) {
                //     System.out.println("-------------mutation----------------------mutation-------------");
                p.mutate();
            }
            p.computeAllChromosomeValue();
            p.crossOver();

            p.createNewGeneration();
            p.computeAllChromosomeValue();
            p.crossOver();
            p.createNewGeneration();
            // p.printValues();
            generation++;
            Chromosome chromosome = p.chromosomes[p.getMinChromosome()];
            //            Double[] d = p.getOptimalChromosome();
            if (generation % 10 == 0) {
                System.out.printf("%d optimal chromosome error = %.20f \n", generation - 1, chromosome.getOverAll());
            }
            if (System.currentTimeMillis() - time >= 2 * 60 * 1000) {
                System.err.println(generation+"-------breaak " + (System.currentTimeMillis() - time));
                break;
            }
        }
//        }
        System.out.println("\n-------------");
        p.printOptimalChromosome();
//        String pattern = "E:\\Documents\\NetBeansProjects\\NNetworkDesigner\\img/patterns/" + 4 + "/" + 8 + ".png";
//        double[] inputs = ImageProcessingBW.loadImage(pattern, size, size);
//        double[] output = net.execute(inputs);
//
//        int max = 0;
//        for (int i = 1; i < npatt; i++) {
//            if (output[i] > output[max]) {
//                max = i;
//            }
//        }
//
//        System.out.println("Il valore massimo e' " + output[max] + " pattern " + (max + 1));
        for (double[] x : data.x) {
            double[] output = net.execute(x);

            System.out.print(Arrays.toString(x) + "  [");
            for (double d : output) {
                System.out.printf("%.20f ,", d);
            }
            System.out.println("");
        }
    }
}//40 seconds




