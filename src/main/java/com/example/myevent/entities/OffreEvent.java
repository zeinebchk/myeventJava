package com.example.myevent.entities;

import java.math.BigInteger;

public class OffreEvent {
      private BigInteger id;
      private Evennement event_id;
      private Offre offre_id;
    public OffreEvent() {
        super();
    }
    public BigInteger getId() {
        return id;
    }
    public void setId(BigInteger id) {
        this.id = id;
    }
    public Evennement getEvent_id() {
        return event_id;
    }
    public void setEvent_id(Evennement event_id) {
        this.event_id = event_id;
    }
    public Offre getOffre_id() {
        return offre_id;
    }
    public void setOffre_id(Offre offre_id) {
        this.offre_id = offre_id;
    }
}
