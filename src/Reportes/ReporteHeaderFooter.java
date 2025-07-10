/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Reportes;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

/**
 *
 * @author USER
 */
public class ReporteHeaderFooter  extends PdfPageEventHelper  {
   
   private String usuarioActual;
    private Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, BaseColor.DARK_GRAY);
    private Font footerFont = FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 9, BaseColor.GRAY);
    private Image logo;

    public ReporteHeaderFooter(String usuarioActual) {
        this.usuarioActual = usuarioActual;
        try {
            logo = Image.getInstance("src/img/logoSistema.png"); // Ruta a tu logo
            logo.scaleToFit(50, 50);
        } catch (Exception e) {
            logo = null;
            System.err.println("No se pudo cargar el logo: " + e.getMessage());
        }
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        PdfContentByte cb = writer.getDirectContent();
        PdfPTable header = new PdfPTable(2);
        try {
            header.setWidths(new int[]{1, 4});
            header.setTotalWidth(527);
            header.setLockedWidth(true);
            header.getDefaultCell().setFixedHeight(50);
            header.getDefaultCell().setBorder(Rectangle.BOTTOM);
            header.getDefaultCell().setBorderColor(new BaseColor(230, 230, 230));

            if (logo != null) {
                PdfPCell logoCell = new PdfPCell(logo, false);
                logoCell.setBorder(Rectangle.BOTTOM);
                logoCell.setBorderColor(new BaseColor(230, 230, 230));
                logoCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                logoCell.setPaddingLeft(10);
                header.addCell(logoCell);
            } else {
                PdfPCell emptyCell = new PdfPCell(new Phrase(""));
                emptyCell.setBorder(Rectangle.BOTTOM);
                emptyCell.setBorderColor(new BaseColor(230, 230, 230));
                header.addCell(emptyCell);
            }

            PdfPCell textCell = new PdfPCell(new Phrase("Reverse S.A.", headerFont));
            textCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            textCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            textCell.setBorder(Rectangle.BOTTOM);
            textCell.setBorderColor(new BaseColor(230, 230, 230));
            textCell.setPaddingLeft(10);
            header.addCell(textCell);

            header.writeSelectedRows(0, -1, 34, 803, cb);

            // Línea separadora pie de página
            float footerY = 50;
            cb.setColorStroke(new BaseColor(230, 230, 230));
            cb.setLineWidth(1f);
            cb.moveTo(34, footerY + 15);
            cb.lineTo(561, footerY + 15);
            cb.stroke();

            // Footer: Página y usuario
            ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT,
                    new Phrase(String.format("Página %d - Generado por: %s", writer.getPageNumber(), usuarioActual), footerFont),
                    document.right(), footerY, 0);

        } catch (DocumentException e) {
            System.err.println("Error al generar encabezado o pie de página: " + e.getMessage());
        }
    }
}