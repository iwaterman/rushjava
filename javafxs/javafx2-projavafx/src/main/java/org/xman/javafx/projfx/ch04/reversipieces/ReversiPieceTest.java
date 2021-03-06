/*
 * Copyright (c) 2011, Pro JavaFX Authors
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 3. Neither the name of JFXtras nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package org.xman.javafx.projfx.ch04.reversipieces;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.SceneBuilder;
import javafx.scene.layout.HBox;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPaneBuilder;
import javafx.stage.Stage;

/**
 * @author Stephen Chin <steveonjava@gmail.com>
 */
public class ReversiPieceTest extends Application {
  public static void main(String[] args) {
    launch(args);
  }
  
  @Override
  public void start(Stage primaryStage) {
    Node white, black;
    primaryStage.setScene(SceneBuilder.create()
      .root(HBoxBuilder.create()
         .snapToPixel(false)
         .children(
            white = StackPaneBuilder.create()
              .children(
                new ReversiSquare(),
                new ReversiPiece(Owner.WHITE)
              )
              .build(),
            black = StackPaneBuilder.create()
              .children(
                new ReversiSquare(),
                new ReversiPiece(Owner.BLACK)
              )
              .build()
         )
         .build())
      .build());
    HBox.setHgrow(white, Priority.ALWAYS);
    HBox.setHgrow(black, Priority.ALWAYS);
    primaryStage.show();
  }
}
