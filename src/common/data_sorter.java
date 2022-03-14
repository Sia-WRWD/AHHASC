/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author chinojen7
 */
public class data_sorter implements Comparator<String> {

    public int compare(String o1, String o2) {
        return o1.compareToIgnoreCase(o2);
    }

    public void sortItemData(String filepath, String tofileName) {

        try { //To sort the txt Data File Alphabetically.

            Path path = Paths.get(filepath);
            Charset charset = Charset.forName("UTF-8");

            List<String> lines = Files.readAllLines(path, charset);
            Collections.sort(lines, new data_sorter());

            Path toPath = Paths.get(tofileName);
            Files.write(toPath, lines, charset);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An Error has Occurred while Sorting!", "Item Sorting Failure", JOptionPane.WARNING_MESSAGE);
        }
    }
}
