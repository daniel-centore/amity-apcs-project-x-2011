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
package org.amityregion5.projectx.common.communication.messages.sound;

import org.amityregion5.projectx.common.communication.messages.Message;
import org.amityregion5.projectx.common.tools.Sound;

/**
 * A sound control message that controls the client's sound system.
 *
 * @author Dan Centore
 * @author Joe Stein
 */
public class SoundControlMessage extends Message {

    private static final long serialVersionUID = 532L;
    
    private Sound sound;
    private Type type;

    public static enum Type {
        START,
        STOP,
        ONCE;
    }

    public SoundControlMessage(Sound s, Type t)
    {
        sound = s;
        type = t;
    }

    public Sound getSound()
    {
        return sound;
    }

    public Type getType()
    {
        return type;
    }
}
