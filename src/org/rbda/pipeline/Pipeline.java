/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rbda.pipeline;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

/**
 *
 * @author kewin
 */
public class Pipeline implements Serializable {
    private String name;
    public double start_point;
    public double end_point;
    public List<Pipeseg> pipesegs = new ArrayList();//这里存放管线所包含管段链表
    
    public Pipeline (String name){
        this.name = name;
    }
    
    public Pipeline (){
        
    }
    
    public int Get_Pipesegs_Number(){
        return this.pipesegs.size();
    }
    
    public void Add_Pipeseg ( Pipeseg newpipeseg ){
        this.pipesegs.add(newpipeseg);  
        Update_Pipeline();
    }
    
    public void Add_Pipeseg ( int index, Pipeseg newpipeseg ){
        this.pipesegs.add(index, newpipeseg);
        Update_Pipeline();
    }  
    
    public void Remove_Pipeseg ( int index ){
        this.pipesegs.remove(index);
        Update_Pipeline();
    }
    
    public void Remove_All_Pipeseg ( ){
        this.pipesegs.removeAll(pipesegs);
        Update_Pipeline();
    }
   
    public void Update_Pipeline(){
        int id = 0;
        for (Iterator iter = pipesegs.iterator(); iter.hasNext();){
            Pipeseg pipeseg = (Pipeseg)iter.next();
            pipeseg.Id = id;
            id ++;            
        }
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * 管线命名
     */
    public void setName(String name) {
        this.name = name;
    }
    
    public void objectWrite(String filename) {          
        try {
                FileOutputStream fos = new FileOutputStream(filename);

                ObjectOutputStream oos = new ObjectOutputStream(fos);
                
                oos.writeObject(this);

                oos.close();       
                
                fos.close();

        } catch (Exception ex){
        	
        	ex.printStackTrace();  
        }
    }
    
    public Pipeline objectRead(String filename){
    	
    	Pipeline pipeline = new Pipeline();
    	
        try { 

                FileInputStream fis = new FileInputStream(filename);

                ObjectInputStream ois = new ObjectInputStream(fis);

                pipeline = (Pipeline) ois.readObject();
                
                ois.close();
                
                fis.close();

        } catch (Exception ex){

           ex.printStackTrace();

        }
        return pipeline;

    }
}
