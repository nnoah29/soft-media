package com.softit.smedia.utils;

import com.softit.smedia.controller.FluxRssControlleur;
import com.softit.smedia.controller.PlayListController;
import com.softit.smedia.model.ConfigData;
import com.softit.smedia.model.FluxRss;
import com.softit.smedia.model.PlayList;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ScheduledFuture;

import static com.softit.smedia.controller.ConfigDataControleur.getConfigData;
import static com.softit.smedia.controller.PlayListController.getPlayList;
import static com.softit.smedia.utils.ConstantGlobal.getEnvProp;

@Component
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");


    private TaskScheduler taskScheduler;
    private TaskScheduler taskSchedulerBackup;
    private TaskScheduler taskSchedulerFluxRss;

    @SuppressWarnings("rawtypes")
    private ScheduledFuture<?> scheduledFuture;
    private ScheduledFuture<?> scheduledFutureBackup;
    private ScheduledFuture<?> scheduledFutureFluss;

    public ScheduledTasks() {
    }

    /**
     * tâche cron pour chercher une nouvelle playList
     */
//    @Scheduled(cron = "${checkPlayListTime}")
    public void checkNewPlayList() {
        PlayList oldPl = getPlayList();
        log.info("The time is now {} {} ", dateFormat.format(new Date()));
        if (oldPl == null) {
            new PlayListController().checkNewPlayList(0);
        } else {
            new PlayListController().checkNewPlayList(oldPl.getIdPl().intValue());
            System.out.println("Playlist id " + oldPl.getIdPl());
        }
//            Thread.sleep(5000);
    }

    //    @Scheduled(cron = "${checkPlayListTime}")
    public void getFluxRss() {
        log.info("Send request");
        FluxRss fluxRss = new FluxRssControlleur().getFluxFromServe();
//        log.info("Result ", fluxRss);

    }

    //    @Scheduled(cron = "${delecteBackupDirTime}")
    public void deleteBackupDir() {
//        log.info("Delecte backup dir ");
        String fileName = getEnvProp("bkpDirMedia");
        try {
            FileUtils.cleanDirectory(new File(fileName));
            log.info("Succes to delete backup dir");
            new File(fileName).mkdir();
        } catch (IOException e) {
            log.info("Error to delete backup dir");
        }
    }

    /**
     * Methode to start the schedule (lance la boucle de check
     */
    public void reSchedule() {


        ConfigData d = getConfigData();
        String newTimer = d.getCheckPlayListTimeVal();
        String newTimerBackup = getEnvProp("delecteBackupDirTime");
//        String newTimerBackup = d.getDelecteBackupDirTimeVal();
        String newTimerFluxRss = d.getRefleshFluxRssVal();
        log.info("new time playlist {}  config {}  ", newTimer);

        // Playlist
        if (taskScheduler == null) {
            this.taskScheduler = new ConcurrentTaskScheduler();
        }
        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
        }
        this.scheduledFuture = this.taskScheduler.schedule(() -> {
            log.info("{} Running playList checking  ... {} ", "=>", newTimer);
            checkNewPlayList();
        }, new CronTrigger(newTimer));
        // backup

        if (taskSchedulerBackup == null) {
            this.taskSchedulerBackup = new ConcurrentTaskScheduler();
        }

        if (scheduledFutureBackup != null) {
            scheduledFutureBackup.cancel(true);
        }
        this.scheduledFutureBackup = this.taskSchedulerBackup.schedule(() -> {
            log.info("{} Running delecting backup dir  ...", "=>");
            deleteBackupDir();
        }, new CronTrigger(newTimerBackup));
        // Flux Rss
        if (taskSchedulerFluxRss == null) {
            this.taskSchedulerFluxRss = new ConcurrentTaskScheduler();
        }
        if (d.isActiveFluxRss()) {
            if (scheduledFutureFluss != null) {
                scheduledFutureFluss.cancel(true);
            }
            this.scheduledFutureFluss = this.taskSchedulerFluxRss.schedule(() -> {
                log.info("{} Running FluxRss checking  ...", "=>");
                getFluxRss();
            }, new CronTrigger(newTimerFluxRss));

        }

        fistStart();
    }

    public void fistStart() {
        checkNewPlayList();
        if (getConfigData().isActiveFluxRss()) {
            getFluxRss();
        }
    }

    @PostConstruct
    public void start() {
        reSchedule();
    }


}
// examples of other CRON expressions
// * "0 0 * * * *" = the top of every hour of every day.
// * "*/10 * * * * *" = every ten seconds.
// * "0 0 8-10 * * *" = 8, 9 and 10 o'clock of every day.
// * "0 0/30 8-10 * * *" = 8:00, 8:30, 9:00, 9:30 and 10 o'clock every day.
// * "0 0 9-17 * * MON-FRI" = on the hour nine-to-five weekdays
// * "0 0 0 25 12 ?" = every Christmas Day at midnight