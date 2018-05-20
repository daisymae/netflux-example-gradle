package com.cheryl.netfluxexamplegradle.domain;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author corcutt
 *
 */
@Data
@NoArgsConstructor
public class MovieEvent {

  private String movieId;
  private Date date;
}
