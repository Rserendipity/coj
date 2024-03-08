package com.cjj.coj.codesandbox.utils;

import com.alibaba.fastjson.JSON;
import com.cjj.coj.codesandbox.model.Command;
import com.cjj.coj.utils.ReadResourceFileUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompileUtil {
    private static final Map<String, Command> compileMap = new HashMap<>();

    // 从json文件中读取编译命令
    static {
        try {
            String json = ReadResourceFileUtil.readResourceFile("/json/command.json");
            List<Command> commands = JSON.parseArray(json, Command.class);
            for (Command command : commands) {
                compileMap.put(command.getName(), command);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Command getCommand(String language) {
        return compileMap.get(language);
    }
}
