package com.example.backswim.admin.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PoolEntity{
    @Id
    private long id;

    private String placeName;

    private String roadAddressName;

    private double longitude;

    private double latitude;

    private String addressName;

    private String phone;
}
