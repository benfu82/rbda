/*
 * 该类是一个辅助类
 * 完整保存了一个极限状态方程在特定参数组下的失效概率结果
 * 方便存取与表示
 */
package org.rbda.calculate;

import java.io.Serializable;
import java.util.*;
import org.rbda.limitstateequation.EquationInformation;
import org.rbda.limitstateequation.LimitStateEquation;
import org.rbda.probability.Distribution;

/**
 *
 * @author kewin
 */
public class ParametersGroup implements Serializable{
    private int equation_Id;
    private List<Distribution> paramters_group = new ArrayList<Distribution>();
    public double equation_fail_probability;
    public int[] Distri_parameters_positions ;//分布的位置记录下来 
    
    public ParametersGroup(List<Distribution> paramters_group){
            this.paramters_group = paramters_group;
        }

    public ParametersGroup() {
        //To change body of generated methods, choose Tools | Templates.
    }

        /**
         * @return the paramters_group
         */
        public List<Distribution> getParamters_group() {
            return paramters_group;
        }

        /**
         * @param paramters_group the paramters_group to set
         */
        public void setParamters_group(List<Distribution> paramters_group) {
            this.paramters_group = paramters_group;
        }

    /**
     * @return the equation_Id
     */
    public int getEquation_Id() {
        return equation_Id;
    }

    /**
     * @param equation_Id the equation_Id to set
     */
    public void setEquation_Id(int equation_Id) {
        this.equation_Id = equation_Id;
    }


    /**
     * @param Distri_parameters_number the Distri_parameters_number to set
     */
    public void setDistri_parameters_positions() {
        int i =0;
        for (Distribution d:paramters_group){
            //如果不是常数类型
            if (d.id != 9){
              i ++ ;//先数一下共有多少个变量是随机分布  
            }   
        }
        Distri_parameters_positions = new int[i];
        i = 0;
        int j = 0;
        for (Distribution d:paramters_group){
            if (d.id != 9){              
              Distri_parameters_positions[i] =j;//记录下随机分布位置
              i ++ ;
            }
            j ++;
        }
    }
    
    
}
