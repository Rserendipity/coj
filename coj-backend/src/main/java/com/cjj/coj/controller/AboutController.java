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
//
//    @GetMapping("/author")
//    public ResultBody aboutAuthor() throws IOException {
//        // 读取resource里的author.md格式文件
//        return ResultBody.success(ReadResourceFileUtil.readResourceFile("/md/author.md"));
//    }
//
//    @GetMapping("/database")
//    public ResultBody aboutDatabase() throws IOException {
//        // 读取resource里的database.md格式文件
//        return ResultBody.success(ReadResourceFileUtil.readResourceFile("/md/database.md"));
//    }
//
//    @GetMapping("/project")
//    public ResultBody aboutProject() throws IOException {
//        // 读取resource里的project.md格式文件
//        return ResultBody.success(ReadResourceFileUtil.readResourceFile("/md/project.md"));
//    }

    @GetMapping("/{file}")
    public ResultBody aboutProject(@PathVariable("file") String file) throws IOException {
        return ResultBody.success(ReadResourceFileUtil.readResourceFile(String.format("/md/%s.md", file)));
    }
}
