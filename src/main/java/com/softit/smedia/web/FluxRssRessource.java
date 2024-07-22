package com.softit.smedia.web;

import com.softit.smedia.model.FluxRss;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.softit.smedia.controller.FluxRssControlleur.getFluxRss;

@RestController
@RequestMapping("/api")
public class FluxRssRessource {


    @GetMapping("/fluxRss")
    public FluxRss getFlux() {
        return getFluxRss();
    }
}
