#!/usr/bin/env groovy

import groovy.transform.Field

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
    echo "This is just an echo"
    logger.info("Some random log message")
  }
}