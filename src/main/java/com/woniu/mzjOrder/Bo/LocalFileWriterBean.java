package com.woniu.mzjOrder.Bo;

import com.woniu.mzjOrder.entity.ArticleRecord;
import com.woniu.mzjOrder.util.DateUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

@Slf4j
@Data
public class LocalFileWriterBean {
    private String localFilePath;
    private PrintWriter pw;

    public void init() throws FileNotFoundException {
        if (pw == null) {
            log.info("LocalFileWriterBean: printWriter init!");
            pw = new PrintWriter(new File(localFilePath +"\\" + "result" + ".txt"));
        }
    }

    public void saveToFile(List<ArticleRecord> records) throws FileNotFoundException {
        if (pw == null) {
            init();
        }
        for (ArticleRecord record : records) {
            //pw.println("area : " + record.getArea());
            pw.println("department: "+ record.getArticleName());
            pw.println("link : " + record.getTargetUrl());
            pw.println("text : " + record.getArticleTitle());
            pw.println("date : " + record.getDateTime());
            pw.println("\n");
        }
        pw.flush();
        destroy();
    }

    public void destroy(){
        if (pw != null) {
            log.info("LocalFileWriterBean: printWriter destroyed!");
            pw.close();
            pw = null;
        }
    }
}
