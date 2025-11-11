package com.Driver.DriverService.service;

import com.Driver.DriverService.event.RiderLocationEvent;
import com.Driver.DriverService.event.RiderRequestEvent;
import com.Driver.DriverService.listener.RiderRequestListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class  RideRequestService {
private static final String TOPIC="Rider_List";
    @Autowired
    private KafkaTemplate<String,List<RiderLocationEvent>>kafkaTemplate;

    @Autowired
    private RiderRequestListener riderRequestListener;
    public List<RiderLocationEvent> getNearbyRiders(RiderRequestEvent event){
      List<RiderLocationEvent>riderList=riderRequestListener.consume(event).stream()
              .map(result->new RiderLocationEvent(result.getContent().getName(),result.getContent().getPoint().getX(),result.getContent().getPoint().getY()))
              .collect(Collectors.toList());
      kafkaTemplate.send(TOPIC,riderList);
      return riderList;
    }
}
