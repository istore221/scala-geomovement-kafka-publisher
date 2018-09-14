package com.nable.athena.geomovement

trait EventPublisher[T,X] {
  def publish(e:T): X

}