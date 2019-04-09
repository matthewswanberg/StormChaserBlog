/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.stormchaserblog.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author ShellyAnneIsaacs
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@Setter
public class MapLocation {

    private int id;
    private String description;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private LocalDateTime date;

    public void setLatitude(BigDecimal latitude) {
        BigDecimal scaled = latitude.setScale(8);
        this.latitude = scaled;
    }

    public void setLongitude(BigDecimal longitude) {
        BigDecimal scaled = longitude.setScale(8);
        this.longitude = scaled;
    }

    public void setDate(LocalDateTime date) {
        String ldt = date.format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        date = LocalDateTime.parse(ldt, ISO_LOCAL_DATE_TIME);
        this.date = date;
    }

}
