package com.fanfan.alon.core;

/**
 * 功能描述:分词器
 * @param:
 * @return:
 * @auther: zoujiulong
 * @date: 2018/9/3   11:48
 */
public final class AnalyzerConstants {


    /**
     * 最细粒度拆分
     */
    public static final String IK_ANALYZER = "ik_max_word";

    /**
     * 粗粒度拆分，一般不用
     */
    public static final String IK_SMART = "ik_smart";

    /**
     * 自定义IK及拼音分词
     */
    public static final String IK_PINYIN_ANALYZER = "ik_pinyin_analyzer";
}
