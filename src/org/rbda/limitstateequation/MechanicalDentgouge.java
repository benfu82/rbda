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

/**
 *
 * @author kewin
 */
public class MechanicalDentgouge  extends LimitStateEquation{
    public final int parameters_number = 10;//参数个数，应该与变量parameters
                                               //大小相同,作为冗余判断
    @Override
    public double EquationValue() {
        double result = 0;
        if (parameters.length == parameters_number){
            double diameter  = parameters[0];
            double thickness = parameters[1];
            double dentgouge_length = parameters[2];
            double lt        = parameters[3];
            double sigma_y   = parameters[4];
            double pressure  = parameters[5];
            double sigma_u   = parameters[6];
            double dentgouge_deepth = parameters[7];
            double Youngs_model = 210000;
            double Charpy_energh = parameters[8];
            double Charpy_energh23 = Charpy_energh*0.667;
            double Charpy_area = 80;
            double Charpy_area23 = 80*0.667;
            double w         = parameters[9];
            
            
            double RD        = 0.67;
            double RN        = 1.0;
            
            double m;
            double q;
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
            
            
            m  = Math.sqrt(1+0.52*dentgouge_length*dentgouge_length/diameter/thickness);
            q  =13.7*Math.pow(w, 0.771)*RD*RN;
            dd0 = 1.43*Math.pow(q/( 0.49* Math.pow(lt*sigma_y*thickness,0.25) * Math.sqrt(thickness+0.7*pressure*diameter*sigma_u) ), 2.381);
            Sm = 1-1.8*dd0/diameter;
            ym = dentgouge_deepth/thickness;
            Ym = 1.12-0.23*ym+10.6*ym*ym-21.7*ym*ym*ym+30.4*ym*ym*ym*ym;
            Yb = 1.12-1.39*ym+7.32*ym*ym-13.1*ym*ym*ym+14*ym*ym*ym*ym;
            b1 = Sm*Ym + 5.1*Yb*dd0/thickness;
            b2 = Sm*(1-ym/m)/(1.15*sigma_y * (1-ym) );
            KIC= Math.sqrt(Youngs_model*Charpy_energh/Charpy_area23)*Math.pow(Charpy_energh23/Charpy_energh, 0.95);
            sigma_c = 2/(Math.PI*b2)*Math.acos(Math.exp(-125*Math.PI*Math.PI*Math.pow(b2/b1,2)*(KIC*KIC/Math.PI*dentgouge_length))) ;
            sigma_h = 0.5*pressure*diameter/thickness;           
            
            
            result = sigma_c - sigma_h;
        }
        else{
            System.out.println("参数个数应为"+parameters_number+"个，请检查是否遗漏！");
        }
        return result;
    }
}
