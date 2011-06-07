/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.amityregion5.projectx.common.communication.messages;

/**
 *
 * @author Mike DiBuduo
 */
public class PointMessage extends Message{

    private static final long serialVersionUID = 1L;
    private int amount;
    private long id;

    public PointMessage(int points, long uniqueID) {
        amount = points;
        id = uniqueID;
    }

    public int getAmount()
    {
        return amount;
    }

    public long getID()
    {
        return id;
    }

}
