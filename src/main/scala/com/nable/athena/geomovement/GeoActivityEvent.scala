package com.nable.athena.geomovement
import java.time.Instant
import java.util.UUID

class GeoActivityEvent(
  private val _customer_id: String = UUID.randomUUID().toString().replace("-", ""),
  private val _event:       String = null,
  private val _source_id:   String = UUID.randomUUID().toString().replace("-", ""),
  private val _source:      String = null,
  private val _location_id: String = UUID.randomUUID().toString().replace("-", ""),
  private val _timestamp:   Long   = Instant.now().toEpochMilli()) {

  var customer_id: String = _customer_id
  var event: String = _event
  var source_id: String = _source_id
  var source: String = _source
  var location_id: String = _location_id
  var timestamp: Long = _timestamp
  
  

}