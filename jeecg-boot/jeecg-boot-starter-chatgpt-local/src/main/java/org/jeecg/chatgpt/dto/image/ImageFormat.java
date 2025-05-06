//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.chatgpt.dto.image;

public enum ImageFormat {
    URL("url"),
    BASE64("b64_json");

    private final String format;

    private ImageFormat(String format) {
        this.format = format;
    }

    public String getFormat() {
        return this.format;
    }
}
