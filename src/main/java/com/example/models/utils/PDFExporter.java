package com.example.models.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Stream;

import javax.imageio.ImageIO;

import com.example.models.Sale;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;

import com.lowagie.text.Element;

import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;

import com.lowagie.text.pdf.PdfWriter;


import javafx.collections.ObservableList;
import javafx.scene.*;


public class PDFExporter {
      public static void exportChartsToPDF(String outputPath, Map<String, Node> chartMap, ObservableList<Sale> sales) {
        
        try {
            
            Document document = new Document(PageSize.A4.rotate());
            PdfWriter.getInstance(document, new FileOutputStream(outputPath));
            document.open();

            // 🔷 Titre principal
            Paragraph title = new Paragraph("Rapport des ventes", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24));
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            // 🔷 Infos supplémentaires
            Paragraph info = new Paragraph(
                "Date du rapport : " + LocalDate.now() + "\n" +
                "Généré par : Analyse des ventes - Amine Koula\n" +
                "Résumé : Rapport graphique sur les ventes\n\n",
                FontFactory.getFont(FontFactory.HELVETICA, 12)
            );
            info.setSpacingAfter(20);
            document.add(info);

            //Tableau

            //Titre de section tableau
            Paragraph titleTab = new Paragraph("Tableau des vesntes :",FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24));
            titleTab.setAlignment(Element.ALIGN_CENTER);
            document.add(titleTab);

            //tableau
            PdfPTable table = new PdfPTable(7);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10);
            
            //Entete : 
            Stream.of("ID","Date","Produit","Categorie","Quantité","Prix unitaire","Prix total")
                .forEach(colTitle->{
                     PdfPCell header = new PdfPCell(new Phrase(colTitle, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
                        header.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(header);
                });

            //  Données
            for (Sale sale : sales) {
                table.addCell(String.valueOf(sale.getIdSale()));
                table.addCell(sale.getDate());
                table.addCell(sale.getProductName());
                table.addCell(sale.getProductCategory());
                table.addCell(String.valueOf(sale.getQuantity()));
                table.addCell(String.valueOf(sale.getPrice()));
                table.addCell(String.valueOf(sale.getTotalPrice()));   
            }  
            document.add(table);  

            // 🔷 Ajouter chaque graphique
            for (Map.Entry<String, Node> entry : chartMap.entrySet()) {
                Node chartNode = entry.getValue();

                // Snapshot du graphique
                BufferedImage chartImage = ChartImg.captureNodeAsImage(chartNode);
                if (chartImage != null) {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ImageIO.write(chartImage, "png", baos);
                    Image image = Image.getInstance(baos.toByteArray());
                    image.scaleToFit(700, 400);
                    image.setAlignment(Image.ALIGN_CENTER);
                    document.add(image);
                } else {
                   System.out.println("Chart img is null !!!!");
                }
                
            }

            document.close();
            System.out.println("PDF généré avec succès : " + outputPath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
