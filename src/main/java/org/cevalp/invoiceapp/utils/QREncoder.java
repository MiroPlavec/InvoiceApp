package org.cevalp.invoiceapp.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.cevalp.invoiceapp.model.PaymentDetails;
import org.tukaani.xz.LZMA2Options;
import org.tukaani.xz.LZMAOutputStream;
import org.tukaani.xz.UnsupportedOptionsException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.CRC32;


public class QREncoder {

    private static final int LC = 3;
    private static final int LP = 0;
    private static final int PB = 2;
    private static final int DICTIONARY_SIZE = 128 * 1024;
    private static final int QR_SIZE = 100;


    public String encode(PaymentDetails details){

        // 1. step -> compute checksum
        String paymentDetails = details.getDetail();
        byte[] dataBytes = paymentDetails.getBytes(StandardCharsets.UTF_8);
        byte[] checksum = getCRC32Checksum(dataBytes);

        // 2. step -> append checksum at the beginning of data
        byte[] total = new byte[checksum.length + dataBytes.length];
        System.arraycopy(checksum, 0, total, 0, checksum.length);
        System.arraycopy(dataBytes, 0, total, checksum.length, dataBytes.length);

        // 3. step -> LZMA compression
        byte[] compressedData = compress(total);

        // 4. step -> concat header and compress data together
        byte[] finalData = concatData(compressedData, (short) total.length);

        // 5. step -> transform to binary
        StringBuilder binaryArray = toBinary(finalData);

        // 6. step -> add padding
        while (binaryArray.length() % 5 != 0) binaryArray.append('0');

        // 7. step -> transform to Base32Hex
        String result = toBase32Hex(binaryArray);

//        System.out.println(result);

        return result;
    }

    private byte[] getCRC32Checksum(byte[] dataBytes){
        CRC32 crc32 = new CRC32();
        crc32.update(dataBytes);
        int checksum = (int) crc32.getValue(); // cast from long to int because CRC32 checksum should be 32 bits

        // in document used LITTLE_ENDIAN for checksum
        return ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(checksum).array();
    }

    private byte[] compress(byte[] data) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // same options as in document
        LZMA2Options options = new LZMA2Options();
        try {
            options.setLc(QREncoder.LC);
            options.setLp(QREncoder.LP);
            options.setPb(QREncoder.PB);
            options.setDictSize(QREncoder.DICTIONARY_SIZE);
        }catch (UnsupportedOptionsException e){
            throw new RuntimeException("Error while setting options parameters");
        }

        // not working with inputSize as 3th parameter, working with usedEndMarker
        // it works for both(true and false) value
        try (LZMAOutputStream lzmaOutputStream = new LZMAOutputStream(outputStream, options, false)){
            lzmaOutputStream.write(data);
        }catch (IOException e){
            throw new RuntimeException(e);
        }

        return outputStream.toByteArray();

    }

    private byte[] concatData(byte[] compressedData, short uncompressedDataLength) {
        ByteArrayOutputStream finalArray = new ByteArrayOutputStream();
        byte[] head = {0, 0}; // 16 bits header
        byte[] length = ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN).putShort(uncompressedDataLength).array(); // length of uncompressed data

        try {
            finalArray.write(head);
            finalArray.write(length);
            finalArray.write(compressedData);
        } catch (IOException e) {
            throw new RuntimeException("Error while concatenating all data into final array");
        }

        return finalArray.toByteArray();
    }

    private StringBuilder toBinary(byte[] bytes){
        StringBuilder binary = new StringBuilder();
        for (byte b : bytes) {
            // https://mkyong.com/java/java-convert-bytes-to-unsigned-bytes/
            String binaryArray = "%8s".formatted(Integer.toBinaryString(b & 0xFF));
            binaryArray = binaryArray.replace(' ', '0'); // swap empty spaces for padding (0)
            binary.append(binaryArray);
        }

        return binary;
    }

    private String toBase32Hex(StringBuilder binaryArray){
        StringBuilder resultBuilder = new StringBuilder();
        String values = "0123456789ABCDEFGHIJKLMNOPQRSTUV";
        for (int i = 0; i < binaryArray.length(); i+=5) {
            int decimal = Integer.parseInt(binaryArray.substring(i, i+5), 2);
            resultBuilder.append(values.charAt(decimal));
        }

        return resultBuilder.toString();
    }

    public byte[] createQrCode(String content){
        byte[] imageArray;
        try {
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.MARGIN, 0);
            BitMatrix bitMatrix = new QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, QR_SIZE, QR_SIZE, hints);
            BufferedImage qrCode = MatrixToImageWriter.toBufferedImage(bitMatrix);

            InputStream logoStream = getClass().getClassLoader().getResourceAsStream("logo.png");
            if(logoStream == null){
                throw new LogoNotFoundException("Can not find logo image in \"resources\" folder");
            }
            BufferedImage logo = ImageIO.read(logoStream);

            int qrPositionX = (logo.getWidth() / 2) - (qrCode.getWidth() / 2);
            // divided by 8 is because of "PAY BY SQUARE" title at the bottom of the logo image
            int qrPositionY = (logo.getHeight() / 2) - (qrCode.getHeight() / 2) - (qrCode.getHeight() / 8);

            BufferedImage combined = new BufferedImage(logo.getWidth(), logo.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D g = combined.createGraphics();
            g.drawImage(logo, 0,0, null);
            g.drawImage(qrCode, qrPositionX, qrPositionY, null);
            g.dispose();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(combined, "PNG", baos);
            imageArray = baos.toByteArray();


        } catch (WriterException e) {
            throw new QrEncodingException("Problem occurred while encoding data into QR image");
        } catch (IOException e) {
            throw new RuntimeException("Problem occurred while reading/saving qr code");
        }

        return imageArray;

    }

}
