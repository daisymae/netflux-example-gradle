package com.cheryl.netfluxexamplegradle.domain;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * 
 * @author corcutt
 *
 */
// @Document makes it a MongoDB Document
@Document
@Data
@NoArgsConstructor
public class Movie {
  private String id;
  
  @NonNull
  private String title;
}
