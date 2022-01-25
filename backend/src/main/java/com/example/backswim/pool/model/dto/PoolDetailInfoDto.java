package com.example.backswim.pool.model.dto;


import com.example.backswim.pool.entity.PoolEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PoolDetailInfoDto {


    public Long id;




}
