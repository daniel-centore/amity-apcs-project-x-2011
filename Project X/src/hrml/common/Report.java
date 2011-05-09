/**
 * Copyright (c) 2011 res.
 * 
 * ex: set filetype=java expandtab tabstop=4 shiftwidth=4 :
 * 
 * This program is free software: you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 * 
 * This code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * This code is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published
 * by the Free Software Foundation.
 */
package hrml.common;

/**
 * @author Michael Zuo <sreservoir@gmail.com>
 */
public enum Report
{
    DEBUG("debug: informational only"),
    MINOR("minor: garbage input, warnings"),
    ERROR("error: sanity is questionable"),
    MAJOR("major: continuation is impossible"),
    ALERT("alert: this can't happen");

    Report() { }
    Report(String _) { }

    private static Report reported = DEBUG;
    private static Report minError = MINOR;

    public static void report(Report s,Object e) {
        String msg = s.name().toLowerCase() + ": " + e;
        if (s.compareTo(reported) < 0)
            return;
        if (s.compareTo(minError) < 0)
            System.out.println(msg);
        else
            System.err.println(msg);
    }

    public static void setReported(Report _) {
        reported = _;
    }

    public static void setMinError(Report _) {
        minError = _;
    }

    public static void debug(Object e) {
        report(DEBUG,e);
    }
    public static void minor(Object e) {
        report(MINOR,e);
    }
    public static void error(Object e) {
        report(ERROR,e);
    }
    public static void major(Object e) {
        report(MAJOR,e);
    }
    public static void alert(Object e) {
        report(ALERT,e);
    }
}
