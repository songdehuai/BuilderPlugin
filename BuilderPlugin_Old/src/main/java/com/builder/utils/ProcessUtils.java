package com.builder.utils;

import com.builder.config.BuilderConfig;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;


/**
 * ProcessToolClass
 *
 * @author songdehuai
 */
public class ProcessUtils {

    /**
     * 创建进程并执行指令返回结果
     *
     * @param commend 子进程执行的命令
     * @return
     */
    public static Result run(String commend) {

        return run(commend, BuilderConfig.DEFAULT_CHARSET_NAME);
    }

    /**
     * 创建进程并执行指令返回结果
     *
     * @param commend     子进程执行的命令
     * @param charsetName 字符集
     * @return
     */
    public static Result run(String commend, String charsetName) {
        StringTokenizer st = new StringTokenizer(commend);
        String[] commendArray = new String[st.countTokens()];
        for (int i = 0; st.hasMoreTokens(); i++) {
            commendArray[i] = st.nextToken();
        }

        return run(Arrays.asList(commendArray), charsetName);
    }

    /**
     * 创建进程并执行指令返回结果
     *
     * @param commend 子进程执行的命令
     * @return
     */
    public static Result run(List<String> commend) {
        return run(commend, BuilderConfig.DEFAULT_CHARSET_NAME);
    }

    /**
     * 创建进程并执行指令返回结果
     *
     * @param commend     子进程执行的命令
     * @param charsetName 字符集
     * @return
     */
    public static Result run(List<String> commend, String charsetName) {
        Result result = new Result();
        InputStream is = null;

        try {
            //重定向异常输出流
            Process process = new ProcessBuilder(commend).redirectErrorStream(true).start();

            //读取输入流中的数据
            is = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, charsetName));
            StringBuilder data = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                data.append(line).append(System.lineSeparator());
                System.out.println("\033[0;35m" + line + "\033[0m");
            }
            //获取返回码
            result.code = process.waitFor();
            //获取执行结果
            result.data = data.toString().trim();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            //关闭数据流
            closeStreamQuietly(is);
        }

        return result;
    }

    /**
     * 关闭数据流
     *
     * @param stream
     */
    private static void closeStreamQuietly(Closeable stream) {
        try {
            if (stream != null) {
                stream.close();
            }
        } catch (IOException e) {
            // ignore
        }
    }

    /**
     * 进程处理结果实体类
     */
    public static class Result {
        /**
         * 返回码，0：正常，其他：异常
         */
        public int code;
        /**
         * 返回结果
         */
        public String data;
    }

}