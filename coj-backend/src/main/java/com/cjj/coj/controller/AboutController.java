package com.cjj.coj.controller;

import com.cjj.coj.common.ResultBody;
import com.cjj.coj.utils.ReadResourceFileUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/about")
public class AboutController {

    @GetMapping("/{file}")
    public ResultBody aboutProject(@PathVariable("file") String file) throws IOException {
        return ResultBody.success(ReadResourceFileUtil.readResourceFile(String.format("/md/%s.md", file)));
    }
}
