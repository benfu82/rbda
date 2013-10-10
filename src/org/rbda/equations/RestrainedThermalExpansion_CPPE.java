/*
 * 依据
 * 极限状态方程构建——运行过程受限管道热膨胀导致局部屈曲的极限状态方程
 * 编制
 * g =εcrit－εth
 * 其中：
 * εcrit——管道压缩应变能力，即临界压缩应变，
 * εth ——受限管道热膨胀所导致的压缩应变，设计应变

 * B1—模型偏差系数，定值1.09；
 * B2—模型偏差系数，正态分布，均值为-0.11，标准偏差为0.047
 * B3—模型偏差系数，正态分布，均值为0.99，标准偏差为0.068。
 */
package org.rbda.equations;

import org.rbda.probability.StdRandom;

/**
 *
 * @author lx
 */
//方程编号22
public class RestrainedThermalExpansion_CPPE extends Equation{
    public final int parameters_number = 8;//参数个数，应该与变量parameters
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
            double imp = parameters[4];
            double offset = parameters[5];            
            double temp2 = parameters[6];
            double temp1 = parameters[7];
            
            double chart_type = 1;//圆顶应力－应变曲线为1；屈服平台应力－应变曲线为2            
            
            //方程模型误差
            double model_error1; //模型误差
            double model_error2; //模型误差

            //中间计算结果
            double epsilon_crit;
            double epsilon_th;
            double epsilon_p;
            double epsilon_t;
            double epsilon_crit_p;
            double epsilon_crit_w;
            double dt;
            double fy;
            double ppy;
            double efy;
            double v = 0.3;//暂时定为0.3
            double youngs_model = 206000;
            double alpha = 0.0000117;//根据C-FER报告取值
            
            epsilon_p = v*pressure*(diameter-2*thickness)/(2*thickness*youngs_model);
            epsilon_t = alpha*(temp2-temp1);
            epsilon_th = epsilon_p - epsilon_t;           
            dt=thickness/diameter;
            fy=sigma_y;
            efy = youngs_model/fy;
            ppy=pressure*(diameter-2*thickness)/(2*thickness*fy);
            if ( chart_type==1 ){
                model_error1 = StdRandom.gaussian(1.03, 0.097);
                epsilon_crit_p = model_error1/100*Math.pow((2.49*dt), 1.59)/(1-0.868*ppy)*Math.pow(efy, 0.854)*(1.27-Math.pow((imp/100), 0.15));
                model_error2 = StdRandom.gaussian(0.99, 0.11);
                epsilon_crit_w = model_error2/100*Math.pow((8.99*dt), 1.72)/(1-0.892*ppy)*Math.pow(efy, 0.701)*(1.09-Math.pow((offset/thickness), 0.0863));
            }
            else {
                model_error1 = StdRandom.gaussian(1.03, 0.092);
                epsilon_crit_p = model_error1*0.404*dt*dt/(1-0.906*ppy)*Math.pow(efy, 0.8)*(1.12-Math.pow((imp/100), 0.15));
                model_error2 = StdRandom.gaussian(1.04, 0.113);
                epsilon_crit_w = model_error2*1.06*dt*dt/(1-0.5*ppy)*Math.pow(efy, 0.7)*(1.10-Math.pow((offset/thickness), 0.09));
            }            
            epsilon_crit = Math.min(epsilon_crit_p, epsilon_crit_w);
            
            double g;
            g = epsilon_crit - epsilon_th;
           
            //缺陷判定
            if(g<=0){
                result.value = g;
                result.type = 1;
            }         
    
        }
        
        else{
            System.out.println("参数个数应为"+parameters_number+"个，请检查是否遗漏！");
        }
        return result;
    }
    
}
