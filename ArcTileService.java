package com.lvmap.cn

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * @author lvmap
 * @date 2018-12
 */

@Component
public class ArcTileService {

    /**
     * 空白的图片
     */
    public static String base64Blank = "iVBORw0KGgoAAAANSUhEUgAAAQAAAAEACAYAAABccqhmAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyJpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuMy1jMDExIDY2LjE0NTY2MSwgMjAxMi8wMi8wNi0xNDo1NjoyNyAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENTNiAoV2luZG93cykiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6NkZFQUUzNjgyRjJBMTFFNEFBQ0JGMEMyRjFFNUE0MUYiIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6NkZFQUUzNjkyRjJBMTFFNEFBQ0JGMEMyRjFFNUE0MUYiPiA8eG1wTU06RGVyaXZlZEZyb20gc3RSZWY6aW5zdGFuY2VJRD0ieG1wLmlpZDo2RkVBRTM2NjJGMkExMUU0QUFDQkYwQzJGMUU1QTQxRiIgc3RSZWY6ZG9jdW1lbnRJRD0ieG1wLmRpZDo2RkVBRTM2NzJGMkExMUU0QUFDQkYwQzJGMUU1QTQxRiIvPiA8L3JkZjpEZXNjcmlwdGlvbj4gPC9yZGY6UkRGPiA8L3g6eG1wbWV0YT4gPD94cGFja2V0IGVuZD0iciI/Pij7ZYAAAAJdSURBVHja7NQxAQAACMMwwL/nYQAHJBJ6tJMU8NNIAAYAGABgAIABAAYAGABgAIABAAYAGABgAIABAAYAGABgAIABAAYAGABgAIABAAYAGABgAIABAAYAGABgAIABAAYAGABgAIABAAYAGABgAIABgAEABgAYAGAAgAEABgAYAGAAgAEABgAYAGAAgAEABgAYAGAAgAEABgAYAGAAgAEABgAYAGAAgAEABgAYAGAAgAEABgAYAGAAgAEABgAYAGAAgAGAAQAGABgAYACAAQAGABgAYACAAQAGABgAYACAAQAGABgAYACAAQAGABgAYACAAQAGABgAYACAAQAGABgAYACAAQAGABgAYACAAQAGABgAYACAAYABAAYAGABgAIABAAYAGABgAIABAAYAGABgAIABAAYAGABgAIABAAYAGABgAIABAAYAGABgAIABAAYAGABgAIABAAYAGABgAIABAAYAGABgAIABgAEABgAYAGAAgAEABgAYAGAAgAEABgAYAGAAgAEABgAYAGAAgAEABgAYAGAAgAEABgAYAGAAgAEABgAYAGAAgAEABgAYAGAAgAEABgAYAGAAgAGAAQAGABgAYACAAQAGABgAYACAAQAGABgAYACAAQAGABgAYACAAQAGABgAYACAAQAGABgAYACAAQAGABgAYACAAQAGABgAYACAAQAGABgAYACAAYABAAYAGABgAIABAAYAGABgAIABAAYAGABgAIABAAYAGABgAIABAAYAGABgAIABAAYAGABgAIABAAYAGABgAIABAAYAGABgAIABAJcVYADnygT9CIf4ngAAAABJRU5ErkJggg==";


    /***
     * RESTFul 方式
     * 获取 arcgis Cache 压缩版本
     * @param level 切片等级
     * @param col 列号
     * @param row 行号
     * @return
     */
    @Override
    public byte[] getTileData(int level, int col, int row，String tileType) {
        byte[] outByte =null;
        //获取服务资源路径
        String tilePath = getBoundleBasePath(level, col, row);

        switch (tileType) {
			//压缩类型
            case "compact":
                String bundleFileName = tilePath + ".bundle";
                String bundlxFileName = tilePath + ".bundlx";
                File resourceBle = new File(bundleFileName);
                File resourceBlx = new File(bundlxFileName);

                if (resourceBle.exists()) {
                    //10.1以前两个文件版本
                    if (resourceBlx.exists()) {
                        outByte = getMapTileV1(level, col, row, tilePath);
                    }
                    //10.1后单个文件版本
                    else {
                        outByte = getMapTileV2(level, col, row, tilePath);
                    }
                } else {
                    String result = String.format("level:%s,col:%s,row:%s", level, col, row);

                    //log.info("压缩切片不存在:" + tilePath + result)
//                    System.out.println("压缩切片不存在:" + tilePath + result);a
                }
                break;
			//离散类型
            case "exploded":
                outByte = getExportData(level, col, row);
                break;

            default:
                break;
        }
        if (outByte == null) {
            byte[] blankImg2 = Base64.decodeBase64(base64Blank);
            outByte = blankImg2;
        }
        return outByte;
    }

    /***
     * 获取指定切片压缩包路径
     * @param level
     * @param col
     * @param row
     * @return path
     */
    private String getBoundleBasePath(int level, int col, int row, String pathStr) {
        //获取服务资源路径
        String tilePath = pathStr;
        //切片等级目录
        String l = "L" + getZero(2, String.valueOf(level).length()) + level;

        int rGroup = 128 * (row / 128);
        int cGroup = 128 * (col / 128);
        //行计算
        String rTail = Integer.toHexString(rGroup);
        String r = "R" + getZero(4, rTail.length()) + rTail;
        //列计算
        String cTail = Integer.toHexString(cGroup);
        String c = "C" + getZero(4, cTail.length()) + cTail;
        //拼接文件名称
        String bundleBase = String.format("%s\\%s\\%s%s", tilePath, l, r, c);

        return bundleBase;
    }

    public byte[] getMapTileV1(int level, int col, int row, String bundleBase) {

        //结果输出局流
        byte[] output = null;
        //行计算
        int rGroup = 128 * (row / 128);
        int cGroup = 128 * (col / 128);

        String bundleFileName = bundleBase + ".bundle";
        String bundlxFileName = bundleBase + ".bundlx";

        try {
            int index = 128 * (col - cGroup) + (row - rGroup);
            //索引文件
            RandomAccessFile isBundlx = new RandomAccessFile(bundlxFileName, "r");
            isBundlx.skipBytes(16 + 5 * index);

            byte[] buffer = new byte[5];
            isBundlx.read(buffer);
            long offset = (long) (buffer[0] & 0xff) + (long) (buffer[1] & 0xff) * 256 + (long) (buffer[2] & 0xff) * 65536 + (long) (buffer[3] & 0xff) * 16777216 + (long) (buffer[4] & 0xff) * 4294967296L;
            isBundlx.close();

            RandomAccessFile isBundle = new RandomAccessFile(bundleFileName, "r");
            isBundle.skipBytes((int) offset);
            byte[] lengthBytes = new byte[4];
            isBundle.read(lengthBytes);
            int length = (int) (lengthBytes[0] & 0xff) + (int) (lengthBytes[1] & 0xff) * 256 + (int) (lengthBytes[2] & 0xff) * 65536 + (int) (lengthBytes[3] & 0xff) * 16777216;
            byte[] result = new byte[length];
            isBundle.read(result);
            if (result.length > 0) {
                output = result;
            }
            isBundle.close();

        } catch (Exception e) {

        }
        return output;
    }

    /***
     * 4Byte 转Long (esri)
     * @param bytes
     * @return
     */
    private long toLong(byte[] bytes) {
        long var4 = 0L;
        int var6 = bytes.length - 1;
        while (var6 > 0) {
            var4 |= (long) (bytes[0 + var6] & 255) << 8 * var6;
            --var6;
        }
        var4 |= (long) (bytes[0] & 255);
        return var4;
    }

    /**
     *  解析读取 arcgis server 10.1 以后版本的紧凑型切片文件
     * @param level 切片层级
     * @param col 列
     * @param row 行
     * @param bundleBase 文件路径，不包含后缀
     * @return
     */


    public byte[] getMapTileV2(int level, int col, int row, String bundleBase) {
        //结果输出局流
        byte[] output2 = null;

        //行计算
        int rGroup = 128 * (row / 128);
        int cGroup = 128 * (col / 128);

        try {
            int index = 128 * (row - rGroup) + (col - cGroup);
            RandomAccessFile isBundle = new RandomAccessFile(bundleBase + ".bundle", "r");
            byte[] buffer = new byte[4];
            isBundle.seek(64 + 8 * index);
            isBundle.read(buffer);

            long dataOffset = toLong(buffer);
            int lengthOffset = (int) dataOffset - 4;

            byte[] length = new byte[4];
            isBundle.seek(lengthOffset);
            isBundle.read(length);
            int tileDataLength = (int) toLong(length);

            output2 = new byte[tileDataLength];
            isBundle.seek(lengthOffset + 4);
            isBundle.read(output2, 0, output2.length);
            isBundle.close();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return output2;
    }


    public byte[] getExportData(int level, int col, int row, String pathStr ,String imgType) {

        String tilePath = pathStr;
        String l = "L" + getZero(2, String.valueOf(level).length()) + level;

        String strRow = Integer.toHexString(row);
        String r = "R" + getZero(8, strRow.length()) + strRow;

        String strCol = Integer.toHexString(col);
        String c = "C" + getZero(8, strCol.length()) + strCol;

        String filePath = tilePath + "/" + l + "/" + r + "/" + c + "." + imgType;
        InputStream is = null;
        File file = new File(filePath);

        byte[] buffer = null;
        try {
            if (!file.exists()) {
                byte[] blankImg = Base64.decodeBase64(base64Blank);
                is = new ByteArrayInputStream(blankImg);
            } else {
                is = new FileInputStream(filePath);
            }
            BufferedImage image = ImageIO.read(is);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "png", outputStream);
            buffer = outputStream.toByteArray();

        } catch (Exception e) {

        }
        return buffer;
    }

    private String getZero(int length, int strLength) {
        int zeroLength = length - strLength;
        String strZero = "";
        for (int i = 0; i < zeroLength; i++) {
            strZero += "0";
        }
        return strZero;
    }
}
