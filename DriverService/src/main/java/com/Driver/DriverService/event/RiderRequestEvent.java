package com.Driver.DriverService.event;

import lombok.Data;

@Data
public class RiderRequestEvent {

        private Long userId;
        private double Origin_Latitude;
        private double Origin_Longitude;
        private double Drop_Latitude;
        private double Drop_Longitude;
        private String rideId;
        private String requestedAt;



    }


