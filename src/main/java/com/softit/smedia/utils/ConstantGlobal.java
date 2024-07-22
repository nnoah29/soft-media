package com.softit.smedia.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.softit.smedia.controller.FluxRssControlleur;
import com.softit.smedia.controller.PlayListController;
import com.softit.smedia.model.ConfigData;
import com.softit.smedia.model.FluxRss;
import com.softit.smedia.model.PlayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

import static com.softit.smedia.controller.ConfigDataControleur.getConfigData;
import static com.softit.smedia.controller.ConfigDataControleur.loadConfDataFile;

@Component
public class ConstantGlobal {
    //    public static final String VIDEO_ROOT = "media/";

    private static final Logger logger = LoggerFactory.getLogger(ConstantGlobal.class);
    protected static Environment env;

    @Autowired
    public void setEnv(Environment _env) {
        env = _env;

    }

    public static String getEnvProp(String prop) {
        ConfigData conf = getConfigData();
        String property = "";
        if (prop.equals("urlServeurMedia")) {
            property = conf.getUrlServeurMedia();
        } else if (prop.equals("idAgence")) {
            property = conf.getIdAgence();
        } else if (prop.equals("volume")) {
            property = "" + conf.getVolume();
        } else {
            property = env.getProperty(prop);
        }

        return property;
    }

    public static int getHttpPort() {
        return Integer.parseInt(getEnvProp("server.http.port"));
    }

    /**
     * lire les fichier au demarrage , playlist et flux
     */
    public static void loadFromFile() {
        System.out.println(getEnvProp("smrest.receipt_media"));
        String file = getEnvProp("baseDirMedia") + getEnvProp("playlistjsongloabal") + ".json";
        String fluxFile = getEnvProp("baseDirMedia") + getEnvProp("fluxjson") + ".json";
        PlayList d = new PlayList();
        FluxRss fluxRss = new FluxRss();

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        try {
            d = mapper.readValue(new File(file), PlayList.class);
            fluxRss = mapper.readValue(new File(fluxFile), FluxRss.class);


            new PlayListController().setPlayList(d);
            FluxRssControlleur.setFluxRss(fluxRss);
            logger.info("Succes to load config yaml file  : {} {} + flux File {} ", "===>", d, fluxRss);


        } catch (IOException e) {
            logger.info("Error to load global yaml file  : {} {} flux {} ", "===>", d, fluxRss);
            // e.printStackTrace();
        }

    }


    public static PlayList getOldPlayList() {
        System.out.println(getEnvProp("smrest.receipt_media"));
        String file = getEnvProp("baseDirMedia") + getEnvProp("playlistjsongloabal") + ".json";
        PlayList d = new PlayList();

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        try {
            d = mapper.readValue(new File(file), PlayList.class);

            //new PlayListController().setPlayList(d);
            logger.info("Succes to load old config yaml file  : {} {}", "===>", d);


        } catch (IOException e) {
            logger.info("Error to load old global yaml file  : {} {}", "===>", d);
            // e.printStackTrace();
        }
        return d;
    }

    @PostConstruct
    void read() {
        loadFromFile();
        loadConfDataFile();


    }

    public static String receptPlayListUrl(Long idPl, int statut, String dateDebut, String dateFin) {
        String url = getEnvProp("urlServeurMedia") + "/" + getEnvProp("serverDir") + getEnvProp("smrest.receipt_playlist")
                .replace("{idplaylist}", "" + idPl)
                .replace("{idAgence}", getEnvProp("idAgence"))
                .replace("{status}", "" + statut)
                .replace("{dateDebut}", dateDebut)
                .replace("{dateFin}", dateFin);
        return url;
    }

    public static String receptMediaUrl(Long idPl, int statut, String dateDebut, String dateFin, String fileName) {
        String url = getEnvProp("urlServeurMedia") + "/" + getEnvProp("serverDir") + getEnvProp("smrest.receipt_media")
                .replace("{idplaylist}", "" + idPl)
                .replace("{idAgence}", getEnvProp("idAgence"))
                .replace("{status}", "" + statut)
                .replace("{dateDebut}", dateDebut)
                .replace("{dateFin}", dateFin)
                .replace("{fileName}", fileName);
        return url;
    }
}
