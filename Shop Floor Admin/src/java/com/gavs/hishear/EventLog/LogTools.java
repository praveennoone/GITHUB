package com.gavs.hishear.EventLog;

import org.apache.log4j.Logger;

/**
 * @author Pavan k
 * 
 */
public class LogTools {




    private static Logger WINDOWS_EVENT_LOGGER = Logger.getLogger("WindowsEventLogLogger");

    private static Logger M3_WINDOWS_EVENT_LOGGER = Logger.getLogger("WindowsEventLog-M3");

    public static void windowsEventLogInfo(String message) {
        try {
            WINDOWS_EVENT_LOGGER.info(message);
        } catch (Exception e) {
            System.out.println("LogTools.windowsEventLogInfo " + e.getMessage());
        }
    }

    public static void windowsEventLogError(String message) {
        try {
            WINDOWS_EVENT_LOGGER.error(message);
        } catch (Exception e) {
            System.out.println("LogTools.windowsEventLogError " + e.getMessage());
        }
    }

    public static void m3WindowsEventLogError(String message) {
        try {
            M3_WINDOWS_EVENT_LOGGER.error(message);
        } catch (Exception e) {
            System.out.println("LogTools.m3WindowsEventLogError " + e.getMessage());
        }
    }
}

