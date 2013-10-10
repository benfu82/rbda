/*
 * 依据
 * 极限状态方程构建——管体焊缝的极限状态函数(A版)
 * 2013-3-5
 * 编制
 * g1 ＝t-d
 * g2 ＝B1Pc+(1-B1)P0-B2P0-p
 * g3 ＝B3Pc1-P
 * g4 = Pr -P= B4Pc－P
 * 其中：
 * Pc—焊缝缺陷下的爆裂压力，MPa；
 * Pc1—焊缝缺陷下的破裂压力，MPa；
 * P0—2tσf/D，MPa；
 * P —运行压力，MPa；
 * B1—模型偏差系数，定值1.09；
 * B2—模型偏差系数，正态分布，均值为-0.11，标准偏差为0.047
 * B3—模型偏差系数，正态分布，均值为0.99，标准偏差为0.068。
 */
package org.rbda.equations;

import org.rbda.probability.StdRandom;

/**
 *
 * @author kewin
 */
//方程编号19
public class SeamWeld_CPPE extends Equation{
    public final int parameters_number = 9;//参数个数，应该与变量parameters
                                           //大小相同,作为冗余判断

    @Override
    public EquationResult EquationValue() {
        
        EquationResult result = new EquationResult();
        if (parameters.length == parameters_number){
            
            //方程输入参数 
            
            double diameter  = parameters[0];
            double thickness = parameters[1];
            double pressure  = parameters[2];
            double sigma_y   = parameters[3];
            double sigma_u = parameters[4];           
            double defect_deep = parameters[5]; 
            double defect_length = parameters[6];
            double steel_grade = parameters[7];
            double charpy_enegy = parameters[8];
            
            
            //方程模型误差
            double model_error1 = 1.09; //模型误差
            double model_error2 = StdRandom.gaussian(-0.11, 0.047); //模型误差
            double model_error3 = StdRandom.gaussian(0.99, 0.068); //模型误差
            double model_error4 = 1.15;
            //中间计算结果
            double sigma_flow_L80;
            double sigma_flow_U80;
            double defect_size;
            double charpy_area = 80;
            double youngs_model = 206000;
            double acos_L80;
            double Pb_L80;
            double Pc_L80;                        
            double P0;
            double acos_U80;
            double Pb_U80;
            double Pc_U80;
            double Pc1_L80;
            double Pc1_U80;
            double Mt;
            double Mp;
            
            
            defect_size = defect_length*defect_length/diameter/thickness;
            if(defect_size<=50){
               Mt = Math.sqrt(1+0.6275*defect_size-0.003375*Math.pow(defect_size, 2)); 
            }
            else{
                Mt = 0.032*defect_size+3.3;
            }            
            Mp = (1-diameter/(Mt*thickness))/(1-diameter/thickness);
            
            sigma_flow_L80 = sigma_y+68.95;
            Pb_L80 = 4*thickness*sigma_flow_L80/diameter;
            acos_L80 = Math.acos(Math.exp((-125*Math.PI*charpy_enegy*youngs_model) / (charpy_area*sigma_flow_L80*sigma_flow_L80*defect_length/2)));
            Pc_L80 = Pb_L80/(Math.PI*Mp) * acos_L80;
            P0 = 2*thickness*sigma_flow_L80/diameter;
            
            Pc1_L80 = Pb_L80/(Math.PI*Mt) * acos_L80;
            
            sigma_flow_U80 = (sigma_y+sigma_u)/2;
            Pb_U80 = 4*thickness*sigma_flow_U80/diameter;
            acos_U80 = Math.acos(Math.exp((-125*Math.PI*charpy_enegy*youngs_model) / (charpy_area*sigma_flow_U80*sigma_flow_U80*defect_length/2)));
            Pc_U80 = Pb_U80/(Math.PI*Mp) * acos_U80;
            
            Pc1_U80 = Pb_U80/(Math.PI*Mt) * acos_U80;
            
            double g1;
            g1 = thickness-defect_deep;
            
            double g2;            
            g2 = model_error1*Pc_L80 + (1-model_error1-model_error2)*P0 -pressure;  
            
            double g3;
            g3 = model_error3*Pc1_L80-pressure;
            
            double g4;
            g4 = model_error4*Pc_U80-pressure;
            
            double g5;
            g5 = Pc1_U80 - pressure;
           
            //缺陷判定
            if (steel_grade < 80){//X80以下钢
                if(g1<=0){//小孔泄漏
                    result.value = g1;
                    result.type = 2;                    
                }
                else{
                    if(g2<0){//爆裂
                        result.value = g3;
                        if (g3>0){//大孔泄漏                           
                            result.type = 31;
                        }
                        else{//破裂
                            result.type = 32;
                        }
                    }
                }
            }
            else{//X80及以上钢
                if(g1<=0){//小孔泄漏
                    result.value = g1;
                    result.type = 2;                    
                }
                else{
                    if(g4<0){//爆裂
                        result.value = g3;
                        if (g5>0){//大孔泄漏                           
                            result.type = 31;
                        }
                        else{//破裂
                            result.type = 32;
                        }
                    }
                }
            }
         
    
        }
        
        else{
            System.out.println("参数个数应为"+parameters_number+"个，请检查是否遗漏！");
        }
        return result;
    }
}
