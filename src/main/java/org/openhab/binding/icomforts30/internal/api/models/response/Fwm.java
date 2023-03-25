package org.openhab.binding.icomforts30.internal.api.models.response;

import java.util.ArrayList;

import org.eclipse.jdt.annotation.Nullable;
import org.openhab.binding.icomforts30.internal.api.models.common.CustomTypes.DeviceType;
import org.openhab.binding.icomforts30.internal.api.models.common.CustomTypes.WriteAccess;

import com.google.gson.annotations.SerializedName;

public class Fwm {

    @SerializedName("fwmversiondetails")
    public ArrayList<FWMVersionDetailsList> fwmversiondetails;

    public ArrayList<FWMVersionDetailsList> getFMVVersionDetails() {
        return this.fwmversiondetails;
    }

    public @Nullable FWMVersionDetailsList getFMVVersionDetail(Integer iD) {
        for (FWMVersionDetailsList FMVVersionDetailElement : fwmversiondetails) {
            if (FMVVersionDetailElement.getId() == iD) {
                return FMVVersionDetailElement;
            }

        }
        return null;
    }

    private class FWMVersionDetailsList {

        @SerializedName("major")
        public Integer major;

        @SerializedName("timeOfUpdate")
        public Long timeOfUpdate;

        @SerializedName("description")
        public String description;

        @SerializedName("deviceModel")
        public String deviceModel;

        @SerializedName("build")
        public Integer build;

        @SerializedName("id")
        private Integer id;

        public Integer getId() {
            return this.id;
        }

        @SuppressWarnings("unused")
        public void setId(Integer id) {
            this.id = id;
        }

        @SerializedName("minor")
        public Integer minor;

        @SerializedName("size")
        public Long size;

        @SerializedName("deviceType")
        public DeviceType deviceType;
    }

    @SerializedName("fwmusbupdate")
    public FWMUSBUpdate fwmusbupdate;

    private class FWMUSBUpdate {

        @SerializedName("fw_USB_available")
        public Boolean fw_USB_available;

        @SerializedName("packagedetails")
        public ArrayList<PackageDetails> packagedetails;

        @SuppressWarnings("unused")
        public ArrayList<PackageDetails> getPackageDetails() {
            return this.packagedetails;
        }

        @SuppressWarnings("unused")
        public @Nullable PackageDetails getPackageDetail(Integer iD) {
            for (PackageDetails packageElement : packagedetails) {
                if (packageElement.getId() == iD) {
                    return packageElement;
                }

            }
            return null;
        }

        private class PackageDetails {

            @SerializedName("major")
            public Integer major;

            @SerializedName("description")
            public String description;

            @SerializedName("deviceModel")
            public String deviceModel;

            @SerializedName("build")
            public Integer build;

            @SerializedName("path")
            public String path;

            @SerializedName("id")
            private Integer id;

            public Integer getId() {
                return this.id;
            }

            @SuppressWarnings("unused")
            public void setId(Integer id) {
                this.id = id;
            }

            @SerializedName("minor")
            public Integer minor;

            @SerializedName("size")
            public Integer size;
        }

        @SerializedName("fw_USB_authorized")
        public Boolean fw_USB_authorized;
    }

    @SerializedName("fwminternal")
    public FWMInternal fwminternal;

    private class FWMInternal {

        @SerializedName("stop_dl")
        public Boolean stop_dl;

        @SerializedName("devPend")
        public Integer devPend;
    }

    @SerializedName("fwmalarminfo")
    public AlarmInfo fwmalarminfo;

    private class AlarmInfo {

        @SerializedName("alarminfo")
        public ArrayList<AlarmInfoList> alarminfo;

        @SuppressWarnings("unused")
        public ArrayList<AlarmInfoList> getAlarmInfoList() {
            return this.alarminfo;
        }

        @SuppressWarnings("unused")
        public @Nullable AlarmInfoList getAlarmInfo(Integer iD) {
            for (AlarmInfoList alarminfoElement : alarminfo) {
                if (alarminfoElement.getId() == iD) {
                    return alarminfoElement;
                }

            }
            return null;
        }

        private class AlarmInfoList {

            @SerializedName("id")
            private Integer id;

            public Integer getId() {
                return this.id;
            }

            @SuppressWarnings("unused")
            public void setId(Integer id) {
                this.id = id;
            }

            @SerializedName("tstat_alarm_msg")
            public String tstat_alarm_msg;
        }
    }

    @SerializedName("fwmdevimagesInt")
    public ArrayList<FWMDevImagesIntList> fwmdevimagesInt;

    public ArrayList<FWMDevImagesIntList> getFWMDevImagesInt() {
        return this.fwmdevimagesInt;
    }

    public @Nullable FWMDevImagesIntList getFWMDevImageInt(Integer iD) {
        for (FWMDevImagesIntList fwmdevimagesIntElement : fwmdevimagesInt) {
            if (fwmdevimagesIntElement.getId() == iD) {
                return fwmdevimagesIntElement;
            }

        }
        return null;
    }

    private class FWMDevImagesIntList {

        @SerializedName("id")
        private Integer id;

        public Integer getId() {
            return this.id;
        }

        @SuppressWarnings("unused")
        public void setId(Integer id) {
            this.id = id;
        }

        @SerializedName("major")
        public Integer major;

        @SerializedName("description")
        public String description;

        @SerializedName("deviceModel")
        public String deviceModel;

        @SerializedName("build")
        public Integer build;

        @SerializedName("path")
        public String path;

        @SerializedName("minor")
        public Integer minor;

        @SerializedName("size")
        public Integer size;
    }

    @SerializedName("fwmdevimages")
    public Binary fwmdevimages;

    private class Binary {

        @SerializedName("binary")
        public ArrayList<BinaryList> binary;

        @SuppressWarnings("unused")
        public ArrayList<BinaryList> getBinaries() {
            return this.binary;
        }

        @SuppressWarnings("unused")
        public @Nullable BinaryList getBinary(Integer iD) {
            for (BinaryList binaryElement : binary) {
                if (binaryElement.getId() == iD) {
                    return binaryElement;
                }

            }
            return null;
        }

        private class BinaryList {

            @SerializedName("path")
            public String path;

            @SerializedName("id")
            private Integer id;

            public Integer getId() {
                return this.id;
            }

            @SuppressWarnings("unused")
            public void setId(Integer id) {
                this.id = id;
            }

            @SerializedName("size")
            public Integer size;
        }
    }

    @SerializedName("fwmAutoOn")
    public FWMAutoOn fwmAutoOn;

    private class FWMAutoOn {

        @SerializedName("auto_on")
        public Boolean auto_on;

        @SerializedName("writeAccess")
        private WriteAccess writeAccess;
    }

    @SerializedName("publisher")
    public Publisher publisher;

    @SerializedName("fwmFiles")
    public ArrayList<FWMFilesList> fwmFiles;

    public ArrayList<FWMFilesList> getBinaries() {
        return this.fwmFiles;
    }

    public @Nullable FWMFilesList getBinary(Integer iD) {
        for (FWMFilesList fwmFilesElement : fwmFiles) {
            if (fwmFilesElement.getId() == iD) {
                return fwmFilesElement;
            }

        }
        return null;
    }

    private class FWMFilesList {

        @SerializedName("major")
        public Integer major;

        @SerializedName("id")
        private Integer id;

        public Integer getId() {
            return this.id;
        }

        @SuppressWarnings("unused")
        public void setId(Integer id) {
            this.id = id;
        }

        @SerializedName("description")
        public String description;

        @SerializedName("deviceModel")
        public String deviceModel;

        @SerializedName("writeAccess")
        public WriteAccess writeAccess;

        @SerializedName("build")
        public Integer build;

        @SerializedName("path")
        public String path;

        @SerializedName("doNotPersist")
        public Boolean doNotPersist;

        @SerializedName("minor")
        public String minor;

        @SerializedName("size")
        public String size;
    }

    @SerializedName("fwmUsbDlInfo")
    public FWMUSBDlInfo fwmUsbDlInfo;

    private class FWMUSBDlInfo {

        @SerializedName("percentagedownload")
        public Integer percentagedownload;

        @SerializedName("doNotPersist")
        public Boolean doNotPersist;
    }

    @SerializedName("fwmdevupdate")
    public FMWDevUpdate fwmdevupdate;

    private class FMWDevUpdate {

        @SerializedName("path")
        public String percentagedpathownload;

        @SerializedName("doNotPersist")
        public Boolean doNotPersist;

        @SerializedName("writeAccess")
        public WriteAccess writeAccess;

        @SerializedName("progress")
        public Integer progress;

        @SerializedName("id")
        private Integer id;

        @SuppressWarnings("unused")
        public Integer getId() {
            return this.id;
        }

        @SuppressWarnings("unused")
        public void setId(Integer id) {
            this.id = id;
        }
    }

    @SerializedName("fwmUI")
    public FMUI fwmUI;

    private class FMUI {

        @SerializedName("usb_updatereq")
        public Boolean usb_updatereq;

        @SerializedName("doNotPersist")
        public Boolean doNotPersist;

        @SerializedName("writeAccess")
        public WriteAccess writeAccess;

        @SerializedName("flashLED")
        public Boolean flashLED;

        @SerializedName("check_fw")
        private Boolean check_fw;
    }

    @SerializedName("fwmdownloadinfo")
    public ArrayList<FWMDownloadInfoList> fwmdownloadinfo;

    public ArrayList<FWMDownloadInfoList> getFWMDownloadInfoList() {
        return this.fwmdownloadinfo;
    }

    public @Nullable FWMDownloadInfoList getFWMDownloadInfo(Integer iD) {
        for (FWMDownloadInfoList fwmdownloadinfoElement : fwmdownloadinfo) {
            if (fwmdownloadinfoElement.getId() == iD) {
                return fwmdownloadinfoElement;
            }

        }
        return null;
    }

    private class FWMDownloadInfoList {

        @SerializedName("currentbytes")
        public Integer currentbytes;

        @SerializedName("src")
        public String src;

        @SerializedName("Qid")
        public Integer Qid;

        @SerializedName("dst")
        public String dst;

        @SerializedName("percentagedownload")
        private Integer percentagedownload;

        @SerializedName("totalbytes")
        public Integer totalbytes;

        @SerializedName("id")
        private Integer id;

        public Integer getId() {
            return this.id;
        }

        @SuppressWarnings("unused")
        public void setId(Integer id) {
            this.id = id;
        }
    }
}
