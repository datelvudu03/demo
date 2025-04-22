package com.example.demo.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class GeocodeResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Result[] results;
    private String credits;
    private String status;

    @Data
    public static class Result implements Serializable {
        private String clazz; // Use 'clazz' instead of 'class' because 'class' is a reserved keyword in Java
        private String type;
        private AddressComponents addressComponents;
        private String formattedAddress;
        private Geometry geometry;
        private String osmurl;

        @Data
        public static class AddressComponents implements Serializable{
            private String name;
            private String island;
            private String neighbourhood;
            private String street;
            private String subdistrict;
            private String district;
            private String city;
            private String state;
            private String postcode;
            private String country;
        }

        @Data
        public static class Geometry implements Serializable {
            private Location location;
            private Viewport viewport;

            @Data
            public static class Location implements Serializable{
                private String lat;
                private String lng;
            }

            @Data
            public static class Viewport implements Serializable {
                private Northeast northeast;
                private Southwest southwest;

                @Data
                public static class Northeast implements Serializable{
                    private String lat;
                    private String lng;
                }

                @Data
                public static class Southwest implements Serializable{
                    private String lat;
                    private String lng;
                }
            }
        }
    }
}
