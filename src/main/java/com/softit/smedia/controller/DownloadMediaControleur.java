package com.softit.smedia.controller;

import com.softit.smedia.model.PlayList;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import static com.softit.smedia.utils.ConstantGlobal.getEnvProp;
import static com.softit.smedia.utils.ConstantGlobal.receptMediaUrl;
import static com.softit.smedia.utils.RestClientConfig.getSSLAth;

public class DownloadMediaControleur {
    private static final Logger log = LoggerFactory.getLogger(DownloadMediaControleur.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm");


    /**
     * methode pour telecharger les video depuis le serveur
     *
     * @param fileName le nom des ficher
     * @throws IOException
     */
    public void fetchFile(String fileName) throws IOException {
        log.info("Cant download file : {} ", fileName);
        long startTime = System.currentTimeMillis();
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new ByteArrayHttpMessageConverter());

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));

        HttpEntity<String> entity = new HttpEntity<String>(headers);
        try {

            ResponseEntity<byte[]> response = restTemplate.exchange(getEnvProp("urlServeurMedia") + getEnvProp("subUrlServeurMedia") + fileName,
                    HttpMethod.GET, entity, byte[].class, "1");
            // verifier si le dossier temporeur existe
            File f1 = new File(getEnvProp("tmpDirMedia"));
            if (!f1.exists()) f1.mkdir();
            File f2 = new File(getEnvProp("baseDirMedia"));
            if (!f2.exists()) f2.mkdir();
            if (response.getStatusCode() == HttpStatus.OK) {
                System.out.println("download  ... " + response.getBody());
                Path p = Files.write(Paths.get(getEnvProp("tmpDirMedia") + fileName), response.getBody());
                long endTime = System.currentTimeMillis();
                long totalTime = endTime - startTime;
                System.out.println("Fille name " + fileName + " Size : " + p.toFile().length() / 1024 + " ko time  : " + totalTime / 1000 + " s");
//                log.info("Succes to download {} Size: {} ko time {} s ", fileName, p.toFile().length() / 1024, totalTime / 1000);
            }
        } catch (Exception e) {
            log.info("Error to download fille : {} ", fileName);
        }
    }

    public void downloadFile(String fileName, PlayList pl) throws IOException {
//        log.info("Cant download file : {} ", fileName);
        long startTime = System.currentTimeMillis();
        String debut = dateFormat.format(new Date());
        RestTemplate restTemplate = new RestTemplate(getSSLAth());
        try {
// Optional Accept header
            RequestCallback requestCallback = request -> request.getHeaders()
                    .setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM, MediaType.ALL));

// Streams the response instead of loading it all in memory
            ResponseExtractor<Void> responseExtractor = response -> {
                // Here I write the response to a file but do what you like
                Path p = Paths.get(getEnvProp("tmpDirMedia") + fileName);
                System.out.println("extration byte  " + response);
                Files.copy(response.getBody(), p);
                long endTime = System.currentTimeMillis();
                long totalTime = endTime - startTime;
//                System.out.println("Fille name " + fileName + " Size : " + (p.toFile().length() / 1024) / 1024 + " ko time  : " + totalTime / 1000 + " s");
                log.info("Succes to download {} Size: {} Mo time {} s ", fileName, (p.toFile().length() / 1024) / 1024, totalTime / 1000);
                String fin = dateFormat.format(new Date());
//              String url = getEnvProp("urlServeurMedia") + "/" + getEnvProp("serverDir") + "/rest/RestController.php?view=receipt_media&idPl=" + pl.getIdPl() + "&idAgence=" + getEnvProp("idAgence") + "&telecharge=1&dateFin=" + fin + "&dateDebut=" + debut + "&guidMedia=" + fileName;
                String url = receptMediaUrl(pl.getIdPl(), 1, debut, fin, fileName);
                // Get get = Http.get(url);
                String goBack = restTemplate.getForObject(url, String.class);
//                log.info("Notifie succes video download  {} result {} ", url, goBack);
                return null;
            };
            restTemplate.execute(URI.create(getEnvProp("urlServeurMedia") + getEnvProp("subUrlServeurMedia") + fileName), HttpMethod.GET, requestCallback, responseExtractor);

        } catch (Exception e) {
            log.info("Error to download fille : {} url: {}  {} ", fileName, getEnvProp("urlServeurMedia") + getEnvProp("subUrlServeurMedia") + fileName, e);
            String fin = dateFormat.format(new Date());
            String url = receptMediaUrl(pl.getIdPl(), -1, debut, fin, fileName);

//            String url = getEnvProp("urlServeurMedia") + "/" + getEnvProp("serverDir") + "/rest/RestController.php?view=receipt_media&idPl=" + pl.getIdPl() + "&idAgence=" + getEnvProp("idAgence") + "&telecharge=-1&dateFin=" + fin + "&dateDebut=" + debut + "&guidMedia=" + fileName;
//            Get get = Http.get(url);
            String goBack = restTemplate.getForObject(url, String.class);
//            log.info("Notifie succes video download {} ", url, goBack);
        }
    }

//    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    public void backupAndMoveToMedia() {
        // creer ficher media
        File media = new File(getEnvProp("baseDirMedia"));
        System.out.println("absolut  " + media.getAbsolutePath());
        File newName = new File(getEnvProp("bkpDirMedia") + dateFormat.format(new Date()).replaceAll(":", "_"));
        newName.mkdir();
        try {
            // parcourire le dossier media
            log.info("Move file from media to backup dir");
            for (File listmedia : media.listFiles()) {
                // deplacer les ficher du media dans le backcup
                FileUtils.moveFileToDirectory(listmedia.getAbsoluteFile(), newName, true);
            }
        } catch (IOException e) {
            log.info("Error on move file to backup dir");
            System.err.println("Error  " + e.getMessage());
        }
        // dossier tmp
        File tmp = new File(getEnvProp("tmpDirMedia"));

        try {
            // parcourire le tmp
//            log.info("Move file from tmp to media dir");
            if (!tmp.exists()) tmp.mkdir();
            for (File listmedia : tmp.listFiles()) {
                FileUtils.moveFileToDirectory(listmedia.getAbsoluteFile(), media, true);
            }
            System.out.println("sucess");
        } catch (IOException e) {
            log.info("Error on move file to media dir {}", e.getMessage());
//            System.err.println("Error  " + e.getMessage());
        }


    }

    public void backupAndCopyToMedia() {
        // creer ficher media
        File media = new File(getEnvProp("baseDirMedia"));
        System.out.println("absolut  " + media.getAbsolutePath());
        File newName = new File(getEnvProp("bkpDirMedia") + dateFormat.format(new Date()).replaceAll(":", "_"));
        newName.mkdir();
        try {
            // parcourire le dossier media
//            log.info("Move file from media to backup dir");
            for (File listmedia : media.listFiles()) {
                // deplacer les ficher du media dans le backcup
                FileUtils.copyFileToDirectory(listmedia.getAbsoluteFile(), newName, true);
            }
        } catch (IOException e) {
            log.info("Error on move file to backup dir");
//            System.err.println("Error  " + e.getMessage());
        }
        // dossier tmp
      /*  File tmp = new File(getEnvProp("tmpDirMedia"));

        try {
            // parcourire le tmp
            log.info("Move file from tmp to media dir");
            if (!tmp.exists()) tmp.mkdir();
            for (File listmedia : tmp.listFiles()) {
                FileUtils.copyFileToDirectory(listmedia.getAbsoluteFile(), media, true);
            }
            System.out.println("sucess");
        } catch (IOException e) {
            log.info("Error on move file to media dir");
            System.err.println("Error  " + e.getMessage());
        }*/


    }


}
