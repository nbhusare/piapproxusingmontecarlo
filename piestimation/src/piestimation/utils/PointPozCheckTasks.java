/*******************************************************************************
 * Copyright (C) 2019 Neeraj Bhusare (https://nbhusare.github.io/)
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package piestimation.utils;

import java.util.ArrayList;
import java.util.concurrent.RecursiveAction;

import piestimation.PiEstimation.PointPozCheckTask;

/**
 * This class represents a collection of {@code PointPozCheckTask} tasks.
 * It provides method {@code #invokeAll} for invoking all the
 * {@code PointPozCheckTask}'s in parallel. The method {@code #joinAll} can
 * be called to put the main thread in `waiting` state, until all the
 * {@code PointPozCheckTask}'s are completed.
 *
 * By extending {@code ArrayList}, we have effectively defined a domain-specific
 * {@code Collection}. This pattern greatly improves the readability of code and
 * also allows defining API that can be used with the collection.
 * 
 * <pre>
 * {
 * 	&#64;code
 * 	final PointPozCheckTasks parallelTasks = new PointPozCheckTasks(totalPoints, radius);
 * 	parallelTasks.invokeAll();
 * 	parallelTasks.joinAll();
 * }
 * </pre>
 */
public class PointPozCheckTasks extends ArrayList<PointPozCheckTask> {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates {@code totalTasks} instances of {@code PointPozCheckTask}
	 * 
	 * @param totalTasks total number of {@code PointPozCheckTask} to be
	 *                   created
	 * @param radius     the radius of the circle
	 */
	public PointPozCheckTasks(int totalTasks, int radius) {
		for (int i = 0; i < totalTasks; i++) {
			add(new PointPozCheckTask(radius));
		}
	}

	/**
	 * Executes the tasks on multiple threads enabling them to be executed on
	 * multiple cores. Each thread has a queue of tasks to be completed, and upon
	 * completion of its tasks (i.e. empty queue), the thread will steal work from
	 * another thread queue, making a more efficient use of the CPU processing
	 * power.
	 */
	public void invokeAll() {
		RecursiveAction.invokeAll(this);
	}

	/**
	 * Moves the calling thread into waiting state until all the tasks are completed
	 * or an exception is thrown during the execution.
	 * 
	 * To ensure correct behavior, it is essential for the calling (i.e main) thread
	 * to call {@code #joinAll()} after calling {@code #invokeAll()}.
	 */
	public void joinAll() {
		stream().forEach(//
				task -> task.join()//
		);
	}
}
