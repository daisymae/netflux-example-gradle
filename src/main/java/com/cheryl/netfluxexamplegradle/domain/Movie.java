package com.cheryl.netfluxexamplegradle.domain;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 
 * @author corcutt
 *
 */
// @Document makes it a MongoDB Document
@Document
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Movie {
  private String id;
  
  @NonNull
  private String title;
}
