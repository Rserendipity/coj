package com.cjj.coj;

import com.cjj.coj.utils.ReadResourceFileUtil;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class FileUtilTests {
    @Test
    public void getResourceFileTest() throws IOException {
        String s = ReadResourceFileUtil.readResourceFile("/md/author.md");
        System.out.println(s);
    }

}
