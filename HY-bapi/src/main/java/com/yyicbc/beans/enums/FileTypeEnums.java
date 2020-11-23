package com.yyicbc.beans.enums;


public enum FileTypeEnums {
    TXT("TXT", 0),
    EXCEL("EXCEL", 1),
    PDF("PDF",2),
    ALL("ALL", 3),
    FOVAFILE("FOVAFILE", 4),
    ;

    private String fileType;
    private int fileCode;

    FileTypeEnums(String fileType, int fileCode) {
        this.fileType = fileType;
        this.fileCode = fileCode;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public int getFileCode() {
        return fileCode;
    }

    public void setFileCode(int fileCode) {
        this.fileCode = fileCode;
    }
}
