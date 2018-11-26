package com.sinocall.phonerecordera.util;

import android.text.TextUtils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;

/**
 * Created by qingchao on 2016/12/15.
 */

public class PinYinUtil {
    /**
     * 获取汉字的拼音
     *
     * @return
     */
    public static String getPinYin(String chinese) {
        if (TextUtils.isEmpty(chinese)) {return null;}

        //创建输出的格式化对象,决定输出的拼音格式，比如大小写，是否带有音标
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.UPPERCASE);//大写字母
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);//去掉声调

        //由于只能对单个汉字获取拼音，我们需要将字符串切割为字符数组，获取每一个汉字的
        //拼音，最后拼接即可
        StringBuilder builder = new StringBuilder();
        char[] chars = chinese.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            //1.过滤空格,如果是空格则跳过，继续下次遍历
            if (Character.isWhitespace(c)) {
                continue;
            }

            //2.简单判断一下是否是汉字,由于一个汉字占用2个以上字节，而一个字节的范围是-128~127；
            //因此，汉字肯定大于127
            if (c > 127) {
                //说明有可能是汉字
                try {
//                    单田芳 单独  说   评书！
//                    分词算法+强大数据库支持
                    //由于多音字的存在，比如单: {dan,  chan,  shan}
                    String[] pinyinArr = PinyinHelper.toHanyuPinyinStringArray(c, format);
                    if (pinyinArr != null && pinyinArr.length > 0) {
                        //在此处我们暂且取第1个,如果真的是多音字，那么我们也无能为力了！
                        builder.append(pinyinArr[0]);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    // 说明不是汉字，那么则忽略不处理即可
                }
            } else {
                //说明绝对不是汉字，一般是大小写字母,针对这个情况我们选择直接拼接
                builder.append(c);
            }
        }

        return builder.toString();
    }

    public static String getJianPin(String chinese) {
        if (TextUtils.isEmpty(chinese)){ return null;}

        //创建输出的格式化对象,决定输出的拼音格式，比如大小写，是否带有音标
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.UPPERCASE);//大写字母
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);//去掉声调

        //由于只能对单个汉字获取拼音，我们需要将字符串切割为字符数组，获取每一个汉字的
        //拼音，最后拼接即可
        StringBuilder builder = new StringBuilder();
        char[] chars = chinese.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            //1.过滤空格,如果是空格则跳过，继续下次遍历
            if (Character.isWhitespace(c)) {
                continue;
            }

            //2.简单判断一下是否是汉字,由于一个汉字占用2个以上字节，而一个字节的范围是-128~127；
            //因此，汉字肯定大于127
            if (c > 127) {
                //说明有可能是汉字
                try {
//                    单田芳 单独  说   评书！
//                    分词算法+强大数据库支持
                    //由于多音字的存在，比如单: {dan,  chan,  shan}
                    String[] pinyinArr = PinyinHelper.toHanyuPinyinStringArray(c, format);
                    if (pinyinArr != null && pinyinArr.length > 0) {
                        //在此处我们暂且取第1个,如果真的是多音字，那么我们也无能为力了！
                        builder.append(pinyinArr[0].substring(0, 1));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    //说明不是汉字，那么则忽略不处理即可
                }
            } else {
                //说明绝对不是汉字，一般是大小写字母,针对这个情况我们选择直接拼接
                builder.append(c);
            }
        }

        return builder.toString();
    }
}
