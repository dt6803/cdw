package com.cdw_ticket.booking_service.service.impl;

import com.cdw_ticket.booking_service.service.QrService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@Service
public class QrServiceImpl implements QrService {
    @Override
    public byte[] generateQRCodeImage(Object data, int width, int height) throws IOException, WriterException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(data);

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(json, BarcodeFormat.QR_CODE, width, height);

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
