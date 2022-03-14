/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.io.IOException;
import javax.swing.JTextField;

/**
 *
 * @author admin
 */
public interface appointment_management_interface {
    // Validation Functions
    public boolean validateApptFields(JTextField roomNo);
    public boolean validateCreateApptFields(JTextField roomNo, JTextField date);
    public boolean validatePaymentFields(JTextField date, JTextField amount);
    public boolean validateFeedbackFields(JTextField date);
    
    // Appointment CRUD Functions
    public void addApptInformation(String client_id, String room_no, String appt_date, String appt_time, String appliance, 
            String technician_id, String created_by) throws IOException;
    public boolean deleteApptData(String filepath, String removeterm, int positionOfTerm, String delimiter)
            throws IOException;
    public boolean updateApptData(String id, String room_no, String appliance, String job_status, String updatedBy)
            throws IOException;
    public boolean updatePayData(String id, String date, String amount, String status, String updated_by)
            throws IOException;
    public boolean updateFeedbackData(String id, String content, String date, String updated_by)
            throws IOException;
    
}
