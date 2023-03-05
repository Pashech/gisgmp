package com.example.adapter.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "penalties")
public class Penalties {

    @XmlElement(name = "penalty")
    private List<Penalty> penaltyList;

    public void setPenaltyList(List<Penalty> penaltyList) {
        this.penaltyList = penaltyList;
    }

    public boolean add(Penalty penalty){
        return penaltyList.add(penalty);
    }

    @Override
    public String toString() {
        return "Penalties{" +
                "penaltyList=" + penaltyList +
                '}';
    }
}
