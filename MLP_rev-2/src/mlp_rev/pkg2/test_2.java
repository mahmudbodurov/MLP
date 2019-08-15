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
import genetics.Chromosome;
import java.util.Arrays;

/**
 *
 * @author Mahmud
 */
public class test_2 {

    static int dt;

    //ga ve linear regression birlesdirmek
    public static void main(String[] args) {
        //funksiyani daxil edirik
        Const.setFunction(new Functions<Double, Double>() {

            @Override
            public Double compute(Double... x) {
                //neyron sebeke burda olacaq
                Layer[] layers = net.layers;
                int pos = 0;
                Neuron[] neurons = layers[dt].getNeurons();
                int prevLayerSize = layers[dt - 1].size();
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

                // hatayı hesaplayalım
                double error_total = 0;
                int count = 0;
                for (int i = 0; i < data.x.length; i++) {
                    double[] new_output = net.execute(data.x[i]);
                    count = new_output.length;
                    for (int j = 0; j < new_output.length; j++) {
                        error_total += Math.abs(new_output[j] - data.y[i][j]);
                        //System.out.println(output[i]+" "+new_output[i]);
                    }
                }
                //                 hatayı hesaplayalım
                double error = 0.0;
                for (int j = 0; j < data.x.length; j++) {
                    double[] new_output = net.execute(data.x[j]);
                    for (int i = 0; i < new_output.length; i++) {
                        error += Math.abs(new_output[i] - data.y[j][i]);

                        //System.out.println(output[i]+" "+new_output[i]);
                    }
                    error += error / new_output.length;
                }
                error=error/data.x.length;

//                double[] new_output = net.execute(data.x[dt]);
//                for (int j = 0; j < new_output.length; j++) {
//                    error_total += Math.abs(new_output[j] - data.y[dt][j]);
//                    //System.out.println(output[i]+" "+new_output[i]);
//                }
//                    System.out.printf("%d data error is %.10f \n", i, error);
                return error;//(error_total / data.x.length) / count;
            }
        }
        );///

        //min ve ya max  optimal noqteni qeyd edirik
        Const.setSearch(Const.MIN);
        //her deyisenin oz araligini veririk

        Const.setRanges(
                new Range[]{new Range(-100, 100)
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
//            return (error_total / data.x.length) / count < 0.51; //To change body of generated lambdas, choose Tools | Templates.
//        });

        //neyron sebeke burda olacaq
        Layer[] layers = net.layers;
        for (int i = 1; i < layers.length; i++) {
            dt = i;
            //Connection count
            int connCount = layers[i].size() * layers[i - 1].size();
            Const.setVar_count(connCount);

            //populyasiya yaradilir
            Population p = new Population();
            //dovr basladilir
//        for (int i = 0; i < data.x.length; i++) {
//            System.out.println("data " + i);
//            dt = i;
            int generation = 0;
            while (generation < 40_000) {
                p.computeAllChromosomeValue();
                p.crossOver();
                if (2 * generation % 5 == 0) {
                    //     System.out.println("-------------mutation----------------------mutation-------------");
                    p.mutate();
                }
                p.createNewGeneration();
                p.computeAllChromosomeValue();
                p.crossOver();
                p.createNewGeneration();
                // p.printValues();
                generation++;
                //            Double[] d = p.getOptimalChromosome();
                if (generation % 10 == 0) {
                    Chromosome chromosome = p.chromosomes[p.getMinChromosome()];
                    System.out.printf("optimal chromosome error = %.20f \n", chromosome.getOverAll());
                }
//                System.out.println("\n-------------");
                //p.printOptimalChromosome();
            }
            System.out.println("data is " + dt);
        }
        for (double[] x : data.x) {
            System.out.println(Arrays.toString(x) + "  " + Arrays.toString(net.execute(x)));
        }
    }
}





