package com.cjj.coj.service;

import com.cjj.coj.common.ResultBody;
import com.cjj.coj.modle.dto.submit.SubmitUpload;
import org.springframework.stereotype.Service;

@Service
public interface SubmitService {
    ResultBody upload(SubmitUpload upload);

    ResultBody getDetail(Long id);

    ResultBody getDetailList(Long userId, Long problemId);
}
