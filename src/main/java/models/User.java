package models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class User {
    private String website;
    private Address address;
    private String phone;
    private String name;
    private Company company;
    private Integer id;
    private String email;
    private String username;

    @Getter
    @Setter
    @ToString
    public static class Address {
        private String zipcode;
        private Geo geo;
        private String suite;
        private String city;
        private String street;
    }

    @Getter
    @Setter
    @ToString
    public static class Company {
        private String bs;
        private String catchPhrase;
        private String name;
    }

    @Getter
    @Setter
    @ToString
    public static class Geo {
        private String lng;
        private String lat;
    }
}
