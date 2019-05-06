/*******************************************************************************
 * Copyright (C) 2019 Neeraj Bhusare (https://nbhusare.github.io/)
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package piestimation;

/**
 * Represents a {@code Point} inside or outside a Circle with {@code radius}.
 * The method {@code isInsideCircle} can be used to check if the {@code Point}
 * lies inside the circle.
 * 
 * The class allows the creation of immutable {@code Point} objects. It also
 * endorsed a strategy {@code isInsideCircle} to check if the point lies inside
 * the circle.
 * 
 */
public class Point {

	private int xPoz;

	private int yPoz;

	public Point(int xPoz, int yPoz) {
		this.xPoz = xPoz;
		this.yPoz = yPoz;
	}

	public int getxPoz() {
		return xPoz;
	}

	public int getyPoz() {
		return yPoz;
	}

	public boolean isInsideCircle(int radius) {
		return Math.hypot(getxPoz(), getyPoz()) <= radius;
	}
}
