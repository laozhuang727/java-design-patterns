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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Customer Class responsible for consume the {@link Item} produced by {@link Producer}
 */
public class Consumer {

  private static final Logger LOGGER = LoggerFactory.getLogger(Consumer.class);

  private ItemQueue[] queueList = null;

  private final String name;

  private final int queueCount;

  /**
   * Customer the Consumer with the random queue list
   * @param name Consumer name
   * @param queueList shared queueList
   */
  public Consumer(String name, ItemQueue[] queueList) {
    this.name = name;
    this.queueList = queueList;
    queueCount = queueList.length;
  }

  /**
   * Consume item from the queue
   */
  public void consume(int queueIndex) throws InterruptedException {
    checkArgument(queueIndex < queueCount);

    Item item = queueList[queueIndex].take();
    Thread.sleep(100);
    LOGGER.info("Consumer [{}] consume item [{}] produced by [{}]", name, item.getId(), item.getProducer());

  }
}
