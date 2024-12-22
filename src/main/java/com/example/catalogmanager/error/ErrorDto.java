package com.example.catalogmanager.error;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@JsonPropertyOrder({"timestamp", "traceId", "faults"})
public class ErrorDto {
  private final String traceId;
  private final LocalDateTime timestamp;
  private final List<FaultDto> faults;

  public ErrorDto(List<FaultDto> faults) {
    this.traceId = UUID.randomUUID().toString();
    this.timestamp = LocalDateTime.now();
    this.faults = faults;
  }

  public String getTraceId() {
    return traceId;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public List<FaultDto> getFaults() {
    return faults;
  }
}
