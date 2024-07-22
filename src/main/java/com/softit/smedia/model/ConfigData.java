package com.softit.smedia.model;

import java.util.Objects;

public class ConfigData {

    private String urlServeurMedia;
    private String subUrlServeurMedia;
    private String baseDirMedia;
    private String tmpDirMedia;
    private String bkpDirMedia;
    private String playlistjsongloabal;
    private String fluxjson;
    private String idAgence;
    private String serverDir;
    private String delecteBackupDirTime;
    private String checkPlayListTime;
    private String refleshFluxRss;
    private String ipBorne;
    private String optionTimer;
    private String bannerColor;
    private String bannerTextColor;
    private boolean activeFluxRss;
    private double volume;
    private int timer_borne_check;

    public ConfigData() {
    }

    public ConfigData(String urlServeurMedia, String subUrlServeurMedia, String baseDirMedia, String tmpDirMedia, String bkpDirMedia, String playlistjsongloabal, String fluxjson, String idAgence, String serverDir, String delecteBackupDirTime, String checkPlayListTime, String ipBorne, int volume, int timer_borne_check) {
        this.urlServeurMedia = urlServeurMedia;
        this.subUrlServeurMedia = subUrlServeurMedia;
        this.baseDirMedia = baseDirMedia;
        this.tmpDirMedia = tmpDirMedia;
        this.bkpDirMedia = bkpDirMedia;
        this.playlistjsongloabal = playlistjsongloabal;
        this.fluxjson = fluxjson;
        this.idAgence = idAgence;
        this.serverDir = serverDir;
        this.delecteBackupDirTime = delecteBackupDirTime;
        this.checkPlayListTime = checkPlayListTime;
        this.ipBorne = ipBorne;
        this.volume = volume;
        this.timer_borne_check = timer_borne_check;
    }

    public ConfigData(String urlServeurMedia, String subUrlServeurMedia, String baseDirMedia, String tmpDirMedia, String bkpDirMedia, String playlistjsongloabal, String fluxjson, String idAgence, String serverDir, String delecteBackupDirTime, String checkPlayListTime, String refleshFluxRss, String ipBorne, String optionTimer, String bannerColor, String bannerTextColor, boolean activeFluxRss, double volume, int timer_borne_check) {
        this.urlServeurMedia = urlServeurMedia;
        this.subUrlServeurMedia = subUrlServeurMedia;
        this.baseDirMedia = baseDirMedia;
        this.tmpDirMedia = tmpDirMedia;
        this.bkpDirMedia = bkpDirMedia;
        this.playlistjsongloabal = playlistjsongloabal;
        this.fluxjson = fluxjson;
        this.idAgence = idAgence;
        this.serverDir = serverDir;
        this.delecteBackupDirTime = delecteBackupDirTime;
        this.checkPlayListTime = checkPlayListTime;
        this.refleshFluxRss = refleshFluxRss;
        this.ipBorne = ipBorne;
        this.optionTimer = optionTimer;
        this.bannerColor = bannerColor;
        this.bannerTextColor = bannerTextColor;
        this.activeFluxRss = activeFluxRss;
        this.volume = volume;
        this.timer_borne_check = timer_borne_check;
    }

    public ConfigData(String urlServeurMedia, String subUrlServeurMedia, String baseDirMedia, String tmpDirMedia, String bkpDirMedia, String playlistjsongloabal, String fluxjson, String idAgence, String serverDir, String delecteBackupDirTime, String checkPlayListTime, String refleshFluxRss, String ipBorne, double volume, int timer_borne_check) {
        this.urlServeurMedia = urlServeurMedia;
        this.subUrlServeurMedia = subUrlServeurMedia;
        this.baseDirMedia = baseDirMedia;
        this.tmpDirMedia = tmpDirMedia;
        this.bkpDirMedia = bkpDirMedia;
        this.playlistjsongloabal = playlistjsongloabal;
        this.fluxjson = fluxjson;
        this.idAgence = idAgence;
        this.serverDir = serverDir;
        this.delecteBackupDirTime = delecteBackupDirTime;
        this.checkPlayListTime = checkPlayListTime;
        this.refleshFluxRss = refleshFluxRss;
        this.ipBorne = ipBorne;
        this.volume = volume;
        this.timer_borne_check = timer_borne_check;
    }

    public boolean isActiveFluxRss() {
        return activeFluxRss;
    }

    public void setActiveFluxRss(boolean activeFluxRss) {
        this.activeFluxRss = activeFluxRss;
    }

    public String getBannerColor() {
        return bannerColor;
    }

    public void setBannerColor(String bannerColor) {
        this.bannerColor = bannerColor;
    }

    public String getBannerTextColor() {
        return bannerTextColor;
    }

    public void setBannerTextColor(String bannerTextColor) {
        this.bannerTextColor = bannerTextColor;
    }

    public String getOptionTimer() {
        return optionTimer;
    }

    public void setOptionTimer(String optionTimer) {
        this.optionTimer = optionTimer;
    }

    public String getRefleshFluxRss() {
        return refleshFluxRss;
    }

    public String getRefleshFluxRssVal() {
        return "0 */{v} * ? * *".replace("{v}", refleshFluxRss);
    }

    public void setRefleshFluxRss(String refleshFluxRss) {
        this.refleshFluxRss = refleshFluxRss;
    }

    public String getUrlServeurMedia() {
        return urlServeurMedia;
    }

    public void setUrlServeurMedia(String urlServeurMedia) {
        this.urlServeurMedia = urlServeurMedia;
    }

    public String getSubUrlServeurMedia() {
        return subUrlServeurMedia;
    }

    public void setSubUrlServeurMedia(String subUrlServeurMedia) {
        this.subUrlServeurMedia = subUrlServeurMedia;
    }

    public String getBaseDirMedia() {
        return baseDirMedia;
    }

    public void setBaseDirMedia(String baseDirMedia) {
        this.baseDirMedia = baseDirMedia;
    }

    public String getTmpDirMedia() {
        return tmpDirMedia;
    }

    public void setTmpDirMedia(String tmpDirMedia) {
        this.tmpDirMedia = tmpDirMedia;
    }

    public String getBkpDirMedia() {
        return bkpDirMedia;
    }

    public void setBkpDirMedia(String bkpDirMedia) {
        this.bkpDirMedia = bkpDirMedia;
    }

    public String getPlaylistjsongloabal() {
        return playlistjsongloabal;
    }

    public void setPlaylistjsongloabal(String playlistjsongloabal) {
        this.playlistjsongloabal = playlistjsongloabal;
    }

    public String getFluxjson() {
        return fluxjson;
    }

    public void setFluxjson(String fluxjson) {
        this.fluxjson = fluxjson;
    }

    public String getIdAgence() {
        return idAgence;
    }

    public void setIdAgence(String idAgence) {
        this.idAgence = idAgence;
    }

    public String getServerDir() {
        return serverDir;
    }

    public void setServerDir(String serverDir) {
        this.serverDir = serverDir;
    }

    public String getDelecteBackupDirTime() {
        return delecteBackupDirTime;
//        return "0 0 {bk} * *?".replace("{bk}",delecteBackupDirTime) ;
    }

    public String getDelecteBackupDirTimeVal() {
        return "0 0 {v} * * ?";
        //return "0 0 {bk} * *?".replace("{bk}",delecteBackupDirTime) ;
    }

    public void setDelecteBackupDirTime(String delecteBackupDirTime) {
        this.delecteBackupDirTime = delecteBackupDirTime;
    }

    public String getCheckPlayListTime() {
        return checkPlayListTime;
        //return "0 0 {pl} * *?".replace("{pl}",checkPlayListTime);
    }

    public String getCheckPlayListTimeVal() {
        if (optionTimer == "heures") {

            return "0 0 {v} * * ?".replace("{v}", "" + Integer.parseInt(checkPlayListTime));
        } else

            return "0 */{v} * ? * *".replace("{v}", "" + Integer.parseInt(checkPlayListTime));


        //return "0 0 {pl} * *?".replace("{pl}",checkPlayListTime);
    }

    public void setCheckPlayListTime(String checkPlayListTime) {
        this.checkPlayListTime = checkPlayListTime;
    }

    public String getIpBorne() {
        return ipBorne;
    }

    public void setIpBorne(String ipBorne) {
        this.ipBorne = ipBorne;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public int getTimer_borne_check() {
        return timer_borne_check;
    }

    public void setTimer_borne_check(int timer_borne_check) {
        this.timer_borne_check = timer_borne_check;
    }

    @Override
    public String toString() {
        return "ConfigData{" +
                "urlServeurMedia='" + urlServeurMedia + '\'' +
                ", subUrlServeurMedia='" + subUrlServeurMedia + '\'' +
                ", baseDirMedia='" + baseDirMedia + '\'' +
                ", tmpDirMedia='" + tmpDirMedia + '\'' +
                ", bkpDirMedia='" + bkpDirMedia + '\'' +
                ", playlistjsongloabal='" + playlistjsongloabal + '\'' +
                ", fluxjson='" + fluxjson + '\'' +
                ", idAgence='" + idAgence + '\'' +
                ", serverDir='" + serverDir + '\'' +
                ", delecteBackupDirTime='" + delecteBackupDirTime + '\'' +
                ", checkPlayListTime='" + checkPlayListTime + '\'' +
                ", ipBorne='" + ipBorne + '\'' +
                ", volume=" + volume +
                ", timer_borne_check=" + timer_borne_check +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConfigData)) return false;
        ConfigData that = (ConfigData) o;
        return getVolume() == that.getVolume() &&
                getTimer_borne_check() == that.getTimer_borne_check() &&
                Objects.equals(getUrlServeurMedia(), that.getUrlServeurMedia()) &&
                Objects.equals(getSubUrlServeurMedia(), that.getSubUrlServeurMedia()) &&
                Objects.equals(getBaseDirMedia(), that.getBaseDirMedia()) &&
                Objects.equals(getTmpDirMedia(), that.getTmpDirMedia()) &&
                Objects.equals(getBkpDirMedia(), that.getBkpDirMedia()) &&
                Objects.equals(getPlaylistjsongloabal(), that.getPlaylistjsongloabal()) &&
                Objects.equals(getFluxjson(), that.getFluxjson()) &&
                Objects.equals(getIdAgence(), that.getIdAgence()) &&
                Objects.equals(getServerDir(), that.getServerDir()) &&
                Objects.equals(getDelecteBackupDirTime(), that.getDelecteBackupDirTime()) &&
                Objects.equals(getCheckPlayListTime(), that.getCheckPlayListTime()) &&
                Objects.equals(getIpBorne(), that.getIpBorne());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getUrlServeurMedia(), getSubUrlServeurMedia(), getBaseDirMedia(), getTmpDirMedia(), getBkpDirMedia(), getPlaylistjsongloabal(), getFluxjson(), getIdAgence(), getServerDir(), getDelecteBackupDirTime(), getCheckPlayListTime(), getIpBorne(), getVolume(), getTimer_borne_check());
    }
}
