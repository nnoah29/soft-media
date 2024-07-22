package com.softit.smedia.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.softit.smedia.model.ConfigData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;

import static com.softit.smedia.utils.ConstantGlobal.getEnvProp;

public class ConfigDataControleur {
    private static final Logger log = LoggerFactory.getLogger(ConfigDataControleur.class);
    public static ConfigData configData;

    public static ConfigDataControleur configDataControleur;

    public ConfigDataControleur() {
    }

    public static ConfigDataControleur getConfigDataControleur() {
        return configDataControleur;
    }

    public static void setConfigDataControleur(ConfigDataControleur configDataControleur) {
        ConfigDataControleur.configDataControleur = configDataControleur;
    }

    public static ConfigData getConfigData() {
        return configData;
    }

    public static void setConfigData(ConfigData configData) {
        ConfigDataControleur.configData = configData;
    }


    public ConfigData saveConfig(ConfigData configData) {
        String outFile = getEnvProp("confFile");
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
//        options.setSplitLines(false);
        Yaml yaml = new Yaml(options);
        File file = new File(outFile);
        BufferedWriter out;
        try {
            out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(outFile), "UTF-8"
            ));
            yaml.dump(configData, out);
            log.info("save Config yaml ");
        } catch (IOException e) {
            //e.printStackTrace();
            log.error("Error to save Config yaml  : {} {}", e.getMessage());
        }
        return configData;
    }


    public static ConfigData loadConfDataFile() {
        String outFile = getEnvProp("confFile");
        ConfigData d = new ConfigData();
        log.info("start loading... the config file  : {} ", outFile);
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(outFile), "UTF8"));
            d = mapper.readValue(in, ConfigData.class);

//          setConfigData(d);
//            log.info("config file loaded successfully  : {} ", d);
        } catch (IOException e) {
            log.info("Error loading config file : {} ", e.getMessage());
            //  e.printStackTrace();
        }
        setConfigData(d);

        return d;
    }
}
