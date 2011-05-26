/**
 * Copyright (c) 2011 Amity AP CS A Students of 2010-2011.
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
package org.amityregion5.projectx.client.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.amityregion5.projectx.client.communication.CommunicationHandler;
import org.amityregion5.projectx.client.preferences.PrefListener;
import org.amityregion5.projectx.client.preferences.PreferenceManager;
import org.amityregion5.projectx.common.communication.DatagramListener;
import org.amityregion5.projectx.common.communication.messages.ActivePlayersMessage;
import org.amityregion5.projectx.common.communication.messages.BooleanReplyMessage;
import org.amityregion5.projectx.common.communication.messages.IntroduceMessage;
import org.amityregion5.projectx.common.communication.messages.Message;

/**
 * The window for choosing a server
 * 
 * @author Daniel Centore
 * @author Joe Stein
 * @author Mike DiBuduo
 */
public class ServerChooserWindow extends JFrame implements DatagramListener, PrefListener {

    private static final long serialVersionUID = 1L;

    private DefaultListModel dlm = new DefaultListModel(); // the list model for ips

    /**
     * Creates new form ServerChooserWindow
     */
    public ServerChooserWindow()
    {
        PreferenceManager.registerListener(this);
        initComponents();
        dlm.addElement("Manual/Internet...");

        joinBtn.setText("Join as " + PreferenceManager.getUsername());

        serverList.setModel(dlm);

        serverList.setSelectedIndex(0);
        this.setVisible(true);
        userBtn.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e)
            {
                new UsernameWindow(ServerChooserWindow.this, true, false);
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        joinBtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        serverList = new javax.swing.JList();
        userBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Join a Server");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setResizable(false);

        joinBtn.setText("Join");
        joinBtn.setEnabled(false);
        joinBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                joinBtnActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 18));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Join a Server");

        serverList.setFont(new java.awt.Font("SansSerif", 0, 15));
        serverList.setModel(new javax.swing.AbstractListModel() {
            private static final long serialVersionUID = 1L;

            String[] strings =
            { "Manual/Internet..." };

            public int getSize()
            {
                return strings.length;
            }

            public Object getElementAt(int i)
            {
                return strings[i];
            }
        });
        serverList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        serverList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt)
            {
                serverListValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(serverList);

        userBtn.setText("Change Username");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE).addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE).addComponent(joinBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE).addComponent(userBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(jLabel1).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(joinBtn).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(userBtn).addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void joinBtnActionPerformed(java.awt.event.ActionEvent evt)// GEN-FIRST:event_joinBtnActionPerformed
    {// GEN-HEADEREND:event_joinBtnActionPerformed
        int selected = serverList.getSelectedIndex();
        String server;
        if (selected == 0) // manual/internet
        {
            server = JOptionPane.showInputDialog(this, "Server IP:", "Server Input", JOptionPane.PLAIN_MESSAGE);
            if (server == null)
            {
                return;
            }
        } else
        {
            ServerListElement sle = (ServerListElement) serverList.getModel().getElementAt(selected);
            server = sle.getIP();
        }

        CommunicationHandler ch = null;
        try
        {
            ch = new CommunicationHandler(server);
        } catch (IOException e)
        {
            JOptionPane.showMessageDialog(null, "Connection refused", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean joined = false;
        while (!joined)
        {
            Message reply = ch.requestReply(new IntroduceMessage(PreferenceManager.getUsername()));
            // ActivePlayerUpdate message serves as an affirmative here.
            if (reply instanceof BooleanReplyMessage)
            {

                JOptionPane.showMessageDialog(null, "Username already in use", "Error", JOptionPane.WARNING_MESSAGE);
                int choice = JOptionPane.showConfirmDialog(this, "Do you want to change your username?", "Do you want to change?", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION)
                {
                    new UsernameWindow(this, true, false);
                } else
                {
                    joined = true;
                }
            } else if (reply instanceof ActivePlayersMessage)
            {
                ActivePlayersMessage apm = (ActivePlayersMessage) reply;
                ServerChooserWindow.this.setVisible(false);
                ServerChooserWindow.this.dispose();
                joined = true;
                new LobbyWindow(ch, apm.getPlayers());
            }
        }
    }// GEN-LAST:event_joinBtnActionPerformed


    /**
     * What to do when the selected value/index on the server list changes.
     * 
     * If the selected index is greater than -1 (i.e. there is a selected element), enable the "Join" button.
     * 
     * @param evt the ListSelectionEvent to handle
     */
    private void serverListValueChanged(javax.swing.event.ListSelectionEvent evt)// GEN-FIRST:event_serverListValueChanged
    {// GEN-HEADEREND:event_serverListValueChanged
        SwingUtilities.invokeLater(new Runnable() {

            public void run()
            {
                joinBtn.setEnabled(serverList.getSelectedIndex() > -1);
            }
        });

    }// GEN-LAST:event_serverListValueChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton joinBtn;
    private javax.swing.JList serverList;
    private javax.swing.JButton userBtn;

    // End of variables declaration//GEN-END:variables
    @Override
    public void handle(DatagramPacket packet)
    {

        ServerListElement sle = new ServerListElement(new String(packet.getData()).trim(), packet.getAddress().getHostAddress());

        if (!dlm.contains(sle))
        {
            dlm.addElement(sle);
        }
    }

    /**
     * An object to hold a server's IP and name.
     */
    private class ServerListElement {

        private String name; // Name of the server
        private String ip; // Server IP

        /**
         * Creates one
         * 
         * @param name The name of the server
         * @param ip The IP address of the server
         */
        public ServerListElement(String name, String ip)
        {
            this.name = name;
            this.ip = ip;
        }

        /**
         * @return Server IP address
         */
        public String getIP()
        {
            return ip;
        }

        /**
         * @return Server name
         */
        public String getName()
        {
            return name;
        }

        @Override
        public String toString()
        {
            return name + " (" + ip + ")";
        }

        @Override
        public boolean equals(Object obj)
        {
            if (obj == null)
            {
                return false;
            }
            if (getClass() != obj.getClass())
            {
                return false;
            }
            final ServerListElement other = (ServerListElement) obj;
            if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name))
            {
                return false;
            }
            if ((this.ip == null) ? (other.ip != null) : !this.ip.equals(other.ip))
            {
                return false;
            }
            return true;
        }

        @Override
        public int hashCode()
        {
            int hash = 3;
            hash = 97 * hash + (this.name != null ? this.name.hashCode() : 0);
            hash = 97 * hash + (this.ip != null ? this.ip.hashCode() : 0);
            return hash;
        }
    }

    @Override
    public void usernameChanged(String username)
    {
        joinBtn.setText("Join as " + username);
    }
}
