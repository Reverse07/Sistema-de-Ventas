
package DAO;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import conexion.Conexion;
import vista.InterFacturacion;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;


public class VentaPDF {
    
    private String nombreCliente;
    private String cedulaCliente;
    private String telefonoCliente;
    private String direccionCliente;

    private String fechaActual = "";
    private String nombreArchivoPDFVenta;

    public void DatosCliente(int idCliente) {
        try (Connection cn = Conexion.getConnection()) {
            String sql = "SELECT * FROM tb_cliente WHERE idCliente = '" + idCliente + "'";
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            if (rs.next()) {
                nombreCliente = rs.getString("nombre") + " " + rs.getString("apellido");
                cedulaCliente = rs.getString("cedula");
                telefonoCliente = rs.getString("telefono");
                direccionCliente = rs.getString("direccion");
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener datos del cliente: " + e);
        }
    }

    public void generarFacturaPDF() {
        try {
            Date date = new Date();
            fechaActual = new SimpleDateFormat("yyyy/MM/dd").format(date);
            String fechaNueva = fechaActual.replace("/", "_");

            nombreArchivoPDFVenta = "Venta_" + nombreCliente.replace(" ", "_") + "_" + fechaNueva + ".pdf";

            File file = new File("src/pdf/" + nombreArchivoPDFVenta);
            FileOutputStream archivo = new FileOutputStream(file);
            Document doc = new Document();
            PdfWriter.getInstance(doc, archivo);
            doc.open();

            // Logo y encabezado
            Image img = Image.getInstance("src/img/ventas.png");

            Paragraph fecha = new Paragraph();
            Font negrita = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLUE);
            fecha.add(Chunk.NEWLINE);
            fecha.add("Factura: 001\nFecha: " + fechaActual + "\n\n");

            PdfPTable encabezado = new PdfPTable(4);
            encabezado.setWidthPercentage(100);
            encabezado.getDefaultCell().setBorder(0);
            encabezado.setWidths(new float[]{20f, 30f, 70f, 40f});
            encabezado.setHorizontalAlignment(Element.ALIGN_LEFT);

            encabezado.addCell(img);
            encabezado.addCell(""); // Espacio vacío

            encabezado.addCell("RUC: 2098765432\nNombre: DAZ\nTeléfono: 965391256\nDirección: Lima, Perú\nRazón Social: Todo lo que buscas, en un solo lugar.");
            encabezado.addCell(fecha);
            doc.add(encabezado);

            // Datos del cliente
            Paragraph cliente = new Paragraph();
            cliente.add(Chunk.NEWLINE);
            cliente.add("Datos del cliente:\n\n");
            doc.add(cliente);

            PdfPTable tablaCliente = new PdfPTable(4);
            tablaCliente.setWidthPercentage(100);
            tablaCliente.getDefaultCell().setBorder(0);
            tablaCliente.setWidths(new float[]{25f, 45f, 30f, 40f});

            tablaCliente.addCell(celda("Cédula/RUC:", negrita));
            tablaCliente.addCell(celda("Nombre:", negrita));
            tablaCliente.addCell(celda("Teléfono:", negrita));
            tablaCliente.addCell(celda("Dirección:", negrita));

            tablaCliente.addCell(cedulaCliente);
            tablaCliente.addCell(nombreCliente);
            tablaCliente.addCell(telefonoCliente);
            tablaCliente.addCell(direccionCliente);

            doc.add(tablaCliente);

            // Tabla de productos
            doc.add(new Paragraph("\n"));

            PdfPTable tablaProducto = new PdfPTable(4);
            tablaProducto.setWidthPercentage(100);
            tablaProducto.getDefaultCell().setBorder(0);
            tablaProducto.setWidths(new float[]{15f, 50f, 15f, 20f});

            tablaProducto.addCell(celdaConFondo("Cantidad", negrita));
            tablaProducto.addCell(celdaConFondo("Descripción", negrita));
            tablaProducto.addCell(celdaConFondo("Precio Unitario", negrita));
            tablaProducto.addCell(celdaConFondo("Precio Total", negrita));

            for (int i = 0; i < InterFacturacion.jTable_productos.getRowCount(); i++) {
                tablaProducto.addCell(InterFacturacion.jTable_productos.getValueAt(i, 2).toString()); // Cantidad
                tablaProducto.addCell(InterFacturacion.jTable_productos.getValueAt(i, 1).toString()); // Descripción
                tablaProducto.addCell(InterFacturacion.jTable_productos.getValueAt(i, 3).toString()); // Precio U
                tablaProducto.addCell(InterFacturacion.jTable_productos.getValueAt(i, 7).toString()); // Total
            }

            doc.add(tablaProducto);

            // Total
            Paragraph info = new Paragraph();
            info.add(Chunk.NEWLINE);
            info.add("Total a pagar: " + InterFacturacion.txt_total_pagar.getText());
            info.setAlignment(Element.ALIGN_RIGHT);
            doc.add(info);

            // Firma
            Paragraph firma = new Paragraph();
            firma.add(Chunk.NEWLINE);
            firma.add("Cancelación y firma\n\n_______________________");
            firma.setAlignment(Element.ALIGN_CENTER);
            doc.add(firma);

            // Mensaje
            Paragraph mensaje = new Paragraph();
            mensaje.add(Chunk.NEWLINE);
            mensaje.add("¡Gracias por su compra!");
            mensaje.setAlignment(Element.ALIGN_CENTER);
            doc.add(mensaje);

            doc.close();
            archivo.close();

            Desktop.getDesktop().open(file);

        } catch (DocumentException | IOException e) {
            System.out.println("Error al generar PDF: " + e);
        }
    }

    private PdfPCell celda(String texto, Font fuente) {
        PdfPCell celda = new PdfPCell(new Phrase(texto, fuente));
        celda.setBorder(0);
        return celda;
    }

    private PdfPCell celdaConFondo(String texto, Font fuente) {
        PdfPCell celda = celda(texto, fuente);
        celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
        return celda;
    }
}
