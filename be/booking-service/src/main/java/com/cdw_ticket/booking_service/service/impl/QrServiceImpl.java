package com.cdw_ticket.booking_service.service.impl;

import com.cdw_ticket.booking_service.service.QrService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@Service
@Slf4j
public class QrServiceImpl implements QrService {
    @Override
    public byte[] generateQRCodeImage(String data, int width, int height) throws IOException, WriterException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, width, height);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
        ImageIO.write(bufferedImage, "PNG", pngOutputStream);
        return pngOutputStream.toByteArray();
    }

    @Override
    public String toBase64(byte[] imageBytes) {
        return Base64.getEncoder().encodeToString(imageBytes);
    }
}
