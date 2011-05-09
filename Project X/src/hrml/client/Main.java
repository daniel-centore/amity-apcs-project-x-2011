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
package hrml.client;

import java.net.Socket;

import hrml.common.Connection;
import hrml.common.Magic;
import hrml.common.Report;

/**
 * @author Michael Zuo <sreservoir@gmail.com>
 */
public class Main
{
    public static void main(String[] argv) {
        Report.debug("started.");
        String addr = "localhost";
        int port = Magic.PORT;
        if (argv.length > 0) {
            addr = argv[0];
            if (argv.length > 1) {
                try {
                    port = Integer.parseInt(argv[1]);
                }
                catch (Exception e) {
                    Report.minor(e);
                    Report.minor("got bogus port " + argv[1]);
                }
            }
        }
        try {
            Server to = new Server(addr,port);
            Report.debug("connected to " + addr + ":" + port);
            double rn = Math.random();
            to.send.println(rn);
            Report.debug("send " + rn);
            String line = to.scan.nextLine();
            Report.debug("recv " + line);
        }
        catch (Exception e) {
            Report.major(e);
            System.exit(1);
        }
    }
}
