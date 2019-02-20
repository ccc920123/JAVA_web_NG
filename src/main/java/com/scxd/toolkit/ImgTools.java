package com.scxd.toolkit;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ImgTools {


    /**
     * 指定图片宽度和高度和压缩比例对图片进行压缩
     *
     * @param inputStream
     * @param widthdist   压缩后图片的宽度
     * @param heightdist  压缩后图片的高度
     * @param rate        压缩的比例
     */
    public static byte[] reduceImg(URL inputStream, int widthdist, int heightdist, Float rate) {
        try {
// 开始读取文件并进行压缩
            BufferedImage src = ImageIO.read(inputStream);
            // 如果比例不为空则说明是按比例压缩
            if (rate != null && rate > 0) {
                //获得源图片的宽高存入数组中
                int[] results = getImgWidthHeight(src);
                if (results == null || results[0] == 0 || results[1] == 0) {
                    return null;
                } else {
                    //按比例缩放或扩大图片大小，将浮点型转为整型
                    widthdist = (int) (results[0] * rate);
                    heightdist = (int) (results[1] * rate);
                }
            }


            // 构造一个类型为预定义图像类型之一的 BufferedImage
            BufferedImage tag = new BufferedImage((int) widthdist, (int) heightdist, BufferedImage.TYPE_INT_RGB);

            //绘制图像  getScaledInstance表示创建此图像的缩放版本，返回一个新的缩放版本Image,按指定的width,height呈现图像
            //Image.SCALE_SMOOTH,选择图像平滑度比缩放速度具有更高优先级的图像缩放算法。
            Image image = src.getScaledInstance(widthdist, heightdist, Image.SCALE_DEFAULT);
            tag.getGraphics().drawImage(image, 0, 0, null);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(tag, "JPEG", bos);
            return bos.toByteArray();
        } catch (Exception ef) {
            ef.printStackTrace();
        }
        return null;
    }

    /**
     * 指定图片宽度和高度和压缩比例对图片进行压缩
     *
     * @param inputStream
     * @param widthdist   压缩后图片的宽度
     * @param heightdist  压缩后图片的高度
     * @param rate        压缩的比例
     */
    public static byte[] reduceImg(InputStream inputStream, int widthdist, int heightdist, Float rate) {
        try {
// 开始读取文件并进行压缩
            BufferedImage src = ImageIO.read(inputStream);
            // 如果比例不为空则说明是按比例压缩
            if (rate != null && rate > 0) {
                //获得源图片的宽高存入数组中
                int[] results = getImgWidthHeight(src);
                if (results == null || results[0] == 0 || results[1] == 0) {
                    return null;
                } else {
                    //按比例缩放或扩大图片大小，将浮点型转为整型
                    widthdist = (int) (results[0] * rate);
                    heightdist = (int) (results[1] * rate);
                }
            }


            // 构造一个类型为预定义图像类型之一的 BufferedImage
            BufferedImage tag = new BufferedImage((int) widthdist, (int) heightdist, BufferedImage.TYPE_INT_RGB);

            //绘制图像  getScaledInstance表示创建此图像的缩放版本，返回一个新的缩放版本Image,按指定的width,height呈现图像
            //Image.SCALE_SMOOTH,选择图像平滑度比缩放速度具有更高优先级的图像缩放算法。
            Image image = src.getScaledInstance(widthdist, heightdist, Image.SCALE_DEFAULT);
            tag.getGraphics().drawImage(image, 0, 0, null);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(tag, "JPEG", bos);
            return bos.toByteArray();
        } catch (Exception ef) {
            ef.printStackTrace();
        }
        return null;
    }
    /**
     * 获取图片宽度和高度
     *
     * @param src LIU
     * @return 返回图片的宽度
     */
    public static int[] getImgWidthHeight(BufferedImage src) {
        int result[] = {0, 0};
        try {
            // 从流里将图片写入缓冲图片区
            result[0] = src.getWidth(null); // 得到源图片宽
            result[1] = src.getHeight(null);// 得到源图片高
        } catch (Exception ef) {
            ef.printStackTrace();
        }

        return result;
    }


    /**
     * 将图片的链接替换成当前服务器的链接
     * 如：http://127.0.0.1:59995/Web/getPhotos?id=403918ec-d23e-49dd-b08e-a123b32f89fb
     * 替换成http://10.192.12.84:9995/Web/getPhotos?id=403918ec-d23e-49dd-b08e-a123b32f89fb
     *  String basePath = request.getScheme() + "://" + request.getServerName() + ":" +
     request.getServerPort();
     * @param src LIU
     * @return 返回图片的宽度
     */
    public static String getLocalServiceUrl(String src,  String basePath ) {

        if (src!=null&&src!="") {
                String oldRequest = getIP(URI.create(src));
                src=src.replaceAll(oldRequest,basePath);
        }
        return src;
    }

    private static String getIP(URI uri) {
        URI effectiveURI = null;

        try {
            effectiveURI = new URI(uri.getScheme(), uri.getUserInfo(), uri.getHost(), uri.getPort(), null, null, null);
        } catch (Throwable var4) {
            effectiveURI = null;
        }

        return effectiveURI.toString();
    }
}
