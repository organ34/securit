/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Security;

import java.io.BufferedReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


/**
 *
 * @author josav
 */
public class ParseDetails {
    
        private String scoreStr, installsStr;
        private int installs =0;
        private double score=0.00;
    
    public void parser () throws FileNotFoundException, IOException{
        //parse the number of downloads and rating from details
        FileReader file = new FileReader("details.txt");
        BufferedReader reader = new BufferedReader(file);
     
        
        String text="";
        
        String line = reader.readLine();
        
        
        while(line !=null){
            text+=line;
            line = reader.readLine();
            try{
                if (line.contains("installs")){
                    installsStr = line.substring(line.lastIndexOf(":")+1);
                    installsStr = installsStr.replaceAll("\\D+","");
                    installs =  Integer.parseInt(installsStr);
                    //System.out.println("Downloads: "+installs+"\n");
                }
                if (line.contains("score:")){
                    scoreStr = line.substring(line.lastIndexOf(":")+1);
                    scoreStr = scoreStr.replaceAll(",","");
                    scoreStr = scoreStr.replaceAll(" ","");
                    score =  Double.parseDouble(scoreStr);
                    //System.out.println("Score: "+score+"/5\n");
                }
            }catch (Exception e){
                
            } 
        }
    }
    
    public int getDownloads(){
        return installs;
    }
    
    public double getRating(){
        return score;
    }
    
}
