/* =========================================================
 * Utilities.java
 *
 * Author:      kenmchugh
 * Created:     Mar 22, 2010, 2:27:50 PM
 *
 * Description
 * --------------------------------------------------------
 * <Description>
 *
 * Change Log
 * --------------------------------------------------------
 * Init.Date        Ref.            Description
 * --------------------------------------------------------
 *
 * =======================================================*/

package Goliath.Graphics;

import Goliath.Applications.Application;
import Goliath.Exceptions.InvalidParameterException;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import javax.imageio.ImageIO;


/**
 *
 * @author kenmchugh
 */
public class Utilities
{
    private static GraphicsConfiguration g_oGConfig;

    /**
     * Gets the default graphics configuration of the default screen
     * @return the Graphics configuration
     */
    public static GraphicsConfiguration getDefaultGraphicsConfig()
    {
        if (g_oGConfig == null)
        {
            g_oGConfig = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        }
        return g_oGConfig;
    }
    
    /**
     * Creates a compatible image of the same width, height, and transparency as the passed in image
     * @param toImage the image to create a compatible image from
     * @return the compatible image
     */
    public static BufferedImage createCompatibleImage(BufferedImage toImage)
    {
        return createCompatibleImage(toImage, toImage.getWidth(), toImage.getHeight());
    }
    
    /**
     * Creates a compatible image with the same transparency as the image passed in
     * @param toImage the image to draw the transparency from
     * @param tnWidth the width of the image
     * @param tnHeight the height of the image
     * @return the buffered image
     */
    public static BufferedImage createCompatibleImage(BufferedImage toImage, int tnWidth, int tnHeight)
    {
        return getDefaultGraphicsConfig().createCompatibleImage(tnWidth, tnHeight, toImage.getTransparency());
    }
    
    /**
     * Creates an opaque image compatible with the primary screen
     * @param tnWidth the width of the image
     * @param tnHeight the height of the image
     * @return the image
     */
    public static BufferedImage createCompatibleImage(int tnWidth, int tnHeight)
    {
        return getDefaultGraphicsConfig().createCompatibleImage(tnWidth, tnHeight);
    }
    
    /**
     * Creates an image compatible with the primary screen
     * @param tnWidth the width of the image
     * @param tnHeight the height of the image
     * @return the image
     */
    public static BufferedImage createCompatibleTranslucentImage(int tnWidth, int tnHeight)
    {
        return getDefaultGraphicsConfig().createCompatibleImage(tnWidth, tnHeight, Transparency.TRANSLUCENT);
    }
    
    /**
     * Helper function to convert the parameter from a logical coordinate to a device coordinate
     * This does not adjust the axis, for example reversing the y axis for logical to screen
     * @param tnCoordinate the coordinate to convert
     * @return the integer version (device) coordinate
     */
    public static int toDevice(float tnCoordinate)
    {
        return Math.round(tnCoordinate);
    }
    
    
    /**
     * Helper function to convert the parameter from a device coordinate to a logical coordinate
     * This does not adjust the axis, for example reversing the y axis for screen to logical
     * @param tnCoordinate the coordinate to convert
     * @return the floating point version (logical) coordinate
     */
    public static float toLogical(int tnCoordinate)
    {
        return (float)tnCoordinate;
    }
    
    /**
     * Converts from the logical space where y = 0 is at the bottom of the space, to the 
     * device space where y = 0 is at the top of the space.  This function does not adjust the x and y
     * coordinates except for adjusting the space, so after a call to the function you will still be
     * working with the floating point values.
     * @param tnX The X coordinate
     * @param tnY The Y coordinate
     * @param tnMaxY The maximum Y coordinate
     * @return the point in device space (y = 0 is at the top)
     */
    public static Point toDevice(float tnX, float tnY, int tnMaxY)
    {
        return new Point(tnX, tnMaxY - tnY);
    }
    
    /**
     * Converts from the device space where y = 0 is at the top of the space, to the 
     * logical space where y = 0 is at the bottom of the space.  This function does not adjust the x and y
     * coordinates except for adjusting the space, so after a call to the function you will still be
     * working with the floating point values.
     * @param tnX The X coordinate
     * @param tnY The Y coordinate
     * @param tnMaxY The maximum Y coordinate
     * @return the point in logical space (y = 0 is at the bottom)
     */
    public static Point toLogical(int tnX, int tnY, int tnMaxY)
    {
        return new Point(tnX, tnMaxY - tnY);
    }

    /**
     * Creates a copy of the specified source image
     * @param toSource the image to copy
     * @return a copy of the specified image
     */
    public static BufferedImage copy(BufferedImage toSource)
    {
        WritableRaster loRaster = toSource.copyData(null);
        return new BufferedImage(toSource.getColorModel(), loRaster, toSource.isAlphaPremultiplied(), null);
    }

    /**
     * Copies the specified pixel data from the source to the destination
     * @param taSource the source image to copy from
     * @param tnSourceWidth the number of columns in the source
     * @param tnSourceX the X offset to copy from
     * @param tnSourceY the Y offset to copy from
     * @param tnWidth the width of the rectangle to copy
     * @param tnHeight the height of the rectangle to copy
     * @param taDestination the destination pixel data
     * @param tnDestWidth the number of columns in the destination
     * @param tnDestX the x offset to copy to
     * @param tnDestY the y offset to copy to
     */
    public static void copyPixelData(int[] taSource, int tnSourceWidth, int tnSourceX, int tnSourceY, int tnWidth, int tnHeight,
            int[] taDestination, int tnDestWidth, int tnDestX, int tnDestY)
    {
        if (tnWidth * tnHeight > taDestination.length)
        {
            throw new InvalidParameterException("The source rectangle will not fit in the destination", "taDestination", taDestination);
        }

        // Can not copy to a negative position
        tnSourceX = Math.max(tnSourceX, 0);
        tnSourceY = Math.max(tnSourceY, 0);
        tnDestX = Math.max(tnDestX, 0);
        tnDestY = Math.max(tnDestY, 0);

        int lnDestOffsetY = tnDestY * tnDestWidth;
        int lnSourceOffsetY = tnSourceY * tnSourceWidth;
        for (int lnY=0; lnY<tnHeight; lnY++)
        {
            int lnDestRowOffset = ((lnY * tnDestWidth) + lnDestOffsetY) + tnDestX;
            int lnOriginalRowOffset = ((lnY * tnSourceWidth) + lnSourceOffsetY) + tnSourceX;
            for (int lnX=0; lnX<tnWidth; lnX++)
            {
                    taDestination[lnDestRowOffset + lnX] = taSource[lnOriginalRowOffset   // Y value
                                        + lnX   // X Value
                                        ];
            }
        }
    }

    /**
     * Gets the specified rectangle of pixels from the source
     * @param taSource the source pixels to get the data from
     * @param tnSourceWidth the number of columns in the source data
     * @param tnX the x position to get the data from
     * @param tnY the y position to get the data from
     * @param tnWidth the width of the data to get
     * @param tnHeight the height of the data to get
     * @return the pixel data
     */
    public static int[] getPixelData(int[] taSource, int tnSourceWidth, int tnX, int tnY, int tnWidth, int tnHeight)
    {
        int[] laReturn = new int[tnWidth * tnHeight];

        copyPixelData(taSource, tnSourceWidth, tnX, tnY, tnWidth, tnHeight, laReturn, tnWidth, 0, 0);

        return laReturn;
    }

    /**
     * Puts the pixel data from the source array into the destination array at the specified offset values
     * @param taSource the source array
     * @param tnSourceWidth the number of columns in the source array
     * @param taDestination the destination array
     * @param tnDestWidth the number of columns in the destination array
     * @param tnX the X offset
     * @param tnY the Y offset
     */
    public static void putPixelData(int[] taSource, int tnSourceWidth, int[] taDestination, int tnDestWidth, int tnX, int tnY)
    {
        copyPixelData(taSource, tnSourceWidth, 0, 0, tnSourceWidth, taSource.length / tnSourceWidth,
                taDestination, tnDestWidth, tnX, tnY);
    }

    /**
     * Gets the pixel data for the entire image
     * @param toImage the image to get the data for
     * @return the pixel information
     */
    public static int[] getPixelData(BufferedImage toImage)
    {
        return getPixelData(toImage, 0, 0, toImage.getWidth(), toImage.getHeight());

    }

    /**
     * Gets the pixel information from the buffered image
     * @param toImage the image to get the information from
     * @param tnX the x position of the rectange to get the data from
     * @param tnY the y position to get the data from
     * @param tnWidth the width to get the data from
     * @param tnHeight the height to get the data from
     * @return the array of pixels
     */
    public static int[] getPixelData(BufferedImage toImage, int tnX, int tnY, int tnWidth, int tnHeight)
    {
        // If the width and height are out of bounds, return an empty array
        if (tnWidth <= 0 || tnHeight <= 0)
        {
            return new int[0];
        }

        // Create the storage array
        int[] laReturn = new int[tnWidth * tnHeight];
        int lnImageType = toImage.getType();

        if (lnImageType == BufferedImage.TYPE_INT_ARGB ||
                lnImageType == BufferedImage.TYPE_INT_RGB)
        {
            return (int[])toImage.getRaster().getDataElements(tnX, tnY, tnWidth, tnHeight, laReturn);
        }
        return toImage.getRGB(tnX, tnY, tnWidth, tnHeight, laReturn, 0, tnWidth);
    }

    public static void setPixelData(BufferedImage toImage, int[] taPixels)
    {
        setPixelData(toImage, 0, 0, toImage.getWidth(), toImage.getHeight(), taPixels);
    }

    /**
     * Sets the pixel data at the specified rectangle to the data from the pixel array
     * @param toImage the image to set the pixels in
     * @param tnX the x position of the rectangle
     * @param tnY the y position of the rectangle
     * @param tnWidth the width of the rectangle
     * @param tnHeight the height of the rectangle
     * @param taPixels the pixel data
     */
    public static void setPixelData(BufferedImage toImage, int tnX, int tnY, int tnWidth, int tnHeight, int[] taPixels)
    {
        if (taPixels == null || taPixels.length == 0 || tnWidth == 0 || tnHeight == 0)
        {
            return;
        }

        if (taPixels.length < tnWidth * tnHeight)
        {
            throw new InvalidParameterException("The array of pixels does not match the size of the rectangle provided", "taPixels");
        }

        int lnImageType = toImage.getType();
        if (lnImageType == BufferedImage.TYPE_INT_ARGB ||
                lnImageType == BufferedImage.TYPE_INT_RGB)
        {
            toImage.getRaster().setDataElements(tnX, tnY, tnWidth, tnHeight, taPixels);
        }
        else
        {
            toImage.setRGB(tnX, tnY, tnWidth, tnHeight, taPixels, 0, tnWidth);
        }
    }

    /**
     * Writes an image to disk, correcting problems with the alpha chanel if the file has a .jpg or .jpeg extension.
     *
     * If the file does not exist, it will be created, if it exists it will be overwritten
     *
     * @param toImage the image to write
     * @param toFile the file to write to
     */
    public static void toFile(BufferedImage toImage, File toFile)
    {
        if (toFile.exists())
        {
            toFile.delete();
        }
        try
        {
            if (Goliath.IO.Utilities.File.create(toFile))
            {
                String lcExtension = toFile.getName().lastIndexOf(".") > 0 ? toFile.getName().substring(toFile.getName().lastIndexOf(".") +1) : "png";
                if (lcExtension.equalsIgnoreCase("jpg") || lcExtension.equalsIgnoreCase("jpeg") && toImage.getType() != BufferedImage.TYPE_INT_RGB)
                {
                    // Write a jpg without the alpha chanel
                    BufferedImage loTemp = new BufferedImage (toImage.getWidth(), toImage.getHeight(), BufferedImage.TYPE_INT_RGB);
                    Graphics2D loGraphics = loTemp.createGraphics();
                    loGraphics.drawRenderedImage(toImage, null);
                    toImage = loTemp;
                }
                ImageIO.write(toImage, lcExtension, toFile);
            }
        }
        catch(NullPointerException ex)
        {
            /* For now do nothing with this, this is happening due to a bug in the GIFWriter
             * It is unable to find a pallette if writing to a gif file and if nothing has been written
             * to the .gif image (spacer.gif)
            */
        }
        catch (Throwable ex)
        {
            Application.getInstance().log(ex);
        }
    }

    

}
