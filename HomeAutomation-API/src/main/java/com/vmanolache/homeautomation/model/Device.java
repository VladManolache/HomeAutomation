package com.vmanolache.homeautomation.model;

import lombok.*;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Device {

  private String id;

  private String name;

  private String type;

  private Map<String, String> state;

}
