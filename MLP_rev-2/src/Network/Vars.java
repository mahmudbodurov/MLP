/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Network;

import com.Network.Functions.ActivationFunction;

/**
 *
 * @author Mahmud
 */
public abstract class Vars {

    static private int i = 0;

    public static int getNext() {
        return ++i;
    }

    public static int getCurrent() {
        return i;
    }

    public static void decrease() {
        i--;
    }

}


