/* ========================================================
 * ScaleEffectArguments.java
 *
 * Author:      kmchugh
 * Created:     Nov 3, 2010, 2:50:30 PM
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

package Goliath.Graphics.Effects;


        
/**
 * Class Description.
 * For example:
 * <pre>
 *      Example usage
 * </pre>
 *
 * @see         Related Class
 * @version     1.0 Nov 3, 2010
 * @author      kmchugh
**/
public class ScaleEffectArguments extends EffectArguments
{
    /**
     * Creates a new instance of the scale effect arguments
     * @param tnWidth the width to scale to
     * @param tnHeight the height to scale to
     */
    public ScaleEffectArguments(float tnWidth, float tnHeight)
    {
        this(tnWidth, tnHeight, 0, 0, 0, 0, false);
    }

    /**
     * Creates a new instance of the scale effect arguments
     * @param tnWidth the width to scale to
     * @param tnHeight the height to scale to
     * @param tnGridTop the top of the scale 9 grid
     * @param tnGridRight the right of the scale 9 grid
     * @param tnGridBottom the bottom of the scale 9 grid
     * @param tnGridLeft the left of the scale 9 grid
     */
    public ScaleEffectArguments(float tnWidth, float tnHeight, int tnGridTop, int tnGridRight, int tnGridBottom, int tnGridLeft)
    {
        this(tnWidth, tnHeight, tnGridTop, tnGridRight, tnGridBottom, tnGridLeft, false);
    }

    /**
     * Creates a new instance of the scale effect arguments
     * @param tnWidth the width to scale to
     * @param tnHeight the height to scale to
     * @param tnGridTop the top of the scale 9 grid
     * @param tnGridRight the right of the scale 9 grid
     * @param tnGridBottom the bottom of the scale 9 grid
     * @param tnGridLeft the left of the scale 9 grid
     * @param tlAllowMultipass sets if this will allow a multipass
     */
    public ScaleEffectArguments(float tnWidth, float tnHeight, int tnGridTop, int tnGridRight, int tnGridBottom, int tnGridLeft, boolean tlAllowMultipass)
    {
        setWidth(tnWidth);
        setHeight(tnHeight);
        setGridTop(tnGridTop);
        setGridRight(tnGridRight);
        setGridBottom(tnGridBottom);
        setGridLeft(tnGridLeft);
        setAllowMultipass(tlAllowMultipass);
    }

    public final void setAllowMultipass(boolean tlMultipass)
    {
        setParameter("multipass", tlMultipass);
    }

    public final boolean getAllowMultipass()
    {
        return (Boolean)getParameter("multipass");
    }
    
    public final void setWidth(float tnWidth)
    {
        setParameter("width", Math.max(0, tnWidth));
    }
    
    public final void setHeight(float tnHeight)
    {
        setParameter("height", Math.max(0, tnHeight));
    }
    
    public final void setGridTop(int tnGridTop)
    {
        setParameter("gridTop", Math.max(0, tnGridTop));
    }
    
    public final void setGridRight(int tnGridRight)
    {
        setParameter("gridRight", Math.max(0, tnGridRight));
    }
    
    public final void setGridBottom(int tnGridBottom)
    {
        setParameter("gridBottom", Math.max(0, tnGridBottom));
    }
    
    public final void setGridLeft(int tnGridLeft)
    {
        setParameter("gridLeft", Math.max(0, tnGridLeft));
    }

    public float getWidth()
    {
        return ((Float)getParameter("width")).floatValue();
    }

    public float getHeight()
    {
        return ((Float)getParameter("height")).floatValue();
    }

    public int getGridTop()
    {
        return ((Integer)getParameter("gridTop")).intValue();
    }

    public int getGridRight()
    {
        return ((Integer)getParameter("gridRight")).intValue();
    }

    public int getGridBottom()
    {
        return ((Integer)getParameter("gridBottom")).intValue();
    }

    public int getGridLeft()
    {
        return ((Integer)getParameter("gridLeft")).intValue();
    }
}
