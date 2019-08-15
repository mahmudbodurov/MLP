/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mlp_rev.pkg2;

import static Data.data.nimagesxpatt;
import static Data.data.npatt;
import static Data.data.size;
import com.Network.utility.ImageProcessingBW;
import java.util.Arrays;

/**
 *
 * @author Mahmud
 */
public class changeData {

    public static void main(String[] args) {
        int pos = 0;
        for (int k = 1; k < nimagesxpatt/8; k++) {
            for (int j = 1; j < npatt + 1; j++) {
                String pattern = "E:\\Documents\\NetBeansProjects\\NNetworkDesigner\\img\\patterns\\" + j + "/" + k + ".png";
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

//                        if (i % 100 == 0) {
//                            System.out.println("Error is " + error + " (" + i + " " + j + " " + k + " ) and Learning rate = " + net.getLearningRate());
//                        }.replaceAll("[", "{").replaceAll("]", "}")
                System.out.println("x[" + pos + "] = " + Arrays.toString(inputs).replace('[', '{').replace(']', '}') +";");
                System.out.println("y[" + pos + "] = " + Arrays.toString(inputs).replace('[', '{').replace(']', '}') +";");
                pos++;
            }

        }

    }
}





