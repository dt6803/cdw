package com.cdw_ticket.payment_service.service;

import com.cdw_ticket.payment_service.dto.response.IpnResponse;

import java.util.Map;

public interface IpnHandler {
    IpnResponse process(Map<String, String> params);
}
