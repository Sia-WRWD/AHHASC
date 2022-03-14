/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author chinojen7
 */
public class login {
    private String userID, saveDir, username, role;
    
    public boolean accountValidator(String usernameText, String passwordText){
        boolean isAuthorized = false;
        try{
            saveDir = System.getProperty("user.dir") + "/src/database/";
            String tmpUser = usernameText;
            String tmpPass = String.valueOf(passwordText);
            
            File staffAccTxt = new File(saveDir + "user_details.txt");
            
            if(!staffAccTxt.exists()){
                staffAccTxt.createNewFile();
            }
            
            Scanner inputFile = new Scanner(staffAccTxt);
            String[] matchedID;
            while(inputFile.hasNext()){
                String entry = inputFile.nextLine();
                matchedID = entry.split("\\|");
                if(tmpUser.equals(matchedID[1]) && tmpPass.equals(matchedID[2])){
                    isAuthorized = true;
                    userID = matchedID[0];
                    username = matchedID[1];
                    role = matchedID[8];
                    startSession();
                }
            }
            inputFile.close();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        
        return isAuthorized;
    }
    
    private void startSession(){
        try{
            saveDir = System.getProperty("user.dir") + "/src/database/";
            File cache = new File(saveDir + "user_cache.txt");
            if(!cache.exists()){
                cache.createNewFile();
            }
            FileWriter ld = new FileWriter(saveDir + "user_cache.txt");
            PrintWriter ldp = new PrintWriter(ld);
            ldp.println(userID + "|" + username + "|" + role);
            ldp.close();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public boolean logout() {
        try {
            saveDir = System.getProperty("user.dir") + "/src/database/";
            File cache = new File(saveDir + "user_cache.txt");
            if (cache.exists()) {
                cache.delete();
            }
            if(!cache.exists()){
                return true;
            }
            return false;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }
    
    
    
}
