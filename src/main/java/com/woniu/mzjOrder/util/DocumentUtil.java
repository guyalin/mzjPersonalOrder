package com.woniu.mzjOrder.util;

import com.woniu.mzjOrder.vo.TextLocationEnum;
import org.jsoup.nodes.Element;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DocumentUtil {
    public static String getLocationText(Element record, TextLocationEnum locationEnum, String textTag, Integer tagIndex, String filterStr){
        String text;
        switch (locationEnum) {
            case OWNER: text = record.select(textTag).get(tagIndex).ownText();
                break;
            case ATTR: text = record.select(textTag).
                    get(tagIndex).
                    attr(filterStr);
                break;
            case UPPER: text = record.ownText();
                break;
            case DATA:
                String sourceDateText = record.select(textTag).
                        get(tagIndex).data();
                text = extractDataStr(sourceDateText, filterStr);
                break;
            default: text = null;
                break;
        }
        return text;
    }

    /**
     * 在字符串数据文本中提取指定字符串
     */
    public static String extractDataStr(String sourceText, String pattern){
        String dataStr = "";
        String regex = pattern;
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(sourceText);
        while (m.find()){
            dataStr = m.group();
            break;
        }
        return dataStr;
    }
}
