package com.builder;

import com.builder.config.BuilderConfig;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 渠道列表工具
 *
 * @author songdehuai
 */
public class ChannelTools {

    private static ChannelTools channelTools = new ChannelTools();

    private ChannelTools() {

    }

    public static ChannelTools getInstance() {
        return channelTools;
    }

    /**
     * 获取渠道list
     *
     * @param file
     * @return
     */
    public List<String> getChannelList(File file) {
        if (!file.exists()) {
            return null;
        }
        try {
            return FileUtils.readLines(file, BuilderConfig.DEFAULT_CHARSET_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
