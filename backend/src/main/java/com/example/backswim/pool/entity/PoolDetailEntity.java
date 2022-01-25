package com.example.backswim.pool.entity;

import lombok.*;
import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PoolDetailEntity {

    @Id
    private Long id;

    private LocalTime monOpen;

    private LocalTime monClose;

    private LocalTime tueOpen;

    private LocalTime tueClose;

    private LocalTime wedOpen;

    private LocalTime wedClose;

    private LocalTime thurOpen;

    private LocalTime thurClose;

    private LocalTime friOpen;

    private LocalTime friClose;

    private LocalTime satOpen;

    private LocalTime satClose;

    private LocalTime sunOpen;

    private LocalTime sunClose;

    private LocalTime regdate;

    private Integer price;


    @OneToOne(targetEntity = PoolEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(name="id",referencedColumnName = "id",insertable = false,updatable = false)
    private PoolEntity poolEntity;
}
