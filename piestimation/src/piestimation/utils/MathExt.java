/*******************************************************************************
 * Copyright (C) 2019 Neeraj Bhusare (https://nbhusare.github.io/)
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package piestimation.utils;

/**
 * Extension to the {@code Math} class. Provides API for computing random values
 * that lie in a given range of values.
 */
public class MathExt {

	/**
	 * Computes a random value that lies between the {@code min} and {@code max}
	 * values.
	 */
	public static int random(int min, int max) {
		return min + (int) (Math.random() * ((max - min) + 1));
	}
}
