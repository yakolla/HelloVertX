/*
 * Copyright (c) 2011-2013 The original author or authors
 * ------------------------------------------------------
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Apache License v2.0 which accompanies this distribution.
 *
 *     The Eclipse Public License is available at
 *     http://www.eclipse.org/legal/epl-v10.html
 *
 *     The Apache License v2.0 is available at
 *     http://www.opensource.org/licenses/apache2.0.php
 *
 * You may elect to redistribute this code under either of these licenses.
 */

import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.AsyncResultHandler;
import org.vertx.java.core.Future;
import org.vertx.java.core.Handler;
import org.vertx.java.core.http.HttpServer;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.platform.Verticle;
import org.vertx.java.testframework.TestUtils;

public class Server extends Verticle {

  public void start(final Future<Void> result) {

    final TestUtils tu = new TestUtils(vertx);

    vertx.createHttpServer().requestHandler(new Handler<HttpServerRequest>() {
      public void handle(HttpServerRequest req) {
        // It's being proxied so should be absolute url
        tu.azzert(req.uri().equals("http://localhost:9193/maven2/io/vertx/mod-maven-proxy-test/1.0.0/mod-maven-proxy-test-1.0.0-mod.zip")
                ||req.uri().equals("http://localhost:9193/maven2/io/vertx/mod-maven-proxy-test/1.0.0/mod-maven-proxy-test-1.0.0.zip"));
        if (req.path().indexOf("..") != -1) {
          req.response().setStatusCode(403);
          req.response().end();
        } else {
          //Clearly in a real server you would check the path for better security!!
          req.response().sendFile("." + req.path());
        }
      }
    }).listen(9194, "127.0.0.1", new AsyncResultHandler<HttpServer>() {
      @Override
      public void handle(AsyncResult<HttpServer> ar) {
        result.setResult(null);
      }
    });
  }
}
