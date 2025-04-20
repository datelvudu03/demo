package dto;

import lombok.Data;

@Data
public class GeocodeResponse {
    private Result[] results;
    private String credits;
    private String status;

    @Data
    public static class Result {
        private String clazz; // Use 'clazz' instead of 'class' because 'class' is a reserved keyword in Java
        private String type;
        private AddressComponents addressComponents;
        private String formattedAddress;
        private Geometry geometry;
        private String osmurl;

        @Data
        public static class AddressComponents {
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
        public static class Geometry {
            private Location location;
            private Viewport viewport;

            @Data
            public static class Location {
                private String lat;
                private String lng;
            }

            @Data
            public static class Viewport {
                private Northeast northeast;
                private Southwest southwest;

                @Data
                public static class Northeast {
                    private String lat;
                    private String lng;
                }

                @Data
                public static class Southwest {
                    private String lat;
                    private String lng;
                }
            }
        }
    }
}
