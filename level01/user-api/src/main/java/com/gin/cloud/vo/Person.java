package com.gin.cloud.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 *
 * @author gin
 * @date  2021/1/7 18:14
 *
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Person {

    private Integer id;

    private String name;

}
