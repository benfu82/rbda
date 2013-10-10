/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rbda.limitstateequation;

/**
 *
 * @author kewin
 */
public class SeamweldThroughwall  extends LimitStateEquation{
    
        public final int parameters_number = 2;//参数个数，应该与变量parameters
                                               //大小相同,作为冗余判断

    @Override
    public double EquationValue() {
        double result = 0;
        if (parameters.length == parameters_number){
            double thickness  = parameters[0];
            double defect_deep = parameters[1];     
            result = thickness-defect_deep;  
        }
        else{
            System.out.println("参数个数应为"+parameters_number+"个，请检查是否遗漏！");
        }
        return result;
    }    
}
