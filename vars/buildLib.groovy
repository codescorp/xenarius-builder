#!/usr/bin/env groovy

import groovy.transform.Field

import io.xenarius.utils.logging.Logger
import io.xenarius.utils.logging.LogLevel

@Field def currentStageName = ''
@Field def node = [:]
@Field def logger

node['label'] = 'master'

/**
 * This is the entrypoint of the pipeline script. This library
 * can have multiple scripts and each of them can have a <code>call()</code>
 * method.
 * @param args
  *
 */
def call(args) {
  Logger.init(this, LogLevel.INFO)
  logger = new Logger(this)
  runOnJenkins(node) {
    setup()
  }
}

/**
 * 
 */
def setup() {
  currentStageName = 'Setup'
  stage(currentStageName) {
    logger.info("Some random log message")
  }
}