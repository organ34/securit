/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Security;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import java.util.Scanner;

/**
 *
 * @author josav
 */
public class ParsePermissions {
   
    private ArrayList<String> permissionPrivacyName = new ArrayList <>();
    private ArrayList<String> permissionPrivacyValueTemp = new ArrayList <>(); 
    private ArrayList<String> permissionSystemName = new ArrayList <>();
    private ArrayList<String> permissionSystemValueTemp = new ArrayList <>(); 
    private ArrayList<String> permissionMoneyName = new ArrayList <>();
    private ArrayList<String> permissionMoneyValueTemp = new ArrayList <>(); 
    private ArrayList<Double> permissionPrivacyValue = new ArrayList <>(); 
    private ArrayList<Double> permissionSystemValue = new ArrayList <>(); 
    private ArrayList<Double> permissionMoneyValue = new ArrayList <>(); 
    
    public void readFilePrivacy() throws FileNotFoundException{
        ////parse all the privacy permissions
        Scanner read = new Scanner (new File("privacy.txt"));
        read.useDelimiter(",");
       
        String n = null , m = null;
        while (read.hasNext()){
           n = read.next();
           n = n.replaceAll("(\\r|\\n)", "");
           permissionPrivacyName.add(n);
           
           m = read.next();
           m = m.replaceAll("(\\r|\\n)", "");
           permissionPrivacyValueTemp.add(m);
                          
        }
        read.close();
        
        this.converterPrivacy();
    }
        //parse all the money permissions
        public void readFileMoney() throws FileNotFoundException{
        Scanner read = new Scanner (new File("money.txt"));
        read.useDelimiter(",");
       
        String n = null , m = null;
        //remove every new line
        while (read.hasNext()){
           n = read.next();
           n = n.replaceAll("(\\r|\\n)", "");
           permissionMoneyName.add(n);
           
           m = read.next();
           m = m.replaceAll("(\\r|\\n)", "");
           permissionMoneyValueTemp.add(m);
                          
        }
        read.close();
        this.converterMoney();
        
    }
        //parse all the system permissions
        public void readFileSystem() throws FileNotFoundException{
        Scanner read = new Scanner (new File("system.txt"));
        read.useDelimiter(",");
       
        String n = null , m = null;
        while (read.hasNext()){
           n = read.next();
           n = n.replaceAll("(\\r|\\n)", "");
           permissionSystemName.add(n);
           
           m = read.next();
           m = m.replaceAll("(\\r|\\n)", "");
           permissionSystemValueTemp.add(m);
                          
        }
        read.close();
        
        this.converterSystem();
        
    }
    
    public void converterPrivacy(){
        Double d = 0.00;
        for(int i=0; i<permissionPrivacyValueTemp.size();i++){
            d = Double.parseDouble(permissionPrivacyValueTemp.get(i));
            permissionPrivacyValue.add(d);
        }
        
    }
    
    public void converterMoney(){
        Double d = 0.00;
        for(int i=0; i<permissionMoneyValueTemp.size();i++){
            d = Double.parseDouble(permissionMoneyValueTemp.get(i));
            permissionMoneyValue.add(d);
        }
    }

    public void converterSystem(){
        Double d = 0.00;
        for(int i=0; i<permissionSystemValueTemp.size();i++){
            d = Double.parseDouble(permissionSystemValueTemp.get(i));
            permissionSystemValue.add(d);
        }
    }
    //get the permissions
    public ArrayList<String> getPrivacyPermissionsNames(){
        return permissionPrivacyName;
    }

    public ArrayList<String> getMoneyPermissionsNames(){
        return permissionMoneyName;
    }
 
    public ArrayList<String> getSystemPermissionsNames(){
        return permissionSystemName;
    }
    
    public ArrayList<Double> getPrivacyPermissionsValues(){
        return permissionPrivacyValue;
    }

    public ArrayList<Double> getMoneyPermissionsValues(){
        return permissionMoneyValue;
    }
 
    public ArrayList<Double> getSystemPermissionsValues(){
        return permissionSystemValue;
    }
    
    
    
}
