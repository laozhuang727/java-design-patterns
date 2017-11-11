/**
 * The MIT License
 * Copyright (c) 2014-2016 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.ryan;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Producer Consumer Design pattern is a classic concurrency or threading pattern which reduces coupling between
 * Producer and Consumer by separating Identification of work with Execution of Work.
 * <p>
 * In producer consumer design pattern a shared queue is used to control the flow and this separation allows you to code
 * producer and consumer separately. It also addresses the issue of different timing require to produce item or
 * consuming item. by using producer consumer pattern both Producer and Consumer Thread can work with different speed.
 * 
 */
public class App {

  public static final int QUEUE_COUNT = 10;
  private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

  /**
   * Program entry point
   * 
   * @param args
   *          command line args
   */
  public static void main(String[] args) {

    ItemQueue[] queueList = new ItemQueue[QUEUE_COUNT];
    for (int i = 0; i < QUEUE_COUNT; i++) {
      queueList[i] = new ItemQueue();
    }
    Random random = new Random();
    ExecutorService executorService = Executors.newFixedThreadPool(100);
    for (int i = 0; i < 40; i++) {

      final Producer producer = new Producer("Producer_" + i, queueList);
      executorService.submit(() -> {
        while (true) {
          producer.produce();
        }
      });
    }

    for (int i = 0; i < 30; i++) {
      final Consumer consumer = new Consumer("Consumer_" + i, queueList);
      executorService.submit(() -> {
        while (true) {
          int queueIndex = random.nextInt(QUEUE_COUNT);
          consumer.consume(queueIndex);
        }
      });
    }

    executorService.shutdown();
    try {
      executorService.awaitTermination(100, TimeUnit.SECONDS);
      executorService.shutdownNow();
    } catch (InterruptedException e) {
      LOGGER.error("Error waiting for ExecutorService shutdown");
    }
  }
}
