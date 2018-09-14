package com.nable.athena.geomovement
import java.time.Instant
import java.util.UUID

class GeoTrackingEvent(
    private val _customer_id: String = UUID.randomUUID().toString().replace("-", ""),
    private val _source: String = null,
    private val _altitude: Double = 0,
    private val _speed: Double = 0,
    private val _longitude: Double = 0,
    private val _latitude: Double = 0,
    private val _timestamp: Long = Instant.now().toEpochMilli()) {
  
   var customer_id: String = _customer_id
   var source: String = _source
   var altitude: Double = _altitude
   var speed: Double = _speed
   var longitude: Double = _longitude
   var latitude: Double = _latitude
   var timestamp: Long = _timestamp
  
  
}