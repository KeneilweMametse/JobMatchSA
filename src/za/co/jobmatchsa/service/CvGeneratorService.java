package za.co.jobmatchsa.service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.font.PdfFont;

import za.co.jobmatchsa.model.CandidateProfile;
import za.co.jobmatchsa.model.User;

import java.io.IOException;

public class CvGeneratorService {

    public String generateBaseCv(User user, CandidateProfile profile) {
        String fileName = "cv_" + user.getId() + "_" + System.currentTimeMillis() + ".pdf";
        String filePath = "generated_cvs/" + fileName;

        try {
            new java.io.File("generated_cvs").mkdirs();

            PdfWriter writer = new PdfWriter(filePath);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            PdfFont boldFont = PdfFontFactory.createFont("Helvetica-Bold");

            document.add(new Paragraph(user.getFullName())
                    .setFont(boldFont).setFontSize(20)
                    .setTextAlignment(TextAlignment.CENTER));

            document.add(new Paragraph(user.getEmail() +
                    (profile.getPhoneNumber() != null && !profile.getPhoneNumber().isBlank()
                            ? " | " + profile.getPhoneNumber() : "") +
                    " | " + (profile.getLocation() != null ? profile.getLocation() : ""))
                    .setFontSize(11).setTextAlignment(TextAlignment.CENTER));

            document.add(new Paragraph("\n"));

            document.add(new Paragraph("PROFESSIONAL SUMMARY")
                    .setFont(boldFont).setFontSize(13));
            document.add(new Paragraph(
                    "Motivated professional with " + profile.getYearsExperience() +
                            " years of experience, based in " + profile.getLocation() +
                            ". Holds a " + profile.getEducationLevel() +
                            " qualification with strong skills in " + profile.getSkills() + "."
            ).setFontSize(11));

            document.add(new Paragraph("\n"));

            document.add(new Paragraph("SKILLS").setFont(boldFont).setFontSize(13));
            String[] skillsArray = profile.getSkills().split(",");
            StringBuilder skillsList = new StringBuilder();
            for (String skill : skillsArray) {
                skillsList.append("• ").append(skill.trim()).append("\n");
            }
            document.add(new Paragraph(skillsList.toString()).setFontSize(11));

            document.add(new Paragraph("EDUCATION").setFont(boldFont).setFontSize(13));
            document.add(new Paragraph(profile.getEducationLevel()).setFontSize(11));

            document.add(new Paragraph("\n"));

            document.add(new Paragraph("EXPERIENCE").setFont(boldFont).setFontSize(13));
            document.add(new Paragraph(profile.getYearsExperience() +
                    " years of relevant professional experience.").setFontSize(11));

            document.close();
            return filePath;

        } catch (IOException e) {
            System.err.println("Error generating CV: " + e.getMessage());
            return null;
        }
    }
}