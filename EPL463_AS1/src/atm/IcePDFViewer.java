/**Copyright (c) 2018 Paraskevas Louka

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.**/
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atm;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.icepdf.ri.common.ComponentKeyBinding;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;

/**
 *
 * @author EPL463
 */
public class IcePDFViewer {

	private static SwingController controller;

	public JPanel createViewer(String filePath) {

		// build a controller
		controller = new SwingController();

		// Build a SwingViewFactory configured with the controller
		SwingViewBuilder factory = new SwingViewBuilder(controller);

		JPanel viewerComponentPanel = new JPanel();

		viewerComponentPanel = factory.buildViewerPanel();

		// add copy keyboard command
		ComponentKeyBinding.install(controller, viewerComponentPanel);

		// add interactive mouse link annotation support via callback
		controller.getDocumentViewController().setAnnotationCallback(
				new org.icepdf.ri.common.MyAnnotationCallback(controller.getDocumentViewController()));

		return viewerComponentPanel;
	}

	public void createWindowViewer(String filePath) {
		if (Desktop.isDesktopSupported()) {
			try {
				// open pdf file
				Desktop.getDesktop().open(new File(filePath));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
 
	public void createIcePDFViewer(String filePath) {
		JPanel viewerComponentPanel = createViewer(filePath);
		// create a new jframe for pdf
		JFrame window = new JFrame("Statement");
		window.getContentPane().add(viewerComponentPanel);
		window.pack();
		window.setVisible(true);
		// Open a PDF document to view
		controller.openDocument(filePath);
	}

}
