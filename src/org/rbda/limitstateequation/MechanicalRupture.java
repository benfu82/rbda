/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rbda.limitstateequation;

import org.rbda.probability.StdRandom;

/**
 *
 * @author kewin
 */
public class MechanicalRupture  extends LimitStateEquation{
    public final int parameters_number = 7;//参数个数，应该与变量parameters
                                               //大小相同,作为冗余判断

    @Override
    public double EquationValue() {
        double result = 0;
        if (parameters.length == parameters_number){
            double diameter  = parameters[0];
            double thickness = parameters[1];
            double sigma_flow = parameters[2] +68.95;
            double defect_deep  = parameters[3];
            double defect_halflength = parameters[4]/2;
            double charpy_enegy = parameters[5];
            double pressure = parameters[6];
            double model_error3 = 1;//StdRandom.gaussian(0.99, 0.068); //模型误差 
            
            double Radis = diameter/2;
            
            
            double Charpy_area = 80;
            double Youngs_model = 210000;
                      
            double Mt;
            double check_value;
            double Scr;
            double sigma_h;
            
            check_value = defect_halflength*defect_halflength/(Radis*thickness);
            if(check_value<=25){
               Mt = Math.sqrt(1+1.255*check_value-0.0135*Math.pow(check_value, 2)); 
            }
            else{
                Mt = 0.064*check_value+3.3;
            }
            Scr = model_error3*2*sigma_flow/(Math.PI*Mt) * Math.acos(Math.exp((-125*Math.PI*charpy_enegy*Youngs_model) / (Charpy_area*sigma_flow*sigma_flow*defect_halflength)));
            sigma_h = 0.5*pressure*diameter/thickness; 
            
            result = Scr - sigma_h;  
        }
        else{
            System.out.println("参数个数应为"+parameters_number+"个，请检查是否遗漏！");
        }
        return result;
    }
}
