/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rbda.probability;

import java.io.Serializable;

/**
 *
 * @author kewin
 */
public class Distribution implements Serializable{
    public int id;  
    public double[] parameters;//确定概率分布函数所用参数    
    
    public double sample_one(){
        double result;
        result = StdRandom.generate_random(this.id, this.parameters);
        return result;
    }
}
