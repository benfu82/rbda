/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rbda.limitstateequation;

import org.rbda.probability.Distribution;

/**
 *
 * @author kewin
 */
public class DefectFreeYield_CFER extends LimitStateEquation{
    
        public final int parameters_number = 4;//参数个数，应该与变量parameters
                                               //大小相同,作为冗余判断
        /**
        public DefectFreeYield_CFER(){
            this.Model_error = new Distribution();
            this.Model_error.id = 9;//常数
            this.Model_error.parameters = new double[1];
            this.Model_error.parameters[0] = 1;
        }
        */

    @Override
    public double EquationValue() {
        double result = 0;
        if (parameters.length == parameters_number){
            double sigma_y   = parameters[0];
            double thickness = parameters[1];
            double pressure  = parameters[2];
            double diameter  = parameters[3];       
            result = 2*sigma_y*thickness-pressure*diameter;  
        }
        else{
            System.out.println("参数个数应为"+parameters_number+"个，请检查是否遗漏！");
        }
        return result;
    }    
}
