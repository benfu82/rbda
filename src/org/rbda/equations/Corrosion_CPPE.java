/*
 * 依据
 * 极限状态方程构建——管体腐蚀的极限状态函数
 * 2013-3-16
 * 编制
 * g1 = t - d
 * g2 ＝B1Pb + (1 – B1 )P0 – B2σu－P
 * g3 ＝B3Pb-P
 * g4 ＝B4Pb－P
 * g5 ＝Pb-P
 * 其中：
 * Pb——管道预测破裂压力,MPa；
 * P —运行压力，MPa；
 * B1、B2 为模型偏差系数,长短缺陷选择不同数值。
 * B3—模型偏差系数，正态分布，均值为0.91，标准偏差为0.102。
 * B4—模型偏差系数，正态分布，均值为1.02，标准偏差为0.05。
 */
package org.rbda.equations;

import org.rbda.probability.StdRandom;

/**
 *
 * @author kewin
 */
//方程编号21
public class Corrosion_CPPE extends Equation{
    public final int parameters_number = 10;//参数个数，应该与变量parameters
                                               //大小相同,作为冗余判断
    
     @Override
    public EquationResult EquationValue() {
        
        EquationResult result = new EquationResult();
        if (parameters.length == parameters_number){
            
            //方程输入参数 
            
            double diameter  = parameters[0];
            double thickness = parameters[1];
            double pressure  = parameters[2];
            double sigma_u = parameters[3];           
            double defect_deep = parameters[4]; 
            double defect_length = parameters[5];
            double steel_grade = parameters[6];
            double defect_deep_growth_rate = parameters[7];
            double defect_length_growth_rate = parameters[8];
            double service_life = parameters[9];
            
            
            //方程模型误差
            double model_error1; //模型误差
            double model_error2; //模型误差
            double model_error3 = StdRandom.gaussian(0.91, 0.102); //模型误差
            double model_error4 = StdRandom.gaussian(1.02, 0.05);
            //中间计算结果
            double defect_size;
            double Mt;
            double Mp;
            double P0_L80;
            double Pb_L80;
            double Pr_L80;            
            double Pbb;
            double Q;
            double Mp_U80;
            double P0_U80;
            double Pb_U80;
            double Pr_U80;   
            
            double t=1;//失效年限,从1开始计
            boolean failed=false;
            
            do{//采用逐年增加的方法模拟失效年限
        
            
            defect_size = defect_length*defect_length/diameter/thickness;
            if(defect_size<=50){
                model_error1 = 1.04;
                model_error2 = StdRandom.gaussian(-0.00056, 0.001469); //模型误差
            }
            else{
                model_error1 = 1.0;
                model_error2 = StdRandom.gaussian(-0.00228, 0.00383); //模型误差
            }
            if(defect_size<=50){
               Mt = Math.sqrt(1+0.6275*defect_size-0.003375*Math.pow(defect_size, 2)); 
            }
            else{
                Mt = 0.032*defect_size+3.3;
            }   
            Mp = (1-diameter/(Mt*thickness))/(1-diameter/thickness);
            
            P0_L80 = 1.8*sigma_u*thickness / diameter;
            Pb_L80 = P0_L80/Mp;             
            Pr_L80 = model_error1*Pb_L80 + (1-model_error1)*P0_L80 -model_error2*sigma_u;
                              
            Pbb = P0_L80/Mt;//g3中用到的预测破裂压力
           
            Q = Math.sqrt(1+0.31*defect_size);
            Mp_U80 = (1-diameter/(Q*thickness))/(1-diameter/thickness);
            P0_U80 = 2*sigma_u*thickness / diameter;
            Pb_U80 = P0_U80/Mp_U80;             
            Pr_U80 = model_error4*Pb_U80;            
       
             
            double g1;
            g1 = thickness-defect_deep;
            
            double g2;            
            g2 = Pr_L80 -pressure;  
            
            double g3;
            g3 = model_error3*Pbb -pressure;
            
            double g4;
            g4 = model_error4*Pr_U80-pressure;
            
            double g5;
            g5 = Pbb - pressure;
           
            //缺陷判定
            if (steel_grade < 80){//X80以下钢
                if(g1<=0){//小孔泄漏
                    result.value = t;
                    result.type = 2;    
                    failed = true;
                }
                else{
                    if(g2<0){//爆裂
                        result.value = t;
                        failed = true;
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
                    result.value = t;
                    result.type = 2;  
                    failed = true;
                }
                else{
                    if(g4<0){//爆裂
                        result.value = t;
                        failed = true;
                        if (g5>0){//大孔泄漏                           
                            result.type = 31;
                        }
                        else{//破裂
                            result.type = 32;
                        }
                    }
                }
            }
            
            defect_deep = defect_deep + defect_deep_growth_rate;
            defect_length = defect_length + defect_length_growth_rate;
            t = t + 1;
            
       }while((!failed) && (t<service_life));//未失效或没到服役年限时，一直仿真
         
    
        }
        
        else{
            System.out.println("参数个数应为"+parameters_number+"个，请检查是否遗漏！");
        }
        return result;
    }
    
}
