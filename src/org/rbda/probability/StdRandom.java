/*
 * 所有随机分布函数具体采样算法的实现，每个函数的入口统一为double[] parameters，返回
 * 值统一为double形式；
 * 具体函数的选择，通过generate_random(int id, double[] parameters)实现，id对应分布
 * 函数的id值。
 */
package org.rbda.probability;

import java.util.Random;
/**
 *
 * @author kewin
 */
public final class StdRandom {

    private static Random random;    // pseudo-random number generator
    private static long seed;        // pseudo-random number generator seed

    // static initializer
    static {
        // this is how the seed was set in Java 1.4
        seed = System.currentTimeMillis();
        random = new Random(seed);
    }

    // don't instantiate
    private StdRandom() { }

    /**
     * Set the seed of the psedurandom number generator.
     */
    public static void setSeed(long s) {
        seed   = s;
        random = new Random(seed);
    }

    /**
     * Get the seed of the psedurandom number generator.
     */
    public static long getSeed() {
        return seed;
    }

    /**
     * Return real number uniformly in [0, 1).
     */
    public static double uniform() {
        return random.nextDouble();
    }

    /**
     * Return an integer uniformly between 0 (inclusive) and N (exclusive).
     */
    public static int uniform(int N) {
        return random.nextInt(N);
    }

    ///////////////////////////////////////////////////////////////////////////
    //  根据列表中对应的函数选择相应的随机函数
    ///////////////////////////////////////////////////////////////////////////
    public static double generate_random(int id, double[] parameters) {
        double result = 0;
            
        switch(id){
            
        case 0:
            result=uniform(parameters);
            break;
        case 1:
            if (bernoulli(parameters)) {
            result=1;
        }
            else {
            result=0;
        }
            break;
        case 2:
            result=gaussian(parameters);
            break;
        case 3:
            result=geometric(parameters);
            break;
        case 4:
            result=poisson(parameters);
            break;
        case 5:
            result=pareto(parameters);
            break;
        case 6:
            result=cauchy();
            break;
        case 7:
            result=discrete(parameters); 
            break;
        case 8:
            result=exp(parameters);
            break;
       case 9:
            result = parameters[0];
            break;
        default:
            break;
    }
        return result;

    }
    
    
    ///////////////////////////////////////////////////////////////////////////
    //  STATIC METHODS BELOW RELY ON JAVA.UTIL.RANDOM ONLY INDIRECTLY VIA
    //  THE STATIC METHODS ABOVE.
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Return real number uniformly in [0, 1).
     */
    public static double random() {
        return uniform();
    }


    /**
     * Return real number uniformly in [a, b).
     */
    public static double uniform(double[] parameters) {
        double a,  b;
        a = parameters[0];
        b = parameters[1];
        return a + uniform() * (b-a);
    }

    /**
     * Return a boolean, which is true with probability p, and false otherwise.
     */
    public static boolean bernoulli(double[] parameters) {
        double p ;
        p = parameters[0];
        return uniform() < p;
    }

    /**
     * Return a boolean, which is true with probability .5, and false otherwise.
     
    public static boolean bernoulli() {
        return bernoulli(0.5);
    }
*/
    /**
     * Return a real number with a standard Gaussian distribution.
     */
    public static double gaussian() {
        // use the polar form of the Box-Muller transform
        double r, x, y;
        double[] downup = {-1.0, 1.0}; 
        do {
            x = uniform(downup);
            y = uniform(downup);
            r = x*x + y*y;
        } while (r >= 1 || r == 0);
        return x * Math.sqrt(-2 * Math.log(r) / r);

        // Remark:  y * Math.sqrt(-2 * Math.log(r) / r)
        // is an independent random gaussian
    }

    /**
     * Return a real number from a gaussian distribution with given mean and stddev
     */
    public static double gaussian(double[] parameters) {
        double mean, stddev;
        mean = parameters[0];
        stddev = parameters[1];
        return mean + stddev * gaussian();
    }
    
    public static double gaussian(double mean, double stddev) {        
        return mean + stddev * gaussian();
    }

    /**
     * Return an integer with a geometric distribution with mean 1/p.
     */
    public static int geometric(double[] parameters) {
        // using algorithm given by Knuth
        double p;
        p = parameters[0];
        return (int) Math.ceil(Math.log(uniform()) / Math.log(1.0 - p));
    }

    /**
     * Return an integer with a Poisson distribution with mean lambda.
     */
    public static int poisson(double[] parameters) {
        // using algorithm given by Knuth
        // see http://en.wikipedia.org/wiki/Poisson_distribution
        double lambda;
        lambda = parameters[0];
        int k = 0;
        double p = 1.0;
        double L = Math.exp(-lambda);
        do {
            k++;
            p *= uniform();
        } while (p >= L);
        return k-1;
    }

    /**
     * Return a real number with a Pareto distribution with parameter alpha.
     */
    public static double pareto(double[] parameters) {
        double alpha;
        alpha = parameters[0];
        return Math.pow(1 - uniform(), -1.0/alpha) - 1.0;
    }

    /**
     * Return a real number with a Cauchy distribution.
     */
    public static double cauchy() {
        return Math.tan(Math.PI * (uniform() - 0.5));
    }

    /**
     * Return a number from a discrete distribution: i with probability a[i].
     * Precondition: array entries are nonnegative and their sum (very nearly) equals 1.0.
     */
    public static int discrete(double[] parameters) {
        double[] a;
        a=parameters;
        double EPSILON = 1E-14;
        double sum = 0.0;
        for (int i = 0; i < a.length; i++) {
            if (a[i] < 0.0) throw new IllegalArgumentException("array entry " + i + " is negative: " + a[i]);
            sum = sum + a[i];
        }
        if (sum > 1.0 + EPSILON || sum < 1.0 - EPSILON)
            throw new IllegalArgumentException("sum of array entries not equal to one: " + sum);

        // the for loop may not return a value when both r is (nearly) 1.0 and when the
        // cumulative sum is less than 1.0 (as a result of floating-point roundoff error)
        while (true) {
            double r = uniform();
            sum = 0.0;
            for (int i = 0; i < a.length; i++) {
                sum = sum + a[i];
                if (sum > r) return i;
            }
        }
    }

    /**
     * Return a real number from an exponential distribution with rate lambda.
     */
    public static double exp(double[] parameters) {
        double lambda;
        lambda = parameters[0];
        return -Math.log(1 - uniform()) / lambda;
    }
    
    
    /**
     * 将均值、方差形式的参数转化成分布函数所需要标准参数
     */
    public double[] moment2parameter(int id, double mean, double std){
        double[] parameters = new double[DistributionInformation.parameters_number[id]];
        switch(id){
            
        case 1:
            
            break;
        case 2:
            
            break;
        case 3:
            
            break;
        case 4:
            
            break;
        case 5:
            
            break;
        case 6:
            
            break;
        case 7:
            
            break;
        case 8:
            
            break;
        case 9:
            
            break;
       case 10:
            
            break;
        default:
            break;
    }    
        return parameters;
    }
    

    /**
     * Rearrange the elements of an array in random order.
     */
    public static void shuffle(Object[] a) {
        int N = a.length;
        for (int i = 0; i < N; i++) {
            int r = i + uniform(N-i);     // between i and N-1
            Object temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    /**
     * Rearrange the elements of a double array in random order.
     */
    public static void shuffle(double[] a) {
        int N = a.length;
        for (int i = 0; i < N; i++) {
            int r = i + uniform(N-i);     // between i and N-1
            double temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    /**
     * Rearrange the elements of an int array in random order.
     */
    public static void shuffle(int[] a) {
        int N = a.length;
        for (int i = 0; i < N; i++) {
            int r = i + uniform(N-i);     // between i and N-1
            int temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }


    /**
     * Rearrange the elements of the subarray a[lo..hi] in random order.
     */
    public static void shuffle(Object[] a, int lo, int hi) {
        if (lo < 0 || lo > hi || hi >= a.length)
            throw new RuntimeException("Illegal subarray range");
        for (int i = lo; i <= hi; i++) {
            int r = i + uniform(hi-i+1);     // between i and hi
            Object temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    /**
     * Rearrange the elements of the subarray a[lo..hi] in random order.
     */
    public static void shuffle(double[] a, int lo, int hi) {
        if (lo < 0 || lo > hi || hi >= a.length)
            throw new RuntimeException("Illegal subarray range");
        for (int i = lo; i <= hi; i++) {
            int r = i + uniform(hi-i+1);     // between i and hi
            double temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    /**
     * Rearrange the elements of the subarray a[lo..hi] in random order.
     */
    public static void shuffle(int[] a, int lo, int hi) {
        if (lo < 0 || lo > hi || hi >= a.length)
            throw new RuntimeException("Illegal subarray range");
        for (int i = lo; i <= hi; i++) {
            int r = i + uniform(hi-i+1);     // between i and hi
            int temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

      
     /**
     * Unit test.
    
    public static void main(String[] args) {
        System.out.println(StdRandom.generate_random(2));
    }
     * * */
}
