package com.shimh.service.impl;

import com.shimh.entity.Log;
import com.shimh.repository.LogRepository;
import com.shimh.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author CSE
 * <p>
 * 2019年4月18日
 */
@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogRepository logRepository;

    @Override
    public Integer saveLog(Log log) {
        return logRepository.save(log).getId();
    }
}
