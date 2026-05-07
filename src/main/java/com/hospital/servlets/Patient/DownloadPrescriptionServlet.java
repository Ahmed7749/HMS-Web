package com.hospital.servlets.Patient;

import com.hospital.daos.DoctorDAO;
import com.hospital.daos.PatientDAO;
import com.hospital.daos.PrescriptionsDAO;
import com.hospital.pojos.Patient;
import com.hospital.pojos.Prescription;
import com.hospital.pojos.User;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/patient/downloadPrescription")
public class DownloadPrescriptionServlet extends HttpServlet {

    private PrescriptionsDAO prescriptionsDAO;
    private DoctorDAO doctorDAO;
    private PatientDAO patientDAO;

    @Override
    public void init(ServletConfig config) throws ServletException {
        prescriptionsDAO = new PrescriptionsDAO();
        doctorDAO = new DoctorDAO();
        patientDAO = new PatientDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = getAuthenticatedUser(req);
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        try {
            int prescriptionId = Integer.parseInt(req.getParameter("id"));

            Prescription prescription = prescriptionsDAO.getPrescriptionById(prescriptionId).orElse(null);
            Patient patient = patientDAO.getPatientByUserId(user.getId()).orElse(null);

            if (prescription == null || patient == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Prescription or Patient profile not found.");
                return;
            }

            if (prescription.getPatientId() != patient.getId()) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
                return;
            }

            String doctorName = doctorDAO.getDoctorById(prescription.getDoctorId())
                    .map(d -> d.getName() + " " + d.getLastName())
                    .orElse("Unknown Specialist");

            String logoPath = req.getServletContext().getRealPath("/images/hospitalLogo.png");

            setupResponseHeaders(resp, prescription.getId());
            generatePdfDocument(resp, prescription, patient, doctorName, logoPath);

        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Prescription ID format.");
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while generating the PDF.");
        }
    }

    private User getAuthenticatedUser(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        return (session != null) ? (User) session.getAttribute("user") : null;
    }

    private void setupResponseHeaders(HttpServletResponse resp, int prescriptionId) {
        resp.setContentType("application/pdf");
        resp.setHeader("Content-Disposition", "attachment; filename=\"Prescription_" + prescriptionId + ".pdf\"");
    }

    private void generatePdfDocument(HttpServletResponse resp, Prescription prescription, Patient patient, String doctorName, String logoPath) throws Exception {
        try (Document document = new Document()) {
            PdfWriter.getInstance(document, resp.getOutputStream());
            document.open();

            insertLogo(document, logoPath);
            insertHeader(document);
            insertParticipantInfo(document, patient, doctorName);
            insertMedicationDetails(document, prescription);
            insertFooter(document);
        }
    }

    private void insertLogo(Document document, String logoPath) {
        try {
            Image logo = Image.getInstance(logoPath);
            logo.scaleToFit(150, 150);
            logo.setAlignment(Image.ALIGN_LEFT);
            document.add(logo);
            document.add(new Paragraph("\n"));
        } catch (Exception e) {
            System.err.println("Failed to load logo: " + e.getMessage());
        }
    }

    private void insertHeader(Document document) {
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

        document.add(new Paragraph("PURE HEARTS HOSPITAL", titleFont));
        document.add(new Paragraph("Official Medical Prescription\n\n", normalFont));
    }

    private void insertParticipantInfo(Document document, Patient patient, String doctorName) {
        Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

        document.add(new Paragraph("Patient Name: " + patient.getName() + " " + patient.getLastName(), normalFont));
        document.add(new Paragraph("Attending Doctor: Dr. " + doctorName, normalFont));
        document.add(new Paragraph("--------------------------------------------------\n\n", normalFont));
    }

    private void insertMedicationDetails(Document document, Prescription prescription) {
        Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
        Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

        document.add(new Paragraph("Medication: " + prescription.getMedicationName(), boldFont));
        document.add(new Paragraph("Dosage: " + prescription.getDosage(), normalFont));
        document.add(new Paragraph("Instructions: " + prescription.getInstructions(), normalFont));
    }

    private void insertFooter(Document document) {
        Font italicFont = FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 10);
        Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

        document.add(new Paragraph("\n\n--------------------------------------------------", normalFont));
        document.add(new Paragraph("Generated securely by HMS-Web Portal", italicFont));
    }
}