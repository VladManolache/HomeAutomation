
module homeautomation {
	requires httpserver;

    requires static lombok;
    requires org.apache.logging.log4j;
    requires com.fasterxml.jackson.databind;

    exports com.vmanolache.homeautomation.model;
}