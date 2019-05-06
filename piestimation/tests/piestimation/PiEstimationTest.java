/*******************************************************************************
 * Copyright (C) 2019 Neeraj Bhusare (https://nbhusare.github.io/)
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package piestimation;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import piestimation.PiEstimation;

/**
 * Basic tests to check the estimated PI value
 */
public class PiEstimationTest {

	@Test
	public void testEstimatePiValue() {
		final int totalPoints = 1000000;
		final int radius = 25;

		double piValue = new PiEstimation().estimate(totalPoints, radius);
		assertEquals(Math.PI, piValue, 0.5);
	}

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testEstimatePiValuePassingNegativeTotalPoints() {
		final int totalPoints = -1;
		final int radius = 25;

		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Invalid param. TotalPoints have to be greater then 0");
		new PiEstimation().estimate(totalPoints, radius);
	}

	@Test
	public void estimatePiValuePassingNegativeCircleRadius() {
		final int totalPoints = 10000;
		final int radius = -1;

		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Invalid param. Radius of the circle has to be greater then 0");
		new PiEstimation().estimate(totalPoints, radius);
	}

}
