/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.amityregion5.projectx.common.entities;

/**
 * An interface for items that can be bought. i.e. block
 * @author Mike DiBuduo
 */
public interface Buyable {

    public static final long serialVersionUID = 1L;
    /**
     * used to find the cost of the item that is being bought
     * @return the cost of the item
     */
    public int getCost();

}
