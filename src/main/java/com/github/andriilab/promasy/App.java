/*
  Copyright 2018 Andrii Labyntsev et al
  <p>
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at
  <p>
  http://www.apache.org/licenses/LICENSE-2.0
  <p>
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
 */

/*
 * This is main Promasy class. It implements general method
 * for launching applications based on Swing interface.
 */

package com.github.andriilab.promasy;

import com.github.andriilab.promasy.data.controller.Controller;
import com.github.andriilab.promasy.presentation.MainFrame;

import javax.swing.*;

class App {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> run(args));
    }

    private static void run(String[] args) {
        MainFrame mainFrame = new MainFrame();
        new Controller(args, mainFrame);
    }
}
