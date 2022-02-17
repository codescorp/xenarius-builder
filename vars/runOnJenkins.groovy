def call(Map nodeConfig = [:], Closure c) {
  if (null == nodeConfig || nodeConfig.size() == 0) {
    node('master') { c()}
  } else {
    def nodeLabel = nodeConfig.get('label')
    if (null == nodeLabel || nodeLabel.trim().isEmpty()) {
      node('master') { c()}
    } else {
      node(nodeLabel) { c()}
    }
  }
}