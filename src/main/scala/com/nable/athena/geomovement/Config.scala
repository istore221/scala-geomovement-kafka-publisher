package com.nable.athena.geomovement

import java.util.Properties

object Config {
  
  var props: Properties = null

  def load(props: Properties): Unit = {
      this.props = props
  }
  
  def get(key: String): Any = {
    return this.props.get(key)
  }
}