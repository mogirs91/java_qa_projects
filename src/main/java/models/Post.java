package models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Post {
    public Integer id;
    private Integer userId;
    private String title;
    private String body;
}
