package com.yyicbc.beans.enums;

import org.apache.commons.lang3.StringUtils;

public enum FovaMediuKind {
    CARD("卡介质", "001"),
    PAPER("纸介质", "002"),
    FIGURE("电子银行数字介质", "003"),
    BANK("银行内部户", "004"),
    ;

    private String mediuKindName;
    private String mediuKindCode;

    FovaMediuKind(String mediuKindName, String mediuKindCode) {
        this.mediuKindName = mediuKindName;
        this.mediuKindCode = mediuKindCode;
    }

    public static String getMediuKindCode(String MediumId) {
        if (StringUtils.isBlank(MediumId)) {
            return "";
        }
        if (MediumId.substring(0, 1).equals("6")) {
            return CARD.mediuKindCode;
        }
        if (MediumId.length() >= 12 && (MediumId.substring(0, 4).equals("0119")
                || MediumId.substring(0, 4).equals("0108")
                || "0000".equals(MediumId.substring(0, 4)))) {
            if (MediumId.substring(8, 12).equals("0000")) {
                return PAPER.mediuKindCode;
            } else {
                return BANK.mediuKindCode;
            }
        }
        return "";
    }

    public String getMediuKindName() {
        return mediuKindName;
    }

    public void setMediuKindName(String mediuKindName) {
        this.mediuKindName = mediuKindName;
    }

    public String getMediuKindCode() {
        return mediuKindCode;
    }

    public void setMediuKindCode(String mediuKindCode) {
        mediuKindCode = mediuKindCode;
    }
}
