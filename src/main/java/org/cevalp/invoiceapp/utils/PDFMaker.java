package org.cevalp.invoiceapp.utils;

import org.cevalp.invoiceapp.model.AbstractUser;
import org.cevalp.invoiceapp.model.InvoiceDetails;
import org.cevalp.invoiceapp.model.Recipient;
import org.cevalp.invoiceapp.model.Sender;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class PDFMaker implements AutoCloseable {


    private File saveFile;
    private final Sender sender;
    private final Recipient recipient;
    private final InvoiceDetails invoiceDetails;

    private final PDDocument document;
    private final PDPage page;
    private final PDPageContentStream contentStream;
    private final byte[] qrArray;

    private final float DOCUMENT_X_START = 30f;
    private final float DOCUMENT_X_END;
    private final float DOCUMENT_Y_START = 30f;
    private final float DOCUMENT_Y_END;
    private static final float SPACE = 20f;
    private static final float X_OFFSET = 40f;
    private static final float Y_OFFSET = 20f;
    private static final float TABULATOR = 10f;

    // starting positions of invoice segments
    private static final float DATE_PART_START = 650f;
    private static final float PAYMENT_PART_START = 635f;
    private static final float SERVICE_PART_START = 430f;
    private static final float AMOUNT_PART_START = 250f;


    // text sizes
    private static final float TEXT_SIZE_NORMAL = 11f;
    private static final float TEXT_SIZE_BIGGER = 13f;
    private static final float PADDING = 4f;


    private final PDType0Font normalFont;
    private final PDType0Font boldFont;
    private static final Color BLACK = new Color(0, 0, 0);
    private static final Color BLUE = new Color(0,0,128);
    private static final Color BACKGROUND = new Color(207, 232, 252);


    public PDFMaker(Sender sender, Recipient recipient, InvoiceDetails invoiceDetails, byte[] qrArray, File file) throws IOException {
        this.sender = sender;
        this.recipient = recipient;
        this.invoiceDetails = invoiceDetails;
        this.qrArray = qrArray;
        this.saveFile = file;

        this.document = new PDDocument();
        page = new PDPage(PDRectangle.A4);
        document.addPage(page);
        contentStream = new PDPageContentStream(document, page);

        DOCUMENT_X_END = page.getMediaBox().getWidth() - 30;
        DOCUMENT_Y_END = page.getMediaBox().getHeight() - 30;

        //fonts
        normalFont = PDType0Font.load(document, PDFMaker.class.getResourceAsStream("/fonts/Arial.ttf"));
        boldFont = PDType0Font.load(document, PDFMaker.class.getResourceAsStream("/fonts/Arial_Bold.ttf"));
    }

    public void makePDF() throws IOException {
        makeHead();
        makeDescription(sender, DOCUMENT_X_START);
        makeDescription(recipient, (DOCUMENT_X_START + DOCUMENT_X_END) / 2);
        makeDatePart();
        makePaymentPart(sender);
        makeServicePart();
        makeAmountPart();
        makeFinalPart();
        drawBorder(0.5f);
        save();
    }

    private void makeHead() throws IOException {
        contentStream.beginText();
        contentStream.setFont(boldFont, TEXT_SIZE_BIGGER);
        contentStream.setNonStrokingColor(BLUE);
        String text = "FAKTÚRA č. " + invoiceDetails.getInvoiceId();
        float width = boldFont.getStringWidth(text) / 1000 * TEXT_SIZE_BIGGER;
        contentStream.newLineAtOffset((DOCUMENT_X_END - width), DOCUMENT_Y_END + 5);
        contentStream.showText(text);
        contentStream.endText();
    }

    private void makeDescription(AbstractUser entity, float startX) throws IOException {
        contentStream.beginText();
        contentStream.newLineAtOffset(startX + X_OFFSET, DOCUMENT_Y_END - SPACE);
        contentStream.setFont(boldFont, TEXT_SIZE_NORMAL);
        contentStream.setNonStrokingColor(BLUE);

        if(entity instanceof Sender){
            contentStream.showText("Dodávateľ:");
        }else {
            contentStream.showText("Odberateľ:");
        }

        // company name
        contentStream.setFont(normalFont, TEXT_SIZE_NORMAL);
        contentStream.setNonStrokingColor(BLACK);
        contentStream.newLineAtOffset(TABULATOR, -SPACE);
        contentStream.showText(entity.getCompanyName());

        // address
        if(entity.getStreet().isBlank()){
            contentStream.newLineAtOffset(0, -TEXT_SIZE_NORMAL - PADDING);
            contentStream.showText(entity.getCity() + " " + entity.getHouseNumber());
        }else{
            contentStream.newLineAtOffset(0, -TEXT_SIZE_NORMAL - PADDING);
            contentStream.showText(entity.getStreet() + " " + entity.getHouseNumber());
            contentStream.newLineAtOffset(0, -TEXT_SIZE_NORMAL - PADDING);
            contentStream.showText(entity.getCity());
        }

        // postcode
        contentStream.newLineAtOffset(0, -TEXT_SIZE_NORMAL - PADDING);
        contentStream.showText(entity.getPostcode());

        // ICO
        contentStream.newLineAtOffset(0, -SPACE);
        contentStream.setFont(boldFont, TEXT_SIZE_NORMAL);
        contentStream.showText("IČO: ");
        contentStream.setFont(normalFont, TEXT_SIZE_NORMAL);
        contentStream.showText(entity.getIco());

        // DIC
        contentStream.newLineAtOffset(0, -TEXT_SIZE_NORMAL - PADDING);
        contentStream.setFont(boldFont, TEXT_SIZE_NORMAL);
        contentStream.showText("DIČ: ");
        contentStream.setFont(normalFont, TEXT_SIZE_NORMAL);
        contentStream.showText(entity.getDic());

        if(entity instanceof Sender){
            contentStream.newLineAtOffset(0, -TEXT_SIZE_NORMAL - PADDING);
            contentStream.setFont(boldFont, TEXT_SIZE_NORMAL);
            contentStream.showText("Neplatiteľ DPH");
        }

        contentStream.endText();

        drawLine(
                (DOCUMENT_X_START + DOCUMENT_X_END)/2,
                DOCUMENT_Y_END - 10,
                (DOCUMENT_X_START + DOCUMENT_X_END)/2,
                DATE_PART_START + 30,
                0.5f,
                new float[]{1}
                );
    }

    private void makeDatePart() throws IOException {
        float space = 175;

        contentStream.setNonStrokingColor(BACKGROUND);
        contentStream.addRect(DOCUMENT_X_START, DATE_PART_START-TEXT_SIZE_NORMAL, (DOCUMENT_X_END - DOCUMENT_X_START), 3*TEXT_SIZE_NORMAL);
        contentStream.fill();

        contentStream.beginText();
        contentStream.setNonStrokingColor(BLACK);

        contentStream.newLineAtOffset(DOCUMENT_X_START+10, DATE_PART_START);
        contentStream.setFont(normalFont, TEXT_SIZE_NORMAL);
        contentStream.showText("Dátum vyhotovenia: ");
        contentStream.setFont(boldFont, TEXT_SIZE_NORMAL);
        contentStream.showText(invoiceDetails.getCreationDate());

        contentStream.newLineAtOffset(space, 0);
        contentStream.setFont(normalFont, TEXT_SIZE_NORMAL);
        contentStream.showText("Dátum splatnosti: ");
        contentStream.setFont(boldFont, TEXT_SIZE_NORMAL);
        contentStream.showText(invoiceDetails.getPayDueDate());

        contentStream.newLineAtOffset(space, 0);
        contentStream.setFont(normalFont, TEXT_SIZE_NORMAL);
        contentStream.showText("Forma úhrady: ");
        contentStream.setFont(boldFont, TEXT_SIZE_NORMAL);
        contentStream.showText(invoiceDetails.getPaymentWay());

        contentStream.endText();
    }

    private void makePaymentPart(Sender sender) throws IOException {
        float space = 130;

        drawLine(DOCUMENT_X_START, PAYMENT_PART_START, DOCUMENT_X_END, PAYMENT_PART_START, 0.5f, new float[0]);

        contentStream.beginText();

        contentStream.newLineAtOffset(DOCUMENT_X_START  + X_OFFSET, PAYMENT_PART_START - Y_OFFSET);
        contentStream.setFont(boldFont, TEXT_SIZE_NORMAL);
        contentStream.setNonStrokingColor(BLUE);
        contentStream.showText("Platobné údaje:");

        // bank
        contentStream.newLineAtOffset(TABULATOR, -SPACE);
        contentStream.setFont(normalFont, TEXT_SIZE_NORMAL);
        contentStream.setNonStrokingColor(BLACK);
        contentStream.showText("Banka:");
        contentStream.newLineAtOffset(space,0);
        contentStream.showText(sender.getBank());

        // swift
        contentStream.newLineAtOffset(-space, -TEXT_SIZE_NORMAL - PADDING);
        contentStream.showText("Swift:");
        contentStream.newLineAtOffset(space, 0);
        contentStream.showText(sender.getSwift());

        //iban
        contentStream.newLineAtOffset(-space, -TEXT_SIZE_NORMAL - PADDING);
        contentStream.showText("IBAN:");
        contentStream.newLineAtOffset(space, 0);
        contentStream.showText(sender.getIban());

        //account number
        contentStream.newLineAtOffset(-space, -TEXT_SIZE_NORMAL -PADDING);
        contentStream.showText("Číslo účtu:");
        contentStream.newLineAtOffset(space, 0);
        contentStream.showText(sender.getAccountNumber());

        // bank number
        contentStream.newLineAtOffset(-space, -TEXT_SIZE_NORMAL -PADDING);
        contentStream.showText("Kód banky:");
        contentStream.newLineAtOffset(space, 0);
        contentStream.showText(sender.getBankNumber());

        // variable symbol
        contentStream.newLineAtOffset(-space, -TEXT_SIZE_NORMAL - PADDING);
        contentStream.showText("Variabilný symbol:");
        contentStream.newLineAtOffset(space, 0);
        contentStream.showText(invoiceDetails.getVariableSymbol());

        // constant symbol
        contentStream.newLineAtOffset(-space, -TEXT_SIZE_NORMAL - PADDING);
        contentStream.showText("Konsštantný symbol:");
        contentStream.newLineAtOffset(space, 0);
        contentStream.showText(invoiceDetails.getConstantSymbol());

        contentStream.endText();

        PDImageXObject qr = PDImageXObject.createFromByteArray(document, qrArray, "QR_CODE");
        contentStream.drawImage(
                qr,
                DOCUMENT_X_END - qr.getWidth() - X_OFFSET,
                PAYMENT_PART_START- Y_OFFSET - qr.getHeight()
        );


    }

    private void makeServicePart() throws IOException {
        float space = 400f;
        drawLine(DOCUMENT_X_START, SERVICE_PART_START, DOCUMENT_X_END, SERVICE_PART_START, 0.5f, new float[0]);
        contentStream.beginText();

        contentStream.newLineAtOffset(DOCUMENT_X_START + X_OFFSET, SERVICE_PART_START - Y_OFFSET);
        contentStream.setFont(boldFont, TEXT_SIZE_NORMAL);
        contentStream.showText("Popis");

        contentStream.newLineAtOffset(space,0);
        contentStream.showText("Celkom EUR");
        contentStream.endText();

        drawLine(
                DOCUMENT_X_START,
                SERVICE_PART_START - 2*SPACE,
                DOCUMENT_X_END,
                SERVICE_PART_START - 2*SPACE,
                0.5f, new float[0]
        );

        contentStream.beginText();
        contentStream.newLineAtOffset(DOCUMENT_X_START + X_OFFSET, SERVICE_PART_START - 3*SPACE);
        contentStream.setFont(normalFont, TEXT_SIZE_NORMAL);
        contentStream.setNonStrokingColor(BLUE);
        contentStream.showText(invoiceDetails.getDescription());

        contentStream.newLineAtOffset(space,0);
        contentStream.setNonStrokingColor(BLACK);
        contentStream.showText("%.2f".formatted(invoiceDetails.getAmount()) + " eur");

        contentStream.endText();

    }

    private void makeAmountPart() throws IOException {
        float width = 200;
        float height = 50;
        contentStream.setNonStrokingColor(BACKGROUND);
        contentStream.addRect(DOCUMENT_X_END-width, AMOUNT_PART_START, width, height);
        contentStream.fill();

        contentStream.beginText();
        contentStream.newLineAtOffset(DOCUMENT_X_END-width+PADDING, AMOUNT_PART_START+SPACE);
        contentStream.setFont(normalFont, TEXT_SIZE_BIGGER);
        contentStream.setNonStrokingColor(BLACK);
        contentStream.showText("Suma na úhradu: ");

        contentStream.setFont(boldFont, TEXT_SIZE_BIGGER);
        contentStream.showText("%.2f".formatted(invoiceDetails.getAmount()) + " €");
        contentStream.endText();

    }

    private void makeFinalPart() throws IOException {
        contentStream.beginText();
        contentStream.newLineAtOffset(DOCUMENT_X_START+PADDING, DOCUMENT_Y_START+PADDING);
        contentStream.setFont(normalFont, TEXT_SIZE_BIGGER);
        contentStream.showText("Vystavil(a): " + sender.getCompanyName());
        contentStream.endText();
    }

    private void drawLine(float xStart, float yStart, float xEnd, float yEnd, float lineWith, float[] pattern) throws IOException {
        contentStream.moveTo(xStart, yStart);
        contentStream.setLineWidth(lineWith);
        contentStream.setLineDashPattern(pattern, 0);
        contentStream.lineTo(xEnd, yEnd);
        contentStream.stroke();
    }

    private void drawBorder(float lineWith) throws IOException {
        float[] pattern = new float[]{0};
        drawLine(DOCUMENT_X_START, DOCUMENT_Y_START, DOCUMENT_X_END, DOCUMENT_Y_START, lineWith, pattern);
        drawLine(DOCUMENT_X_START, DOCUMENT_Y_START, DOCUMENT_X_START, DOCUMENT_Y_END, lineWith, pattern);
        drawLine(DOCUMENT_X_START, DOCUMENT_Y_END, DOCUMENT_X_END, DOCUMENT_Y_END, lineWith, pattern);
        drawLine(DOCUMENT_X_END, DOCUMENT_Y_START, DOCUMENT_X_END, DOCUMENT_Y_END, lineWith, pattern);
    }

    private void save() throws IOException {
        contentStream.close();
        document.save(saveFile);
    }

    @Override
    public void close() throws IOException{
        contentStream.close();
        document.close();
    }
}
