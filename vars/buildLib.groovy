#!/usr/bin/env groovy

import groovy.transform.Field

import java.util.logging.Logger

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
  runOnJenkins(node) {
    setup()
  }
}

/**
 * 
 */
def setup() {
  logger = Logger.getLogger('MainPipeline')
  currentStageName = 'Setup'
  stage(currentStageName) {
    logger.info("Setting up all the environments.")
  }
}