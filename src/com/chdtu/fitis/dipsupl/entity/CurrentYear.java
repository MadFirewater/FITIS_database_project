package com.chdtu.fitis.dipsupl.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Supreme on 27.04.2016.
 */
@Entity
@Table(name="CURRENTYEAR")
public class CurrentYear {
    @Id
    @Column(name = "CURR_YEAR")
    int currentYear;

    public int getCurrentYear() {
        return currentYear;
    }

    public void setCurrentYear(int currentYear) {
        this.currentYear = currentYear;
    }
}
