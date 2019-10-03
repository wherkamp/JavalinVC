package me.kingtux.javalinvc.core;

public enum MimeType {

    TEXT("text/plain"),
    HTML("text/html", "html"),
    CSS("text/css", "css"),
    JPEG("image/jpeg", "jpg", "jpeg"),
    PNG("image/png", "png"),
    GIF("image/gif", "gif"),
    BMP("image/bmp"),
    WEBP("image/webp"),
    MPEG("audio/mpeg", "mp3"),
    OGG("audio/ogg"),
    MIDI("audio/midi"),
    WEBM("audio/webm"),
    WAV("audio/wav"),
    MP4("video/mp4", "mp4"),
    JSON("application/json"),
    JAVASCRIPT("application/javascript", "js"),
    ECMASCRIPT("application/ecmascript"),
    OCTETSTREAM("application/octet-stream"),
    XML("application/xml", "xml"),
    XHTMLXML("application/xhtml+xml"),
    VNDMSPOWERPOINT("application/vnd.mspowerpoint"),
    PDF("application/pdf"),
    SVG("image/svg+xml");

    private String value;
    private String[] extensions;

    MimeType(String mimeType, String... extensions) {
        this.value = mimeType;
        this.extensions = extensions;
    }

    MimeType(String mimeType) {
        this.value = mimeType;
        extensions = new String[0];
    }

    public String getMimeType() {
        return value;
    }

    public static MimeType getMimeTypeFromExtension(String extension) {
        for (MimeType type : values()) {
            for (String s : type.extensions) {
                if (s.equalsIgnoreCase(extension)) {
                    return type;
                }

            }
        }
        return TEXT;
    }
}