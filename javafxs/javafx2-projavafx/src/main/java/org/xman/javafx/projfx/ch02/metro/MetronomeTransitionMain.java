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
 *
 * Metronome1Main.fx - A simple example of animation using a Timeline
 *
 *  Developed 2011 by James L. Weaver jim.weaver [at] javafxpert.com
 *  as a JavaFX SDK 2.0 example for the Pro JavaFX book.
 */
package org.xman.javafx.projfx.ch02.metro;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.animation.TranslateTransitionBuilder;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.GroupBuilder;
import javafx.scene.Scene;
import javafx.scene.SceneBuilder;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CircleBuilder;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MetronomeTransitionMain extends Application {
  Button startButton;
  Button pauseButton;
  Button resumeButton;
  Button stopButton;
  Circle circle = CircleBuilder.create()
    .centerX(100)
    .centerY(50)
    .radius(4)
    .fill(Color.BLUE)
    .build();
  
  TranslateTransition anim = TranslateTransitionBuilder.create()
    .duration(new Duration(1000.0))
    .node(circle)     
    .fromX(0)
    .toX(200)
    .interpolator(Interpolator.LINEAR)
    .autoReverse(true)
    .cycleCount(Timeline.INDEFINITE)
    .build();
  
  public static void main(String[] args) {
    Application.launch(args);
  }
  
  @Override
  public void start(Stage stage) {
    Scene scene  = SceneBuilder.create()
      .width(400)
      .height(500)
      .root(
        GroupBuilder.create()
          .children(
            circle,
            HBoxBuilder.create()
              .layoutX(60)
              .layoutY(420)
              .spacing(10)
              .children(
                startButton = ButtonBuilder.create()
                  .text("Start")
                  .onAction(new EventHandler<ActionEvent>() {
                    @Override public void handle(ActionEvent e) {
                      anim.playFromStart();
                    }
                  })
                  .build(),
                pauseButton = ButtonBuilder.create()
                  .text("Pause")
                  .onAction(new EventHandler<ActionEvent>() {
                    @Override public void handle(ActionEvent e) {
                      anim.pause();
                    }
                  })
                  .build(),
                resumeButton = ButtonBuilder.create()
                  .text("Resume")
                  .onAction(new EventHandler<ActionEvent>() {
                    @Override public void handle(ActionEvent e) {
                      anim.play();
                    }
                  })
                  .build(),
                stopButton = ButtonBuilder.create()
                  .text("Stop")
                  .onAction(new EventHandler<ActionEvent>() {
                    @Override public void handle(ActionEvent e) {
                      anim.stop();
                    }
                  })
                  .build()
              )
              .build()
          )
          .build()
      )
      .build();

    startButton.disableProperty().bind(anim.statusProperty()
            .isNotEqualTo(Animation.Status.STOPPED));
    pauseButton.disableProperty().bind(anim.statusProperty()
            .isNotEqualTo(Animation.Status.RUNNING));
    resumeButton.disableProperty().bind(anim.statusProperty()
            .isNotEqualTo(Animation.Status.PAUSED));
    stopButton.disableProperty().bind(anim.statusProperty()
            .isEqualTo(Animation.Status.STOPPED));
    
    stage.setScene(scene);
    stage.setTitle("Metronome using TranslateTransition");
    stage.show();
  }
}
