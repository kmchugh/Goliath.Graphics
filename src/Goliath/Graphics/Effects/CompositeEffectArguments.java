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

import Goliath.Exceptions.FileNotFoundException;
import Goliath.Graphics.Image;
import Goliath.Graphics.Point;
import java.io.File;


        
/**
 * Class Description.
 * For example:
 * <pre>
 *      Example usage
 * </pre>
 *
 * @see         Related Class
 * @version     1.0 Jul 11, 2011
 * @author      kmchugh
**/
public class CompositeEffectArguments extends EffectArguments
{
    /**
     * Creates a new instance of the composite effect arguments
     * @param toImage   the image to blend over
     * @param tnAlpha the transparency value to blend based on
     * @param toOffset the offset position
     */
    public CompositeEffectArguments(Image toImage, float tnAlpha, Point toOffset)
    {
        setOffset(toOffset);
        setAlpha(tnAlpha);
        setImage(toImage);
    }
    
    
    /**
     * Creates a new instance of the composite effect arguments
     * @param tnImg   the image to blend over
     * @param tnAlpha the transparency value to blend based on
     */
    public CompositeEffectArguments(Image toImg, float tnAlpha)
    {
        this(toImg, tnAlpha, new Point(0,0));
    }
    
    /**
     * Creates a new instance of the composite effect arguments
     * @param tnImg   the image to blend over
     * @param tnAlpha the transparency value to blend based on
     */
    public CompositeEffectArguments(File toImg, float tnAlpha)
            throws FileNotFoundException
    {
        this(new Image(toImg), tnAlpha, new Point(0,0));
    }
    
    /**
     * Creates a new instance of the composite effect arguments
     * @param tnImg   the image to blend over
     * @param tnAlpha the transparency value to blend based on
     * @param tnX     the X index in the original image to overlay from
     * @param tnY     the Y index in the original image to overlay from
     */
    public CompositeEffectArguments(File toImg, float tnAlpha, Point toOffset)
            throws FileNotFoundException
    {
        this(new Image(toImg), tnAlpha, toOffset);
    }
    
    public final void setOffset(Point toOffset)
    {
    	setParameter("Offset", toOffset);
    }
    
    public final Point getOffset()
    {
    	return (Point)getParameter("Offset");
    }
    
    public final void setImage(Image toImg)
    {
    	setParameter("image", toImg);
    }
    
    public final Image getImage()
    {
    	return (Image)getParameter("image");
    }
    
    public final void setAlpha(float tnAlpha)
    {
    	setParameter("alpha", tnAlpha);
    }
    
    public final Float getAlpha()
    {
    	return (Float)getParameter("alpha");
    }    
}
