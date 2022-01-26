package com.example.backswim.pool.entity;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PoolEntity{
    @Id
    private long id;

    @NotNull
    private String placeName;

    private String roadAddressName;

    @NotNull
    private double longitude;

    @NotNull
    private double latitude;

    private String addressName;

    private String phone;

    private LocalDateTime regdate;

    private LocalDateTime updateDate;
}
