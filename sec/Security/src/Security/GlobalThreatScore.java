/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Security;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 *
 * @author josav
 */
public class GlobalThreatScore {
    private ArrayList<Double> moneyPermsFinal = new ArrayList <>();
    private ArrayList<Double> privacyPermsFinal = new ArrayList <>();
    private ArrayList<Double> systemPermsFinal = new ArrayList <>();
    
    //list of permission names
    private ArrayList<String> moneyPermissions = new ArrayList <>();
    private ArrayList<String> privacyPermissions = new ArrayList <>();
    private ArrayList<String> systemPermissions = new ArrayList <>();
    
    //list of permission values
    private ArrayList<Double> moneyPermissionsValue = new ArrayList <>();
    private ArrayList<Double> privacyPermissionsValue = new ArrayList <>();
    private ArrayList<Double> systemPermissionsValue = new ArrayList <>();
    
    //list of applications permissions
    private ArrayList<String> appPerms=new ArrayList <>();
    
    public GlobalThreatScore(){
        
    }
    
    //read permissions names
    public void setPermissionsNames(ArrayList<String> moneyPermissions, ArrayList<String> privacyPermissions, ArrayList<String> systemPermissions){
        this.moneyPermissions=moneyPermissions;
        this.privacyPermissions=privacyPermissions;
        this.systemPermissions=systemPermissions;
    }
    
    //read permissions values
    public void setPermissionsValues(ArrayList<Double> moneyPermissionsValue, ArrayList<Double> privacyPermissionsValue, ArrayList<Double> systemPermissionsValue){
        this.moneyPermissionsValue=moneyPermissionsValue;
        this.privacyPermissionsValue=privacyPermissionsValue;
        this.systemPermissionsValue=systemPermissionsValue;
    }
    
    public void setAppsPermissions(ArrayList<String> appPerms){
        this.appPerms=appPerms;
    }
    
    
    //find the values of the permissions that are included in the application
    public void findPermissionsIncluded(){
        
        for(int i=0; i<appPerms.size();i++){
            for(int j=0;j<privacyPermissions.size();j++){
                
                if(appPerms.get(i).toString().equals(privacyPermissions.get(j).toString())){
                    privacyPermsFinal.add(privacyPermissionsValue.get(j));
                    
                }
            }
        }
        
        
        for(int i=0; i<appPerms.size();i++){
            for(int j=0;j<moneyPermissions.size();j++){
                
                if(appPerms.get(i).toString().equals(moneyPermissions.get(j).toString())){
                    moneyPermsFinal.add(moneyPermissionsValue.get(j));
                    
                }
            }
        }
        
        for(int i=0; i<appPerms.size();i++){
            for(int j=0;j<systemPermissions.size();j++){
                
                if(appPerms.get(i).toString().equals(systemPermissions.get(j).toString())){
                    systemPermsFinal.add(systemPermissionsValue.get(j));
                    
                }
            }
        } 
    }
    
    public double calculateSigma() throws FileNotFoundException, UnsupportedEncodingException, IOException{
        int numberOfParams = privacyPermsFinal.size() + moneyPermsFinal.size() + systemPermsFinal.size();
        double denominator=0.00, numerator=0.00;
        double wp=0.2, wm=0.6, ws = 0.2;
        double sigma;
        
        //impemantation of the formula
        //calculate the log of the number of permission
        //if this value is greater than 1, it will be the dominator of the formula
        //else the dominator will be 1
        if(1>Math.log10(numberOfParams)){
            denominator = 1;
        }else{
            denominator = Math.log10(numberOfParams);
        }
        //the arrays should have the same size
        //hence we add the appropriate number of objects  
        int max = Math.max(Math.max(privacyPermsFinal.size(),moneyPermsFinal.size()),systemPermsFinal.size());
        
        int limit = max-privacyPermsFinal.size();
        for(int i=0;i<limit;i++){
            privacyPermsFinal.add(0.0);
            
        }
        
        limit = max-moneyPermsFinal.size();
        for(int i=0;i<limit;i++){
          
            moneyPermsFinal.add(0.0);
            
        }
        
        limit = max-systemPermsFinal.size();
        for(int i=0;i<limit;i++){
          
            systemPermsFinal.add(0.0);
            
        }
        
        for(int i=0;i<max;i++){
            numerator += wp*privacyPermsFinal.get(i) + wm*moneyPermsFinal.get(i) + ws*systemPermsFinal.get(i);
        }
        
        sigma=numerator/denominator;
        this.export(sigma);
        return sigma;
        
    }
    
    public void export(double sigma) throws FileNotFoundException, UnsupportedEncodingException, IOException{
    PrintWriter writer = new PrintWriter("report.txt");
    writer.print("");
    writer.close();
    try(FileWriter fw = new FileWriter("report.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter out = new PrintWriter(bw))
    {
        out.println("---------Money Threats---------");
        for(int i=0; i<appPerms.size();i++){
            for(int j=0;j<moneyPermissions.size();j++){
                
                if(appPerms.get(i).toString().equals(moneyPermissions.get(j).toString())){
                    out.println(appPerms.get(i).toString());
                    
                }
            }
        }
        out.println(moneyPermsFinal.toString());
        
        out.println("");
        out.println("---------Privacy Threats---------");
        for(int i=0; i<appPerms.size();i++){
            for(int j=0;j<privacyPermissions.size();j++){
                
                if(appPerms.get(i).toString().equals(privacyPermissions.get(j).toString())){
                    out.println(appPerms.get(i).toString());
                    
                }
            }
        }
        out.println(privacyPermsFinal.toString());
        
        out.println("");
        out.println("---------System Threats---------");
        for(int i=0; i<appPerms.size();i++){
            for(int j=0;j<systemPermissions.size();j++){
                
                if(appPerms.get(i).toString().equals(systemPermissions.get(j).toString())){
                    out.println(appPerms.get(i).toString());
                    
                }
            }
        }
        out.println(systemPermsFinal.toString());
        
        out.println("|------------------------------------|");
        out.println("|Sigma = " + sigma);
        out.println("|------------------------------------|");
        
        
        
        //more code
    } catch (IOException e) {
        //exception handling left as an exercise for the reader
      }
    }
    
    
}
