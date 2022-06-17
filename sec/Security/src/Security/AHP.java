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
import java.util.Arrays;

/**
 *
 * @author josav
 */
public class AHP {
    
    public AHP( ){
       
    }
    
    public void controller(double sigma, int downloads, double rating) throws FileNotFoundException, UnsupportedEncodingException{
        double[][] initialArray = new double[3][3];
        double[] sigmaTrust = new double[3];
        double[] downloadsTrust = new double[3];
        double[] ratingTrust = new double[3];
        double[] sigmaRelevant = new double[3];
        double[] downloadsRelevant = new double[3];
        double[] ratingRelevant = new double[3];
        double[] eqRelevant = new double[3];
        
        //initialize a 3x3 matrix for each cretario and calculate the eigenvector  associated  with  the  largest  eigenvalue 
        //based on Reyleigh's algorithm
        initialArray = this.buildSigmaMatrix(sigma); 
        sigmaTrust = this.Rayleigh(initialArray);
        this.exportMatrx(initialArray, 0);
        
        //---------------------------
        initialArray = this.buildDownloadsMatrix(downloads);
        downloadsTrust = this.Rayleigh(initialArray);
        this.exportMatrx(initialArray, 1);
        
        //-------------------------------
        initialArray = this.buildRatingMatrix(rating);
        ratingTrust = this.Rayleigh(initialArray);
        this.exportMatrx(initialArray, 2);
        
        this.exportEigenVectors(sigmaTrust,0);
        this.exportEigenVectors(downloadsTrust,1);
        this.exportEigenVectors(ratingTrust,1);
        //initialize a 3x3 matrix for each preference(sigma,downloads,rating) and calculate the eigenvector  associated  with  the  largest  eigenvalue 
        //based on Reyleigh's algorithm
        //-------------------------------
        initialArray = this.buildRelevanceMatrix(0);
        sigmaRelevant = this.Rayleigh(initialArray);
        
        //-------------------------------
        initialArray = this.buildRelevanceMatrix(1);
        downloadsRelevant = this.Rayleigh(initialArray);
 
        //-------------------------------
        initialArray = this.buildRelevanceMatrix(2);
        ratingRelevant = this.Rayleigh(initialArray);
        
        //-------------------------------
        initialArray = this.buildRelevanceMatrix(3);
        eqRelevant = this.Rayleigh(initialArray);
        
        //Result
        System.out.println("Result if there are equal weights on Perimissions, Number of Downloads and Ratings");
        try(FileWriter fw = new FileWriter("report.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter out = new PrintWriter(bw))
        {
            out.println("");
            out.println("Result if there are equal weights on Perimissions, Number of Downloads and Ratings");

        } catch (IOException e) {
            //exception handling left as an exercise for the reader
        }
        this.computeGlobalPriority(eqRelevant, sigmaTrust, downloadsTrust, ratingTrust);
        
        //-------------------------------
        System.out.println("Result if Perimissions are more important");
        try(FileWriter fw = new FileWriter("report.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter out = new PrintWriter(bw))
        {
            out.println("");
            out.println("Result if Perimissions are more important");

        } catch (IOException e) {
            //exception handling left as an exercise for the reader
        }
        this.computeGlobalPriority(sigmaRelevant, sigmaTrust, downloadsTrust, ratingTrust);
        
        //-------------------------------
        System.out.println("Result if Number of Downloads are more important");
        try(FileWriter fw = new FileWriter("report.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter out = new PrintWriter(bw))
        {
            out.println("");
            out.println("Result if Number of Downloads are more important");

        } catch (IOException e) {
            //exception handling left as an exercise for the reader
        }
        this.computeGlobalPriority(downloadsRelevant, sigmaTrust, downloadsTrust, ratingTrust);
        
        //-------------------------------
        System.out.println("Result if Rating are more important");
        try(FileWriter fw = new FileWriter("report.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter out = new PrintWriter(bw))
        {
            out.println("");
            out.println("Result if Rating are more important");

        } catch (IOException e) {
            //exception handling left as an exercise for the reader
        }
        this.computeGlobalPriority(ratingRelevant, sigmaTrust, downloadsTrust, ratingTrust);
        
    }

    
    public double[] Rayleigh(double[][] initialArray){
        
        
        double[] procedureArray = {1.0,0.0,0.0};
        double[] finalArray = {0.0,0.0,0.0};
        double[] assistant = {0.0,0.0,0.0};
        int[] downloadsTrust = {0,0,0};
        int[] ratingTrust = {0,0,0};
        boolean flag = false;
        int counter = 0;
        boolean loop = true;
        
        
        while(loop){
            //multiply the initial 3x3 matrix with [1.0,0.0,0.0]
            for (int i=0;i<3;i++){
                for (int j=0;j<3;j++){
                    finalArray[i] +=  initialArray[i][j] * procedureArray[j];
                   
                }
                
            }
           
            //find the max value and devide the result matrix with this value
            double maxValue =0.00;
            
            for(int i=0;i<finalArray.length;i++){
                     if (finalArray[i] > maxValue){
                         maxValue = finalArray[i];
                     }
            }
            
            for(int i=0;i<finalArray.length;i++){
                finalArray[i] = finalArray[i]/maxValue;
            }
            
            //the result matrix is the new procedure matrix
            for(int i=0;i<finalArray.length;i++){
                procedureArray[i] = finalArray[i];
                finalArray[i]=0.0;
          
            }
            
            //if the last two procedureArrays are almost equal then we find the eigenvector
            for(int i =0;i<procedureArray.length;i++){
                if(Math.abs(procedureArray[i] - assistant[i]) < 1e-5){
                    counter++;
                }
            }
            
            if (counter==3){
                flag=true;
                loop = false;
            }else{
                flag=false;
                counter=0;
                
            }
            
            if(flag){
                
            }else{
               
                for(int i=0;i<procedureArray.length;i++){
                    assistant[i] = procedureArray[i];
                }
            }  
        }
        
        //normilize the procedureArray
        double sum=0.0;
        for (int i =0;i<procedureArray.length;i++){
            sum += procedureArray[i];
        }
        
        for (int i =0;i<procedureArray.length;i++){
            procedureArray[i] = procedureArray[i]/sum;
        }
        

        return procedureArray;
    }
    
    public double[][] buildSigmaMatrix(double sigma){
        double[][] initialArray = new double[3][3];
        double[] sigmaTrust = new double[3];
        double x12=0.0;
        
        //we build the 3x3 matrix based on sigma value
        
        if(sigma<4){
            sigmaTrust[0]=1.0;
            sigmaTrust[1]=7.0;
            sigmaTrust[2]=3.0;
            x12 = Math.abs(sigmaTrust[1] - sigmaTrust[2]);
        }else if(sigma<=7){
            sigmaTrust[0]=1.0;
            sigmaTrust[1]=1.0/9;
            sigmaTrust[2]=1.0/7;
            x12 = Math.abs(sigmaTrust[1] - sigmaTrust[2]);
        }else if(sigma>7){
            sigmaTrust[0]=1.0;
            sigmaTrust[1]=1.0/7;
            sigmaTrust[2]=1.0/9;
            x12 = Math.abs(sigmaTrust[1] - sigmaTrust[2]);
            
        }
        initialArray = new double[][] {{sigmaTrust[0],sigmaTrust[1],sigmaTrust[2]},{1.0/sigmaTrust[1],sigmaTrust[0],x12},{1.0/sigmaTrust[2],1.0/x12,sigmaTrust[0]}};
        return initialArray;
        
    }
    
    public double[][] buildDownloadsMatrix(int downloads){
        double[][] initialArray = new double[3][3];
        double[] downloadsTrust = new double[3];

        double x12 = 0.0;
        
        //we build the 3x3 matrix based on download value
        if(downloads<1000){
            downloadsTrust[0]=1.0;
            downloadsTrust[1]=1.0/7;
            downloadsTrust[2]=1.0/9;
            x12 = Math.abs(downloadsTrust[1] - downloadsTrust[2]);  
        }else if(downloads<10000){
            downloadsTrust[0]=1.0;
            downloadsTrust[1]=1.0/3;
            downloadsTrust[2]=1.0/5;
            x12 = Math.abs(downloadsTrust[1] - downloadsTrust[2]);             
        }else if(downloads<50000){
            downloadsTrust[0]=1.0;
            downloadsTrust[1]=1.0/7;
            downloadsTrust[2]=1.0/5;
            x12 = Math.abs(downloadsTrust[1] - downloadsTrust[2]);  
            
        }else if(downloads<100000){
            downloadsTrust[0]=1.0;
            downloadsTrust[1]=3.0;
            downloadsTrust[2]=5.0;
            x12 = Math.abs(downloadsTrust[1] - downloadsTrust[2]);  
            
        }else if(downloads<500000){
            downloadsTrust[0]=1.0;
            downloadsTrust[1]=5.0;
            downloadsTrust[2]=7.0;
            x12 = Math.abs(downloadsTrust[1] - downloadsTrust[2]);  
            
        }else if(downloads<1000000){
            downloadsTrust[0]=1.0;
            downloadsTrust[1]=7.0;
            downloadsTrust[2]=9.0;
            x12 = Math.abs(downloadsTrust[1] - downloadsTrust[2]);  
        }else if(downloads>10000000){
            downloadsTrust[0]=1.0;
            downloadsTrust[1]=9.0;
            downloadsTrust[2]=7.0;
            x12 = Math.abs(downloadsTrust[1] - downloadsTrust[2]);    
        }
        
        initialArray = new double[][] {{downloadsTrust[0],downloadsTrust[1],downloadsTrust[2]},{1.0/downloadsTrust[1],downloadsTrust[0],x12},{1.0/downloadsTrust[2],1.0/x12,downloadsTrust[0]}};
        return initialArray;
        
    }
    public double[][] buildRatingMatrix(double rating){
        double[][] initialArray = new double[3][3];
        double[] ratingTrust = new double[3];
       
        double x12 = 0.0;

        //we build the 3x3 matrix based on rating value
        if(rating<=1){
            ratingTrust[0]=1.0;
            ratingTrust[1]=1.0/7;
            ratingTrust[2]=1.0/9;
            x12 = Math.abs(ratingTrust[1] - ratingTrust[2]);  
        }else if(rating<=2){
            ratingTrust[0]=1.0;
            ratingTrust[1]=1.0/3;
            ratingTrust[2]=1.0/5;
            x12 = Math.abs(ratingTrust[1] - ratingTrust[2]);             
        }else if(rating<=3){
            ratingTrust[0]=1.0;
            ratingTrust[1]=1.0/7;
            ratingTrust[2]=1.0/5;
            x12 = Math.abs(ratingTrust[1] - ratingTrust[2]);  
            
        }else if(rating<=4){
            ratingTrust[0]=1.0;
            ratingTrust[1]=3.0;
            ratingTrust[2]=5.0;
            x12 = Math.abs(ratingTrust[1] - ratingTrust[2]);  
            
        }else if(rating<=5){
            ratingTrust[0]=1.0;
            ratingTrust[1]=5.0;
            ratingTrust[2]=7.0;
            x12 = Math.abs(ratingTrust[1] - ratingTrust[2]);  
            
        }
        
        
        initialArray = new double[][] {{ratingTrust[0],ratingTrust[1],ratingTrust[2]},{1.0/ratingTrust[1],ratingTrust[0],x12},{1.0/ratingTrust[2],1.0/x12,ratingTrust[0]}};
        
        
        return initialArray;
        
    }
    
        public double[][] buildRelevanceMatrix(int pref){
        double[][] initialArray = new double[3][3];
        double[] preference = new double[3];
        
        double x12=0.0;
       
        //we build the 3x3 matrix based on the parameter that concerns the user most
        if(pref==0){
            preference[0]=1.0;
            preference[1]=9.0;
            preference[2]=3.0;
            x12 = Math.abs(preference[1] - preference[2]);
        }else if(pref==1){
            preference[0]=1.0;
            preference[1]=1.0/9;
            preference[2]=1.0;
            x12 = Math.abs(preference[1] - preference[2]);
        }else if(pref==2){
            preference[0]=1.0;
            preference[1]=1.0/9;
            preference[2]=1.0/3;
            x12 = Math.abs(preference[1] - preference[2]);
            
        }else if(pref==3){
            preference[0]=1.0;
            preference[1]=1.0;
            preference[2]=1.0;
            x12 = 1.0;
        }
        
        initialArray = new double[][] {{preference[0],preference[1],preference[2]},{1.0/preference[1],preference[0],x12},{1.0/preference[2],1.0/x12,preference[0]}};
        
        return initialArray;
        
    }
    
    public void computeGlobalPriority(double[] priorities, double[] sigma, double[] downloads, double[] rating) throws FileNotFoundException, UnsupportedEncodingException{
        double[] gb = new double[3];
        double x=0.00, y=0.00, z=0.00;
        //calculate the final matrix
        
        for(int i=0;i<3;i++){
            x = sigma[i] * priorities[0];
            y = downloads[i] * priorities[1];
            z = rating[i] * priorities[2];
            gb[i]=x+y+z;
        }
        
        //find the max value that shows the final result
        double max=0.0;
        int flag = 0;
        for(int i=0;i<3;i++){
            if(max<gb[i]){
                max = gb[i];
                flag = i;
            }
        }
        
        this.exportFinalResult(gb);
        if(flag==0){
            System.out.println("Trusted");
            try(FileWriter fw = new FileWriter("report.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
            {
                out.println("");
                out.println("|------------------------------------|");
                out.println("|RESULT: TRUSTED                     |");
                out.println("|------------------------------------|");

            } catch (IOException e) {
                
            }
        }else if(flag==1){
            System.out.println("UnTrusted");
            try(FileWriter fw = new FileWriter("report.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
            {
                out.println("");
                out.println("|------------------------------------|");
                out.println("|RESULT: UNTRUSTED                   |");
                out.println("|------------------------------------|");
            } catch (IOException e) {
                
            }
        }else if(flag==2){
            System.out.println("Deceptive");
            try(FileWriter fw = new FileWriter("report.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
            {
                out.println("");
                out.println("|------------------------------------|");
                out.println("|RESULT: DECEPTIVE                   |");
                out.println("|------------------------------------|");
            } catch (IOException e) {
                
            }
        }
    }
    
    public void exportMatrx(double[][] matrix, int x) throws FileNotFoundException, UnsupportedEncodingException{

    try(FileWriter fw = new FileWriter("report.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter out = new PrintWriter(bw))
    {
        
        if(x==0){
            out.println("");
            out.println("");
            out.println("---------Sigma Matrix---------");
            
        }else if(x==1){
            out.println("");
            out.println("");
            out.println("---------Downloads Matrix---------");
          
        }else if(x==2){
            out.println("");
            out.println("");
            out.println("---------Rating Matrix---------");
        }
        
        for(int i=0;i<3;i++){
            out.println("");
            for(int j=0;j<3;j++){
                out.print(matrix[i][j]);
                out.print("   ");
            }
            
        }
        
    } catch (IOException e) {
        
      }
    }
    
    public void exportEigenVectors(double[] matrix, int x) throws FileNotFoundException, UnsupportedEncodingException{

    try(FileWriter fw = new FileWriter("report.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter out = new PrintWriter(bw))
    {
        out.println("");
        if(x==0){
            out.println("");
            out.println("---------Sigma EigenVector---------");
            
        }else if(x==1){
            out.println("");
            out.println("---------Downloads EigenVector---------");
            
        }else if(x==2){
            out.println("");
            out.println("---------Rating EigenVector---------");
            
        }
        
        for(int i=0;i<3;i++){
            out.println(matrix[i]);            
        }
        
    } catch (IOException e) {
        
      }
    }
     public void exportFinalResult(double[] matrix) throws FileNotFoundException, UnsupportedEncodingException{

    try(FileWriter fw = new FileWriter("report.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter out = new PrintWriter(bw))
    {
        out.println("");
        
        out.println("Global Priority");
        
        for(int i=0;i<3;i++){
            out.println(matrix[i]);            
        }
        
    } catch (IOException e) {
        
      }
    }
        
}
    
