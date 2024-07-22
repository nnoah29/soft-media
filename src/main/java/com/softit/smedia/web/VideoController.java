package com.softit.smedia.web;

import com.softit.smedia.controller.ConfigDataControleur;
import com.softit.smedia.model.ConfigData;
import com.softit.smedia.model.PlayList;
import com.softit.smedia.utils.ScheduledTasks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import static com.softit.smedia.controller.ConfigDataControleur.getConfigData;
import static com.softit.smedia.controller.ConfigDataControleur.setConfigData;
import static com.softit.smedia.controller.PlayListController.getPlayList;
import static com.softit.smedia.utils.ConstantGlobal.getEnvProp;

@Controller
public class VideoController {
    @Autowired
    private SimpMessagingTemplate brokerMessagingTemplate;

    @RequestMapping("videos/**")
    public String videoList(Map<String, Object> model) throws FileNotFoundException {
        ConfigData conf = ConfigDataControleur.getConfigData();
        String volume;
        try {
            volume = getEnvProp("volume");
//            stringname.equalsIgnoreCase(null);
        } catch (NullPointerException npe) {
            volume = "0.2";
        }

        if (volume == null) {
            volume = "0.2";
        }

        System.out.println(volume + conf.getRefleshFluxRss());
        System.out.println("Flux time " + conf.getRefleshFluxRss());

        /*if(volume = null){
            volume = 1;
        }*/
        model.put("volume", Double.valueOf(getProprietie("volume", "2")) / 10);
        model.put("hours_open_day", getProprietie("hours_open_day", "8"));
        model.put("hours_close_day", getProprietie("hours_close_day", "18"));
        model.put("hours_open_week", getProprietie("hours_open_week", "9"));
        model.put("hours_close_week", getProprietie("hours_close_week", "13"));
        model.put("ipBorne", getProprietie("ipBorne", "192.168.0.11"));
        model.put("timer_check", getProprietie("timer_check", "1"));
        model.put("fluxReflesh", conf.getRefleshFluxRss());
        model.put("activeFluxRss", conf.isActiveFluxRss());
        return "video";
    }

    @RequestMapping("/")
    public String index(Map<String, Object> model) throws FileNotFoundException {
        ConfigData conf = ConfigDataControleur.getConfigData();
        String volume;
        String position;
        String font_size;
        try {
            volume = getEnvProp("volume");
        } catch (NullPointerException npe) {
            volume = "0.2";
        }

        if (volume == null) {
            volume = "0.2";
        }

        System.out.println(volume);
        System.out.println("Flux time " + conf.getRefleshFluxRss());

        /*if(volume = null){
            volume = 1;
        }*/
        String banner_position = getProprietie("banner_font_size", "bas");
        if (banner_position.equals("haut")) {
            banner_position = "none";
        } else {
            banner_position = "10 px";
        }
        Map<String, String> map = new HashMap<>();
        map.put("volume", volume);
        PlayList pl = getPlayList();
        String banner = pl.getBanner();
        if (banner.isEmpty()) banner = "SOYEZ LES BIENVENUS";
        map.put("banner", banner);
//        map.put("banner", banner);
        model.put("volume", Double.valueOf(getProprietie("volume", "2")) / 10);
        model.put("banner_font_size", getProprietie("banner_font_size", "50"));
        model.put("banner_position", banner_position);
//        model.put("volume", volume);
        model.put("banner", banner);
        model.put("fluxReflesh", conf.getRefleshFluxRss());
        model.put("bannerColor", conf.getBannerColor());
        model.put("bannerTextColor", conf.getBannerTextColor());
        model.put("activeFluxRss", conf.isActiveFluxRss());
        return "index";
    }

    public String getProprietie(String value, String defaults) {

        try {
            value = getEnvProp(value);
        } catch (NullPointerException npe) {
            value = defaults;
        }

        if (value == null) {
            value = value;
        }
        return value;
    }


    @RequestMapping(value = "/config", method = RequestMethod.GET)
    public String config(Model model) {

        model.addAttribute("conf", getConfigData());
//        model.addAttribute("check", getConfigData().getCheckPlayListTime());
        return "config";
    }

    @RequestMapping(value = "/config", method = RequestMethod.POST)
    public String saveConfig(@Valid @ModelAttribute("conf") ConfigData form, BindingResult bindingResult) {

        ConfigDataControleur configDataControleur = new ConfigDataControleur();
        ConfigData configData = configDataControleur.saveConfig(form);
        setConfigData(configData);

        if (configData != null) {
            ScheduledTasks scheduledTasks = new ScheduledTasks();
            scheduledTasks.reSchedule();
            brokerMessagingTemplate.convertAndSend("/topic/confreload", "new config");
            return "redirect:/config?success";
        } else {
            return "config";
        }
    }


}
