package com.bantvegas.documentibackend.service;

import com.bantvegas.documentibackend.model.Faktura;
import com.bantvegas.documentibackend.model.Polozka;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class FakturaPdfService {
    public byte[] generatePdf(Faktura faktura) {
        try {
            Document document = new Document();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, out);

            document.open();
            Font bold = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            Font normal = FontFactory.getFont(FontFactory.HELVETICA, 12);

            document.add(new Paragraph("FAKTÚRA", bold));
            document.add(new Paragraph("Číslo faktúry: " + faktura.getCisloFaktury(), normal));
            document.add(new Paragraph("Variabilný symbol: " + faktura.getVariabilnySymbol(), normal));
            document.add(new Paragraph("Dátum vystavenia: " + faktura.getDatumVydania(), normal));
            document.add(new Paragraph(" "));

            // Dodávateľ a Odberateľ
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);

            PdfPCell dodCell = new PdfPCell();
            dodCell.addElement(new Paragraph("Dodávateľ:", bold));
            dodCell.addElement(new Paragraph(faktura.getDodavatelMeno(), normal));
            dodCell.addElement(new Paragraph(faktura.getDodavatelUlica() + ", " + faktura.getDodavatelMesto(), normal));
            dodCell.addElement(new Paragraph("IČO: " + faktura.getDodavatelIco(), normal));
            dodCell.addElement(new Paragraph("DIČ: " + faktura.getDodavatelDic(), normal));
            dodCell.setBorder(Rectangle.NO_BORDER);

            PdfPCell odbCell = new PdfPCell();
            odbCell.addElement(new Paragraph("Odberateľ:", bold));
            odbCell.addElement(new Paragraph(faktura.getOdberatelMeno(), normal));
            odbCell.addElement(new Paragraph(faktura.getOdberatelUlica() + ", " + faktura.getOdberatelMesto(), normal));
            odbCell.addElement(new Paragraph("IČO: " + faktura.getOdberatelIco(), normal));
            odbCell.addElement(new Paragraph("DIČ: " + faktura.getOdberatelDic(), normal));
            odbCell.setBorder(Rectangle.NO_BORDER);

            table.addCell(dodCell);
            table.addCell(odbCell);

            document.add(table);
            document.add(new Paragraph(" "));

            // Tabuľka položiek
            PdfPTable t = new PdfPTable(5);
            t.setWidthPercentage(100);
            t.addCell("Popis");
            t.addCell("Počet");
            t.addCell("M.J.");
            t.addCell("Cena");
            t.addCell("Spolu");

            double suma = 0;
            for (Polozka p : faktura.getPolozky()) {
                t.addCell(p.getPopis());
                t.addCell(String.valueOf(p.getPocet()));
                t.addCell(p.getMj());
                t.addCell(String.format("%.2f €", p.getCena()));
                t.addCell(String.format("%.2f €", p.getCelkom()));
                suma += p.getCelkom();
            }
            // Súčet
            t.addCell("");
            t.addCell("");
            t.addCell("");
            t.addCell("Celkom");
            t.addCell(String.format("%.2f €", suma));
            document.add(t);

            document.close();
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Chyba pri generovaní PDF: " + e.getMessage());
        }
    }
}
