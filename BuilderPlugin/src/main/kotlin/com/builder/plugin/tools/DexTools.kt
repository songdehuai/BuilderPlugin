package com.builder.plugin.tools

import java.io.*
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.zip.Adler32
import kotlin.experimental.and


object DexTools {

    /**
     * @param args
     */
    @JvmStatic
    fun main(args: Array<String>) {
        // TODO Auto-generated method stub
        try {
            val payloadSrcFile = File("force/app-release.apk") //需要加壳的程序
            System.out.println("apk size:" + payloadSrcFile.length())
            val unShellDexFile = File("force/app-release.dex") //解客dex
            val payloadArray = encrpt(readFileBytes(payloadSrcFile)!!) //以二进制形式读出apk，并进行加密处理//对源Apk进行加密操作
            val unShellDexArray = readFileBytes(unShellDexFile) //以二进制形式读出dex
            val payloadLen = payloadArray!!.size
            val unShellDexLen = unShellDexArray!!.size
            val totalLen = payloadLen + unShellDexLen + 4 //多出4字节是存放长度的。
            val newdex = ByteArray(totalLen) // 申请了新的长度
            //添加解壳代码
            System.arraycopy(unShellDexArray, 0, newdex, 0, unShellDexLen) //先拷贝dex内容
            //添加加密后的解壳数据
            System.arraycopy(payloadArray, 0, newdex, unShellDexLen, payloadLen) //再在dex内容后面拷贝apk的内容
            //添加解壳数据长度
            System.arraycopy(intToByte(payloadLen), 0, newdex, totalLen - 4, 4) //最后4为长度
            //修改DEX file size文件头
            fixFileSizeHeader(newdex)
            //修改DEX SHA1 文件头
            fixSHA1Header(newdex)
            //修改DEX CheckSum文件头
            fixCheckSumHeader(newdex)
            val str = "force/app-release.dex"
            val file = File(str)
            if (!file.exists()) {
                file.createNewFile()
            }
            val localFileOutputStream = FileOutputStream(str)
            localFileOutputStream.write(newdex)
            localFileOutputStream.flush()
            localFileOutputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    //直接返回数据，读者可以添加自己加密方法
    private fun encrpt(srcdata: ByteArray): ByteArray? {
        for (i in srcdata.indices) {
            srcdata[i] = (0xFF xor srcdata[i].toInt()).toByte()
        }
        return srcdata
    }

    /**
     * 修改dex头，CheckSum 校验码
     * @param dexBytes
     */
    private fun fixCheckSumHeader(dexBytes: ByteArray) {
        val adler = Adler32()
        adler.update(dexBytes, 12, dexBytes.size - 12) //从12到文件末尾计算校验码
        val value: Long = adler.getValue()
        val va = value.toInt()
        val newcs = intToByte(va)
        //高位在前，低位在前掉个个
        val recs = ByteArray(4)
        for (i in 0..3) {
            recs[i] = newcs[newcs.size - 1 - i]
            println(Integer.toHexString(newcs[i].toInt()))
        }
        System.arraycopy(recs, 0, dexBytes, 8, 4) //效验码赋值（8-11）
        println(java.lang.Long.toHexString(value))
        println()
    }


    /**
     * int 转byte[]
     * @param number
     * @return
     */
    fun intToByte(number: Int): ByteArray {
        var number = number
        val b = ByteArray(4)
        for (i in 3 downTo 0) {
            b[i] = (number % 256).toByte()
            number = number shr 8
        }
        return b
    }

    /**
     * 修改dex头 sha1值
     * @param dexBytes
     * @throws NoSuchAlgorithmException
     */
    @Throws(NoSuchAlgorithmException::class)
    private fun fixSHA1Header(dexBytes: ByteArray) {
        val md: MessageDigest = MessageDigest.getInstance("SHA-1")
        md.update(dexBytes, 32, dexBytes.size - 32) //从32为到结束计算sha--1
        val newdt: ByteArray = md.digest()
        System.arraycopy(newdt, 0, dexBytes, 12, 20) //修改sha-1值（12-31）
        //输出sha-1值，可有可无
        var hexstr = ""
        for (i in newdt.indices) {
            hexstr += Integer.toString((newdt[i] and 0xff.toByte()) + 0x100, 16)
                .substring(1)
        }
        println(hexstr)
    }

    /**
     * 修改dex头 file_size值
     * @param dexBytes
     */
    private fun fixFileSizeHeader(dexBytes: ByteArray) {
        //新文件长度
        val newfs = intToByte(dexBytes.size)
        println(Integer.toHexString(dexBytes.size))
        val refs = ByteArray(4)
        //高位在前，低位在前掉个个
        for (i in 0..3) {
            refs[i] = newfs[newfs.size - 1 - i]
            println(Integer.toHexString(newfs[i].toInt()))
        }
        System.arraycopy(refs, 0, dexBytes, 32, 4) //修改（32-35）
    }


    /**
     * 以二进制读出文件内容
     * @param file
     * @return
     * @throws IOException
     */
    @Throws(IOException::class)
    private fun readFileBytes(file: File): ByteArray? {
        val arrayOfByte = ByteArray(1024)
        val localByteArrayOutputStream = ByteArrayOutputStream()
        val fis = FileInputStream(file)
        while (true) {
            val i: Int = fis.read(arrayOfByte)
            if (i != -1) {
                localByteArrayOutputStream.write(arrayOfByte, 0, i)
            } else {
                return localByteArrayOutputStream.toByteArray()
            }
        }
    }
}