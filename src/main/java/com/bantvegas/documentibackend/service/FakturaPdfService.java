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
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            Font sectionFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 11);

            // HLAVIČKA
            Paragraph header = new Paragraph("FAKTÚRA", headerFont);
            header.setAlignment(Element.ALIGN_CENTER);
            document.add(header);
            document.add(new Paragraph(" "));

            document.add(new Paragraph("Číslo faktúry: " + notNull(faktura.getCisloFaktury()), normalFont));
            document.add(new Paragraph("Variabilný symbol: " + notNull(faktura.getVariabilnySymbol()), normalFont));
            document.add(new Paragraph("Dátum vystavenia: " + notNull(faktura.getDatumVydania()), normalFont));
            document.add(new Paragraph("Dátum dodania: " + notNull(faktura.getDatumDodania()), normalFont));
            document.add(new Paragraph("Dátum splatnosti: " + notNull(faktura.getDatumSplatnosti()), normalFont));
            document.add(new Paragraph("Stav: " + notNull(faktura.getStav()), normalFont));
            document.add(new Paragraph(" "));

            // DODÁVATEĽ / ODBERATEĽ
            PdfPTable parties = new PdfPTable(2);
            parties.setWidthPercentage(100);

            PdfPCell dodCell = new PdfPCell();
            dodCell.addElement(new Paragraph("Dodávateľ", sectionFont));
            dodCell.addElement(new Paragraph(notNull(faktura.getDodavatelMeno()), normalFont));
            dodCell.addElement(new Paragraph(notNull(faktura.getDodavatelUlica()) + ", " + notNull(faktura.getDodavatelMesto()), normalFont));
            dodCell.addElement(new Paragraph(notNull(faktura.getDodavatelPsc()) + " " + notNull(faktura.getDodavatelStat()), normalFont));
            dodCell.addElement(new Paragraph("IČO: " + notNull(faktura.getDodavatelIco()), normalFont));
            dodCell.addElement(new Paragraph("DIČ: " + notNull(faktura.getDodavatelDic()), normalFont));
            dodCell.addElement(new Paragraph("IČ DPH: " + notNull(faktura.getDodavatelIcdph()), normalFont));
            dodCell.addElement(new Paragraph("E-mail: " + notNull(faktura.getDodavatelEmail()), normalFont));
            dodCell.addElement(new Paragraph("IBAN: " + notNull(faktura.getDodavatelIban()), normalFont));
            dodCell.addElement(new Paragraph("SWIFT: " + notNull(faktura.getDodavatelSwift()), normalFont));
            dodCell.setBorder(Rectangle.NO_BORDER);

            PdfPCell odbCell = new PdfPCell();
            odbCell.addElement(new Paragraph("Odberateľ", sectionFont));
            odbCell.addElement(new Paragraph(notNull(faktura.getOdberatelMeno()), normalFont));
            odbCell.addElement(new Paragraph(notNull(faktura.getOdberatelUlica()) + ", " + notNull(faktura.getOdberatelMesto()), normalFont));
            odbCell.addElement(new Paragraph(notNull(faktura.getOdberatelPsc()) + " " + notNull(faktura.getOdberatelStat()), normalFont));
            odbCell.addElement(new Paragraph("IČO: " + notNull(faktura.getOdberatelIco()), normalFont));
            odbCell.addElement(new Paragraph("DIČ: " + notNull(faktura.getOdberatelDic()), normalFont));
            odbCell.addElement(new Paragraph("IČ DPH: " + notNull(faktura.getOdberatelIcdph()), normalFont));
            odbCell.setBorder(Rectangle.NO_BORDER);

            parties.addCell(dodCell);
            parties.addCell(odbCell);
            document.add(parties);

            document.add(new Paragraph(" "));

            // PLATOBNÉ ÚDAJE
            document.add(new Paragraph("Platobné údaje:", sectionFont));
            document.add(new Paragraph("Forma úhrady: " + notNull(faktura.getFormaUhrady()), normalFont));
            document.add(new Paragraph("Číslo účtu: " + notNull(faktura.getCisloUctu()), normalFont));
            document.add(new Paragraph(" "));

            // TABUĽKA POLOŽIEK
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);

            // Hlavička tabuľky
            table.addCell(new PdfPCell(new Phrase("Popis", sectionFont)));
            table.addCell(new PdfPCell(new Phrase("Počet", sectionFont)));
            table.addCell(new PdfPCell(new Phrase("M.J.", sectionFont)));
            table.addCell(new PdfPCell(new Phrase("Cena", sectionFont)));
            table.addCell(new PdfPCell(new Phrase("Spolu", sectionFont)));

            double suma = 0;
            if (faktura.getPolozky() != null) {
                for (Polozka p : faktura.getPolozky()) {
                    table.addCell(new PdfPCell(new Phrase(notNull(p.getPopis()), normalFont)));
                    table.addCell(new PdfPCell(new Phrase(String.valueOf(p.getPocet()), normalFont)));
                    table.addCell(new PdfPCell(new Phrase(notNull(p.getMj()), normalFont)));
                    table.addCell(new PdfPCell(new Phrase(String.format("%.2f €", p.getCena()), normalFont)));
                    table.addCell(new PdfPCell(new Phrase(String.format("%.2f €", p.getCelkom()), normalFont)));
                    suma += p.getCelkom();
                }
            }

            // Riadok súčtu
            for (int i = 0; i < 3; i++) table.addCell(""); // prázdne bunky
            table.addCell(new PdfPCell(new Phrase("Celkom", sectionFont)));
            table.addCell(new PdfPCell(new Phrase(String.format("%.2f €", suma), sectionFont)));
            document.add(table);

            document.add(new Paragraph(" "));

            // Poznámka
            if (faktura.getPoznamka() != null && !faktura.getPoznamka().isEmpty()) {
                document.add(new Paragraph("Poznámka: " + faktura.getPoznamka(), normalFont));
                document.add(new Paragraph(" "));
            }

            // Podpis
            document.add(new Paragraph(" ")); // extra medzera
            document.add(new Paragraph(".....................................................", normalFont));
            document.add(new Paragraph("Podpis a pečiatka", normalFont));

            document.close();
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Chyba pri generovaní PDF: " + e.getMessage());
        }
    }

    // Helper na null hodnoty
    private String notNull(Object o) {
        return o == null ? "" : o.toString();
    }
}

