package com.cjj.coj.service.impl;

import com.cjj.coj.modle.dto.submit.SubmitUpload;
import com.cjj.coj.service.SubmitService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootTest
@EnableAsync
class SubmitServiceImplTest {

    @Autowired
    private SubmitService submitService;

    @Test
    void upload() {
        SubmitUpload submitUpload = new SubmitUpload();
        submitUpload.setCode("code");
        submitUpload.setProblemId(1758843545285931009L);
        submitUpload.setUserId(1L);
        submitUpload.setLanguage("java");

        submitService.upload(submitUpload);
    }
}