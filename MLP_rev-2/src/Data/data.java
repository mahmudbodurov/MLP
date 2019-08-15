package Data;

import Network.Network;
import com.Network.Functions.SigmoidFunction;

/**
 *
 * @author Mahmud
 */
public abstract class data {

    public static final int size = 24;
    public static boolean isRunning = true;
    public static double error = 0.0;
    public static double accouracy = 0.01;
    public static int nimagesxpatt = 89;//folder inside image size 88image counting start at 1 
    public static int npatt = 5;//pattern size--folders size
    public static int dir = 1;
    public static int cpatt = 1;
    public static int maxit = 5000;//iteration count
    public static double last_error = 10;
    public static int length = npatt * 1;
    public static int[] layer = {3, 6, 6, 3}; // 8 16 8 sum equal 32
    public static Network net = new Network(layer, 0.6, new SigmoidFunction());

    public static double x[][] = {{0, 0, 0}, {0, 0, 1}, {0, 1, 0}, {0, 1, 1}, {1, 0, 0}, {1, 0, 1}, {1, 1, 0}, {1, 1, 1}};//new double[length][];
    public static double y[][] = {{1, 1, 1}, {1, 1, 0}, {1, 0, 1}, {1, 0, 0}, {0, 1, 1}, {0, 1, 0}, {0, 0, 1}, {0, 0, 0}};//new double[length][];

}

//3204 24*24 36


