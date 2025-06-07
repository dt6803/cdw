package com.cdw_ticket.payment_service.service.impl;

import com.cdw_ticket.payment_service.exception.AppException;
import com.cdw_ticket.payment_service.exception.ErrorCode;
import com.cdw_ticket.payment_service.service.CryptoService;
import com.cdw_ticket.payment_service.util.EncodingUtil;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

public class CryptoServiceImpl implements CryptoService {

    Mac mac = Mac.getInstance("HmacSHA512");
    @NonFinal
    @Value("${payment.vnpay.secret-key}")
    String secretKey;
    public CryptoServiceImpl() throws NoSuchAlgorithmException {
    }

    @PostConstruct
    void init() throws InvalidKeyException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA512");
        mac.init(secretKeySpec);
    }

    @Override
    public String sign(String data) {
        try {
            return EncodingUtil.toHexString(mac.doFinal(data.getBytes()));
        }
        catch (Exception e) {
            throw new AppException(ErrorCode.VNPAY_SIGNING_FAILED);
        }
    }
}
