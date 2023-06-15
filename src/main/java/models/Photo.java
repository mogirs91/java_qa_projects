package models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Photo {
    private String server;
    private String photo;
    private String hash;
}
