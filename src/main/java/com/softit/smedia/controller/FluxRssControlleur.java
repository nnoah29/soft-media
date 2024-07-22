package com.softit.smedia.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.softit.smedia.model.FluxRss;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;

import static com.softit.smedia.utils.ConstantGlobal.getEnvProp;
import static com.softit.smedia.utils.RestClientConfig.getSSLAth;

public class FluxRssControlleur {
    private static final Logger log = LoggerFactory.getLogger(FluxRssControlleur.class);
    public static FluxRss fluxRss;

    public FluxRss getFluxFromServe() {
        String requet = getEnvProp("urlServeurMedia") + "/" + getEnvProp("serverDir") + getEnvProp("smrest.fluxRss");
//        log.info("Requet get flux {} ", requet);
        String response = null;
        RestTemplate restTemplate = new RestTemplate(getSSLAth());
        try {
//            log.info("Send request ....");
            response = restTemplate.getForObject(requet, String.class);
//            log.info("Response : {} ", response);

        } catch (Exception ex) {
            log.info("Error to get flux {} ", ex.getMessage());
            return FluxRssControlleur.getFluxRss();

        }

        return mapToFluxRss(response);
    }


    public FluxRss mapToFluxRss(String flux) {
        FluxRss fluxRss = new FluxRss();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        try {
//            log.info("Extra Flux rss ");
            fluxRss = mapper.readValue(flux, FluxRss.class);
//            log.info("FluxRss : {} ", fluxRss);
        } catch (IOException e) {
            log.info("Error to Extra FluxRss {}", e.getMessage());

        }
        genJsonFlux(fluxRss);
        setFluxRss(fluxRss);
        return fluxRss;
    }


    public static void genJsonFlux(FluxRss fluxRss) {
//        log.info("Can generate {}.json file", getEnvProp("fluxjson"));


        ObjectMapper mapper = new ObjectMapper();
        try {

//            mapper.writeValue(new File(getEnvProp("baseDirMedia") + getEnvProp("playlistjsonmedia") + ".json"), map);
            mapper.writeValue(new File(getEnvProp("baseDirMedia") + getEnvProp("fluxjson") + ".json"), fluxRss);
//            log.info("Succes to generate {}.json file", getEnvProp("fluxjson"));
        } catch (IOException e) {
            log.info("Error to  generate {}.json file {} ", getEnvProp("fluxjson"), e.getMessage());
        }
    }

    public static FluxRss getFluxRss() {
        return fluxRss;
    }

    public static void setFluxRss(FluxRss fluxRss) {
        FluxRssControlleur.fluxRss = fluxRss;
    }
}
