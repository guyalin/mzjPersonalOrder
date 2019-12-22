
package com.woniu.mzjOrder.task;

//import com.woniu.mzjOrder.service.NetInformationService;
import com.woniu.mzjOrder.service.NetInformationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;


@Slf4j
@Configuration
@EnableScheduling
public class ScheduledTaskService {

    @Autowired
    private NetInformationService netInformationService;

     /*
     *定时更新网页文章记录到数据
     * */

    @Scheduled(cron = "00 30 08 * * *")
    private void updateNetArticleRecord(){
        try {
            //全量更新
            netInformationService.loadNetNewsArticleToDB(null);
            log.info("taskScheduled start...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

