/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import javax.swing.JOptionPane;

/**
 *
 * @author chinojen7
 */
public class data_deletion {

    boolean succeed = false;

    public boolean deleteData(String filepath, String removeterm, int positionOfTerm, String delimiter) {

        int pos = positionOfTerm - 1;

        String tempFile = System.getProperty("user.dir") + "\\src\\database\\temp.txt";
        File oldFile = new File(filepath);
        File newFile = new File(tempFile);
        String readData;
        String data[];

        try {

            FileWriter fw = new FileWriter(tempFile, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            FileReader fr = new FileReader(filepath);
            BufferedReader br = new BufferedReader(fr);

            while ((readData = br.readLine()) != null) { //Data in data.txt Will Be Inserted into Data Array.
                data = readData.split("\\|");
                if (!(data[pos].equalsIgnoreCase(removeterm))) { //Identify Which Line Contains the removeterm Value.
                    pw.println(readData); //Writes to the temp.txt File Without the Data of the Identified Line with the removeterm Value.
                }
            }

            pw.flush();
            pw.close();
            fr.close();
            br.close();
            bw.close();
            fw.close();

            oldFile.delete(); //Delete the old Admin.txt File.
            File dump = new File(filepath);
            newFile.renameTo(dump); //Renames The temp.txt File created just now to Admin.txt.

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An Error has Occured while Deleting File!", "File Deletion Failure", JOptionPane.WARNING_MESSAGE);
        }

        return succeed = true;
    }
}
