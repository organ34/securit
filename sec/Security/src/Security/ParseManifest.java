/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Security;
import java.io.*;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;


import org.w3c.dom.Document;
import org.w3c.dom.Element;

import org.w3c.dom.NodeList;


/**
 *
 * @author josav
 */
public class ParseManifest {
    

    private ArrayList<String> permissionList = new ArrayList <>();  
    public void parser() throws ParserConfigurationException, IOException, SAXException{
        //parse permissions from Manifest
        
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        
        Document doc = (Document) builder.parse("AndroidManifest.xml");
        doc.getDocumentElement().normalize();

        NodeList list = doc.getElementsByTagName("uses-permission");
        
        for (int i = 0; i<list.getLength();i++){
            Element el = (Element) list.item(i);
         
            String permission =  el.getAttribute("android:name");
            permissionList.add(permission);
                    
        }
    }
    public ArrayList<String> getPermissionsList(){
        return permissionList;
    }
}
