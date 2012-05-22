/* ========================================================
 * BoxDimensions.java
 *
 * Author:      admin
 * Created:     Aug 30, 2011, 3:24:13 PM
 *
 * Description
 * --------------------------------------------------------
 * General Class Description.
 *
 * Change Log
 * --------------------------------------------------------
 * Init.Date        Ref.            Description
 * --------------------------------------------------------
 *
 * ===================================================== */
package Goliath.Graphics;

/**
 * Class Description.
 * For example:
 * <pre>
 *      Example usage
 * </pre>
 *
 * @see         Related Class
 * @version     1.0 Aug 30, 2011
 * @author      admin
 **/
public class BoxDimension extends Goliath.Object
{
    private float m_nTop;
    private float m_nBottom;
    private float m_nLeft;
    private float m_nRight;
    private boolean m_lIsAuto;

    
    public BoxDimension(boolean tlAuto)
    {
        m_lIsAuto = tlAuto;
    }
    
    public BoxDimension(float tnSize)
    {
        this(tnSize, tnSize, tnSize, tnSize);
    }
    
    public BoxDimension(float tnVertical, float tnHorizontal)
    {
        this(tnVertical, tnHorizontal, tnVertical, tnHorizontal);
    }
    
    public BoxDimension(float tnTop, float tnHorizontal, float tnBottom)
    {
        this(tnTop, tnHorizontal, tnBottom, tnHorizontal);
    }
    
    public BoxDimension(float tnTop, float tnRight, float tnBottom, float tnLeft)
    {
        m_nTop = tnTop;
        m_nRight = tnRight;
        m_nBottom = tnBottom;
        m_nLeft = tnLeft;
    }
    
    // TODO: We need to implement an auto for each dimension
    public boolean isAuto()
    {
        return m_lIsAuto;
    }
    
    public float getTop()
    {
        return m_nTop;
    }
    
    public float getLeft()
    {
        return m_nLeft;
    }
    
    public float getBottom()
    {
        return m_nBottom;
    }
    
    public float getRight()
    {
        return m_nRight;
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
        final BoxDimension other = (BoxDimension) obj;
        if (Float.floatToIntBits(this.m_nTop) != Float.floatToIntBits(other.m_nTop))
        {
            return false;
        }
        if (Float.floatToIntBits(this.m_nBottom) != Float.floatToIntBits(other.m_nBottom))
        {
            return false;
        }
        if (Float.floatToIntBits(this.m_nLeft) != Float.floatToIntBits(other.m_nLeft))
        {
            return false;
        }
        if (Float.floatToIntBits(this.m_nRight) != Float.floatToIntBits(other.m_nRight))
        {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 67 * hash + Float.floatToIntBits(this.m_nTop);
        hash = 67 * hash + Float.floatToIntBits(this.m_nBottom);
        hash = 67 * hash + Float.floatToIntBits(this.m_nLeft);
        hash = 67 * hash + Float.floatToIntBits(this.m_nRight);
        return hash;
    }
    
    
}
