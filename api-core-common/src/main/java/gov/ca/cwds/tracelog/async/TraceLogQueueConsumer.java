package gov.ca.cwds.tracelog.async;

public interface TraceLogQueueConsumer {

  void consumeSearchQueue();

  void consumeAccessQueue();

}
