package com.example.diploma.service.impl;

import com.example.diploma.repository.AuditLogRepository;
import com.example.diploma.service.interfaces.AuditLogService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuditLogServiceImpl implements AuditLogService {
    private AuditLogRepository auditLogRepository;

}
