package com.practise.SpringBootProject.controller;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.interactive.action.PDDocumentCatalogAdditionalActions;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.cos.COSName;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

public class PdfSanitizer {

    public static byte[] sanitizePdf(byte[] pdfBytes) throws IOException {
        PDDocument document = null;
        ByteArrayInputStream bais = null;
        ByteArrayOutputStream baos = null;
        try {
            bais = new ByteArrayInputStream(pdfBytes);
            document = PDDocument.load(bais);

            PDDocumentCatalog catalog = document.getDocumentCatalog();
            if (catalog != null) {
                sanitizeCatalog(catalog);
            }

            PDPageTree pages = document.getPages();
            Iterator<PDPage> iterator = pages.iterator();
            while (iterator.hasNext()) {
                PDPage page = iterator.next();
                sanitizePage(page);
            }

            baos = new ByteArrayOutputStream();
            document.save(baos);
            return baos.toByteArray();

        } finally {
            if (document != null) {
                document.close();
            }
            if (bais != null) {
                bais.close();
            }
            if (baos != null) {
                baos.close();
            }
        }
    }

    private static void sanitizeCatalog(PDDocumentCatalog catalog) {
        COSDictionary catalogDict = catalog.getCOSObject();
        if (catalogDict.containsKey(COSName.NAMES)) {
            catalogDict.removeItem(COSName.NAMES);
        }
        if (catalogDict.containsKey(COSName.OPEN_ACTION)) {
            catalogDict.removeItem(COSName.OPEN_ACTION);
        }
        if (catalogDict.containsKey(COSName.AA)) {
            catalogDict.removeItem(COSName.AA);
        }

        PDDocumentCatalogAdditionalActions actions = catalog.getActions();
        if (actions != null) {
            actions.setWC(null);
            actions.setWS(null);
            actions.setDS(null);
            actions.setWP(null);
        }
    }

    private static void sanitizePage(PDPage page) throws IOException {
        COSDictionary pageDict = page.getCOSObject();
        if (pageDict.containsKey(COSName.AA)) {
            pageDict.removeItem(COSName.AA);
        }

        List<PDAnnotation> annotations = page.getAnnotations();
        for (PDAnnotation annotation : annotations) {
            COSDictionary annotDict = annotation.getCOSObject();
            if (annotDict.containsKey(COSName.A)) {
                annotDict.removeItem(COSName.A);
            }
            if (annotDict.containsKey(COSName.AA)) {
                annotDict.removeItem(COSName.AA);
            }
        }
    }

    public static void main(String[] args) {
        try {
            byte[] originalPdf = Files.readAllBytes(Paths.get("F:\\pdftest\\Xsstest.pdf"));
            byte[] sanitizedPdf = sanitizePdf(originalPdf);
            Files.write(Paths.get("F:\\pdftest\\new_test.pdf"), sanitizedPdf);
            System.out.println("PDF sanitized and saved as new_test.pdf");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
