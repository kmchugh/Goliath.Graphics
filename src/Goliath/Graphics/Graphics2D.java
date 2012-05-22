/* ========================================================
 * Graphics2D.java
 *
 * Author:      admin
 * Created:     Aug 25, 2011, 11:53:33 AM
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

import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.RenderingHints.Key;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.text.AttributedCharacterIterator;
import java.util.Map;

/**
 * This class should be used for any drawing within the framework
 * This class will create a new graphics object for drawing on if required,
 * if not required this will allow you to draw directly on the underlying 
 * java Graphics2D.  Disposal of this class will dispose of any temporary 
 * graphics objects as well.
 *
 * @see         Related Class
 * @version     1.0 Aug 25, 2011
 * @author      admin
 **/
public class Graphics2D extends java.awt.Graphics2D
{
    private java.awt.Graphics2D m_oOriginalState;
    private java.awt.Graphics2D m_oCurrentState;
    
    /**
     * Creates a new instance of the graphics object
     * @param toGraphics  requires a reference to an existing graphics object
     */
    public Graphics2D(java.awt.Graphics2D toGraphics)
    {
        Goliath.Utilities.checkParameterNotNull("toGraphics", toGraphics);
        m_oOriginalState = toGraphics;
    }

    /**
     * This will get the correct internal graphics object
     * to delegate the calls to
     */
    private java.awt.Graphics2D getGraphics()
    {
        return m_oCurrentState == null ? m_oOriginalState : m_oCurrentState;
    }
    
    /**
     * Helper function that will create the temporary graphics state if needed
     */
    private void createTempGraphics()
    {
        if (m_oCurrentState == null)
        {
            m_oCurrentState = (java.awt.Graphics2D)m_oOriginalState.create();
        }
    }
    
    /**
     * Calling dispose on this will only dispose the temporary graphics object, 
     * the original object will not be dispose by this call.  This allows transparent
     * usage of this class in the framework.
     */
    @Override
    public void dispose()
    {
        if (m_oCurrentState != null)
        {
            m_oCurrentState.dispose();
        }
    }
    
    /**
     * The following methods will cause a temporary graphics state to be created
     */
    
    @Override
    public void setXORMode(Color color)
    {
        createTempGraphics();
        getGraphics().setXORMode(color);
    }

    @Override
    public void setPaintMode()
    {
        createTempGraphics();
        getGraphics().setPaintMode();
    }

    @Override
    public void setFont(Font font)
    {
        createTempGraphics();
        getGraphics().setFont(font);
    }

    @Override
    public void setColor(Color color)
    {
        createTempGraphics();
        getGraphics().setColor(color);
    }

    @Override
    public void setClip(Shape shape)
    {
        createTempGraphics();
        getGraphics().setClip(shape);
    }

    @Override
    public void setClip(int i, int i1, int i2, int i3)
    {
        createTempGraphics();
        getGraphics().setClip(i, i1, i2, i3);
    }
    
    @Override
    public void clipRect(int i, int i1, int i2, int i3)
    {
        createTempGraphics();
        getGraphics().clipRect(i, i1, i2, i3);
    }

    @Override
    public void clearRect(int i, int i1, int i2, int i3)
    {
        createTempGraphics();
        getGraphics().clearRect(i, i1, i2, i3);
    }

    @Override
    public void translate(double d, double d1)
    {
        createTempGraphics();
        getGraphics().translate(d, d1);
    }

    @Override
    public void translate(int i, int i1)
    {
        createTempGraphics();
        getGraphics().translate(i, i1);
    }

    @Override
    public void transform(AffineTransform at)
    {
        createTempGraphics();
        getGraphics().transform(at);
    }

    @Override
    public void shear(double d, double d1)
    {
        createTempGraphics();
        getGraphics().shear(d, d1);
    }

    @Override
    public void setTransform(AffineTransform at)
    {
        createTempGraphics();
        getGraphics().setTransform(at);
    }

    @Override
    public void setStroke(Stroke stroke)
    {
        createTempGraphics();
        getGraphics().setStroke(stroke);
    }

    @Override
    public void setRenderingHints(Map<?, ?> map)
    {
        createTempGraphics();
        getGraphics().setRenderingHints(map);
    }

    @Override
    public void setRenderingHint(Key key, Object o)
    {
        createTempGraphics();
        getGraphics().setRenderingHint(key, o);
    }

    @Override
    public void setPaint(Paint paint)
    {
        createTempGraphics();
        getGraphics().setPaint(paint);
    }

    @Override
    public void setComposite(Composite cmpst)
    {
        createTempGraphics();
        getGraphics().setComposite(cmpst);
    }

    @Override
    public void setBackground(Color color)
    {
        createTempGraphics();
        getGraphics().setBackground(color);
    }

    @Override
    public void scale(double d, double d1)
    {
        createTempGraphics();
        getGraphics().scale(d, d1);
    }

    @Override
    public void rotate(double d, double d1, double d2)
    {
        createTempGraphics();
        getGraphics().rotate(d, d1, d2);
    }

    @Override
    public void rotate(double d)
    {
        createTempGraphics();
        getGraphics().rotate(d);
    }
    
    @Override
    public void clip(Shape shape)
    {
        createTempGraphics();
        getGraphics().clip(shape);
    }

    
    
    /***
     * All of the methods below are straight delegation, no additional features added
     */
    
    @Override
    public int hashCode()
    {
        return getGraphics().hashCode();
    }

    @Override
    public boolean equals(Object o)
    {
        return getGraphics().equals(o);
    }

    @Override
    public String toString()
    {
        return getGraphics().toString();
    }
    
    @Override
    public boolean hitClip(int i, int i1, int i2, int i3)
    {
        return getGraphics().hitClip(i, i1, i2, i3);
    }

    @Override
    public FontMetrics getFontMetrics(Font font)
    {
        return getGraphics().getFontMetrics(font);
    }

    @Override
    public FontMetrics getFontMetrics()
    {
        return getGraphics().getFontMetrics();
    }

    @Override
    public Font getFont()
    {
        return getGraphics().getFont();
    }

    @Override
    public Color getColor()
    {
        return getGraphics().getColor();
    }

    @Override
    public Rectangle getClipRect()
    {
        return getGraphics().getClipRect();
    }

    @Override
    public Rectangle getClipBounds(Rectangle rctngl)
    {
        return getGraphics().getClipBounds(rctngl);
    }

    @Override
    public Rectangle getClipBounds()
    {
        return getGraphics().getClipBounds();
    }

    @Override
    public Shape getClip()
    {
        return getGraphics().getClip();
    }

    @Override
    public void fillRoundRect(int i, int i1, int i2, int i3, int i4, int i5)
    {
        getGraphics().fillRoundRect(i, i1, i2, i3, i4, i5);
    }

    @Override
    public void fillRect(int i, int i1, int i2, int i3)
    {
        getGraphics().fillRect(i, i1, i2, i3);
    }

    @Override
    public void fillPolygon(Polygon plgn)
    {
        getGraphics().fillPolygon(plgn);
    }

    @Override
    public void fillPolygon(int[] ints, int[] ints1, int i)
    {
        getGraphics().fillPolygon(ints, ints1, i);
    }

    @Override
    public void fillOval(int i, int i1, int i2, int i3)
    {
        getGraphics().fillOval(i, i1, i2, i3);
    }

    @Override
    public void fillArc(int i, int i1, int i2, int i3, int i4, int i5)
    {
        getGraphics().fillArc(i, i1, i2, i3, i4, i5);
    }

    @Override
    public void drawRoundRect(int i, int i1, int i2, int i3, int i4, int i5)
    {
        getGraphics().drawRoundRect(i, i1, i2, i3, i4, i5);
    }

    @Override
    public void drawRect(int i, int i1, int i2, int i3)
    {
        getGraphics().drawRect(i, i1, i2, i3);
    }

    @Override
    public void drawPolyline(int[] ints, int[] ints1, int i)
    {
        getGraphics().drawPolyline(ints, ints1, i);
    }

    @Override
    public void drawPolygon(Polygon plgn)
    {
        getGraphics().drawPolygon(plgn);
    }

    @Override
    public void drawPolygon(int[] ints, int[] ints1, int i)
    {
        getGraphics().drawPolygon(ints, ints1, i);
    }

    @Override
    public void drawOval(int i, int i1, int i2, int i3)
    {
        getGraphics().drawOval(i, i1, i2, i3);
    }

    @Override
    public void drawLine(int i, int i1, int i2, int i3)
    {
        getGraphics().drawLine(i, i1, i2, i3);
    }

    @Override
    public boolean drawImage(Image image, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7, Color color, ImageObserver io)
    {
        return getGraphics().drawImage(image, i, i1, i2, i3, i4, i5, i6, i7, color, io);
    }

    @Override
    public boolean drawImage(Image image, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7, ImageObserver io)
    {
        return getGraphics().drawImage(image, i, i1, i2, i3, i4, i5, i6, i7, io);
    }

    @Override
    public boolean drawImage(Image image, int i, int i1, int i2, int i3, Color color, ImageObserver io)
    {
        return getGraphics().drawImage(image, i, i1, i2, i3, color, io);
    }

    @Override
    public boolean drawImage(Image image, int i, int i1, Color color, ImageObserver io)
    {
        return getGraphics().drawImage(image, i, i1, color, io);
    }

    @Override
    public boolean drawImage(Image image, int i, int i1, int i2, int i3, ImageObserver io)
    {
        return getGraphics().drawImage(image, i, i1, i2, i3, io);
    }

    @Override
    public boolean drawImage(Image image, int i, int i1, ImageObserver io)
    {
        return getGraphics().drawImage(image, i, i1, io);
    }

    @Override
    public void drawChars(char[] chars, int i, int i1, int i2, int i3)
    {
        getGraphics().drawChars(chars, i, i1, i2, i3);
    }

    @Override
    public void drawBytes(byte[] bytes, int i, int i1, int i2, int i3)
    {
        getGraphics().drawBytes(bytes, i, i1, i2, i3);
    }

    @Override
    public void drawArc(int i, int i1, int i2, int i3, int i4, int i5)
    {
        getGraphics().drawArc(i, i1, i2, i3, i4, i5);
    }

    @Override
    public Graphics create(int i, int i1, int i2, int i3)
    {
        return getGraphics().create(i, i1, i2, i3);
    }

    @Override
    public Graphics create()
    {
        return getGraphics().create();
    }

    @Override
    public void copyArea(int i, int i1, int i2, int i3, int i4, int i5)
    {
        getGraphics().copyArea(i, i1, i2, i3, i4, i5);
    }

    @Override
    public boolean hit(Rectangle rctngl, Shape shape, boolean bln)
    {
        return getGraphics().hit(rctngl, shape, bln);
    }

    @Override
    public AffineTransform getTransform()
    {
        return getGraphics().getTransform();
    }

    @Override
    public Stroke getStroke()
    {
        return getGraphics().getStroke();
    }

    @Override
    public RenderingHints getRenderingHints()
    {
        return getGraphics().getRenderingHints();
    }

    @Override
    public Object getRenderingHint(Key key)
    {
        return getGraphics().getRenderingHint(key);
    }

    @Override
    public Paint getPaint()
    {
        return getGraphics().getPaint();
    }

    @Override
    public FontRenderContext getFontRenderContext()
    {
        return getGraphics().getFontRenderContext();
    }

    @Override
    public GraphicsConfiguration getDeviceConfiguration()
    {
        return getGraphics().getDeviceConfiguration();
    }

    @Override
    public Composite getComposite()
    {
        return getGraphics().getComposite();
    }

    @Override
    public Color getBackground()
    {
        return getGraphics().getBackground();
    }

    @Override
    public void fill3DRect(int i, int i1, int i2, int i3, boolean bln)
    {
        getGraphics().fill3DRect(i, i1, i2, i3, bln);
    }

    @Override
    public void fill(Shape shape)
    {
        getGraphics().fill(shape);
    }

    @Override
    public void drawString(AttributedCharacterIterator aci, float f, float f1)
    {
        getGraphics().drawString(aci, f, f1);
    }

    @Override
    public void drawString(AttributedCharacterIterator aci, int i, int i1)
    {
        getGraphics().drawString(aci, i, i1);
    }

    @Override
    public void drawString(String string, float f, float f1)
    {
        getGraphics().drawString(string, f, f1);
    }

    @Override
    public void drawString(String string, int i, int i1)
    {
        getGraphics().drawString(string, i, i1);
    }

    @Override
    public void drawRenderedImage(RenderedImage ri, AffineTransform at)
    {
        getGraphics().drawRenderedImage(ri, at);
    }

    @Override
    public void drawRenderableImage(RenderableImage ri, AffineTransform at)
    {
        getGraphics().drawRenderableImage(ri, at);
    }

    @Override
    public void drawImage(BufferedImage bi, BufferedImageOp bio, int i, int i1)
    {
        getGraphics().drawImage(bi, bio, i, i1);
    }

    @Override
    public boolean drawImage(Image image, AffineTransform at, ImageObserver io)
    {
        return getGraphics().drawImage(image, at, io);
    }

    @Override
    public void drawGlyphVector(GlyphVector gv, float f, float f1)
    {
        getGraphics().drawGlyphVector(gv, f, f1);
    }

    @Override
    public void draw3DRect(int i, int i1, int i2, int i3, boolean bln)
    {
        getGraphics().draw3DRect(i, i1, i2, i3, bln);
    }

    @Override
    public void draw(Shape shape)
    {
        getGraphics().draw(shape);
    }

    @Override
    public void addRenderingHints(Map<?, ?> map)
    {
        getGraphics().addRenderingHints(map);
    }
}
