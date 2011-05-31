/**
 * Copyright (c) 2011 Amity AP CS A Students of 2010-2011.
 *
 * ex: set filetype=java expandtab tabstop=4 shiftwidth=4 :
 * * This program is free software: you can redistribute it and/or
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
package org.amityregion5.projectx.client.communication;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.amityregion5.projectx.client.preferences.PreferenceManager;
import org.amityregion5.projectx.common.communication.Constants;
import org.amityregion5.projectx.common.communication.RawListener;

/**
 * Client-side raw communications handler.
 *
 * @author Joe Stein
 * @author Daniel Centore
 */
public class RawCommunicationHandler extends Thread {
    private Socket rawSock = null; // raw socket we created
    private DataOutputStream dos;
    private boolean keepRunning = true;
    private ArrayList<RawListener> rawListeners;

    public RawCommunicationHandler(String serverIP)
    {
        rawListeners = new ArrayList<RawListener>();
        try
        {
            rawSock = new Socket(serverIP, Constants.RAW_PORT);
            dos = new DataOutputStream(rawSock.getOutputStream());
        } catch (UnknownHostException ex)
        {
            Logger.getLogger(RawCommunicationHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex)
        {
            Logger.getLogger(RawCommunicationHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try
        {
            dos.writeUTF(PreferenceManager.getUsername());
        } catch (IOException e)
        {
        }
    }

    @Override
    public void run()
    {
        BufferedReader bis = null;
        try
        {
            bis = new BufferedReader(new InputStreamReader(rawSock.getInputStream()));
        } catch (IOException ex)
        {
            Logger.getLogger(RawCommunicationHandler.class.getName()).log(Level.SEVERE, null, ex);
            keepRunning = false;
        }
        while (keepRunning)
        {
            // String to read. Current format is:
            // uniqueId,x-coord,y-coord,direction
            String mes;
            try
            {
                mes = bis.readLine();
                if (mes == null)
                {
                    kill();
                } else {
                    for (RawListener rl : rawListeners)
                    {
                        rl.handle(mes);
                    }
                }
            } catch (IOException ex)
            {
                Logger.getLogger(RawCommunicationHandler.class.getName()).log(Level.SEVERE, null, ex);
                keepRunning = false;
            }
        }
    }

    public void kill()
    {
        keepRunning = false;
    }
    
    public synchronized void send(int dir)
    {
        try
        {
            dos.writeInt(dir);
            dos.flush();
        }
        catch(IOException ex)
        {
            Logger.getLogger(RawCommunicationHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public synchronized void registerRawListener(RawListener rl)
    {
        rawListeners.add(rl);
    }

    public synchronized void removeRawListener(RawListener rl)
    {
        rawListeners.remove(rl);
    }
    
}