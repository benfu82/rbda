 /*
 * 管道是程序计算的基本单元
 * 包括管道本身的基本信息：编号、起终点。。。
 * 包括管道物理属性参数：以名字和对应分布的Id组成HASH表存放
 * 管道的极限状态方程：以名字和对应分布的Id组成HASH表存放
 * 
 */
package org.rbda.pipeline;

import java.util.*;
import org.rbda.calculate.MenteCarlo;
import org.rbda.calculate.ParametersGroup;
import org.rbda.limitstateequation.EquationInformation;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 *
 * @author kewin
 */
public class Pipeseg implements Serializable{
    public String name;
    public int Id;//不在本类中赋值，因为会随管段链表变化而改变；
    public double start_point;
    public double end_point;
    public HashMap properties = new HashMap();//这里存放属性数据，由名字和分布组成的HASH对
    public Vector limitstate_Ids = new Vector();//这里存放所要考虑的极限状态的Id号，默认可以放10个，如果超过10则更新
    //public List<String> limitstates_name = new ArrayList();//放Id号在操作时有困难，所以改放方程名字，可以动态添加
    public double relaibility_target;
    public List<ParametersGroup>  parameters_groups = new ArrayList();//存放管段上的参数组
    public HashMap parameters_needed = new HashMap();//根据极限状态方程确定的需要的参数名字
    public double protection_factor =1.0;//保护因子，针对第三方破坏采取措施的有效度,这里默认为1,有待更正！！！！！！！！！
    public double fail_probability = 0;
    
    public boolean Parameters_groups_Contain(int equation_id){
        boolean contain = false;
        if(!parameters_groups.isEmpty()){
                ParametersGroup parameters_group; 
                //如果参数组不空，则逐个检查是否已经包含了该方程
                Iterator iterr = parameters_groups.iterator();
                while(iterr.hasNext()){
                    parameters_group = (ParametersGroup)iterr.next();
                    if (parameters_group.getEquation_Id() == equation_id){
                        contain = true;//找到了
                        break;
                    }
                }
            }
        
        return contain;
    }
    
    public void Map_Equations_Properties(){
        Iterator iter = this.limitstate_Ids.iterator();
        int equation_id;
        while(iter.hasNext()){
            //为保证已经加过的方程参数不再重复增加，这里要先检验是否已存在该方程对应的参数组
            equation_id = (int) iter.next();
            ParametersGroup parameters_group;
            //如果参数组中不包含该方程，则新建一个参数组
            if(!Parameters_groups_Contain(equation_id)){
                parameters_group = new ParametersGroup();
                parameters_group = MenteCarlo.Map_Property2Equation(equation_id, this);
                parameters_groups.add(parameters_group);
            }         
           
        }
    }
    
    public void Set_parameters_needed(){
        Iterator iter = limitstate_Ids.iterator();
        int i, j;
        while(iter.hasNext()){
            i = (int)iter.next();
            for(j = 0; j < EquationInformation.parameters_number[i]; j++){
                parameters_needed.put(EquationInformation.parameters_names[i][j], i);
            }            
        }
        
    }
    
    public void Add_all_equation_failprobability(){
        ParametersGroup parametersgroup;
        Iterator iter = parameters_groups.iterator();
        while(iter.hasNext()){
            parametersgroup = (ParametersGroup)iter.next();
            if (parametersgroup.getEquation_Id()==10){//第三方破坏极限状态方程，需要再乘上碰撞概率
                parametersgroup.equation_fail_probability = parametersgroup.equation_fail_probability*this.protection_factor;
            }
            fail_probability = fail_probability + parametersgroup.equation_fail_probability;
        }
    }
    
    public void objectWrite(String filename) {          

    	Pipeseg owPipeseg = new Pipeseg();

        try {

                FileOutputStream fos = new FileOutputStream(filename);

                ObjectOutputStream oos = new ObjectOutputStream(fos);
                
                oos.writeObject(owPipeseg);

                oos.close();       
                
                fos.close();

        } catch (Exception ex){
        	
        	ex.printStackTrace();  
        }
    }
    
    public void objectRead(String filename){
    	
    	Pipeseg orPipeseg = new Pipeseg();
    	
        try { 

                FileInputStream fis = new FileInputStream(filename);

                ObjectInputStream ois = new ObjectInputStream(fis);

                orPipeseg = (Pipeseg) ois.readObject();
                
                ois.close();
                
                fis.close();

        } catch (Exception ex){

           ex.printStackTrace();

        }

    }

    /**这是管段计算极限状态方程的函数
    public double failprobability(Limitstate ls, int n){
        double result = 0;
        double[][] sampled_values = new double[ls.parameter_number][n];
        int m = 0;
        Distribution ds = new Distribution();
        for (int i = 0; i < ls.parameter_number; i++){
            ds = (Distribution) ls.parameters_map.get(ls.parameters_name[i]);
            sampled_values[i] = ds.sample(n);
        }
        
        for (int i = 0; i < n; i ++){
            for (int k = 0; k <ls.parameter_number; k++){
                ls.parameters[k] = sampled_values[k][i];
            }           
            if (ls.equation_value()<0){
                m++ ;
            }
        }
        
        result = ((double)m)/(double)n;
        
        return result;
    }    
    
    
    
    public double failprobability_one(Limitstate ls, int n){
        double result = 0;
        double[] sampled_values = new double[ls.parameter_number];
        int m = 0;
        Distribution ds = new Distribution();
        for (int simu_times = 0; simu_times< n ; simu_times++){
        for (int i = 0; i < ls.parameter_number; i++){
            ds = (Distribution) ls.parameters_map.get(ls.parameters_name[i]);
            ls.parameters[i] = ds.sample_one();       
        }
        if (ls.equation_value()<0){
                m++ ;
            }
        }
        result = ((double)m)/(double)n;        
        return result;
    }
    */
}
