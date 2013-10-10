/*
 * 依据
 * 极限状态方程构建——管体焊缝的极限状态函数(A版)
 * 2013-3-5
 * 编制
 * g3 ＝B3Pc1-P
 * 其中：
 * Pc1—焊缝缺陷下的破裂压力，MPa；
 * P —运行压力，MPa；
 * B3—模型偏差系数，正态分布，均值为0.99，标准偏差为0.068。
 */
package org.rbda.limitstateequation;

import org.rbda.probability.StdRandom;

/**
 *
 * @author kewin
 */
public class SeamweldRupture extends LimitStateEquation{
    
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
            double defect_halflength = parameters[4];
            double charpy_enegy = parameters[5];
            double pressure = parameters[6];
            double model_error3 = StdRandom.gaussian(0.99, 0.068); //模型误差 
            
            double Charpy_area = 80;
            double Youngs_model = 201000;
            double Radis = diameter/2;
            double Pc1;            
            double Mt;
            double check_value;
            check_value = defect_halflength*defect_halflength/(Radis*thickness);
            if(check_value<=25){
               Mt = Math.sqrt(1+1.255*check_value-0.0135*Math.pow(check_value, 2)); 
            }
            else{
                Mt = 0.064*check_value+3.3;
            }
            
            Pc1= 4*thickness*sigma_flow/(diameter*Math.PI*Mt) * Math.acos(Math.exp((-125*Math.PI*charpy_enegy*Youngs_model) / (Charpy_area*sigma_flow*sigma_flow*defect_halflength)));
            result = model_error3*Pc1 -pressure;  
        }
        else{
            System.out.println("参数个数应为"+parameters_number+"个，请检查是否遗漏！");
        }
        return result;
    }    
}
