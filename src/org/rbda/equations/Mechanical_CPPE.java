/*
 * 依据
 * 可靠性计算软件资料——第三方机械损伤(给温凯).xls 2013-5-5
 * 天然气管道基于可靠性的设计和评价方法研究——第三方开挖设备碰撞研究 2013-07-08
 * 编制
 * g1=ra-q
 * g2=sigma_c-sigma_h
 * g3=Scr-sigma_h
 * 其中
 * σu为管材抗拉强度（MPa），
 * lt 为斗齿齿尖长度（mm），
 * wt为斗齿齿尖宽度（mm），
 * w 为挖掘机的重量（吨），
 * RD 为冲击系数，取值为2/3，
 * RN为冲击力法向分力系数，即载荷垂直于管道壁之分力与总作用力之比，为在0和1之间均匀分布的随机量；
 * e 为模型误差项，服从正态分布，均值0.833kN，标准偏差26.7kN。
 * dg为划伤的深度，
 * lg为划伤的长度，
 * E 为杨氏模量，
 * cv2/3 是2/3尺寸夏比(V型缺口) 冲击试样的冲击功值，
 * cv0 是经验系数，取值为110.3J， 
 * aC 是2/3尺寸夏比(V型缺口) 冲击试样的截面面积，等于53.6 mm2，
 * lt 为挖掘机斗齿齿尖横截面长度。

 */
package org.rbda.equations;

import org.rbda.probability.StdRandom;

/**
 *
 * @author kewin
 */
//方程编号19
public class Mechanical_CPPE extends Equation{
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
            double sigma_u   = parameters[4];
            
            double lt        = parameters[5];
            double wt        = parameters[6];
            double w         = parameters[7];
            
            double charpy_enegy = parameters[8];
            
            //方程模型误差
            double model_error1 = StdRandom.gaussian(0.833, 26.7); //模型误差
            double model_error3 = StdRandom.gaussian(1.00, 0.089); //模型误差
            //中间计算结果
            double RD = 2/3;
            double RN = StdRandom.uniform();
            double ra;
            double q;
            
            double youngs_model = 206000;
            double charpy_enegy23 = charpy_enegy*0.667;
            double charpy_area = 80;
            double charpy_area23 = 80*0.667;
            double dentgouge_length = lt; //报告中可取斗齿齿尖横截面长度
            double dentgouge_deepth = StdRandom.exp(1/0.53);//报告中可取指数分布，均值0.53，COV是100
            double m;
            double dd0;
            double Sm;
            double ym;
            double Ym;
            double Yb;
            double b1;
            double b2;
            double KIC;
            double sigma_c;
            double sigma_h;
            
            double defect_length = lt; //根据报告，缺陷长度按挖掘机斗齿齿尖横截面长度计算
            double defect_size;
            double sigma_flow;
            double acos;
            double Mt;
            double Scr;
            
                       
            ra =(1.17-0.0029*diameter/thickness)*(lt+wt)*thickness*sigma_u/1000+model_error1;
            q  =8.09*Math.pow(w, 0.922)*RD*RN;
            
            m  = Math.sqrt(1+0.52*dentgouge_length*dentgouge_length/diameter/thickness);
            dd0 = 1.43*Math.pow(q/( 0.49* Math.pow(lt*sigma_y*thickness,0.25) * Math.sqrt(thickness+0.7*pressure*diameter*sigma_u) ), 2.381);
            Sm = 1-1.8*dd0/diameter;
            ym = dentgouge_deepth/thickness;
            Ym = 1.12-0.23*ym+10.6*ym*ym-21.7*ym*ym*ym+30.4*ym*ym*ym*ym;
            Yb = 1.12-1.39*ym+7.32*ym*ym-13.1*ym*ym*ym+14*ym*ym*ym*ym;
            b1 = Sm*Ym + 5.1*Yb*dd0/thickness;
            b2 = Sm*(1-ym/m)/(1.15*sigma_y * (1-ym) );
            KIC= Math.sqrt(youngs_model*charpy_enegy/charpy_area23)*Math.pow(charpy_enegy23/charpy_enegy, 0.95);
            sigma_c = 2/(Math.PI*b2)*Math.acos(Math.exp(-125*Math.PI*Math.PI*Math.pow(b2/b1,2)*(KIC*KIC/Math.PI*dentgouge_length))) ;
            sigma_h = 0.5*pressure*diameter/thickness;
            
            defect_size = defect_length*defect_length/diameter/thickness;
            if(defect_size<=50){
               Mt = Math.sqrt(1+0.6275*defect_size-0.003375*Math.pow(defect_size, 2)); 
            }
            else{
               Mt = 0.032*defect_size+3.3;
            }
            sigma_flow = sigma_y+68.95;
            acos = Math.acos(Math.exp((-125*Math.PI*charpy_enegy*youngs_model) / (charpy_area*sigma_flow*sigma_flow*defect_length/2)));
            Scr = 2*model_error3*sigma_flow*acos/Math.PI/Mt;
            
            double g1; 
            g1 = ra - q;
            
            double g2;
            g2 = sigma_c - sigma_h;
            
            double g3;
            g3 = Scr - sigma_h;
            
            //缺陷判定
            if(g1<=0 ){//穿孔
                if(g3<=0){//破裂
                    result.value = g3;
                    result.type = 32;
                } 
                else{
                    result.value = g1;
                    result.type = 31;
                }                                              
                }
            else if (g2<0){
                    if(g3<=0){//破裂
                    result.value = g3;
                    result.type = 32;
                } 
                    else if (defect_length>10){//长缺陷，大泄漏
                    result.value = g2;
                    result.type = 31;
                }
                    else {
                    result.value = g2;
                    result.type = 2; 
                    }
                }
            
        }
        
        else{
            System.out.println("参数个数应为"+parameters_number+"个，请检查是否遗漏！");
        }
        return result;
    }
    
}
