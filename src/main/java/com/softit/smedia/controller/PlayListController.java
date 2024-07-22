package com.softit.smedia.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.softit.smedia.model.MediaListe;
import com.softit.smedia.model.PlayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.softit.smedia.utils.ConstantGlobal.*;
import static com.softit.smedia.utils.RestClientConfig.getSSLAth;

public class PlayListController {
    private static final Logger log = LoggerFactory.getLogger(PlayListController.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm");
    static PlayList playList;

    public void setPlayList(PlayList playList) {
        this.playList = playList;
    }

    public static PlayList getPlayList() {
        return playList;
    }

    /**
     * Recuperer une playList depuis le serveur
     *
     * @param idPl
     * @return
     */
    public PlayList getPlayListFromServer(int idPl) {
        PlayList playList = null;
//        String requet = getEnvProp("urlServeurMedia") + "/" + getEnvProp("serverDir") + "/rest/RestController.php?view=display&idPl=" + idPl + "&idAgence=" + getEnvProp("idAgence");
        String requet = getEnvProp("urlServeurMedia") + "/" + getEnvProp("serverDir") + getEnvProp("smrest.getallplaylist")
                .replace("{idplaylist}", "" + idPl)
                .replace("{idAgence}", getEnvProp("idAgence"));
        // Get get = Http.get(requet);
        RestTemplate restTemplate = new RestTemplate(getSSLAth());
        String response = null;
        try {
            response = restTemplate.getForObject(requet, String.class);
            // response = get.text();
            System.out.println("playList " + response);
            playList = mapToPlayListe(response);
            if (!playList.getMediaListe().isEmpty()) {
                downloadMedia(playList);
                setPlayList(playList);
            } else {
                updatePlaylist(playList);
            }

        } catch (Exception e) {
            log.info("Problem de  connexion getPlayLis  " + e);
        }
//        String response = get.text();


        return playList;
    }

    /**
     * Metre ajour la playlist avec les anciens video
     *
     * @param playList
     */

    public void updatePlaylist(PlayList playList) {
//        log.info("Update playlist  " + playList);
        PlayList old = getOldPlayList();
//        log.info("old playlist : {}  ", old);
        playList.setMediaListe(old.getMediaListe());
        log.info("new playlist : {}  ", playList);
        new DownloadMediaControleur().backupAndCopyToMedia();
        setPlayList(playList);
        genJsonFile(playList);
    }

    /**
     * convertir le string en playlist
     *
     * @param pl
     * @return
     */

    private PlayList mapToPlayListe(String pl) {
        PlayList playList = new PlayList();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
//            log.info("Extra playList ");
            playList = mapper.readValue(pl, PlayList.class);
//            log.info("PlayList : {} ", playList);
        } catch (IOException e) {
            log.info("Error to Extra playList {}", e.getMessage());
        }
        return playList;
    }

    /**
     * generer le ficher json des video lui par la borne
     *
     * @param playList
     */
    public static void genJsonFile(PlayList playList) {
//        log.info("Can generate {}.json file", getEnvProp("playlistjsongloabal"));
        Map<String, Object> map = new HashMap<>();
        List<String> videoList = new ArrayList<>();
        List<MediaListe> mediaListes = playList.getMediaListe();
        mediaListes.forEach(mediaListe -> videoList.add(mediaListe.getFileName()));
        map.put("videos", videoList);
        ObjectMapper mapper = new ObjectMapper();
        try {

//            mapper.writeValue(new File(getEnvProp("baseDirMedia") + getEnvProp("playlistjsonmedia") + ".json"), map);
            mapper.writeValue(new File(getEnvProp("baseDirMedia") + getEnvProp("playlistjsongloabal") + ".json"), playList);
            log.info("Succes to generate {}.json file", getEnvProp("playlistjsongloabal"));
        } catch (IOException e) {
            log.info("Error to  generate {}.json file {} ", getEnvProp("playlistjsongloabal"), e.getMessage());
        }
    }

    /**
     * chercher la disponibilité d'une nouvelle playlist
     *
     * @param idPl
     * @return
     */

    public String checkNewPlayList(int idPl) {
        // url de checking
        String requet = getEnvProp("urlServeurMedia") + "/" + getEnvProp("serverDir") + getEnvProp("smrest.verify")
                .replace("{idplaylist}", "" + idPl)
                .replace("{idAgence}", getEnvProp("idAgence"));
//        String requet = getEnvProp("urlServeurMedia") + "/"+getEnvProp("serverDir)"+/rest/RestController.php?view=verify&idPl=" + idPl + "&idAgence=" + getEnvProp("idAgence");
        String response = null;
        RestTemplate restTemplate = new RestTemplate(getSSLAth());

        //log.debug(record.toString());
        /*RestTemplate restTemplate = new RestTemplate();
        String quote = restTemplate.getForObject(requet, String.class);*/
        // System.out.println(record);
        System.out.println(".********************************************************.");

//        Get get = Http.get(requet);
        System.out.println(requet);

        try {
            response = restTemplate.getForObject(requet, String.class);
            //response = get.text();
            //System.out.println(response);
            System.out.println(response);
//            log.info("Respone checking new PlayList");
            Map<String, Object> map;
            Map<String, String> map2;
            ObjectMapper mapper = new ObjectMapper();

            // convertir en map
            map = mapper.readValue(response, Map.class);
            System.out.println("map " + map);
            // ResponseStatut statut  = mapper.readValue(map.get("response").toString(), ResponseStatut.class);;
            map2 = (Map<String, String>) map.get("response");
            System.out.println("map 2 stat " + Integer.parseInt(map2.get("statut").toString()));
            // traitement
//            String rs = map2.get("statut");
            if (Integer.parseInt(map2.get("statut")) == 1) {
//            if ( Integer.parseInt(map2.get("statut")) == 1 ) {
//                log.info("New playList availiable");
                getPlayListFromServer(Integer.parseInt(map2.get("idPl")));
            } else {
                log.info("No playList availiable ");
            }

//                log.info("Error to map response checking {}" + e.getMessage());

        } catch (Exception e) {
            log.info("Problem connexion  {}", e);
        }

        return null;
    }

    public void downloadMedia(PlayList pl) {
        String debut = dateFormat.format(new Date());
        List<MediaListe> mediaListes = pl.getMediaListe();
//        log.info("Start Download");
        final Boolean[] error = {false};
        /*if (mediaListes == null) {
            error.set(true);
        } else {*/


        mediaListes.forEach(mediaListe -> {
            try {
                new DownloadMediaControleur().downloadFile(mediaListe.getFileName(), pl);
            } catch (IOException e) {
                error[0] = true;
                log.info("Error to start download");
                // e.printStackTrace();
            }
        });
        RestTemplate restTemplate = new RestTemplate(getSSLAth());
        String fin = dateFormat.format(new Date());
        if (error[0]) {
//            String url = getEnvProp("urlServeurMedia") + "/" + getEnvProp("serverDir") + "/rest/RestController.php?view=receipt_playlist&idPl=" + pl.getIdPl() + "&idAgence=" + getEnvProp("idAgence") + "&telecharge=-1&dateFin=" + fin + "&dateDebut=" + debut;
            String url = receptPlayListUrl(pl.getIdPl(), -1, debut, fin);
            //Get get = Http.get(url);
            String response = restTemplate.getForObject(url, String.class);
            log.info("Error in playList  {} ", url, response);
        } else {
//            String url = getEnvProp("urlServeurMedia") + "/" + getEnvProp("serverDir") + "/rest/RestController.php?view=receipt_playlist&idPl=" + pl.getIdPl() + "&idAgence=" + getEnvProp("idAgence") + "&telecharge=1&dateFin=" + fin + "&dateDebut=" + debut;
            String url = receptPlayListUrl(pl.getIdPl(), 1, debut, fin);
            String response = restTemplate.getForObject(url, String.class);
            log.info("Succes  playList {} {} ", url, response);
        }

        new DownloadMediaControleur().backupAndMoveToMedia();
        genJsonFile(pl);
        /*} else {
            log.info("Error to download video on playList {} ", error.get());
        }*/

    }
}
