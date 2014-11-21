package org.jsondoc.maven;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.maven.plugin.logging.Log;

public class MavenLoggerLog4jBridge extends AppenderSkeleton {

	private final Log logger;

	public MavenLoggerLog4jBridge(Log logger) {
		this.logger = logger;
	}

	@Override
	protected void append(LoggingEvent event) {
		int level = event.getLevel().toInt();
		String msg = event.getMessage().toString();
		switch (level) {
		case Level.DEBUG_INT:
		case Level.TRACE_INT:
			if (event.getThrowableInformation() != null) {
				logger.debug(msg, event.getThrowableInformation().getThrowable());
			} else {
				logger.debug(msg);
			}
			break;
		case Level.INFO_INT:
			if( event.getThrowableInformation() != null ) {
				logger.info(msg, event.getThrowableInformation().getThrowable());
			} else {
				logger.info(msg);
			}
			break;
		case Level.WARN_INT:
			if( event.getThrowableInformation() != null ) {
				logger.warn(msg, event.getThrowableInformation().getThrowable());
			} else {
				logger.warn(msg);
			}
			break;
		case Level.ERROR_INT:
		case Level.FATAL_INT:
			if( event.getThrowableInformation() != null ) {
				logger.error(msg, event.getThrowableInformation().getThrowable());
			} else {
				logger.error(msg);
			}
			break;
		default:
			logger.info(msg);
			break;
		}
	}
	
	@Override
	public void close() {
		//do nothing
	}
	
	@Override
	public boolean requiresLayout() {
		return false;
	}

}
