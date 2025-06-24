package com.bantvegas.documentibackend.service;

import com.bantvegas.documentibackend.dto.*;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class PdfService {

    private final String FONT_PATH = "src/main/resources/fonts/NotoSans-VariableFont_wdth,wght.ttf";

    /** SPLNOMOCNENIE */
    public byte[] generateSplnomocnenie(SplnomocnenieRequest request) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);
            PDPageContentStream content = new PDPageContentStream(document, page);

            PDType0Font font = PDType0Font.load(document, new File(FONT_PATH));

            float y = 700;

            // Hlavička
            y = addTitle(content, font, 22, "SPLNOMOCNENIE", 220, y);

            y -= 40;
            // Splnomocniteľ
            y = addWrappedText(content, font, 14,
                    String.format("Ja, %s %s, narodený/á: %s, rodné číslo: %s, bytom: %s,",
                            safe(request.getMeno()), safe(request.getPriezvisko()),
                            safe(request.getDatumNarodenia()), safe(request.getRodneCislo()),
                            safe(request.getAdresa())
                    ), 50, y, 480);

            y -= 15;
            // Splnomocnenec
            y = addWrappedText(content, font, 14,
                    String.format("týmto splnomocňujem: %s, narodený/á: %s, bytom: %s,",
                            safe(request.getSplnomocnenecMeno()), safe(request.getSplnomocnenecDatumNarodenia()),
                            safe(request.getSplnomocnenecAdresa())
                    ), 50, y, 480);

            y -= 15;
            // Účel
            y = addWrappedText(content, font, 14,
                    "na vykonanie nasledujúceho úkonu/účelu: " + safe(request.getUcel()),
                    50, y, 480);

            y -= 40;
            y = addPlainText(content, font, 12, "V _____________________ dňa ________________", 50, y);

            y -= 40;
            addPlainText(content, font, 12, "Podpis splnomocniteľa: ______________________________", 50, y);

            content.close();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            document.save(baos);
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Chyba pri generovaní splnomocnenia: " + e.getMessage(), e);
        }
    }

    /** ŽIVOTOPIS + MOTIVAČNÝ LIST */
    public byte[] generateZivotopisFullPdf(String zivotopisText, String motivationText, ZivotopisRequest request) {
        try (PDDocument document = new PDDocument()) {
            PDType0Font font = PDType0Font.load(document, new File(FONT_PATH));

            // Strana 1: Životopis
            PDPage page1 = new PDPage(PDRectangle.A4);
            document.addPage(page1);
            PDPageContentStream content = new PDPageContentStream(document, page1);

            float y = 750;
            y = addTitle(content, font, 16, "Životopis", 50, y);

            y -= 25;
            y = addPlainText(content, font, 12, "Meno: " + safe(request.getMeno()), 50, y);
            y = addPlainText(content, font, 12, "Priezvisko: " + safe(request.getPriezvisko()), 250, y + 15);

            y -= 15;
            y = addPlainText(content, font, 12, "Dátum narodenia: " + safe(request.getDatumNarodenia()), 50, y);
            y = addPlainText(content, font, 12, "Adresa: " + safe(request.getAdresa()), 250, y + 15);

            y -= 15;
            y = addPlainText(content, font, 12, "Telefón: " + safe(request.getTelefon()), 50, y);
            y = addPlainText(content, font, 12, "Email: " + safe(request.getEmail()), 250, y + 15);

            y -= 25;
            y = addPlainText(content, font, 12, "AI generovaný životopis:", 50, y);

            y -= 20;
            y = addWrappedText(content, font, 12, zivotopisText, 50, y, 500);

            content.close();

            // Strana 2: Motivačný list (ak je prítomný)
            if (motivationText != null && !motivationText.trim().isEmpty()) {
                PDPage page2 = new PDPage(PDRectangle.A4);
                document.addPage(page2);
                PDPageContentStream motivationContent = new PDPageContentStream(document, page2);

                float yMotivation = 750;
                yMotivation = addTitle(motivationContent, font, 16, "Motivačný list", 50, yMotivation);

                yMotivation -= 25;
                addWrappedText(motivationContent, font, 12, motivationText, 50, yMotivation, 500);

                motivationContent.close();
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            document.save(baos);
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Chyba pri generovaní životopisu + motivačného listu: " + e.getMessage(), e);
        }
    }

    /** NÁJOMNÁ ZMLUVA */
    public byte[] generateNajomnaZmluva(NajomnaZmluvaRequest request) {
        try (PDDocument document = new PDDocument()) {
            PDType0Font font = PDType0Font.load(document, new File(FONT_PATH));

            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);
            PDPageContentStream content = new PDPageContentStream(document, page);

            float y = 750;
            y = addTitle(content, font, 16, "Nájomná zmluva", 50, y);

            y -= 25;
            y = addPlainText(content, font, 12, "Prenajímateľ: " + safe(request.getPrenajimatelMeno()), 50, y);
            y = addPlainText(content, font, 12, "Adresa: " + safe(request.getPrenajimatelAdresa()), 250, y + 15);

            y -= 15;
            y = addPlainText(content, font, 12, "Nájomca: " + safe(request.getNajomcaMeno()), 50, y);
            y = addPlainText(content, font, 12, "Adresa: " + safe(request.getNajomcaAdresa()), 250, y + 15);

            y -= 15;
            y = addPlainText(content, font, 12, "Predmet nájmu: " + safe(request.getPredmetNajmu()), 50, y);
            y = addPlainText(content, font, 12, "Adresa predmetu: " + safe(request.getAdresaPredmetu()), 250, y + 15);

            y -= 15;
            y = addPlainText(content, font, 12, "Doba nájmu: " + safe(request.getDatumOd()) + " - " + safe(request.getDatumDo()), 50, y);

            y -= 15;
            y = addPlainText(content, font, 12, "Výška nájomného: " + safe(request.getVyskaNajomneho()), 50, y);

            content.close();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            document.save(baos);
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Chyba pri generovaní nájomnej zmluvy: " + e.getMessage(), e);
        }
    }

    /** PRACOVNÁ ZMLUVA */
    public byte[] generatePracovnaZmluva(PracovnaZmluvaRequest request) {
        try (PDDocument document = new PDDocument()) {
            PDType0Font font = PDType0Font.load(document, new File(FONT_PATH));

            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);
            PDPageContentStream content = new PDPageContentStream(document, page);

            float y = 750;
            y = addTitle(content, font, 16, "Pracovná zmluva", 50, y);

            y -= 25;
            y = addPlainText(content, font, 12, "Zamestnanec: " + safe(request.getMeno()) + " " + safe(request.getPriezvisko()), 50, y);
            y = addPlainText(content, font, 12, "Adresa: " + safe(request.getAdresa()), 250, y + 15);

            y -= 15;
            y = addPlainText(content, font, 12, "Rodné číslo: " + safe(request.getRodneCislo()), 50, y);

            y -= 20;
            y = addPlainText(content, font, 12, "Zamestnávateľ: " + safe(request.getZamestnavatel()), 50, y);

            y -= 20;
            y = addPlainText(content, font, 12, "Miesto výkonu práce: " + safe(request.getMiestoVykonuPrace()), 50, y);

            y -= 20;
            y = addPlainText(content, font, 12, "Pracovný čas: " + safe(request.getPracovnyCas()), 50, y);

            y -= 20;
            y = addPlainText(content, font, 12, "Dátum nástupu: " + safe(request.getDatumNastupu()), 50, y);

            y -= 20;
            y = addPlainText(content, font, 12, "Pozícia: " + safe(request.getPozicia()), 50, y);

            y -= 20;
            y = addPlainText(content, font, 12, "Mzda: " + safe(request.getMzda()), 50, y);

            content.close();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            document.save(baos);
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Chyba pri generovaní pracovnej zmluvy: " + e.getMessage(), e);
        }
    }

    /** VÝPOVEĎ Z PRÁCE */
    public byte[] generateVypovedZPrace(VypovedRequest request) {
        try (PDDocument document = new PDDocument()) {
            PDType0Font font = PDType0Font.load(document, new File(FONT_PATH));

            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);
            PDPageContentStream content = new PDPageContentStream(document, page);

            float y = 750;
            y = addTitle(content, font, 16, "Výpoveď z práce", 50, y);

            y -= 25;
            y = addPlainText(content, font, 12, "Meno: " + safe(request.getMeno()), 50, y);
            y = addPlainText(content, font, 12, "Priezvisko: " + safe(request.getPriezvisko()), 250, y + 15);

            y -= 15;
            y = addPlainText(content, font, 12, "Adresa: " + safe(request.getAdresa()), 50, y);

            y -= 15;
            y = addPlainText(content, font, 12, "Zamestnávateľ: " + safe(request.getZamestnavatel()), 50, y);
            y = addPlainText(content, font, 12, "Pozícia: " + safe(request.getPozicia()), 250, y + 15);

            y -= 15;
            y = addPlainText(content, font, 12, "Dátum nástupu: " + safe(request.getDatumNastupu()), 50, y);
            y = addPlainText(content, font, 12, "Dátum výpovede: " + safe(request.getDatumVypovede()), 250, y + 15);

            y -= 15;
            addPlainText(content, font, 12, "Dôvod výpovede: " + safe(request.getDovodVypovede()), 50, y);

            content.close();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            document.save(baos);
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Chyba pri generovaní výpovede: " + e.getMessage(), e);
        }
    }

    /** KÚPNA ZMLUVA */
    public byte[] generateKupnaZmluva(KupnaZmluvaRequest request) {
        try (PDDocument document = new PDDocument()) {
            PDType0Font font = PDType0Font.load(document, new File(FONT_PATH));

            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);
            PDPageContentStream content = new PDPageContentStream(document, page);

            float y = 750;
            y = addTitle(content, font, 16, "Kúpna zmluva", 50, y);

            y -= 25;
            y = addPlainText(content, font, 12, "Predávajúci: " + safe(request.getPredavajuciMeno()), 50, y);
            y = addPlainText(content, font, 12, "Adresa: " + safe(request.getPredavajuciAdresa()), 250, y + 15);

            y -= 15;
            y = addPlainText(content, font, 12, "Kupujúci: " + safe(request.getKupujuciMeno()), 50, y);
            y = addPlainText(content, font, 12, "Adresa: " + safe(request.getKupujuciAdresa()), 250, y + 15);

            y -= 15;
            y = addPlainText(content, font, 12, "Predmet zmluvy: " + safe(request.getPredmetZmluvy()), 50, y);
            y = addPlainText(content, font, 12, "Cena: " + safe(request.getCena()), 250, y + 15);

            y -= 15;
            y = addPlainText(content, font, 12, "Miesto odovzdania: " + safe(request.getMiestoOdovzdania()), 50, y);
            y = addPlainText(content, font, 12, "Dátum: " + safe(request.getDatum()), 250, y + 15);

            content.close();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            document.save(baos);
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Chyba pri generovaní kúpnej zmluvy: " + e.getMessage(), e);
        }
    }

    // ----- UNIVERSAL PDF HELPERS (WORD-WRAP, HEADERS, ...)

    /** Pekný word-wrapovaný text – vracia nové Y */
    private float addWrappedText(PDPageContentStream content, PDType0Font font, int fontSize,
                                 String text, float x, float y, float maxWidth) throws Exception {
        final float leading = 1.7f * fontSize;
        List<String> lines = wrapText(text, font, fontSize, maxWidth);
        content.beginText();
        content.setFont(font, fontSize);
        content.newLineAtOffset(x, y);
        for (String line : lines) {
            content.showText(line);
            content.newLineAtOffset(0, -leading);
        }
        content.endText();
        return y - (lines.size() * leading);
    }

    /** Jednoduchý text bez wrapu – vracia nové Y */
    private float addPlainText(PDPageContentStream content, PDType0Font font, int fontSize,
                               String text, float x, float y) throws Exception {
        content.beginText();
        content.setFont(font, fontSize);
        content.newLineAtOffset(x, y);
        content.showText(text);
        content.endText();
        return y - (fontSize * 1.5f);
    }

    /** Tučný nadpis (alebo zarovnaný na x) */
    private float addTitle(PDPageContentStream content, PDType0Font font, int fontSize,
                           String text, float x, float y) throws Exception {
        content.beginText();
        content.setFont(font, fontSize);
        content.newLineAtOffset(x, y);
        content.showText(text);
        content.endText();
        return y;
    }

    /** Word-wrap textu podľa šírky (px) */
    private List<String> wrapText(String text, PDType0Font font, int fontSize, float maxWidth) throws Exception {
        List<String> lines = new ArrayList<>();
        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder();
        for (String word : words) {
            String testLine = line.length() == 0 ? word : line + " " + word;
            float size = font.getStringWidth(testLine) / 1000 * fontSize;
            if (size > maxWidth) {
                lines.add(line.toString());
                line = new StringBuilder(word);
            } else {
                if (line.length() > 0) line.append(" ");
                line.append(word);
            }
        }
        if (line.length() > 0) lines.add(line.toString());
        return lines;
    }

    private String safe(String value) {
        return value != null ? value : "";
    }
    // GENERICKÝ PDF (právna analýza)
    public byte[] createPdf(PdfRequest request) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            PDType0Font font = PDType0Font.load(document, new File(FONT_PATH));
            PDPageContentStream content = new PDPageContentStream(document, page);

            float y = 750;
            y = addTitle(content, font, 16, "Generické PDF", 50, y);

            y -= 30;
            y = addWrappedText(content, font, 12,
                    "Obsah požiadavky: " + (request != null ? request.toString() : "žiadny obsah"),
                    50, y, 500
            );

            content.close();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            document.save(baos);
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Chyba pri generovaní PDF: " + e.getMessage(), e);
        }
    }

}

