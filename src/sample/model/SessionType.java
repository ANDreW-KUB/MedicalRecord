package sample.model;

public enum SessionType {
    WATCHVIDE("watch video"),WATCHSLIDES("watch slides"),PLAY("play");

    private  final String type;

    SessionType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
