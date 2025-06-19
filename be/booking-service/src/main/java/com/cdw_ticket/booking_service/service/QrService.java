package com.cdw_ticket.booking_service.service;

import com.google.zxing.WriterException;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface QrService {
    byte[] generateQRCodeImage(Object data, int width, int height) throws IOException, WriterException;
    String toBase64(byte[] imageBytes);
}
