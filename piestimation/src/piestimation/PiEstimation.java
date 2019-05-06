/*******************************************************************************
 * Copyright (C) 2019 Neeraj Bhusare (https://nbhusare.github.io/)
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package piestimation;

import static piestimation.utils.MathExt.random;

import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicInteger;

import piestimation.utils.PointPozCheckTasks;

/**
 * Calculates the approximate value of PI using the Monte Carlo method. It makes
 * use of the {@code MathExt} to generate random points that lie in a given
 * range. The counter {@code totalPointsInCircle} is incremented for every value
 * that lies inside the circle of given {@code radius}. It is then used for
 * calculating the value of PI.
 */
public class PiEstimation {

	/**
	 * The {@code totalPointsInCircle} is incremented concurrently by multiple
	 * threads from the {@code ForkJoin} pool. By using {@code AtomicInteger}, we
	 * make sure that the increment operation in carried out in a thread-safe manor.
	 */
	private final static AtomicInteger totalPointsInCircle = new AtomicInteger(0);

	/**
	 * Computes a {@code Point} that lies between the -{@code radius} and
	 * {@code radius}. A check is done to verify if the {@code Point} lies inside
	 * the circle with given {@code radius}. The {@code totalPointsInCircle} is
	 * incremented if the check passes.
	 * 
	 * The {@code PointPozCheckTask} is executed within a {@code ForkJoinPool}.
	 */
	public static class PointPozCheckTask extends RecursiveAction {

		private static final long serialVersionUID = 1L;

		private final int radius;

		public PointPozCheckTask(int radius) {
			this.radius = radius;
		}

		@Override
		protected void compute() {
			// Compute the values of x and y
			int xPoz = random(-radius, radius);
			int yPoz = random(-radius, radius);

			// Check if the point lies inside the circle and increment the
			// totalPointsInCircle
			if (new Point(xPoz, yPoz).isInsideCircle(radius)) {
				totalPointsInCircle.incrementAndGet();
			}
		}
	}

	public double estimate(int totalPoints, int radius) {
		if (totalPoints <= 0) {
			throw new IllegalArgumentException("Invalid param. TotalPoints have to be greater then 0");
		}

		if (radius <= 0) {
			throw new IllegalArgumentException("Invalid param. Radius of the circle has to be greater then 0");
		}

		final PointPozCheckTasks parallelTasks = new PointPozCheckTasks(totalPoints, radius);

		// Invoke all {@code PointPozCheckTasks} tasks in parallel
		parallelTasks.invokeAll();

		// Wait for all the tasks to complete
		parallelTasks.joinAll();

		// Compute the approximate value of PI. It is equal to - 4 * (the total number
		// of points in the circle / total number of points)
		return 4.0 * (totalPointsInCircle.doubleValue() / totalPoints);

	}

}
