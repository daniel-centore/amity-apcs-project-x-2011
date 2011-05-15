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
package org.amityregion5.projectx.common.tools;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Class documentation.
 *
 * @author Mike DiBuduo
 */
public class ImageHandler {
    /**
     * Loads an image from a source specified by a string.
     *
     * @param str A String representing the image source.
     * @return An image, or null if none could be found or read.
     */
    public BufferedImage loadImage(String str)
    {
        if (looksLikeFilePath(str))
            return loadFile(str);
        return null;
    }

    /**
     * Determines whether a given string seems to be a file path.
     *
     * @param str The string to be tested.
     * @return Whether the string should be treated as a file path.
     */
    private boolean looksLikeFilePath(String str) {
        return true;
    }

    /**
     * Loads an image from a file, given the path.
     *
     * @param path The filesystem path to the image.
     * @return The image in the file, or null if reading failed.
     */
    public BufferedImage loadFile(String path)
    {
        try {
            return ImageIO.read(new File(path));
        }
        catch (IOException e) {
            return null;
        }
    }
}
