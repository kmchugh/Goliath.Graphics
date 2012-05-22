/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Goliath.Graphics;

import Goliath.Constants.StringFormatType;
import Goliath.Exceptions.InvalidParameterException;

/**
 *
 * @author kmchugh
 */
public class Dimension extends Goliath.Object
{
    private static Dimension g_oEmptySize;

    public static Dimension EMPTYSIZE()
    {
        if (g_oEmptySize == null)
        {
            g_oEmptySize = new Dimension(0, 0);
        }
        return g_oEmptySize;
    }

    /**
     * Helper function to create a new dimension using the largest width and height from
     * all the passed in dimesions
     * @param toDim1
     * @param toDim2
     * @return the largest area
     */
    public static Dimension createLargestArea(Dimension toDim1, Dimension toDim2)
    {
        return new Dimension(
                Math.max(toDim1.m_nWidth, toDim2.m_nWidth),
                Math.max(toDim1.m_nHeight, toDim2.m_nHeight));
    }

    /**
     * Helper function to create a new dimension using the smallest width and height from
     * all the passed in dimesions
     * @param toDim1
     * @param toDim2
     * @return the smallest area
     */
    public static Dimension createSmallestArea(Dimension toDim1, Dimension toDim2)
    {
        return new Dimension(
                Math.min(toDim1.m_nWidth, toDim2.m_nWidth),
                Math.min(toDim1.m_nHeight, toDim2.m_nHeight));
    }

    private float m_nWidth;
    private float m_nHeight;
    private float m_nWidthPercent;
    private float m_nHeightPercent;
    private boolean m_lIsPercent;

    public Dimension(java.awt.Dimension toDim)
    {
        m_nWidth = toDim.width;
        m_nHeight = toDim.height;
        m_lIsPercent = false;
    }

    public Dimension(float tnWidth, float tnHeight)
    {
        m_nWidth = tnWidth;
        m_nHeight = tnHeight;
        m_lIsPercent = false;
    }

    public Dimension(float tnWidth, float tnHeight, boolean tlIsPercent)
    {
        m_lIsPercent = tlIsPercent;
        if (tlIsPercent)
        {
            m_nWidthPercent = tnWidth;
            m_nHeightPercent = tnHeight;
        }
        else
        {
            m_nWidth = tnWidth;
            m_nHeight = tnHeight;
        }
    }

    public Dimension(float[] taPoints)
    {
        if (taPoints.length != 2)
        {
            throw new InvalidParameterException("The array passed in does not have 2 values", "taPoints");
        }
        m_lIsPercent = false;
        m_nWidth = taPoints[0];
        m_nHeight = taPoints[1];
    }

    public Dimension(float[] taPoints, boolean tlIsPercent)
    {
        if (taPoints.length != 2)
        {
            throw new InvalidParameterException("The array passed in does not have 2 values", "taPoints");
        }

        m_lIsPercent = tlIsPercent;
        if (tlIsPercent)
        {
            m_nWidthPercent = taPoints[0];
            m_nHeightPercent = taPoints[1];
        }
        else
        {
            m_nWidth = taPoints[0];
            m_nHeight = taPoints[1];
        }
    }

    public float getWidth()
    {
        // TODO: If this is a percent, then convert the percent to a screen coordinate and return, do not set the member variable however
        return m_nWidth;
    }
    
    public int getIntWidth()
    {
        return Goliath.Graphics.Utilities.toDevice(m_nWidth);
    }
    
    public int getIntHeight()
    {
        return Goliath.Graphics.Utilities.toDevice(m_nHeight);
    }

    public float getHeight()
    {
        // TODO: If this is a percent, then convert the percent to a screen coordinate and return, do not set the member variable however
        return m_nHeight;
    }

    public float getWidthPercent()
    {
        return m_nWidthPercent;
    }

    public float getHeightPercent()
    {
        return m_nHeightPercent;
    }

    public boolean isPercent()
    {
        return m_lIsPercent;
    }

    @Override
    protected String formatString(StringFormatType toFormat)
    {
        if (isPercent())
        {
            return "[" + getWidthPercent() + "%, " + getHeightPercent() + "%]";
        }
        return "[" + getWidth() + ", " + getHeight() + "]";
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
        final Dimension other = (Dimension) obj;
        if (Float.floatToIntBits(this.m_nWidth) != Float.floatToIntBits(other.m_nWidth))
        {
            return false;
        }
        if (Float.floatToIntBits(this.m_nHeight) != Float.floatToIntBits(other.m_nHeight))
        {
            return false;
        }
        if (Float.floatToIntBits(this.m_nWidthPercent) != Float.floatToIntBits(other.m_nWidthPercent))
        {
            return false;
        }
        if (Float.floatToIntBits(this.m_nHeightPercent) != Float.floatToIntBits(other.m_nHeightPercent))
        {
            return false;
        }
        if (this.m_lIsPercent != other.m_lIsPercent)
        {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 47 * hash + Float.floatToIntBits(this.m_nWidth);
        hash = 47 * hash + Float.floatToIntBits(this.m_nHeight);
        hash = 47 * hash + Float.floatToIntBits(this.m_nWidthPercent);
        hash = 47 * hash + Float.floatToIntBits(this.m_nHeightPercent);
        hash = 47 * hash + (this.m_lIsPercent ? 1 : 0);
        return hash;
    }







}