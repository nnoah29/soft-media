package com.softit.smedia.model;

import lombok.Data;

import java.util.List;

@Data
public class PlayList {
    private Long idPl;
    private String nomPlaylist;
    private String dateAjoutPlaylist;
    private String datePassagePlaylist;
    private String heurePassagePlaylist;
    private String nomAgence;
    private String zone;
    private String banner;
    List<MediaListe> mediaListe;


}
