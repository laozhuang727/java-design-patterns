/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.ryan.pool2;

import java.net.Socket;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

/**
 * @author ryan
 * @version $Id: MyPoolableObjectFactory.java, v 0.1 2017年11月11日 下午2:07 ryan Exp $
 */
public class MyPoolableObjectFactory extends BasePooledObjectFactory<Resource> {


  public MyPoolableObjectFactory() {
  }

  /**
   * 创建一个对象实例
   */
  @Override
  public Resource create() throws Exception {
    Resource Resource = new Resource();
    return Resource;
  }

  /**
   * 包裹创建的对象实例，返回一个pooledobject
   */
  @Override
  public PooledObject<Resource> wrap(Resource obj) {
    return new DefaultPooledObject<Resource>(obj);
  }

  @Override
  public void destroyObject(PooledObject<Resource> obj) throws Exception {
     obj.getObject().close();

  }

  @Override
  public boolean validateObject(PooledObject<Resource> obj) {
    Socket socket = obj.getObject().getSocket();
    if (socket == null) {
      return false;
    }
    //if (socket.isClosed()) {
    //  return false;
    //}
    return true;

  }
}