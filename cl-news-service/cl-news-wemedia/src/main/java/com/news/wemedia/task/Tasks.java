package com.news.wemedia.task;

import com.news.wemedia.service.IAuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
public class Tasks {
    @Autowired
    private IAuditService auditService;

    @Async
    public void auditNews(Integer id){
        auditService.audit(id);
    }
}
