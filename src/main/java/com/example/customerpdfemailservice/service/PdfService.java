package com.example.customerpdfemailservice.service;

import com.example.customerpdfemailservice.entity.Customer;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import org.springframework.stereotype.Service;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class PdfService {

    public byte[] generateCustomerPdf(Customer customer) {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        Document document = new Document(PageSize.A4, 45, 45, 45, 45);

        try {
            PdfWriter.getInstance(document, outputStream);

            document.open();

            // =========================================================
            // FONTS
            // =========================================================

            Font titleFont = new Font(
                    Font.HELVETICA,
                    22,
                    Font.BOLD,
                    new Color(31, 41, 55)
            );

            Font subtitleFont = new Font(
                    Font.HELVETICA,
                    10,
                    Font.NORMAL,
                    new Color(107, 114, 128)
            );

            Font sectionFont = new Font(
                    Font.HELVETICA,
                    12,
                    Font.BOLD,
                    new Color(31, 41, 55)
            );

            Font labelFont = new Font(
                    Font.HELVETICA,
                    9,
                    Font.BOLD,
                    new Color(107, 114, 128)
            );

            Font valueFont = new Font(
                    Font.HELVETICA,
                    11,
                    Font.NORMAL,
                    new Color(17, 24, 39)
            );

            Font smallFont = new Font(
                    Font.HELVETICA,
                    8,
                    Font.NORMAL,
                    new Color(107, 114, 128)
            );

            Font confidentialFont = new Font(
                    Font.HELVETICA,
                    8,
                    Font.BOLD,
                    new Color(75, 85, 99)
            );


            // =========================================================
            // HEADER
            // =========================================================

            PdfPTable header = new PdfPTable(2);
            header.setWidthPercentage(100);
            header.setWidths(new float[]{70, 30});

            PdfPCell titleCell = new PdfPCell();
            titleCell.setBorder(Rectangle.NO_BORDER);
            titleCell.setPadding(0);

            Paragraph title = new Paragraph(
                    "CUSTOMER PROFILE",
                    titleFont
            );

            Paragraph subtitle = new Paragraph(
                    "Customer Information Document",
                    subtitleFont
            );

            titleCell.addElement(title);
            titleCell.addElement(subtitle);

            header.addCell(titleCell);


            // Customer ID box

            PdfPCell idCell = new PdfPCell();
            idCell.setBorder(Rectangle.NO_BORDER);
            idCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            idCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

            Paragraph idParagraph = new Paragraph(
                    "CUSTOMER ID\n#" + String.format("%06d", customer.getId()),
                    new Font(
                            Font.HELVETICA,
                            10,
                            Font.BOLD,
                            Color.WHITE
                    )
            );

            idParagraph.setAlignment(Element.ALIGN_CENTER);

            idCell.setBackgroundColor(
                    new Color(37, 99, 235)
            );

            idCell.setPadding(10);
            idCell.addElement(idParagraph);

            header.addCell(idCell);

            document.add(header);


            // =========================================================
            // SPACE
            // =========================================================

            document.add(new Paragraph(" "));


            // =========================================================
            // GENERATED INFORMATION
            // =========================================================

            LocalDateTime now = LocalDateTime.now();

            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern(
                            "dd MMMM yyyy, hh:mm a"
                    );

            String generatedDate = now.format(formatter);

            PdfPTable documentInfo = new PdfPTable(2);
            documentInfo.setWidthPercentage(100);
            documentInfo.setWidths(new float[]{50, 50});

            addInfoCell(
                    documentInfo,
                    "DOCUMENT TYPE",
                    "Customer Profile",
                    labelFont,
                    valueFont
            );

            addInfoCell(
                    documentInfo,
                    "GENERATED",
                    generatedDate,
                    labelFont,
                    valueFont
            );

            document.add(documentInfo);

            document.add(new Paragraph(" "));


            // =========================================================
            // SECTION TITLE
            // =========================================================

            Paragraph sectionTitle =
                    new Paragraph(
                            "CUSTOMER INFORMATION",
                            sectionFont
                    );

            sectionTitle.setSpacingBefore(8);
            sectionTitle.setSpacingAfter(8);

            document.add(sectionTitle);


            // =========================================================
            // CUSTOMER DETAILS TABLE
            // =========================================================

            PdfPTable detailsTable = new PdfPTable(2);

            detailsTable.setWidthPercentage(100);
            detailsTable.setWidths(new float[]{32, 68});


            addDetailRow(
                    detailsTable,
                    "Customer ID",
                    String.valueOf(customer.getId()),
                    labelFont,
                    valueFont
            );

            addDetailRow(
                    detailsTable,
                    "Full Name",
                    customer.getName(),
                    labelFont,
                    valueFont
            );

            addDetailRow(
                    detailsTable,
                    "Father Name",
                    customer.getFatherName(),
                    labelFont,
                    valueFont
            );

            addDetailRow(
                    detailsTable,
                    "Mother Name",
                    customer.getMotherName(),
                    labelFont,
                    valueFont
            );

            addDetailRow(
                    detailsTable,
                    "CNIC",
                    customer.getCnic(),
                    labelFont,
                    valueFont
            );

            addDetailRow(
                    detailsTable,
                    "Email Address",
                    customer.getEmail(),
                    labelFont,
                    valueFont
            );

            document.add(detailsTable);


            // =========================================================
            // DOCUMENT INFORMATION SECTION
            // =========================================================

            document.add(new Paragraph(" "));

            Paragraph documentSection =
                    new Paragraph(
                            "DOCUMENT INFORMATION",
                            sectionFont
                    );

            documentSection.setSpacingBefore(8);
            documentSection.setSpacingAfter(8);

            document.add(documentSection);


            PdfPTable statusTable = new PdfPTable(2);

            statusTable.setWidthPercentage(100);
            statusTable.setWidths(new float[]{32, 68});


            addDetailRow(
                    statusTable,
                    "Document Type",
                    "Customer Profile",
                    labelFont,
                    valueFont
            );

            addDetailRow(
                    statusTable,
                    "Generated By",
                    "Customer PDF & Email Service",
                    labelFont,
                    valueFont
            );

            addDetailRow(
                    statusTable,
                    "Generated On",
                    generatedDate,
                    labelFont,
                    valueFont
            );

            addDetailRow(
                    statusTable,
                    "Status",
                    "VERIFIED",
                    labelFont,
                    valueFont
            );

            document.add(statusTable);


            // =========================================================
            // FOOTER SPACE
            // =========================================================

            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));


            // =========================================================
            // CONFIDENTIALITY NOTICE
            // =========================================================

            PdfPTable noticeTable = new PdfPTable(1);
            noticeTable.setWidthPercentage(100);

            PdfPCell noticeCell = new PdfPCell();

            noticeCell.setBackgroundColor(
                    new Color(243, 244, 246)
            );

            noticeCell.setBorderColor(
                    new Color(229, 231, 235)
            );

            noticeCell.setPadding(12);

            Paragraph noticeTitle =
                    new Paragraph(
                            "CONFIDENTIAL CUSTOMER INFORMATION",
                            confidentialFont
                    );

            Paragraph noticeText =
                    new Paragraph(
                            "This document contains registered customer information "
                                    + "and is intended only for the authorized recipient.",
                            smallFont
                    );

            noticeCell.addElement(noticeTitle);
            noticeCell.addElement(noticeText);

            noticeTable.addCell(noticeCell);

            document.add(noticeTable);


            // =========================================================
            // FOOTER
            // =========================================================

            document.add(new Paragraph(" "));

            Paragraph footer =
                    new Paragraph(
                            "Customer PDF & Email Service  •  Page 1 of 1",
                            smallFont
                    );

            footer.setAlignment(Element.ALIGN_CENTER);

            document.add(footer);


            // =========================================================
            // CLOSE
            // =========================================================

            document.close();

            return outputStream.toByteArray();

        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed to generate customer PDF",
                    e
            );
        }
    }


    // =============================================================
    // ADD INFORMATION CELL
    // =============================================================

    private void addInfoCell(
            PdfPTable table,
            String label,
            String value,
            Font labelFont,
            Font valueFont
    ) {

        PdfPCell cell = new PdfPCell();

        cell.setBorderColor(
                new Color(229, 231, 235)
        );

        cell.setPadding(10);

        Paragraph labelParagraph =
                new Paragraph(label, labelFont);

        Paragraph valueParagraph =
                new Paragraph(value, valueFont);

        cell.addElement(labelParagraph);
        cell.addElement(valueParagraph);

        table.addCell(cell);
    }


    // =============================================================
    // ADD DETAIL ROW
    // =============================================================

    private void addDetailRow(
            PdfPTable table,
            String label,
            String value,
            Font labelFont,
            Font valueFont
    ) {

        // LABEL CELL

        PdfPCell labelCell =
                new PdfPCell(
                        new Paragraph(label, labelFont)
                );

        labelCell.setBackgroundColor(
                new Color(249, 250, 251)
        );

        labelCell.setBorderColor(
                new Color(229, 231, 235)
        );

        labelCell.setPadding(11);

        labelCell.setVerticalAlignment(
                Element.ALIGN_MIDDLE
        );


        // VALUE CELL

        PdfPCell valueCell =
                new PdfPCell(
                        new Paragraph(
                                value != null ? value : "-",
                                valueFont
                        )
                );

        valueCell.setBorderColor(
                new Color(229, 231, 235)
        );

        valueCell.setPadding(11);

        valueCell.setVerticalAlignment(
                Element.ALIGN_MIDDLE
        );


        table.addCell(labelCell);
        table.addCell(valueCell);
    }
}