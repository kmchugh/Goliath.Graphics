/* ========================================================
 * Image.java
 *
 * Author:      kmchugh
 * Created:     Nov 3, 2010, 8:34:18 AM
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

import Goliath.Applications.Application;
import Goliath.Collections.HashTable;
import Goliath.Collections.List;
import Goliath.Constants.EventType;
import Goliath.Event;
import Goliath.EventDispatcher;
import Goliath.Exceptions.FileNotFoundException;
import Goliath.Graphics.Constants.ImageType;
import Goliath.Graphics.Effects.EffectArguments;
import Goliath.Graphics.Effects.ScaleEffect;
import Goliath.Graphics.Effects.ScaleEffectArguments;
import Goliath.Interfaces.Graphics.IEffect;
import Goliath.Interfaces.IDelegate;
import Goliath.Interfaces.IEventDispatcher;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.TimerTask;
import javax.imageio.ImageIO;


        
/**
 * Image is the representation of a graphical image
 *
 * @see         Related Class
 * @version     1.0 Nov 3, 2010
 * @author      kmchugh
**/
public class Image extends Goliath.Object
        implements IEventDispatcher<EventType, Event<Image>>
{
    /**
     * Stores information about the original image
     */
    private class SourceInfo
    {
        private int[] m_aPixels;
        private int m_nWidth;
        private int m_nHeight;
        private long m_nLastAccess;
        private ImageType m_oType;
        private BufferedImage m_oImage;

        /**
         * Creates a new Source Image
         * @param toImage the buffered image to create the source info from
         */
        public SourceInfo(BufferedImage toImage)
        {   
            m_oImage = toImage;
            update(toImage);
        }
        
        /**
         * Creates a new instance of SourceImage
         */
        public SourceInfo()
        {   
        }
        
        /**
         * Updates the parameters based on the buffered image passed in
         * @param toImage the buffered image to get the measurement from
         */
        private void update(BufferedImage toImage)
        {
            m_nWidth = m_oImage.getWidth();
            m_nHeight = m_oImage.getHeight();
            int lnType = m_oImage.getType();
            m_oType = (lnType == BufferedImage.TYPE_INT_RGB) ? ImageType.RGB() : ImageType.ARGB();
        }

        /**
         * Gets the type of this image
         * @return the image type
         */
        public ImageType getType()
        {
            return m_oType;
        }

        /**
         * Gets the pixel data for this image
         * @return the image data for this image
         */
        public int[] getPixels()
        {
            if (m_aPixels == null)
            {
                m_aPixels = Goliath.Graphics.Utilities.getPixelData(m_oImage);
                m_oImage.flush();
                m_oImage = null;
            }
            touch();
            return m_aPixels;
        }

        /**
         * Gets the width of the image
         * @return the image width
         */
        public int getWidth()
        {
            return m_nWidth;
        }

        /**
         * Gets the height of this image
         * @return the image height
         */
        public int getHeight()
        {
            return m_nHeight;
        }
        
        /**
         * Marks the last access date as now, prevents this image being cleaned
         * up until a later cycle
         */
        public void touch()
        {
            m_nLastAccess = new Date().getTime();
            if (m_oImage == null)
            {
                try
                {
                    m_oImage = createCompatibleImage(ImageIO.read(m_oSource));
                    update(m_oImage);
                }
                catch (Throwable ex)
                {
                    Application.getInstance().log(ex);
                }
            }
        }
        
        /**
         * Gets the last time this image data was accessed
         * @return the last access time
         */
        public long getLastAccess()
        {
            return m_nLastAccess;
        }
    }
    
    private static HashTable<String, SourceInfo> g_oImageCache;
    private static boolean g_lCleaningScheduled;
    
    /**
     * Cleans up any images that are not being used
     */
    private static void scheduleCleanup()
    {
        if(!g_lCleaningScheduled)
        {
            g_lCleaningScheduled = true;
            
            Application.getInstance().scheduleTask(new TimerTask() {
                    @Override
                    public void run()
                    {
                        if (g_oImageCache != null)
                        {
                            List<String> loRemove = new List<String>();
                            List<String> loKeys = new List<String>(g_oImageCache.keySet());
                            long lnTime = new Date().getTime();
                            for (String lcKey : loKeys)
                            {
                                SourceInfo loInfo = g_oImageCache.get(lcKey);
                                if (lnTime - loInfo.getLastAccess() > 30000)
                                {
                                    loRemove.add(lcKey);
                                }
                            }
                            synchronized(g_oImageCache)
                            {
                                for (String lcKey: loRemove)
                                {
                                    g_oImageCache.remove(lcKey);
                                }
                            }
                        }
                        else
                        {
                            g_lCleaningScheduled = false;
                        }
                    }
                }, 30000);
        }
    }


    private BufferedImage m_oProcessedImage;
    private List<IEffect> m_oEffects;
    private HashTable<IEffect, EffectArguments> m_oEffectArguments;

    private ScaleEffect m_oScaleEffect;
    private ScaleEffectArguments m_oScaleEffectArgs;
    
    private boolean m_lScaleable;
    private boolean m_lMaintainRatio;
    private Dimension m_oSize;
    private int[] m_aScale9Grid;
    private File m_oSource;
    private SourceInfo m_oSourceImage;
    private EventDispatcher<EventType, Event<Image>> m_oEventDispatcher;


    /**
     * Creates a new instance of image from the source specified
     * @param toSource the original source of the image
     * @throws FileNotFoundException if the source file is not found
     */
    public Image(File toSource)
            throws FileNotFoundException
    {
        if (toSource.exists())
        {
            m_oSource = toSource;
        }
        else
        {
            throw new FileNotFoundException(toSource);
        }
    }

    /**
     * Creates a new image from the source specified
     * @param tcSource the full path and file name of the source file
     * @throws FileNotFoundException if the file is not found
     */
    public Image(String tcSource)
            throws FileNotFoundException
    {
        this(new Goliath.IO.File(tcSource));
    }

    /**
     * Creates a new image from the buffered image passed in
     * @param toImage the source image to use
     */
    public Image(BufferedImage toImage)
    {
        m_oSourceImage = new SourceInfo(toImage);
        m_oSize = new Dimension(m_oSourceImage.getWidth(), m_oSourceImage.getHeight());
    }
    
    public void draw(Graphics2D toGraphics, Point toLocation)
    {
        toGraphics.drawImage(getImage(), toLocation.getIntX(), toLocation.getIntY(), null);
    }
    
    
    /**
     * Converts this image to a compatible image, or returns the image if it is already compatible
     * @param toImage the image to make compatible
     * @return the compatible version of the image
     */
    private BufferedImage createCompatibleImage(BufferedImage toImage)
    {
        GraphicsConfiguration loGC = Goliath.Graphics.Utilities.getDefaultGraphicsConfig();
        if (toImage.getColorModel().equals(loGC.getColorModel()))
        {
            // Already compatible, no need to do anything
            return toImage;
        }
        
        BufferedImage loImage = loGC.createCompatibleImage(toImage.getWidth(), toImage.getHeight(), toImage.getTransparency());
        // Copy the source to the destination
        Graphics loGraphics = loImage.createGraphics();
        loGraphics.drawImage(toImage, 0, 0, null);
        loGraphics.dispose();
        
        return loImage;
    }



    /**
     * Gets the cached version of the image data, this cached data is shared against all sources
     * that are the same
     * @return the cached image data
     */
    private SourceInfo getCachedImage()
    {
        if (m_oSourceImage != null)
        {
            return m_oSourceImage;
        }
        
        if (g_oImageCache == null)
        {
            g_oImageCache = new HashTable<String, SourceInfo>();
        }
        
        try
        {
            String lcKey = m_oSource.getCanonicalPath().toLowerCase();
            if (!g_oImageCache.containsKey(lcKey))
            {
                BufferedImage loImage = createCompatibleImage(ImageIO.read(m_oSource));
                synchronized(g_oImageCache)
                {
                    SourceInfo loInfo = new SourceInfo();
                    g_oImageCache.put(lcKey, loInfo);
                    loInfo.touch();
                }
                scheduleCleanup();
            }

            SourceInfo loImage = g_oImageCache.get(lcKey);
            loImage.touch();
            
            if (m_oSize == null)
            {
                m_oSize = new Dimension(loImage.getWidth(), loImage.getHeight());
            }
            return loImage;
        }
        catch(Throwable ex)
        {
            // TODO : Catch file not found and IOException and fill image with relevant graphic
            Application.getInstance().log(ex);
        }
        return null;
    }

    /**
     * Gets the height of this image
     * @return the height of the image
     */
    protected float getHeight()
    {
        SourceInfo loOriginal = getCachedImage();

        float lnWidth = m_oSize.getWidth();
        float lnHeight = m_oSize.getHeight();
        return m_lScaleable ?
                m_lMaintainRatio ?
                    (lnWidth < lnHeight) ? (lnWidth/loOriginal.getWidth()) * loOriginal.getHeight() : lnHeight
                    : lnHeight : loOriginal.getHeight();
    }

    /**
     * Gets the width of this image
     * @return the width of the image
     */
    protected float getWidth()
    {
        SourceInfo loOriginal = getCachedImage();

        float lnWidth = m_oSize.getWidth();
        float lnHeight = m_oSize.getHeight();
        return m_lScaleable ?
                m_lMaintainRatio ?
                    (lnWidth < lnHeight) ? lnWidth : (lnHeight/loOriginal.getHeight()) * loOriginal.getWidth()
                    : lnWidth : loOriginal.getWidth();
    }

    /**
     * Sets the height of this image
     * @param tnHeight the new height
     */
    public void setHeight(float tnHeight)
    {
        setSize(new Dimension((m_oSize == null ? 0 : m_oSize.getWidth()), tnHeight));
    }

    /**
     * Sets the width of this image
     * @param tnWidth the new height
     */
    public void setWidth(float tnWidth)
    {
        setSize(new Dimension(tnWidth, (m_oSize == null ? 0 : m_oSize.getHeight())));
    }

    /**
     * Sets the new size of this image
     * @param toSize the new size
     */
    public void setSize(Dimension toSize)
    {
        if (m_oSize != toSize)
        {
            m_oSize = toSize;
            corePropertyChanged();
        }
    }

    /**
     * Gets the size of this image
     * @return the new size of the image
     */
    public Dimension getSize()
    {
        return new Dimension(getWidth(), getHeight());
    }

    /**
     * This is called when any core property has changed to clear the rendered image cache
     */
    private void corePropertyChanged()
    {
        if (m_oProcessedImage != null)
        {
            m_oProcessedImage.flush();
        }

        // Clear the processed image as something has changed that will require it to be recreated
        m_oProcessedImage = null;

        // TODO: Rescale the image if required, clearing out any old scales.  (Just modify the scale transform)
        if (m_oScaleEffect != null)
        {
            m_oScaleEffectArgs.setWidth(getWidth());
            m_oScaleEffectArgs.setHeight(getHeight());
            m_oScaleEffectArgs.setGridTop(getScale9Top());
            m_oScaleEffectArgs.setGridRight(getScale9Right());
            m_oScaleEffectArgs.setGridBottom(getScale9Bottom());
            m_oScaleEffectArgs.setGridLeft(getScale9Left());
        }

        fireEvent(EventType.ONCHANGED(), new Event<Image>(this));
    }

    /**
     * Checks if this image has already been processed and has an image stored instead of needing to redraw
     * @return true if there is a preprocessed image prepared
     */
    public boolean isProcessed()
    {
        return m_oProcessedImage != null;
    }

    /**
     * Gets the image with all effects applied
     * @return the image with all effects applied
     */
    public BufferedImage getImage()
    {
        if (m_oProcessedImage == null)
        {
            createProcessedImage();
        }
        return m_oProcessedImage;
    }

    /**
     * Checks if this image can be scaled or not
     * @return true if this image can be resized
     */
    public boolean isScaleable()
    {
        return m_lScaleable;
    }

    /**
     * Sets if this image can be scaled or not
     * @param tlScaleable true to allow resizing, false otherwise
     */
    public void setScaleable(boolean tlScaleable)
    {
        m_lScaleable = tlScaleable;
        if (m_lScaleable && m_oScaleEffect == null)
        {
            // Create the Scale effect and add it
            m_oScaleEffect = new ScaleEffect();
            m_oScaleEffectArgs = new ScaleEffectArguments(getWidth(),
                            getHeight(),
                            getScale9Top(),
                            getScale9Right(),
                            getScale9Bottom(),
                            getScale9Left());

            addEffect(m_oScaleEffect, m_oScaleEffectArgs);
        }

        if (!m_lScaleable && m_oScaleEffect != null)
        {
            removeEffect(m_oScaleEffect);
            m_oScaleEffect = null;
        }
    }

    /**
     * Sets if this image should maintain the aspect ratio when scaling
     * @param tlMaintainRatio true to maintain the aspect ratio
     */
    public void setMaintainRatio(boolean tlMaintainRatio)
    {
        m_lMaintainRatio = tlMaintainRatio;
    }

    /**
     * Checks if this image will maintain the aspect ration when scaling
     * @return true if the image will maintain the aspect ratio
     */
    public boolean maintainRatio()
    {
        return m_lMaintainRatio;
    }

    /**
     * Adds an effect to the rendering pipeline
     * @param toEffect the effect to add to the pipeline
     * @param toArgs the arguments for the effect
     */
    public void addEffect(IEffect toEffect, EffectArguments toArgs)
    {
        if (m_oEffects == null)
        {
            m_oEffects = new List<IEffect>();
        }
        if (m_oEffectArguments == null)
        {
            m_oEffectArguments = new HashTable<IEffect, EffectArguments>();
        }

        boolean llAdded = false;

        if (!m_oEffects.contains(toEffect))
        {
            llAdded = m_oEffects.add(toEffect) || llAdded;
        }

        // No need to check if the effect argument already exists, this will force an update
        llAdded = m_oEffectArguments.put(toEffect, toArgs) != toArgs || llAdded;

        if (llAdded)
        {
            corePropertyChanged();
        }
    }

    /**
     * Removes an effect for the rendering pipeline
     * @param toEffect the effect to remove
     */
    public void removeEffect(IEffect toEffect)
    {
        if (m_oEffects != null)
        {
            m_oEffects.remove(toEffect);
        }

        if (m_oEffectArguments != null)
        {
            if (m_oEffectArguments.remove(toEffect) != null)
            {
                corePropertyChanged();
            }
        }
    }

    /**
     * Clears all of the effects that have been placed on this image
     */
    public void clearEffects()
    {
        if (m_oEffects != null)
        {
            m_oEffects.clear();
        }
        if (m_oEffectArguments != null)
        {
            m_oEffectArguments.clear();
        }
        corePropertyChanged();
    }

    /**
     * Checks if the rendering pipeline contains the specified effect
     * @param toEffect the effect to check for
     * @return true if the effect is in the pipeline
     */
    public boolean hasEffect(IEffect toEffect)
    {
        return m_oEffects != null && m_oEffects.contains(toEffect);
    }

    /**
     * Flattens the rendering pipeline
     */
    public void flatten()
    {
        // TODO : Write this back as a source info and a compatible image so that it can be blitted faster
        if (m_oProcessedImage != null)
        {
            m_oSourceImage = new SourceInfo(m_oProcessedImage);
            m_oSource = null;
            m_oEffects = null;
        }
    }

    /**
     * Invalidates the image that has been calculated
     */
    public void invalidate()
    {
        m_oProcessedImage = null;
        corePropertyChanged();
    }

    /**
     * Creates the renderable image from the render pipleline and original source
     */
    private void createProcessedImage()
    {
        if (m_oProcessedImage != null)
        {
            m_oProcessedImage.flush();
        }

        // If this is empty then there is no processing to do
        if (getSize().equals(Dimension.EMPTYSIZE()))
        {
            m_oProcessedImage = Goliath.Graphics.Utilities.createCompatibleTranslucentImage(1, 1);
            return;
        }
        
        // If there are no effects, just use the source image
        if(m_oScaleEffect == null || 
                (m_oScaleEffect != null &&
                (((ScaleEffectArguments)getEffectArgument(m_oScaleEffect)).getWidth() == getCachedImage().m_oImage.getWidth() &&
                ((ScaleEffectArguments)getEffectArgument(m_oScaleEffect)).getHeight() == getCachedImage().m_oImage.getHeight())) && 
                (m_oEffects == null || m_oEffects.size() == 0))
        {
            m_oProcessedImage = getCachedImage().m_oImage;
        }
        else
        {
            SourceInfo loInfo = getCachedImage();
            int[] laOriginal = loInfo.getPixels();
            int[] laProcessPixels = Arrays.copyOf(laOriginal, laOriginal.length);

            // Always process the Scale Effect first
            if (m_oScaleEffect != null)
            {
                laProcessPixels = m_oScaleEffect.process(laProcessPixels, loInfo.getWidth(), loInfo.getHeight(), (ScaleEffectArguments)getEffectArgument(m_oScaleEffect));
            }

            Dimension loDim = getSize();
            int lnWidth = (int) loDim.getWidth();
            int lnHeight = (int) loDim.getHeight();

            if (m_oEffects != null)
            {
                synchronized(m_oEffects)
                {
                    List<IEffect>  loEffects = new List<IEffect>(m_oEffects);
                    for (IEffect loEffect : loEffects)
                    {
                        if (loEffect != m_oScaleEffect)
                        {
                            laProcessPixels = loEffect.process(laProcessPixels, lnWidth, lnHeight, getEffectArgument(loEffect));
                        }
                    }
                }
            }

            // Create the final image
            m_oProcessedImage = new BufferedImage(lnWidth, lnHeight, (loInfo.getType().equals(ImageType.RGB()) ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB));
            WritableRaster loRaster = m_oProcessedImage.getRaster();
            loRaster.setDataElements(0, 0, lnWidth, lnHeight, laProcessPixels);
        }
    }
    
    /**
     * Gets the arguments for the specified effect
     * @param toEffect the effect to get the arguments for
     * @return the effect arguments
     */
    private EffectArguments getEffectArgument(IEffect toEffect)
    {
        return toEffect == null || m_oEffectArguments == null ? null : m_oEffectArguments.get(toEffect);
    }

    /**
     * Clears the images and all resource buffers
     */
    public void flush()
    {
        if (m_oProcessedImage != null)
        {
            m_oProcessedImage.flush();
            m_oProcessedImage = null;
        }

        if (m_oSourceImage != null)
        {
            m_oSourceImage = null;
        }
    }









    public void setScale9Grid(int tnTop, int tnRight, int tnBottom, int tnLeft)
    {
        if (m_aScale9Grid == null)
        {
            m_aScale9Grid = new int[4];
        }
        m_aScale9Grid[0] = tnTop;
        m_aScale9Grid[1] = tnRight;
        m_aScale9Grid[2] = tnBottom;
        m_aScale9Grid[3] = tnLeft;

        corePropertyChanged();
    }

    public int getScale9Top()
    {
        return m_aScale9Grid == null ? 0 : m_aScale9Grid[0];
    }

    public int getScale9Right()
    {
        return m_aScale9Grid == null ? 0 : m_aScale9Grid[1];
    }

    public int getScale9Bottom()
    {
        return m_aScale9Grid == null ? 0 : m_aScale9Grid[2];
    }

    public int getScale9Left()
    {
        return m_aScale9Grid == null ? 0 : m_aScale9Grid[3];
    }



    @Override
    public void suppressEvents(boolean tlSuppress)
    {
        if (m_oEventDispatcher == null)
        {
            return;
        }
        m_oEventDispatcher.suppressEvents(tlSuppress);
    }

    @Override
    public boolean removeEventListener(EventType toEvent, IDelegate toCallback)
    {
        return (m_oEventDispatcher != null) ? m_oEventDispatcher.removeEventListener(toEvent, toCallback) : false;
    }

    @Override
    public boolean hasEventsFor(EventType toEvent)
    {
        return (m_oEventDispatcher != null) ? m_oEventDispatcher.hasEventsFor(toEvent) : false;
    }

    @Override
    public void fireEvent(EventType toEventType, Event<Image> toEvent)
    {
        if (m_oEventDispatcher == null)
        {
            return;
        }
        m_oEventDispatcher.fireEvent(toEventType, toEvent);
    }

    @Override
    public boolean clearEventListeners(EventType toEvent)
    {
        return (m_oEventDispatcher != null) ? m_oEventDispatcher.clearEventListeners(toEvent) : false;
    }

    @Override
    public boolean clearEventListeners()
    {
        return (m_oEventDispatcher != null) ? m_oEventDispatcher.clearEventListeners() : false;
    }

    @Override
    public boolean areEventsSuppressed()
    {
        return (m_oEventDispatcher != null) ? m_oEventDispatcher.areEventsSuppressed() : false;
    }

    @Override
    public boolean addEventListener(EventType toEvent, IDelegate toCallback)
    {
        if (m_oEventDispatcher == null)
        {
            m_oEventDispatcher = new EventDispatcher<EventType, Event<Image>>();
        }
        return m_oEventDispatcher.addEventListener(toEvent, toCallback);
    }

}
