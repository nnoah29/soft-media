package com.softit.smedia.web;

import com.softit.smedia.model.PlayList;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.softit.smedia.controller.PlayListController.getPlayList;

@RestController
@CrossOrigin
public class BannerRessource {
    // rest qui fournir le banner
    @GetMapping("banner")
    public String banner() {
       /* PlayList ps = new PlayList();
        ps.setBanner("Test");
        new PlayListController().setPlayList(ps);*/
        PlayList pl = getPlayList();
        String banner = pl.getBanner();
        if (banner.isEmpty()) banner = "SOYEZ LES BIENVENUES";
        return banner;
    }
}
