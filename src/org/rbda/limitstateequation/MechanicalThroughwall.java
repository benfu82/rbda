/*
 * 依据
 * 可靠性计算软件资料——第三方机械损伤(给温凯).xls
 * 2013-5-5
 * 编制
 * g1=ra-q
 * g2=sigma_c-sigma_h
 * g3=Scr-sigma_h
 * 其中：
 * 计算参数				单位
 * 管道内压			P	MPa
 * 管道外直径			D	mm
 * 钢管壁厚			t	mm
 * 管材屈服强度			σy	MPa
 * 管材抗拉强度			σu	MPa
 * 管材的杨氏模量			E	MPa
 * 夏比(V型缺口)冲击能经验值			cv0	Joules
 * 2/3夏比(V型缺口)冲击能			cv2/3	Joules
 * 夏比(V型缺口)冲击试件受剪截面积			Ac	mm2
 * 2/3夏比(V型缺口)冲击试件受剪截面积			ac	mm2
 * 挖掘机重量			w	tonne
 * 斗齿齿尖长度			lt	mm
 * 斗齿齿尖宽度			wt	mm
 * 冲击系数			RD	-
 * 冲击力法向分力系数			RN	-
 * 凹坑-划痕长度			lg	mm
 * 凹坑-划痕深度			dg	mm
 * 缺陷长度			c	mm
 * 穿孔模型误差项			e	kN
 * 凹坑-划痕模型误差项			B	-
 */
package org.rbda.limitstateequation;

import org.rbda.probability.StdRandom;

/**
 *
 * @author kewin
 */
public class MechanicalThroughwall  extends LimitStateEquation{
    
    public final int parameters_number = 6;//参数个数，应该与变量parameters
                                               //大小相同,作为冗余判断
    @Override
    public double EquationValue() {
        double result = 0;
        if (parameters.length == parameters_number){
            double diameter  = parameters[0];
            double thickness = parameters[1];
            double sigma_u   = parameters[2];
            double model_error1 = 0.833;//StdRandom.gaussian(0.833, 0.08); //模型误差
            double lt        = parameters[3];
            double wt        = parameters[4];
            double w         = parameters[5];
            
            double RD        = 0.67;
            double RN        = 1.0;
            double ra;
            double q;
            
            ra =(1.17-0.0029*diameter/thickness)*(lt+wt)*thickness*sigma_u/1000+model_error1;
            q  =13.7*Math.pow(w, 0.771)*RD*RN;
            result = ra -q;
        }
        else{
            System.out.println("参数个数应为"+parameters_number+"个，请检查是否遗漏！");
        }
        return result;
    }
    
}
