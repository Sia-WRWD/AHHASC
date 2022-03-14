/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.io.FileOutputStream;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.*;
import java.text.DecimalFormat;
import common.fetch_data;
import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author chinojen7
 */
public class generate_report_data {

    String fileDir = System.getProperty("user.dir") + "\\src\\reports\\";
    String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October",
        "November", "December"};

    private void insertCell(PdfPTable table, String text, int align, int colspan, Font font) {
        //create a new cell with the specified Text and Font
        PdfPCell cell = new PdfPCell(new Phrase(text.trim(), font));
        //set the cell alignment
        cell.setHorizontalAlignment(align);
        //set the cell column span in case you want to merge two or more cells
        cell.setColspan(colspan);
        //in case there is no text and you wan to create an empty row
        if (text.trim().equalsIgnoreCase("")) {
            cell.setMinimumHeight(10f);
        }
        //add the call to the table
        table.addCell(cell);
    }

    private boolean filterData(String[] data, String reportType, String filterType, int filterInt) {
        String[] date;

        // Transaction 
        if (reportType.equals("Transaction") && !data[2].equals("?")) {
            date = data[2].split("-", 3);
            if (filterType.equals("Monthly")) {
                if (Integer.parseInt(date[1]) == filterInt) {
                    return true;
                }
            } else if (filterType.equals("Yearly")) {
                if (Integer.parseInt(date[0]) == filterInt) {
                    return true;
                }
            }
        } else if (reportType.equals("Feedback") && !data[3].equals("?") && !data[2].equals("?")) {
            // Feedback (Customers who didn't provide feedbacks will not be displayed)
            date = data[3].split("-", 3);
            if (filterType.equals("Monthly")) {
                if (Integer.parseInt(date[1]) == filterInt) {
                    return true;
                }
            } else if (filterType.equals("Yearly")) {
                if (Integer.parseInt(date[0]) == filterInt) {
                    return true;
                }
            }
        } else if (reportType.equals("Appointment") && !data[2].equals("?")) {
            // Appointment
            date = data[2].split("-", 3);
            if (filterType.equals("Monthly")) {
                if (Integer.parseInt(date[1]) == filterInt) {
                    return true;
                }
            } else if (filterType.equals("Yearly")) {
                if (Integer.parseInt(date[0]) == filterInt) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean filterUsers(String[] data, String userType) {
        if (data[7].equals(userType)) {
            return true;
        }

        return false;
    }

    public void createTransactionReport(String type, int filter) throws IOException {
        Document doc = new Document();
        PdfWriter docWriter = null;
        fetch_data fd = new fetch_data();
        ArrayList<String[]> transactionData = fd.fetchTransactionData();

        DecimalFormat df = new DecimalFormat("0.00");

        try {
            //special font sizes
            Font bfBold12 = new Font(FontFamily.TIMES_ROMAN, 10, Font.BOLD, new BaseColor(0, 0, 0));
            Font bf12 = new Font(FontFamily.TIMES_ROMAN, 10);
            String path = "";
            String filename = "";

            //file path
            if (type == "Monthly") {
                filename = type + " " + "Transaction Report for " + months[filter];
                path = fileDir + filename + ".pdf";
            } else if (type == "Yearly") {
                filename = type + " " + "Transaction Report for " + filter;
                path = fileDir + filename + ".pdf";
            }
            docWriter = PdfWriter.getInstance(doc, new FileOutputStream(path));
            docWriter.setPageEvent(new watermark());

            //open document
            doc.open();

            //create a paragraph
            Font bold = FontFactory.getFont(FontFactory.TIMES_BOLD, 12, Font.BOLD);
            Paragraph paragraph = new Paragraph(filename, bold);

            //specify column widths
            float[] columnWidths = {2f, 2f, 2f, 2f, 2f, 2f};
            //create PDF table with the given widths
            PdfPTable table = new PdfPTable(columnWidths);
            // set table width a percentage of the page width
            table.setWidthPercentage(90f);

            //insert column headings
            insertCell(table, "Transaction ID", Element.ALIGN_CENTER, 1, bfBold12);
            insertCell(table, "Appointment ID", Element.ALIGN_CENTER, 1, bfBold12);
            insertCell(table, "Date", Element.ALIGN_CENTER, 1, bfBold12);
            insertCell(table, "Amount", Element.ALIGN_CENTER, 1, bfBold12);
            insertCell(table, "Status", Element.ALIGN_CENTER, 1, bfBold12);
            insertCell(table, "Handled By", Element.ALIGN_CENTER, 1, bfBold12);
            table.setHeaderRows(1);

            //insert an empty row
            insertCell(table, "", Element.ALIGN_LEFT, 6, bfBold12);

            //just some random data to fill 
            for (int x = 0; x < transactionData.size(); x++) {
                String[] matchedID = transactionData.get(x);
                if (type == "Monthly") {
                    if (this.filterData(matchedID, "Transaction", type, filter + 1)) {
                        insertCell(table, matchedID[0], Element.ALIGN_CENTER, 1, bf12);
                        insertCell(table, matchedID[1], Element.ALIGN_CENTER, 1, bf12);
                        insertCell(table, matchedID[2], Element.ALIGN_CENTER, 1, bf12);
                        insertCell(table, matchedID[3], Element.ALIGN_CENTER, 1, bf12);
                        insertCell(table, matchedID[4], Element.ALIGN_CENTER, 1, bf12);
                        insertCell(table, matchedID[5], Element.ALIGN_CENTER, 1, bf12);
                    }
                } else if (type == "Yearly") {
                    if (this.filterData(matchedID, "Transaction", type, filter)) {
                        insertCell(table, matchedID[0], Element.ALIGN_CENTER, 1, bf12);
                        insertCell(table, matchedID[1], Element.ALIGN_CENTER, 1, bf12);
                        insertCell(table, matchedID[2], Element.ALIGN_CENTER, 1, bf12);
                        insertCell(table, matchedID[3], Element.ALIGN_CENTER, 1, bf12);
                        insertCell(table, matchedID[4], Element.ALIGN_CENTER, 1, bf12);
                        insertCell(table, matchedID[5], Element.ALIGN_CENTER, 1, bf12);
                    }
                }
            }

            //add the PDF table to the paragraph 
            paragraph.add(table);
            // add the paragraph to the document
            doc.add(paragraph);

        } catch (DocumentException dex) {
            dex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (doc != null) {
                //close the document
                doc.close();
            }
            if (docWriter != null) {
                //close the writer
                docWriter.close();
            }
        }
    }

    public void createFeedbackReport(String type, int filter) throws IOException {
        Document doc = new Document();
        PdfWriter docWriter = null;
        fetch_data fd = new fetch_data();
        ArrayList<String[]> feedbackData = fd.fetchFeedbackData();

        DecimalFormat df = new DecimalFormat("0.00");

        try {
            //special font sizes
            Font bfBold12 = new Font(FontFamily.TIMES_ROMAN, 10, Font.BOLD, new BaseColor(0, 0, 0));
            Font bf12 = new Font(FontFamily.TIMES_ROMAN, 10);
            String path = "";
            String filename = "";

            //file path
            if (type == "Monthly") {
                filename = type + " " + "Feedback Report for " + months[filter];
                path = fileDir + filename + ".pdf";
            } else if (type == "Yearly") {
                filename = type + " " + "Feedback Report for " + filter;
                path = fileDir + filename + ".pdf";
            }
            docWriter = PdfWriter.getInstance(doc, new FileOutputStream(path));
            docWriter.setPageEvent(new watermark());

            //open document
            doc.open();

            //create a paragraph
            Font bold = FontFactory.getFont(FontFactory.TIMES_BOLD, 12, Font.BOLD);
            Paragraph paragraph = new Paragraph(filename, bold);

            //specify column widths
            float[] columnWidths = {1.5f, 1.8f, 4f, 1.5f, 1.5f};
            //create PDF table with the given widths
            PdfPTable table = new PdfPTable(columnWidths);
            // set table width a percentage of the page width
            table.setWidthPercentage(90f);

            //insert column headings
            insertCell(table, "Feedback ID", Element.ALIGN_CENTER, 1, bfBold12);
            insertCell(table, "Appointment ID", Element.ALIGN_CENTER, 1, bfBold12);
            insertCell(table, "Feedback Content", Element.ALIGN_CENTER, 1, bfBold12);
            insertCell(table, "Date", Element.ALIGN_CENTER, 1, bfBold12);
            insertCell(table, "Technician / Manager ID", Element.ALIGN_CENTER, 1, bfBold12);
            table.setHeaderRows(1);

            //insert an empty row
            insertCell(table, "", Element.ALIGN_LEFT, 5, bfBold12);

            //just some random data to fill 
            for (int x = 0; x < feedbackData.size(); x++) {
                String[] matchedID = feedbackData.get(x);
                if (type == "Monthly") {
                    if (this.filterData(matchedID, "Feedback", type, filter + 1)) {
                        insertCell(table, matchedID[0], Element.ALIGN_CENTER, 1, bf12);
                        insertCell(table, matchedID[1], Element.ALIGN_CENTER, 1, bf12);
                        insertCell(table, matchedID[2], Element.ALIGN_CENTER, 1, bf12);
                        insertCell(table, matchedID[3], Element.ALIGN_CENTER, 1, bf12);
                        insertCell(table, matchedID[4], Element.ALIGN_CENTER, 1, bf12);
                    }
                } else if (type == "Yearly") {
                    if (this.filterData(matchedID, "Feedback", type, filter)) {
                        insertCell(table, matchedID[0], Element.ALIGN_CENTER, 1, bf12);
                        insertCell(table, matchedID[1], Element.ALIGN_CENTER, 1, bf12);
                        insertCell(table, matchedID[2], Element.ALIGN_CENTER, 1, bf12);
                        insertCell(table, matchedID[3], Element.ALIGN_CENTER, 1, bf12);
                        insertCell(table, matchedID[4], Element.ALIGN_CENTER, 1, bf12);
                    }
                }
            }

            //add the PDF table to the paragraph 
            paragraph.add(table);
            // add the paragraph to the document
            doc.add(paragraph);

        } catch (DocumentException dex) {
            dex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (doc != null) {
                //close the document
                doc.close();
            }
            if (docWriter != null) {
                //close the writer
                docWriter.close();
            }
        }
    }

    public void createApptReport(String type, int filter) throws IOException {
        Document doc = new Document();
        PdfWriter docWriter = null;
        fetch_data fd = new fetch_data();
        ArrayList<String[]> apptData = fd.fetchApptData();

        DecimalFormat df = new DecimalFormat("0.00");

        try {
            //special font sizes
            Font bfBold12 = new Font(FontFamily.TIMES_ROMAN, 10, Font.BOLD, new BaseColor(0, 0, 0));
            Font bf12 = new Font(FontFamily.TIMES_ROMAN, 10);
            String path = "";
            String filename = "";

            //file path
            if (type == "Monthly") {
                filename = type + " " + "Appointment Report for " + months[filter];
                path = fileDir + filename + ".pdf";
            } else if (type == "Yearly") {
                filename = type + " " + "Appointment Report for " + filter;
                path = fileDir + filename + ".pdf";
            }
            docWriter = PdfWriter.getInstance(doc, new FileOutputStream(path));
            docWriter.setPageEvent(new watermark());

            //open document
            doc.open();

            //create a paragraph
            Font bold = FontFactory.getFont(FontFactory.TIMES_BOLD, 12, Font.BOLD);
            Paragraph paragraph = new Paragraph(filename, bold);

            //specify column widths
            float[] columnWidths = {3f, 3f, 2f, 2f, 2f, 4f, 3f, 3f, 3f};
            //create PDF table with the given widths
            PdfPTable table = new PdfPTable(columnWidths);
            // set table width a percentage of the page width
            table.setWidthPercentage(100f);

            //insert column headings
            insertCell(table, "Appointment ID", Element.ALIGN_CENTER, 1, bfBold12);
            insertCell(table, "Client ID", Element.ALIGN_CENTER, 1, bfBold12);
            insertCell(table, "Date", Element.ALIGN_CENTER, 1, bfBold12);
            insertCell(table, "Time", Element.ALIGN_CENTER, 1, bfBold12);
            insertCell(table, "Room No.", Element.ALIGN_CENTER, 1, bfBold12);
            insertCell(table, "Activities", Element.ALIGN_CENTER, 1, bfBold12);
            insertCell(table, "Handled By", Element.ALIGN_CENTER, 1, bfBold12);
            insertCell(table, "Status", Element.ALIGN_CENTER, 1, bfBold12);
            insertCell(table, "Assigned By", Element.ALIGN_CENTER, 1, bfBold12);
            table.setHeaderRows(1);

            //insert an empty row
            insertCell(table, "", Element.ALIGN_LEFT, 9, bfBold12);

            //just some random data to fill 
            for (int x = 0; x < apptData.size(); x++) {
                String[] matchedID = apptData.get(x);
                if (type == "Monthly") {
                    if (this.filterData(matchedID, "Appointment", type, filter + 1)) {
                        insertCell(table, matchedID[0], Element.ALIGN_CENTER, 1, bf12);
                        insertCell(table, matchedID[1], Element.ALIGN_CENTER, 1, bf12);
                        insertCell(table, matchedID[2], Element.ALIGN_CENTER, 1, bf12);
                        insertCell(table, matchedID[3], Element.ALIGN_CENTER, 1, bf12);
                        insertCell(table, matchedID[4], Element.ALIGN_CENTER, 1, bf12);
                        insertCell(table, matchedID[5], Element.ALIGN_CENTER, 1, bf12);
                        insertCell(table, matchedID[6], Element.ALIGN_CENTER, 1, bf12);
                        insertCell(table, matchedID[7], Element.ALIGN_CENTER, 1, bf12);
                        insertCell(table, matchedID[8], Element.ALIGN_CENTER, 1, bf12);
                    }
                } else if (type == "Yearly") {
                    if (this.filterData(matchedID, "Appointment", type, filter)) {
                        insertCell(table, matchedID[0], Element.ALIGN_CENTER, 1, bf12);
                        insertCell(table, matchedID[1], Element.ALIGN_CENTER, 1, bf12);
                        insertCell(table, matchedID[2], Element.ALIGN_CENTER, 1, bf12);
                        insertCell(table, matchedID[3], Element.ALIGN_CENTER, 1, bf12);
                        insertCell(table, matchedID[4], Element.ALIGN_CENTER, 1, bf12);
                        insertCell(table, matchedID[5], Element.ALIGN_CENTER, 1, bf12);
                        insertCell(table, matchedID[6], Element.ALIGN_CENTER, 1, bf12);
                        insertCell(table, matchedID[7], Element.ALIGN_CENTER, 1, bf12);
                        insertCell(table, matchedID[8], Element.ALIGN_CENTER, 1, bf12);
                    }
                }
            }

            //add the PDF table to the paragraph 
            paragraph.add(table);
            // add the paragraph to the document
            doc.add(paragraph);

        } catch (DocumentException dex) {
            dex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (doc != null) {
                //close the document
                doc.close();
            }
            if (docWriter != null) {
                //close the writer
                docWriter.close();
            }
        }
    }

    public void createUserDetailsReport() throws IOException {
        Document doc = new Document();
        PdfWriter docWriter = null;
        fetch_data fd = new fetch_data();
        ArrayList<String[]> usersData = fd.fetchUserData();
        ArrayList<String[]> clientData = fd.fetchClientData();

        DecimalFormat df = new DecimalFormat("0.00");

        try {
            //special font sizes
            Font bfBold12 = new Font(FontFamily.TIMES_ROMAN, 10, Font.BOLD, new BaseColor(0, 0, 0));
            Font bf12 = new Font(FontFamily.TIMES_ROMAN, 10);
            String path = "";
            String filename = "Users Details Report";

            //file path
            path = fileDir + filename + ".pdf";

            docWriter = PdfWriter.getInstance(doc, new FileOutputStream(path));
            docWriter.setPageEvent(new watermark());

            //open document
            doc.open();

            // Technician Details
            Font bold = FontFactory.getFont(FontFactory.TIMES_BOLD, 12, Font.BOLD);
            Paragraph t_paragraph = new Paragraph("Technician Details", bold);

            float[] t_columnWidths = {3f, 3f, 3f, 3f, 3f, 3f, 3f, 3f};
            PdfPTable t_table = new PdfPTable(t_columnWidths);
            t_table.setWidthPercentage(90f);

            insertCell(t_table, "User ID", Element.ALIGN_CENTER, 1, bfBold12);
            insertCell(t_table, "Username", Element.ALIGN_CENTER, 1, bfBold12);
            insertCell(t_table, "Name", Element.ALIGN_CENTER, 1, bfBold12);
            insertCell(t_table, "Contact No.", Element.ALIGN_CENTER, 1, bfBold12);
            insertCell(t_table, "Address", Element.ALIGN_CENTER, 1, bfBold12);
            insertCell(t_table, "Email", Element.ALIGN_CENTER, 1, bfBold12);
            insertCell(t_table, "Gender", Element.ALIGN_CENTER, 1, bfBold12);
            insertCell(t_table, "Role", Element.ALIGN_CENTER, 1, bfBold12);
            t_table.setHeaderRows(1);

            insertCell(t_table, "", Element.ALIGN_LEFT, 9, bfBold12);

            for (int x = 0; x < usersData.size(); x++) {
                String[] matchedID = usersData.get(x);
                if (this.filterUsers(matchedID, "Technician")) {
                    insertCell(t_table, matchedID[0], Element.ALIGN_CENTER, 1, bf12);
                    insertCell(t_table, matchedID[1], Element.ALIGN_CENTER, 1, bf12);
                    insertCell(t_table, matchedID[2], Element.ALIGN_CENTER, 1, bf12);
                    insertCell(t_table, matchedID[3], Element.ALIGN_CENTER, 1, bf12);
                    insertCell(t_table, matchedID[4], Element.ALIGN_CENTER, 1, bf12);
                    insertCell(t_table, matchedID[5], Element.ALIGN_CENTER, 1, bf12);
                    insertCell(t_table, matchedID[6], Element.ALIGN_CENTER, 1, bf12);
                    insertCell(t_table, matchedID[7], Element.ALIGN_CENTER, 1, bf12);
                }
            }

            t_paragraph.add(t_table);
            doc.add(t_paragraph);

            // Manager Details
            Paragraph m_paragraph = new Paragraph("Manager Details", bold);

            float[] m_columnWidths = {3f, 3f, 3f, 3f, 3f, 3f, 3f, 3f};
            PdfPTable m_table = new PdfPTable(m_columnWidths);
            m_table.setWidthPercentage(90f);

            insertCell(m_table, "User ID", Element.ALIGN_CENTER, 1, bfBold12);
            insertCell(m_table, "Username", Element.ALIGN_CENTER, 1, bfBold12);
            insertCell(m_table, "Name", Element.ALIGN_CENTER, 1, bfBold12);
            insertCell(m_table, "Contact No.", Element.ALIGN_CENTER, 1, bfBold12);
            insertCell(m_table, "Address", Element.ALIGN_CENTER, 1, bfBold12);
            insertCell(m_table, "Email", Element.ALIGN_CENTER, 1, bfBold12);
            insertCell(m_table, "Gender", Element.ALIGN_CENTER, 1, bfBold12);
            insertCell(m_table, "Role", Element.ALIGN_CENTER, 1, bfBold12);
            m_table.setHeaderRows(1);

            insertCell(m_table, "", Element.ALIGN_LEFT, 9, bfBold12);

            for (int x = 0; x < usersData.size(); x++) {
                String[] matchedID = usersData.get(x);
                if (this.filterUsers(matchedID, "Manager")) {
                    insertCell(m_table, matchedID[0], Element.ALIGN_CENTER, 1, bf12);
                    insertCell(m_table, matchedID[1], Element.ALIGN_CENTER, 1, bf12);
                    insertCell(m_table, matchedID[2], Element.ALIGN_CENTER, 1, bf12);
                    insertCell(m_table, matchedID[3], Element.ALIGN_CENTER, 1, bf12);
                    insertCell(m_table, matchedID[4], Element.ALIGN_CENTER, 1, bf12);
                    insertCell(m_table, matchedID[5], Element.ALIGN_CENTER, 1, bf12);
                    insertCell(m_table, matchedID[6], Element.ALIGN_CENTER, 1, bf12);
                    insertCell(m_table, matchedID[7], Element.ALIGN_CENTER, 1, bf12);
                }
            }

            m_paragraph.add(m_table);
            doc.add(m_paragraph);

            // Customer Details
            Paragraph c_paragraph = new Paragraph("Customer Details", bold);

            float[] c_columnWidths = {3f, 3f, 3f, 3f, 3f, 3f};
            PdfPTable c_table = new PdfPTable(c_columnWidths);
            c_table.setWidthPercentage(90f);

            insertCell(c_table, "Client ID", Element.ALIGN_CENTER, 1, bfBold12);
            insertCell(c_table, "Name", Element.ALIGN_CENTER, 1, bfBold12);
            insertCell(c_table, "Contact No.", Element.ALIGN_CENTER, 1, bfBold12);
            insertCell(c_table, "Address", Element.ALIGN_CENTER, 1, bfBold12);
            insertCell(c_table, "Email", Element.ALIGN_CENTER, 1, bfBold12);
            insertCell(c_table, "Gender", Element.ALIGN_CENTER, 1, bfBold12);
            c_table.setHeaderRows(1);

            insertCell(c_table, "", Element.ALIGN_LEFT, 9, bfBold12);

            for (int x = 0; x < clientData.size(); x++) {
                String[] matchedID = clientData.get(x);
                insertCell(c_table, matchedID[0], Element.ALIGN_CENTER, 1, bf12);
                insertCell(c_table, matchedID[1], Element.ALIGN_CENTER, 1, bf12);
                insertCell(c_table, matchedID[2], Element.ALIGN_CENTER, 1, bf12);
                insertCell(c_table, matchedID[3], Element.ALIGN_CENTER, 1, bf12);
                insertCell(c_table, matchedID[4], Element.ALIGN_CENTER, 1, bf12);
                insertCell(c_table, matchedID[5], Element.ALIGN_CENTER, 1, bf12);
            }

            c_paragraph.add(c_table);
            doc.add(c_paragraph);
        } catch (DocumentException dex) {
            dex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (doc != null) {
                //close the document
                doc.close();
            }
            if (docWriter != null) {
                //close the writer
                docWriter.close();
            }
        }
    }

    public static void main(String[] args) throws IOException {
//        generate_report_data gr = new generate_report_data();
//        gr.createTransactionReport("Monthly", 0);
//        gr.createFeedbackReport("Monthly", 11);
//        gr.createApptReport("Monthly", 11);
//
//        gr.createTransactionReport("Yearly", 2022);
//        gr.createFeedbackReport("Yearly", 2021);
//        gr.createApptReport("Yearly", 2021);
//        
//        gr.createUserDetailsReport();
    }

}
