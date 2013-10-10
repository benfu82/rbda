/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rbda.limitstateequation;

/**
 *
 * @author kewin
 */
public class MechanicalFail  extends LimitStateEquation{
    public final int parameters_number = 11;//参数个数，应该与变量parameters
                                               //大小相同,作为冗余判断
    @Override
    public double EquationValue() {
        double result = 0;
        if (parameters.length == parameters_number){
            double diameter  = parameters[0];
            double thickness = parameters[1];
            double pressure  = parameters[2];
            double sigma_y   = parameters[3];
            double sigma_u   = parameters[4];
            double charpy_enegy = parameters[5];
            double w         = parameters[6];
            double lt        = parameters[7];
            double wt        = parameters[8];            
            double dentgouge_length = parameters[9];
            double dentgouge_deepth = parameters[10];
            
            double Youngs_model = 210000;
            double charpy_energy23 = charpy_enegy*0.667;
            double Charpy_area = 80;
            double Charpy_area23 = 80*0.667;
            double RD        = 0.67;
            double RN        = 1.0;
            double defect_halflength = lt/2;
            double model_error1 = 0.833;//StdRandom.gaussian(0.833, 0.08); //模型误差
            double model_error3 = 1;//StdRandom.gaussian(0.99, 0.068); //模型误差 
            double Radis = diameter/2;
            double sigma_flow = sigma_y+68.95;
            
            
            
            double ra;
            double q;
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
            double Mt;
            double check_value;
            double Scr;
            sigma_h = 0.5*pressure*diameter/thickness;
            ra =(1.17-0.0029*diameter/thickness)*(lt+wt)*thickness*sigma_u/1000+model_error1;
            q  =13.7*Math.pow(w, 0.771)*RD*RN;
            result = ra -q;
            if (result >0 ){//如果没有发生穿孔，则再看是否发生划伤
            m  = Math.sqrt(1+0.52*dentgouge_length*dentgouge_length/diameter/thickness);
            dd0 = 1.43*Math.pow(q/( 0.49* Math.pow(lt*sigma_y*thickness,0.25) * Math.sqrt(thickness+0.7*pressure*diameter*sigma_u) ), 2.381);
            Sm = 1-1.8*dd0/diameter;
            ym = dentgouge_deepth/thickness;
            Ym = 1.12-0.23*ym+10.6*ym*ym-21.7*ym*ym*ym+30.4*ym*ym*ym*ym;
            Yb = 1.12-1.39*ym+7.32*ym*ym-13.1*ym*ym*ym+14*ym*ym*ym*ym;
            b1 = Sm*Ym + 5.1*Yb*dd0/thickness;
            b2 = Sm*(1-ym/m)/(1.15*sigma_y * (1-ym) );
            KIC= Math.sqrt(Youngs_model*charpy_enegy/Charpy_area23)*Math.pow(charpy_energy23/charpy_enegy, 0.95);
            sigma_c = 2/(Math.PI*b2)*Math.acos(Math.exp(-125*Math.PI*Math.PI*Math.pow(b2/b1,2)*(KIC*KIC/Math.PI*dentgouge_length))) ;
                        
            result = sigma_c - sigma_h;                         
            }
            if (result >0 ){//如果没有发生穿孔，没发生划伤，再看是否可能直接破裂
                check_value = defect_halflength*defect_halflength/(Radis*thickness);
                if(check_value<=25){
                    Mt = Math.sqrt(1+1.255*check_value-0.0135*Math.pow(check_value, 2)); 
                }
                else{
                    Mt = 0.064*check_value+3.3;
                }
            Scr = model_error3*2*sigma_flow/(Math.PI*Mt) * Math.acos(Math.exp((-125*Math.PI*charpy_enegy*Youngs_model) / (Charpy_area*sigma_flow*sigma_flow*defect_halflength)));
            result = Scr - sigma_h; 
                
            }
            
        }
        else{
            System.out.println("参数个数应为"+parameters_number+"个，请检查是否遗漏！");
        }
        return result;
    }
    
    
}
