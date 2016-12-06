
package com.sp2p.util;

import java.awt.image.BufferedImage;

import jp.sourceforge.qrcode.data.QRCodeImage;

public class QCodeImage implements QRCodeImage {

	BufferedImage bufImg;
	
	public QCodeImage(BufferedImage bufImg) {
		this.bufImg = bufImg;
	}
	
	@Override
	public int getHeight() {
		return bufImg.getHeight();
	}

	@Override
	public int getPixel(int x, int y) {
		return bufImg.getRGB(x, y);
	}

	@Override
	public int getWidth() {
		return bufImg.getWidth();
	}

}