package net.digitalid.utility.logging;

/**
 * This class makes it easier to {@link Logger log} messages.
 */
public final class Log {
    
    /**
     * Stores the index of the caller in the stack trace, which is different on Android.
     */
    private static final int INDEX = System.getProperty("java.vendor").equals("The Android Project") ? 4 : 3;
    
    /**
     * Returns the caller of the logging method.
     * 
     * @return the caller of the logging method.
     */
    private static String getCaller() {
        final StackTraceElement element = Thread.currentThread().getStackTrace()[INDEX];
        final String className = element.getClassName();
        final int lineNumber = element.getLineNumber();
        return className.substring(className.lastIndexOf('.') + 1) + "." + element.getMethodName() + (lineNumber > 0 ? ":" + lineNumber : "");
    }
    
    /**
     * Logs the given message and throwable as an error.
     * 
     * @param message the message to be logged.
     * @param throwable the throwable to log.
     */
    public static void error(String message, Throwable throwable) {
        Logger.log(Level.ERROR, getCaller(), message, throwable);
    }
    
    /**
     * Logs the given message as an error.
     * 
     * @param message the message to be logged.
     */
    public static void error(String message) {
        Logger.log(Level.ERROR, getCaller(), message);
    }
    
    /**
     * Logs the given message and throwable as a warning.
     * 
     * @param message the message to be logged.
     * @param throwable the throwable to log.
     */
    public static void warning(String message, Throwable throwable) {
        Logger.log(Level.WARNING, getCaller(), message, throwable);
    }
    
    /**
     * Logs the given message as a warning.
     * 
     * @param message the message to be logged.
     */
    public static void warning(String message) {
        Logger.log(Level.WARNING, getCaller(), message);
    }
    
    /**
     * Logs the given message and throwable as an information.
     * 
     * @param message the message to be logged.
     * @param throwable the throwable to log.
     */
    public static void information(String message, Throwable throwable) {
        Logger.log(Level.INFORMATION, getCaller(), message, throwable);
    }
    
    /**
     * Logs the given message as an information.
     * 
     * @param message the message to be logged.
     */
    public static void information(String message) {
        Logger.log(Level.INFORMATION, getCaller(), message);
    }
    
    /**
     * Logs the given message and throwable for debugging.
     * 
     * @param message the message to be logged.
     * @param throwable the throwable to log.
     */
    public static void debugging(String message, Throwable throwable) {
        Logger.log(Level.DEBUGGING, getCaller(), message, throwable);
    }
    
    /**
     * Logs the given message for debugging.
     * 
     * @param message the message to be logged.
     */
    public static void debugging(String message) {
        Logger.log(Level.DEBUGGING, getCaller(), message);
    }
    
    /**
     * Logs the given message and throwable only in verbose mode.
     * 
     * @param message the message to be logged.
     * @param throwable the throwable to log.
     */
    public static void verbose(String message, Throwable throwable) {
        Logger.log(Level.VERBOSE, getCaller(), message, throwable);
    }
    
    /**
     * Logs the given message only in verbose mode.
     * 
     * @param message the message to be logged.
     */
    public static void verbose(String message) {
        Logger.log(Level.VERBOSE, getCaller(), message);
    }
    
}
