/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Goliath.Graphics;

import Goliath.Constants.StringFormatType;
import Goliath.Exceptions.InvalidParameterException;

/**
 *
 * @author kenmchugh
 */
public class Point extends Goliath.Object
{
    /**
     * Gets the difference between point a and point b
     * @param toA point a
     * @param toB point b
     * @return the difference in pixels between the two points
     */
    public static Point subtract(Point toA, Point toB)
    {
        // TODO: Implement percentage and combination points
        return new Point(toA.m_nX - toB.m_nX, toA.m_nY - toB.m_nY);
    }
    
    /**
     * Adds two points together
     * @param toA point a
     * @param toB point b
     * @return the sum of the two points
     */
    public static Point add(Point toA, Point toB)
    {
        // TODO: Implement percentage and combination points
        return new Point(toA.m_nX + toB.m_nX, toA.m_nY + toB.m_nY);
    }
    
    public static float distance(Point to, Point toB)
    {
        // TODO: Implement this
        return 0;
    }
    
    
    private float m_nX;
    private float m_nY;
    private float m_nXPercent;
    private float m_nYPercent;
    private boolean m_lIsPercent;

    /**
     * Copy constructor for an awt point
     * @param toPoint the awt point to convert
     */
    public Point(java.awt.Point toPoint)
    {
        m_nX = toPoint.x;
        m_nY = toPoint.y;
    }

    /**
     * Creates a new instance of a point
     * @param tnX the x coordinates
     * @param tnY the y coordinates
     */
    public Point(float tnX, float tnY)
    {
        m_nX = tnX;
        m_nY = tnY;
        m_lIsPercent = false;
    }

    public Point(float tnX, float tnY, boolean tlIsPercent)
    {
        m_lIsPercent = tlIsPercent;
        if (tlIsPercent)
        {
            m_nXPercent = tnX;
            m_nYPercent = tnY;
        }
        else
        {
            m_nX = tnX;
            m_nY = tnY;
        }
    }

    public Point(float[] taPoints)
    {
        if (taPoints.length != 2)
        {
            throw new InvalidParameterException("The array passed in does not have 2 values", "taPoints");
        }
        m_lIsPercent = false;
        m_nX = taPoints[0];
        m_nY = taPoints[1];
    }

    public Point(float[] taPoints, boolean tlIsPercent)
    {
        if (taPoints.length != 2)
        {
            throw new InvalidParameterException("The array passed in does not have 2 values", "taPoints");
        }

        m_lIsPercent = tlIsPercent;
        if (tlIsPercent)
        {
            m_nXPercent = taPoints[0];
            m_nYPercent = taPoints[1];
        }
        else
        {
            m_nX = taPoints[0];
            m_nY = taPoints[1];
        }
    }

    public float getX()
    {
        // TODO: If this is a percent, then convert the percent to a screen coordinate and return, do not set the member variable however
        return m_nX;
    }

    public float getY()
    {
        // TODO: If this is a percent, then convert the percent to a screen coordinate and return, do not set the member variable however
        return m_nY;
    }
    
    public int getIntX()
    {
        return Goliath.Graphics.Utilities.toDevice(m_nX);
    }
    
    public int getIntY()
    {
        return Goliath.Graphics.Utilities.toDevice(m_nY);
    }
    
    public float getXPercent()
    {
        return m_nXPercent;
    }

    public float getYPercent()
    {
        return m_nYPercent;
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
            return "[" + getXPercent() + "%, " + getYPercent() + "%]";
        }
        return "[" + getX() + ", " + getY() + "]";
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
        final Point other = (Point) obj;
        if (Float.floatToIntBits(this.m_nX) != Float.floatToIntBits(other.m_nX))
        {
            return false;
        }
        if (Float.floatToIntBits(this.m_nY) != Float.floatToIntBits(other.m_nY))
        {
            return false;
        }
        if (Float.floatToIntBits(this.m_nXPercent) != Float.floatToIntBits(other.m_nXPercent))
        {
            return false;
        }
        if (Float.floatToIntBits(this.m_nYPercent) != Float.floatToIntBits(other.m_nYPercent))
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
        int hash = 3;
        hash = 23 * hash + Float.floatToIntBits(this.m_nX);
        hash = 23 * hash + Float.floatToIntBits(this.m_nY);
        hash = 23 * hash + Float.floatToIntBits(this.m_nXPercent);
        hash = 23 * hash + Float.floatToIntBits(this.m_nYPercent);
        hash = 23 * hash + (this.m_lIsPercent ? 1 : 0);
        return hash;
    }


    
    



}
