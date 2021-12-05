/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahhasc;

import java.io.IOException;
import view.login_page;
import view.manager_mm;

/**
 *
 * @author chinojen7
 */
public class AHHASC {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        login_page loginPage = new login_page();
        loginPage.setVisible(true);
    }
}
