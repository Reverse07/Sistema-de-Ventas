package DAO;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import conexion.Conexion;
import java.awt.Desktop;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;

public class Reportes {

    class HeaderFooter extends PdfPageEventHelper {

        private String usuarioActual;
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, BaseColor.DARK_GRAY);
        Font footerFont = FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 9, BaseColor.GRAY);
        Image logo;

        public HeaderFooter(String usuarioActual) {
            this.usuarioActual = usuarioActual;
            try {
                logo = Image.getInstance("src/img/logoSistema.png");
                logo.scaleToFit(50, 50);
            } catch (Exception e) {
                logo = null;
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

                PdfPCell text = new PdfPCell(new Phrase("Reverse S.A.", headerFont));
                text.setVerticalAlignment(Element.ALIGN_MIDDLE);
                text.setHorizontalAlignment(Element.ALIGN_LEFT);
                text.setBorder(Rectangle.BOTTOM);
                text.setBorderColor(new BaseColor(230, 230, 230));
                text.setPaddingLeft(10);
                header.addCell(text);

                header.writeSelectedRows(0, -1, 34, 803, cb);

                // Línea inferior del pie
                float footerY = 50;
                cb.setColorStroke(new BaseColor(230, 230, 230));
                cb.setLineWidth(1f);
                cb.moveTo(34, footerY + 15);
                cb.lineTo(561, footerY + 15);
                cb.stroke();

                ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT,
                        new Phrase(String.format("Página %d - Generado por: %s", writer.getPageNumber(), usuarioActual), footerFont),
                        document.right(), footerY, 0);

            } catch (DocumentException de) {
                de.printStackTrace();
            }
        }
    }

    public void reporteClientes() {
        Document documento = new Document(PageSize.A4, 36, 36, 90, 60);

        try {
            FileSystemView filesys = FileSystemView.getFileSystemView();
            String desktopPath = filesys.getHomeDirectory().getAbsolutePath();
            String filePath = desktopPath + "/Reportes_Clientes.pdf";

            File file = new File(filePath);
            File parent = file.getParentFile();
            if (!parent.exists()) {
                parent.mkdirs();
            }

            PdfWriter writer = PdfWriter.getInstance(documento, new FileOutputStream(filePath));
            writer.setPageEvent(new HeaderFooter("UsuarioActual"));  // Cambia aquí el usuario o sistema

            documento.open();

            // Título
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, BaseColor.DARK_GRAY);
            Paragraph titulo = new Paragraph("Reporte de Clientes", titleFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingAfter(5f);
            documento.add(titulo);

            // Subtítulo
            Paragraph subtitulo = new Paragraph("Listado actualizado de todos los clientes registrados",
                    FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 11, BaseColor.GRAY));
            subtitulo.setAlignment(Element.ALIGN_CENTER);
            subtitulo.setSpacingAfter(10f);
            documento.add(subtitulo);

            // Fecha
            Font dateFont = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.GRAY);
            String fecha = "Fecha: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
            Paragraph fechaParrafo = new Paragraph(fecha, dateFont);
            fechaParrafo.setAlignment(Element.ALIGN_RIGHT);
            fechaParrafo.setSpacingAfter(10);
            documento.add(fechaParrafo);

            // Línea separadora
            LineSeparator ls = new LineSeparator();
            ls.setLineColor(new BaseColor(230, 230, 230));
            documento.add(new Chunk(ls));

            // ------- Mostrar filtros aplicados -------
            // Supón que tienes estos filtros (los puedes cambiar dinámicamente)
            String filtroNombre = null;  // null = sin filtro
            String filtroCiudad = null;

            Paragraph filtros = new Paragraph("Filtros aplicados:", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10));
            filtros.setSpacingBefore(10);
            filtros.setSpacingAfter(5);

            Paragraph valores = new Paragraph(
                    String.format("Nombre: %s | Ciudad: %s",
                            filtroNombre == null ? "Todos" : filtroNombre,
                            filtroCiudad == null ? "Todas" : filtroCiudad),
                    FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.DARK_GRAY)
            );
            valores.setSpacingAfter(10);

            documento.add(filtros);
            documento.add(valores);
            // ---------------------------------------

            // Tabla
            PdfPTable tabla = new PdfPTable(5);
            tabla.setWidthPercentage(100);
            tabla.setWidths(new float[]{1f, 3f, 2.5f, 2.5f, 4f});
            tabla.setSpacingBefore(15f);
            tabla.setSpacingAfter(15f);

            Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.WHITE);
            BaseColor headerColor = new BaseColor(33, 150, 243);

            String[] columnas = {"Código", "Nombres", "Cédula", "Teléfono", "Dirección"};
            for (String columna : columnas) {
                PdfPCell cell = new PdfPCell(new Phrase(columna, headFont));
                cell.setBackgroundColor(headerColor);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setPaddingTop(12f);
                cell.setPaddingBottom(12f);
                tabla.addCell(cell);
            }

            Font dataFont = FontFactory.getFont(FontFactory.HELVETICA, 11, BaseColor.BLACK);
            boolean gris = false;

            try (Connection cn = Conexion.getConnection()) {
                PreparedStatement pst = cn.prepareStatement(
                        "SELECT idCliente, CONCAT(nombre, ' ', apellido) AS nombre, cedula, telefono, direccion FROM tb_cliente"
                );
                ResultSet rs = pst.executeQuery();

                while (rs.next()) {
                    BaseColor bgColor = gris ? new BaseColor(245, 245, 245) : BaseColor.WHITE;
                    tabla.addCell(createStyledCell(rs.getString("idCliente"), dataFont, Element.ALIGN_CENTER, bgColor));
                    tabla.addCell(createStyledCell(rs.getString("nombre"), dataFont, Element.ALIGN_LEFT, bgColor));
                    tabla.addCell(createStyledCell(rs.getString("cedula"), dataFont, Element.ALIGN_CENTER, bgColor));
                    tabla.addCell(createStyledCell(rs.getString("telefono"), dataFont, Element.ALIGN_CENTER, bgColor));
                    tabla.addCell(createStyledCell(rs.getString("direccion"), dataFont, Element.ALIGN_LEFT, bgColor));
                    gris = !gris;
                }

                documento.add(tabla);

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al obtener datos: " + e.getMessage());
            }

            // Firma escaneada
            try {
                Image firma = Image.getInstance("src/img/firma_Sistema.png");
                firma.scaleToFit(120, 60);
                firma.setAlignment(Element.ALIGN_CENTER);
                documento.add(firma);

                // Línea debajo de la firma
                LineSeparator linea = new LineSeparator();
                linea.setLineColor(BaseColor.GRAY);
                linea.setPercentage(50); // ancho relativo al ancho de la página (50%)
                linea.setAlignment(Element.ALIGN_CENTER);
                documento.add(new Chunk(linea));

                // Texto debajo de la línea
                Paragraph textoFirma = new Paragraph("Firma autorizada",
                        FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 10, BaseColor.GRAY));
                textoFirma.setAlignment(Element.ALIGN_CENTER);
                textoFirma.setSpacingBefore(5f);
                documento.add(textoFirma);

            } catch (Exception e) {
                System.err.println("No se pudo cargar la firma: " + e.getMessage());
            }

            // Código QR (opcional)
            try {
                Image qr = Image.getInstance(new URL("https://api.qrserver.com/v1/create-qr-code/?size=80x80&data=https://github.com/Reverse07"));
                qr.setAlignment(Element.ALIGN_RIGHT);
                qr.scaleToFit(80, 80);
                documento.add(qr);
            } catch (MalformedURLException e) {
                System.err.println("QR URL inválida.");
            }

        } catch (DocumentException | IOException e) {
            JOptionPane.showMessageDialog(null, "Error al generar PDF: " + e.getMessage());
        } finally {
            documento.close();
        }

        JOptionPane.showMessageDialog(null, "Reporte creado correctamente en el escritorio.");

        // BONUS: abrir automáticamente
        try {
            File path = new File(FileSystemView.getFileSystemView().getHomeDirectory() + "/Reportes_Clientes.pdf");
            if (path.exists()) {
                Desktop.getDesktop().open(path);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "No se pudo abrir el archivo automáticamente.");
        }
    }

    //Codigo para reporte de categorias
    public void reporteCategorias() {
        Document documento = new Document(PageSize.A4, 36, 36, 90, 60);

        try {
            FileSystemView filesys = FileSystemView.getFileSystemView();
            String desktopPath = filesys.getHomeDirectory().getAbsolutePath();
            String filePath = desktopPath + "/Reportes_Categorias.pdf";

            File file = new File(filePath);
            File parent = file.getParentFile();
            if (!parent.exists()) {
                parent.mkdirs();
            }

            PdfWriter writer = PdfWriter.getInstance(documento, new FileOutputStream(filePath));
            writer.setPageEvent(new HeaderFooter("UsuarioActual")); // Cambia por el nombre del usuario actual si deseas
            documento.open();

            // Título
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, BaseColor.DARK_GRAY);
            Paragraph titulo = new Paragraph("Reporte de Categorías", titleFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingAfter(5f);
            documento.add(titulo);

            // Subtítulo
            Paragraph subtitulo = new Paragraph("Listado actualizado de todas las categorías registradas",
                    FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 11, BaseColor.GRAY));
            subtitulo.setAlignment(Element.ALIGN_CENTER);
            subtitulo.setSpacingAfter(10f);
            documento.add(subtitulo);

            // Fecha
            Font dateFont = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.GRAY);
            String fecha = "Fecha: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
            Paragraph fechaParrafo = new Paragraph(fecha, dateFont);
            fechaParrafo.setAlignment(Element.ALIGN_RIGHT);
            fechaParrafo.setSpacingAfter(10);
            documento.add(fechaParrafo);

            // Línea separadora
            LineSeparator ls = new LineSeparator();
            ls.setLineColor(new BaseColor(230, 230, 230));
            documento.add(new Chunk(ls));

            // Filtros (si los hubiera)
            Paragraph filtros = new Paragraph("Filtros aplicados:", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10));
            filtros.setSpacingBefore(10);
            filtros.setSpacingAfter(5);
            documento.add(filtros);

            Paragraph valores = new Paragraph("Todos",
                    FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.DARK_GRAY));
            valores.setSpacingAfter(10);
            documento.add(valores);

            // Tabla
            PdfPTable tabla = new PdfPTable(3);
            tabla.setWidthPercentage(100);
            tabla.setWidths(new float[]{1f, 3f, 5f});
            tabla.setSpacingBefore(15f);
            tabla.setSpacingAfter(15f);

            Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.WHITE);
            BaseColor headerColor = new BaseColor(33, 150, 243);
            String[] columnas = {"ID", "Nombre", "Estado"};

            for (String columna : columnas) {
                PdfPCell cell = new PdfPCell(new Phrase(columna, headFont));
                cell.setBackgroundColor(headerColor);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setPaddingTop(12f);
                cell.setPaddingBottom(12f);
                tabla.addCell(cell);
            }

            Font dataFont = FontFactory.getFont(FontFactory.HELVETICA, 11, BaseColor.BLACK);
            boolean gris = false;

            try (Connection cn = Conexion.getConnection()) {
                PreparedStatement pst = cn.prepareStatement(
                        "SELECT idCategoria, descripcion, estado FROM tb_Categoria"
                );
                ResultSet rs = pst.executeQuery();

                while (rs.next()) {
                    BaseColor bgColor = gris ? new BaseColor(245, 245, 245) : BaseColor.WHITE;
                    tabla.addCell(createStyledCell(String.valueOf(rs.getInt("idCategoria")), dataFont, Element.ALIGN_CENTER, bgColor));
                    tabla.addCell(createStyledCell(rs.getString("descripcion"), dataFont, Element.ALIGN_LEFT, bgColor));
                    String estado = rs.getInt("estado") == 1 ? "Activo" : "Inactivo";
                    tabla.addCell(createStyledCell(estado, dataFont, Element.ALIGN_CENTER, bgColor));
                    gris = !gris;
                }

                documento.add(tabla);

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al obtener datos: " + e.getMessage());
            }

            // Firma
            try {
                Image firma = Image.getInstance("src/img/firma_Sistema.png");
                firma.scaleToFit(120, 60);
                firma.setAlignment(Element.ALIGN_CENTER);
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

            // QR
            try {
                Image qr = Image.getInstance(new URL("https://api.qrserver.com/v1/create-qr-code/?size=80x80&data=https://github.com/Reverse07"));
                qr.setAlignment(Element.ALIGN_RIGHT);
                qr.scaleToFit(80, 80);
                documento.add(qr);
            } catch (MalformedURLException e) {
                System.err.println("QR URL inválida.");
            }

        } catch (DocumentException | IOException e) {
            JOptionPane.showMessageDialog(null, "Error al generar PDF: " + e.getMessage());
        } finally {
            documento.close();
        }

        JOptionPane.showMessageDialog(null, "Reporte de categorías creado correctamente en el escritorio.");

        try {
            File path = new File(FileSystemView.getFileSystemView().getHomeDirectory() + "/Reportes_Categorias.pdf");
            if (path.exists()) {
                Desktop.getDesktop().open(path);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "No se pudo abrir el archivo automáticamente.");
        }
    }

    // Método auxiliar para crear celdas estilizadas
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

    public void reporteProductos() {
        Document documento = new Document(PageSize.A4.rotate(), 36, 36, 90, 60);

        try {
            FileSystemView filesys = FileSystemView.getFileSystemView();
            String desktopPath = filesys.getHomeDirectory().getAbsolutePath();
            String filePath = desktopPath + "/Reportes_Productos.pdf";

            File file = new File(filePath);
            File parent = file.getParentFile();
            if (!parent.exists()) {
                parent.mkdirs();
            }

            PdfWriter writer = PdfWriter.getInstance(documento, new FileOutputStream(filePath));
            writer.setPageEvent(new HeaderFooter("UsuarioActual")); // Puedes personalizar esto
            documento.open();

            // LOGO
            try {
                Image logo = Image.getInstance("src/img/logoSistema.png"); // Cambia la ruta según tu imagen
                logo.scaleToFit(80, 80);
                logo.setAlignment(Image.ALIGN_LEFT);
                documento.add(logo);
            } catch (Exception e) {
                System.err.println("No se pudo cargar el logo: " + e.getMessage());
            }

            // TÍTULO
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, BaseColor.DARK_GRAY);
            Paragraph titulo = new Paragraph("Reporte de Productos", titleFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingAfter(5f);
            documento.add(titulo);

            // SUBTÍTULO
            Paragraph subtitulo = new Paragraph("Listado actualizado de todos los productos registrados",
                    FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 11, BaseColor.GRAY));
            subtitulo.setAlignment(Element.ALIGN_CENTER);
            subtitulo.setSpacingAfter(10f);
            documento.add(subtitulo);

            // FECHA
            Paragraph fechaParrafo = new Paragraph("Fecha: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()),
                    FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.GRAY));
            fechaParrafo.setAlignment(Element.ALIGN_RIGHT);
            fechaParrafo.setSpacingAfter(10);
            documento.add(fechaParrafo);

            // LÍNEA SEPARADORA
            LineSeparator ls = new LineSeparator();
            ls.setLineColor(new BaseColor(230, 230, 230));
            documento.add(new Chunk(ls));

            // FILTROS
            Paragraph filtros = new Paragraph("Filtros aplicados:", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10));
            filtros.setSpacingBefore(10);
            filtros.setSpacingAfter(5);
            documento.add(filtros);

            Paragraph valores = new Paragraph("Todos", FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.DARK_GRAY));
            valores.setSpacingAfter(10);
            documento.add(valores);

            // TABLA
            PdfPTable tabla = new PdfPTable(7);
            tabla.setWidthPercentage(100);
            tabla.setWidths(new float[]{1.2f, 3f, 1.5f, 2f, 4f, 1.5f, 2.5f});
            tabla.setSpacingBefore(15f);
            tabla.setSpacingAfter(15f);

            Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11, BaseColor.WHITE);
            BaseColor headerColor = new BaseColor(33, 150, 243);
            String[] columnas = {"ID", "Nombre", "Cantidad", "Precio", "Descripción", "IVA%", "Categoría"};

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

            try (Connection cn = Conexion.getConnection()) {
                PreparedStatement pst = cn.prepareStatement(
                        "SELECT p.idProducto, p.nombre, p.cantidad, p.precio, p.descripcion, p.porcentajeIva, c.descripcion AS categoria "
                        + "FROM tb_producto p INNER JOIN tb_Categoria c ON p.idCategoria = c.idCategoria "
                        + "WHERE p.estado = 1"
                );
                ResultSet rs = pst.executeQuery();

                while (rs.next()) {
                    BaseColor bgColor = gris ? new BaseColor(245, 245, 245) : BaseColor.WHITE;
                    tabla.addCell(createStyledCell(rs.getString("idProducto"), dataFont, Element.ALIGN_CENTER, bgColor));
                    tabla.addCell(createStyledCell(rs.getString("nombre"), dataFont, Element.ALIGN_LEFT, bgColor));
                    tabla.addCell(createStyledCell(rs.getString("cantidad"), dataFont, Element.ALIGN_CENTER, bgColor));
                    tabla.addCell(createStyledCell(String.format("S/ %.2f", rs.getDouble("precio")), dataFont, Element.ALIGN_RIGHT, bgColor));
                    tabla.addCell(createStyledCell(rs.getString("descripcion"), dataFont, Element.ALIGN_LEFT, bgColor));
                    tabla.addCell(createStyledCell(rs.getString("porcentajeIva") + "%", dataFont, Element.ALIGN_CENTER, bgColor));
                    tabla.addCell(createStyledCell(rs.getString("categoria"), dataFont, Element.ALIGN_LEFT, bgColor));
                    gris = !gris;
                }

                documento.add(tabla);

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al obtener productos: " + e.getMessage());
            }

            // FIRMA ESCANEADA
            documento.newPage();

            try {
                Paragraph espacio = new Paragraph("\n\n\n\n");
                documento.add(espacio);

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

            // CÓDIGO QR
            try {
                Image qr = Image.getInstance(new URL("https://api.qrserver.com/v1/create-qr-code/?size=80x80&data=https://github.com/Reverse07"));
                qr.setAlignment(Image.ALIGN_RIGHT);
                qr.scaleToFit(80, 80);
                documento.add(qr);
            } catch (Exception e) {
                System.err.println("QR URL inválida.");
            }

        } catch (DocumentException | IOException e) {
            JOptionPane.showMessageDialog(null, "Error al generar PDF: " + e.getMessage());
        } finally {
            documento.close();
        }

        JOptionPane.showMessageDialog(null, "Reporte de productos creado correctamente en el escritorio.");

        // ABRIR AUTOMÁTICAMENTE
        try {
            File path = new File(FileSystemView.getFileSystemView().getHomeDirectory() + "/Reportes_Productos.pdf");
            if (path.exists()) {
                Desktop.getDesktop().open(path);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "No se pudo abrir el archivo automáticamente.");
        }
    }

    public void reporteVentas() {
    Document documento = new Document(PageSize.A4.rotate(), 36, 36, 90, 60);

    try {
        FileSystemView filesys = FileSystemView.getFileSystemView();
        String desktopPath = filesys.getHomeDirectory().getAbsolutePath();
        String filePath = desktopPath + "/Reportes_Ventas.pdf";

        File file = new File(filePath);
        File parent = file.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }

        PdfWriter writer = PdfWriter.getInstance(documento, new FileOutputStream(filePath));

        // HEADER Y FOOTER PERSONALIZADOS
        writer.setPageEvent(new PdfPageEventHelper() {
            Font footerFont = FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 8, BaseColor.GRAY);

            @Override
            public void onEndPage(PdfWriter writer, Document document) {
                PdfContentByte cb = writer.getDirectContent();
                Phrase footer = new Phrase("Página " + writer.getPageNumber(), footerFont);
                ColumnText.showTextAligned(cb, Element.ALIGN_CENTER,
                        footer,
                        (document.right() - document.left()) / 2 + document.leftMargin(),
                        document.bottom() - 10, 0);

                Phrase fechaFooter = new Phrase(
                        "Generado el: " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()),
                        footerFont);
                ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT,
                        fechaFooter,
                        document.right(), document.bottom() - 10, 0);
            }
        });

        documento.open();

        // LOGO (manejo seguro)
        try {
            Image logo = Image.getInstance("src/img/logoSistema.png");
            logo.scaleToFit(80, 80);
            logo.setAlignment(Image.ALIGN_LEFT);
            documento.add(logo);
        } catch (Exception e) {
            System.err.println("No se pudo cargar el logo: " + e.getMessage());
        }

        // TÍTULO
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, BaseColor.DARK_GRAY);
        Paragraph titulo = new Paragraph("Reporte de Ventas", titleFont);
        titulo.setAlignment(Element.ALIGN_CENTER);
        titulo.setSpacingAfter(5f);
        documento.add(titulo);

        // SUBTÍTULO
        Paragraph subtitulo = new Paragraph(
                "Listado actualizado de todas las ventas registradas",
                FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 11, BaseColor.GRAY));
        subtitulo.setAlignment(Element.ALIGN_CENTER);
        subtitulo.setSpacingAfter(10f);
        documento.add(subtitulo);

        // FECHA DE GENERACIÓN (encabezado del contenido)
        Paragraph fechaParrafo = new Paragraph(
                "Fecha de generación: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()),
                FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.GRAY));
        fechaParrafo.setAlignment(Element.ALIGN_RIGHT);
        fechaParrafo.setSpacingAfter(10);
        documento.add(fechaParrafo);

        // LÍNEA SEPARADORA MÁS VISIBLE
        LineSeparator ls = new LineSeparator();
        ls.setLineColor(new BaseColor(100, 100, 100));
        ls.setPercentage(100);
        documento.add(new Chunk(ls));

        // FILTROS (Aquí solo dice "Ninguno")
        Paragraph filtros = new Paragraph("Filtros aplicados:", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10));
        filtros.setSpacingBefore(10);
        filtros.setSpacingAfter(5);
        documento.add(filtros);

        Paragraph valoresFiltros = new Paragraph("Ninguno", FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.DARK_GRAY));
        valoresFiltros.setSpacingAfter(10);
        documento.add(valoresFiltros);

        // TABLA (ID, Fecha, Cliente, Total, Usuario)
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

        String sql = "SELECT cv.idCabeceraVenta AS idVenta, cv.fechaVenta AS fecha, \n"
                + "       CONCAT(c.nombre, ' ', c.apellido) AS cliente, cv.valorPagar AS total\n"
                + "FROM tb_cabecera_Venta cv\n"
                + "INNER JOIN tb_cliente c ON cv.idCliente = c.idCliente\n"
                + "ORDER BY cv.fechaVenta DESC";

        try (Connection cn = Conexion.getConnection(); PreparedStatement pst = cn.prepareStatement(sql)) {

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                BaseColor bgColor = gris ? new BaseColor(245, 245, 245) : BaseColor.WHITE;

                tabla.addCell(createStyledCell(rs.getString("idVenta"), dataFont, Element.ALIGN_CENTER, bgColor));
                tabla.addCell(createStyledCell(
                        new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("fecha")),
                        dataFont, Element.ALIGN_CENTER, bgColor));
                tabla.addCell(createStyledCell(rs.getString("cliente"), dataFont, Element.ALIGN_LEFT, bgColor));
                tabla.addCell(createStyledCell(String.format("S/ %.2f", rs.getDouble("total")), dataFont, Element.ALIGN_RIGHT, bgColor));

                // Si quieres mostrar usuario, deberías añadirlo en la DB
                tabla.addCell(createStyledCell("-", dataFont, Element.ALIGN_CENTER, bgColor));

                totalGeneral += rs.getDouble("total");
                gris = !gris;
            }
            if (totalGeneral > 0) {
                // Total general destacado - azul oscuro con fuente blanca
                BaseColor totalColor = new BaseColor(25, 55, 110); // Azul oscuro

                PdfPCell cellTotalLabel = new PdfPCell(new Phrase("TOTAL GENERAL", headFont));
                cellTotalLabel.setColspan(3);
                cellTotalLabel.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cellTotalLabel.setBackgroundColor(totalColor);
                cellTotalLabel.setPadding(8f);
                tabla.addCell(cellTotalLabel);

                PdfPCell cellTotalValue = new PdfPCell(new Phrase(String.format("S/ %.2f", totalGeneral), headFont));
                cellTotalValue.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cellTotalValue.setBackgroundColor(totalColor);
                cellTotalValue.setPadding(8f);
                tabla.addCell(cellTotalValue);

                // Celda vacía para cerrar fila
                PdfPCell emptyCell = new PdfPCell(new Phrase(""));
                emptyCell.setBackgroundColor(totalColor);
                tabla.addCell(emptyCell);
            }

            documento.add(tabla);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener ventas: " + e.getMessage());
        }

        // Firma escaneada
        documento.newPage();

        try {
            documento.add(new Paragraph("\n\n\n\n"));

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

        // QR con link a la web de tu empresa
        try {
            Image qr = Image.getInstance(new URL("https://api.qrserver.com/v1/create-qr-code/?size=80x80&data=https://github.com/Reverse07"));
            qr.setAlignment(Image.ALIGN_RIGHT);
            qr.scaleToFit(80, 80);
            documento.add(qr);
        } catch (Exception e) {
            System.err.println("QR URL inválida.");
        }

    } catch (DocumentException | IOException e) {
        JOptionPane.showMessageDialog(null, "Error al generar PDF: " + e.getMessage());
    } finally {
        documento.close();
    }

    JOptionPane.showMessageDialog(null, "Reporte de ventas creado correctamente en el escritorio.");

    // Abrir automáticamente si existe
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
