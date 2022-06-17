/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author josav
 */
public class Security {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
               
        //---------------------parsers--------------------------------
        ParseManifest appsPermissions = new ParseManifest();        
        ParseDetails appsDetails = new ParseDetails();
        ParsePermissions permissionList = new ParsePermissions();
        
        //get apps permissions
        appsPermissions.parser();
        ArrayList<String> appPerms=appsPermissions.getPermissionsList();
        
        //apps playstore details
        appsDetails.parser();
        
        //parse the three files
        permissionList.readFilePrivacy();
        permissionList.readFileMoney();
        permissionList.readFileSystem();
        
        //get the results from parsers
        ArrayList<String> moneyPermissions = permissionList.getMoneyPermissionsNames();
        ArrayList<String> privacyPermissions = permissionList.getPrivacyPermissionsNames();
        ArrayList<String> systemPermissions = permissionList.getSystemPermissionsNames();
        
        ArrayList<Double> moneyPermissionsValue = permissionList.getMoneyPermissionsValues();
        ArrayList<Double> privacyPermissionsValue = permissionList.getPrivacyPermissionsValues();
        ArrayList<Double> systemPermissionsValue = permissionList.getSystemPermissionsValues();
        
        //create the object gts in order to calculate the GlobalThreatScore 
        GlobalThreatScore gts = new GlobalThreatScore();
        
        //send the appropriate values
        gts.setAppsPermissions(appPerms);
        gts.setPermissionsNames(moneyPermissions, privacyPermissions, systemPermissions);
        gts.setPermissionsValues(moneyPermissionsValue, privacyPermissionsValue, systemPermissionsValue);
        
        //find the threats' values of permissions that are included in application
        gts.findPermissionsIncluded();
        double sigma = gts.calculateSigma(); //get the result
        
        //make a decision about the security based on sigma downloads and rating
        AHP r = new AHP();
        
        r.controller(sigma, appsDetails.getDownloads(), appsDetails.getRating());
        

    }
    
}
