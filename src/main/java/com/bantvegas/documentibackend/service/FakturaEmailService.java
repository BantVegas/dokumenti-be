package com.bantvegas.documentibackend.service;

import com.bantvegas.documentibackend.model.Faktura;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.mail.internet.MimeMessage; // OPRAVENÝ IMPORT!

@Service
public class FakturaEmailService {

    @Autowired
    private FakturaPdfService fakturaPdfService;

    @Autowired
    private JavaMailSender mailSender;

    public void posliFakturuEmailom(Faktura faktura, String email) throws Exception {
        byte[] pdfBytes = fakturaPdfService.generatePdf(faktura);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(email);
        helper.setSubject("Vaša faktúra č. " + faktura.getCisloFaktury());
        helper.setText(
                "Dobrý deň,<br><br>v prílohe nájdete Vašu faktúru.<br><br>S pozdravom,<br>dokumenti.sk", true);

        helper.addAttachment("faktura-" + faktura.getCisloFaktury() + ".pdf", new ByteArrayResource(pdfBytes));

        mailSender.send(message);
    }
}
