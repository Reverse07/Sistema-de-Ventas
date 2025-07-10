
package Reportes;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.LineSeparator;
import conexion.Conexion;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReporteVentasPDF {
    
    
    private PdfPCell createStyledCell(String text, Font font, int alignment, BaseColor bgColor) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setHorizontalAlignment(alignment);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBackgroundColor(bgColor);
        cell.setPaddingTop(8f);
        cell.setPaddingBottom(8f);
        cell.setPaddingLeft(5f);
        return cell;
    }

    public void generarReporteVentas() {
        Document documento = new Document(PageSize.A4.rotate(), 36, 36, 90, 60);

        try {
            String desktopPath = FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath();
            String filePath = desktopPath + "/Reportes_Ventas.pdf";

            PdfWriter writer = PdfWriter.getInstance(documento, new FileOutputStream(filePath));
            writer.setPageEvent(new ReporteHeaderFooter("UsuarioActual"));

            documento.open();

            // Título
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, BaseColor.DARK_GRAY);
            Paragraph titulo = new Paragraph("Reporte de Ventas", titleFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingAfter(5f);
            documento.add(titulo);

            // Subtítulo
            Paragraph subtitulo = new Paragraph("Listado actualizado de todas las ventas registradas",
                    FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 11, BaseColor.GRAY));
            subtitulo.setAlignment(Element.ALIGN_CENTER);
            subtitulo.setSpacingAfter(10f);
            documento.add(subtitulo);

            // Fecha
            Paragraph fechaParrafo = new Paragraph("Fecha: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()),
                    FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.GRAY));
            fechaParrafo.setAlignment(Element.ALIGN_RIGHT);
            fechaParrafo.setSpacingAfter(10);
            documento.add(fechaParrafo);

            // Línea separadora
            LineSeparator ls = new LineSeparator();
            ls.setLineColor(new BaseColor(230, 230, 230));
            documento.add(new Chunk(ls));

            // Filtros
            Paragraph filtros = new Paragraph("Filtros aplicados:", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10));
            filtros.setSpacingBefore(10);
            filtros.setSpacingAfter(5);
            documento.add(filtros);

            Paragraph valores = new Paragraph("Todos", FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.DARK_GRAY));
            valores.setSpacingAfter(10);
            documento.add(valores);

            // Tabla
            PdfPTable tabla = new PdfPTable(5);
            tabla.setWidthPercentage(100);
            tabla.setWidths(new float[]{1f, 2f, 4f, 2f, 2f});
            tabla.setSpacingBefore(15f);
            tabla.setSpacingAfter(15f);

            Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11, BaseColor.WHITE);
            BaseColor headerColor = new BaseColor(33, 150, 243);
            String[] columnas = {"ID Venta", "Fecha", "Cliente", "Total (S/)", "Usuario"};

            for (String columna : columnas) {
                PdfPCell cell = new PdfPCell(new Phrase(columna, headFont));
                cell.setBackgroundColor(headerColor);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setPaddingTop(10f);
                cell.setPaddingBottom(10f);
                tabla.addCell(cell);
            }

            Font dataFont = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.BLACK);
            boolean gris = false;
            double totalGeneral = 0;

            String sql = """
                    SELECT cv.idCabeceraVenta AS idVenta, cv.fechaVenta AS fecha, 
                           CONCAT(c.nombre, ' ', c.apellido) AS cliente, cv.valorPagar AS total
                    FROM tb_cabecera_Venta cv
                    INNER JOIN tb_cliente c ON cv.idCliente = c.idCliente
                    WHERE cv.estado = 1
                    ORDER BY cv.fechaVenta DESC
                    """;

            try (Connection cn = Conexion.getConnection();
                 PreparedStatement pst = cn.prepareStatement(sql);
                 ResultSet rs = pst.executeQuery()) {

                while (rs.next()) {
                    BaseColor bgColor = gris ? new BaseColor(245, 245, 245) : BaseColor.WHITE;

                    tabla.addCell(createStyledCell(rs.getString("idVenta"), dataFont, Element.ALIGN_CENTER, bgColor));
                    tabla.addCell(createStyledCell(
                            new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("fecha")),
                            dataFont, Element.ALIGN_CENTER, bgColor));
                    tabla.addCell(createStyledCell(rs.getString("cliente"), dataFont, Element.ALIGN_LEFT, bgColor));
                    tabla.addCell(createStyledCell(String.format("S/ %.2f", rs.getDouble("total")), dataFont, Element.ALIGN_RIGHT, bgColor));
                    tabla.addCell(createStyledCell("-", dataFont, Element.ALIGN_CENTER, bgColor)); // Usuario pendiente si agregas columna

                    totalGeneral += rs.getDouble("total");
                    gris = !gris;
                }

                if (totalGeneral > 0) {
                    BaseColor totalColor = new BaseColor(25, 55, 110); // Azul oscuro
                    PdfPCell totalLabel = new PdfPCell(new Phrase("TOTAL GENERAL", headFont));
                    totalLabel.setColspan(3);
                    totalLabel.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    totalLabel.setBackgroundColor(totalColor);
                    totalLabel.setPadding(8f);
                    tabla.addCell(totalLabel);

                    PdfPCell totalValue = new PdfPCell(new Phrase(String.format("S/ %.2f", totalGeneral), headFont));
                    totalValue.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    totalValue.setBackgroundColor(totalColor);
                    totalValue.setPadding(8f);
                    tabla.addCell(totalValue);

                    PdfPCell empty = new PdfPCell(new Phrase(""));
                    empty.setBackgroundColor(totalColor);
                    tabla.addCell(empty);
                }

                documento.add(tabla);

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al obtener datos: " + e.getMessage());
            }

            // Firma escaneada
            try {
                documento.add(new Paragraph("\n\n"));
                Image firma = Image.getInstance("src/img/firma_Sistema.png");
                firma.scaleToFit(120, 60);
                firma.setAlignment(Image.ALIGN_CENTER);
                documento.add(firma);

                LineSeparator linea = new LineSeparator();
                linea.setLineColor(BaseColor.GRAY);
                linea.setPercentage(50);
                linea.setAlignment(Element.ALIGN_CENTER);
                documento.add(new Chunk(linea));

                Paragraph textoFirma = new Paragraph("Firma autorizada",
                        FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 10, BaseColor.GRAY));
                textoFirma.setAlignment(Element.ALIGN_CENTER);
                textoFirma.setSpacingBefore(5f);
                documento.add(textoFirma);

            } catch (Exception e) {
                System.err.println("No se pudo cargar la firma: " + e.getMessage());
            }

            // Código QR
            try {
                Image qr = Image.getInstance(new java.net.URL("https://api.qrserver.com/v1/create-qr-code/?size=80x80&data=https://github.com/Reverse07"));
                qr.scaleToFit(80, 80);
                qr.setAlignment(Image.ALIGN_RIGHT);
                documento.add(qr);
            } catch (Exception e) {
                System.err.println("No se pudo cargar el QR: " + e.getMessage());
            }

            JOptionPane.showMessageDialog(null, "Reporte de ventas creado correctamente en el escritorio.");

        } catch (DocumentException | IOException e) {
            JOptionPane.showMessageDialog(null, "Error al generar PDF: " + e.getMessage());
        } finally {
            documento.close();
        }

        // Abrir automáticamente
        try {
            File path = new File(FileSystemView.getFileSystemView().getHomeDirectory() + "/Reportes_Ventas.pdf");
            if (path.exists()) {
                Desktop.getDesktop().open(path);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "No se pudo abrir el archivo automáticamente.");
        }
    }
}

