package models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProjectTestsModel {
    private String duration;
    private String method;
    private String name;
    private String startTime;
    private String endTime;
    private String status;
}
