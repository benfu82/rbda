/*
 * 依据
 * 极限状态方程构建——管体焊缝的极限状态函数(A版)
 * 2013-3-5
 * 编制
 * g2 ＝B1Pc+(1-B1)P0-B2P0-p
 * 其中：
 * Pc—焊缝缺陷下的爆裂压力，MPa；
 * P0—2tσf/D，MPa；
 * P —运行压力，MPa；
 * B1—模型偏差系数，定值1.09；
 * B2—模型偏差系数，正态分布，均值为-0.11，标准偏差为0.047。
 */
package org.rbda.limitstateequation;

import org.rbda.probability.StdRandom;

/**
 *
 * @author kewin
 */
public class SeamweldBurst extends LimitStateEquation{
    
        public final int parameters_number = 7;//参数个数，应该与变量parameters
                                               //大小相同,作为冗余判断

    @Override
    public double EquationValue() {
        double result = 0;
        if (parameters.length == parameters_number){
            double diameter  = parameters[0];
            double thickness = parameters[1];
            double  sigma_flow = parameters[2] +68.95;
            double defect_deep  = parameters[3];
            double defect_halflength = parameters[4];
            double charpy_enegy = parameters[5];
            double  pressure = parameters[6];
            double model_error1 = 1.09;
            double model_error2 = StdRandom.gaussian(-0.11, 0.047); //模型误差 
            
            double Charpy_area = 80;
            double Youngs_model = 201000;
            double Radis = diameter/2;
            double Pr;
            double Pc;
            double P0;
            double Mt;
            double Mp;
            double check_value;
            check_value = defect_halflength*defect_halflength/(Radis*thickness);
            if(check_value<=25){
               Mt = Math.sqrt(1+1.255*check_value-0.0135*Math.pow(check_value, 2)); 
            }
            else{
                Mt = 0.064*check_value+3.3;
            }
            
            Mp = (1-diameter/(Mt*thickness))/(1-diameter/thickness);    
            Pc = 4*thickness*sigma_flow/(diameter*Math.PI*Mp) * Math.acos(Math.exp((-125*Math.PI*charpy_enegy*Youngs_model) / (Charpy_area*sigma_flow*sigma_flow*defect_halflength)));
            P0 = 2*thickness*sigma_flow/diameter;
            Pr = model_error1*Pc + (1-model_error1-model_error2)*P0;
            result = Pr -pressure;  
        }
        else{
            System.out.println("参数个数应为"+parameters_number+"个，请检查是否遗漏！");
        }
        return result;
    }    
}
