package com.softit.smedia.web;

import com.softit.smedia.model.FileNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static com.softit.smedia.controller.PlayListController.getPlayList;
import static com.softit.smedia.utils.ConstantGlobal.getEnvProp;

@RestController
@RequestMapping("media")
public class ListMedia {
    private static final Logger logger = LoggerFactory.getLogger(ListMedia.class);

    @RequestMapping("list2/**")
    @ResponseBody
    public List<FileNode> list2() {
        final List<FileNode> list = new ArrayList<>();
        final String filePath = getEnvProp("baseDirMedia");
        try {
            final File directory = new File(filePath);
            final File[] files = directory.listFiles();
            for (File file : files) {
                String fileName = file.getName();
                if (file.isDirectory())
                    list.add(new FileNode(fileName, false));
                else if (fileName.toLowerCase().matches("^.*\\.mp4$"))
                    list.add(new FileNode(fileName, true));
            }
        } catch (Exception e) {
            logger.error("ERROR DURING FOR LOOP: " + e.getMessage());
            if (e.getCause() != null)
                logger.error("ERROR DURING FOR LOOP: " + e.getCause().getMessage());
        }
//        logger.info("File List Request. List size is " + list.size());
        return list;
    }

    @RequestMapping("list/**")
    @ResponseBody
    public List<String> list() {
        final List<String> list = new ArrayList<>();
        final String filePath = getEnvProp("baseDirMedia");
        getPlayList().getMediaListe().forEach(mediaListe -> {
//            SimpleDateFormat parser = new SimpleDateFormat("HH:mm:ss");
            if (mediaListe.getStartTime() != null || mediaListe.getEndTime() != null) {
                try {
                    LocalTime time = LocalTime.now();
                    LocalTime startTime = LocalTime.parse(mediaListe.getStartTime());
                    LocalTime endTime = LocalTime.parse(mediaListe.getEndTime());
                    System.out.println(startTime + " end " + endTime + " now " + time);
//                Date userDate = parser.parse(Date.from(Instant.now()).toInstant().toString());
//                    logger.info("Add video in list date actuelle {} start {} end {} ", time, startTime, endTime);
                    if (isBetween(time, startTime, endTime)) {
                        list.add(mediaListe.getFileName());
                    }
                } catch (Exception e) {
                    logger.info("Ereur de conversion  de date ");
                    ;
                }

//          String time =  date.getTime();

            } else {
                list.add(mediaListe.getFileName());
            }
        });
        System.out.println(list);
        return list;
    }

    public static boolean isBetween(LocalTime candidate, LocalTime start, LocalTime end) {
        return !candidate.isBefore(start) && !candidate.isAfter(end);  // Inclusive.
    }

    @RequestMapping("video/**")
    public ResponseEntity<InputStreamResource> video(@RequestParam String videoName) throws FileNotFoundException {
        //final String decodeString = decodePath(request, "/video/");
        System.out.println(videoName);
        if (videoName != null || videoName == "undefined") {
            File file = new File(getEnvProp("baseDirMedia") + videoName);
//        logger.info("DOWNLOAD " + file.getAbsolutePath());
            final HttpHeaders headers = new HttpHeaders();
            headers.add("Content-disposition", "attachment; filename=\"" + file.getName() + "\"");
            return ResponseEntity.ok().headers(headers).contentLength(file.length())
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .body(new InputStreamResource(new FileInputStream(file)));
        }
        return (ResponseEntity<InputStreamResource>) ResponseEntity.notFound();
    }
}
