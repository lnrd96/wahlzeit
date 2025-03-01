/*
 * SPDX-FileCopyrightText: 2006-2009 Dirk Riehle <dirk@riehle.org> https://dirkriehle.com
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */

package org.wahlzeit.model;

import java.io.*;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;

import org.wahlzeit.services.*;

/**
 * PhotoUtil provides a set of utility functions to create defined images.
 * Images are created from a source in different sizes as needed by the app.
 */
public class PhotoUtil {
	
	/**
	 * 
	 */
	public static Photo createPhoto(File source, PhotoId id) throws Exception {
		Photo result = DogPhotoFactory.getInstance().createPhoto(id);
		
		Image sourceImage = createImageFiles(source, id);

		int sourceWidth = sourceImage.getWidth(null);
		int sourceHeight = sourceImage.getHeight(null);
		result.setWidthAndHeight(sourceWidth, sourceHeight);

		return result;
	}
	
	/**
	 * 
	 */
	public static Image createImageFiles(File source, PhotoId id) throws Exception {
		Image sourceImage = ImageIO.read(source);
		assertIsValidImage(sourceImage);

		int sourceWidth = sourceImage.getWidth(null);
		int sourceHeight = sourceImage.getHeight(null);
		assertHasValidSize(sourceWidth, sourceHeight);
		
		for (PhotoSize size : PhotoSize.values()) {
			if (!size.isWiderAndHigher(sourceWidth, sourceHeight)) {
				createImageFile(sourceImage, id, size);
			}
		}
		
		return sourceImage;
	}
	
	/**
	 * 
	 */
	protected static void createImageFile(Image source, PhotoId id, PhotoSize size) throws Exception {	
		int sourceWidth = source.getWidth(null);
		int sourceHeight = source.getHeight(null);
		
		int targetWidth = size.calcAdjustedWidth(sourceWidth, sourceHeight);
		int targetHeight = size.calcAdjustedHeight(sourceWidth, sourceHeight);

		BufferedImage targetImage = scaleImage(source, targetWidth, targetHeight);
		File target = new File(SysConfig.getPhotosDir().asString() + File.separator + id.asString() + size.asInt() + ".jpg");
		ImageIO.write(targetImage, "jpg", target);

		SysLog.logSysInfo("created image file for id: " + id.asString() + " of size: " + size.asString());
	}

	/**
	 * 
	 */
	protected static BufferedImage scaleImage(Image source, int width, int height) {
		source = source.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = result.createGraphics();
		g2d.setBackground(Color.WHITE);
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		g2d.drawImage(source, 0, 0, null);
		return result;
	}
	
	/**
	 * @methodtype assertion 
	 */
	protected static void assertIsValidImage(Image image) {
		if (image == null) {
			throw new IllegalArgumentException("Not a valid photo!");
		}
	}

	/**
	 * 
	 */
	protected static void assertHasValidSize(int cw, int ch) {
		if (PhotoSize.THUMB.isWiderAndHigher(cw, ch)) {
			throw new IllegalArgumentException("Photo too small!");
		}
	}

}
