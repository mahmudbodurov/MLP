/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Network;

import com.Network.Functions.SigmoidFunction;
import java.util.Arrays;

/**
 *
 * @author Mahmud
 */
public class test {
    public static void main(String[] args) {
        int[] layer={4,16,2};
        Network network=new Network(layer, 0.6, new SigmoidFunction());
        System.out.println(Arrays.toString(network.execute(new double[]{1,0,0,0})));
        
        
    }
}






