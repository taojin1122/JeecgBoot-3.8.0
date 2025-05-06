//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.chatgpt.dto.image;

public enum ImageSize {
    size_1024_1792("1024x1792"),
    size_1792_1024("1792x1024"),
    size_1024("1024x1024");

    private final String size;

    private ImageSize(String size) {
        this.size = size;
    }

    public String getSize() {
        return this.size;
    }
}
