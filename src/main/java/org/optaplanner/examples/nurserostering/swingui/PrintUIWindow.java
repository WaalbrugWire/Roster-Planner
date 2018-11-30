/*
 * Copyright 2018 JBoss by Red Hat.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.optaplanner.examples.nurserostering.swingui;





/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 


import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.print.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import javax.imageio.ImageIO;
import javax.swing.*;



public class PrintUIWindow implements Printable, ActionListener {

    JFrame frameToPrint = null;
    JPanel panelToPrint = null;
    LocalDate startDate = null;
    File file = null;

    public int print(Graphics g, PageFormat pf, int page) throws
                                                        PrinterException {

        if (page > 0) { /* We have only one page, and 'page' is zero-based */
            return NO_SUCH_PAGE;
        }

        /* User (0,0) is typically outside the imageable area, so we must
         * translate by the X and Y values in the PageFormat to avoid clipping
         */
        
        Graphics2D g2d = (Graphics2D)g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());

        /* Now print the window and its visible contents */
        int imageHeight;
        int imageWidth;
        JLayeredPane jLayeredPane = null;
        if (frameToPrint != null) {
            jLayeredPane = frameToPrint.getLayeredPane();
            imageHeight = jLayeredPane.getHeight();
            imageWidth = jLayeredPane.getWidth();
        }
        else if (panelToPrint != null){
            imageHeight = panelToPrint.getHeight();
            imageWidth = panelToPrint.getWidth();
        }
        else {
            /* nothing to print */
            imageHeight = 0;
            imageWidth = 0;
            JOptionPane.showMessageDialog(null, "Nothing to print. No frame or panel found.");
        }
        try {
            //get height/width from the jLayeredPane or jPanel inside the scroll pane
                        
            //Draw contents of the jLayeredPane or jPanel inside the scroll pane (but not the scroll pane itself)
            BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);  
            Graphics2D gg = image.createGraphics();  
            if (jLayeredPane != null) {
                jLayeredPane.paint(gg);
            }
            else if (panelToPrint != null){
                panelToPrint.paint(gg);
            }
            

            //The "image" now has jLayers contents
            //Insert your code here to scale and print the image here
            //Some Code
            //In my example I have chosen to save the image to file with no scaling

            /* Instead of writing to file we want to print??? */
            String extension = "png";
            String fileName = "Roster" + startDate.toString() + "." + extension;
            ImageIO.write(image, extension, new File("./data/nurserostering/export/", fileName));
            extension = "jpeg";
            fileName = "Roster" + startDate.toString() + "." + extension;
            ImageIO.write(image, extension, new File("./data/nurserostering/export/", fileName));
            //ImageIO.write(image, "png", new File(file));
            
            //Some Code
            
            System.out.println("Done");
        }
        catch (IOException ex)
        {
            //Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Print event error occured: \n"+
                    "Error : " + ex);
        }
                
        //frameToPrint.printAll(g);
        

        /* tell the caller that this page is part of the printed document */
        return PAGE_EXISTS;
    }

    /* overridden in NurseRosteringPanel but still needed all necessary handler code moved to the apropriate event handler (where the print button is */
    public void actionPerformed(ActionEvent e) {
        /* 
        JOptionPane.showMessageDialog(null, "Print event: Do we ever get here???" + e);
         PrinterJob job = PrinterJob.getPrinterJob();
         job.setPrintable(this);
         boolean ok = job.printDialog();
         if (ok) {
             try {
                  job.print();
             } catch (PrinterException ex) {
              // The job did not successfully complete 
             }
         }
        */
    }
    

    public PrintUIWindow(JFrame f, LocalDate startDate) {
        frameToPrint = f;
        this.startDate = startDate;
    }
    
    public PrintUIWindow(JPanel p, LocalDate startDate) {
        panelToPrint = p;
        this.startDate = startDate;
    }

    /* disabled main example, WaalbrugWire, 13-5-2018
    public static void main(String args[]) {
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        JFrame f = new JFrame("Print UI Example");
        f.addWindowListener(new WindowAdapter() {
           public void windowClosing(WindowEvent e) {System.exit(0);}
        });
        JTextArea text = new JTextArea(50, 20);
        for (int i=1;i<=50;i++) {
            text.append("Line " + i + "\n");
        }
        JScrollPane pane = new JScrollPane(text);
        pane.setPreferredSize(new Dimension(250,200));
        f.add("Center", pane);
        JButton printButton = new JButton("Print This Window");
        printButton.addActionListener(new PrintUIWindow(f));
        f.add("South", printButton);
        f.pack();
        f.setVisible(true);
    }
    */
    
    /* NOTES  function will not print all
       suggested solution was to paint the layered pane to a buffered image like this code below
    
    try
    {
        //get height/width from the jLayeredPane or jPanel inside the scroll pane
        int imageHeight = jLayeredPane.getHeight();
        int imageWidth = jLayeredPane.getWidth();

        //Draw contents of the jLayeredPane or jPanel inside the scroll pane (but not the scroll pane itself)
        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);  
        Graphics2D gg = image.createGraphics();  
        jLayeredPane.paint(gg);

        //The "image" now has jLayers contents
        //Insert your code here to scale and print the image here
        //Some Code
        //In my example I have chosen to save the image to file with no scaling
        ImageIO.write(canvas, "png", new File("C:\\out.png"));
        //Some Code

        System.out.println("Done");
    }
    catch (IOException ex)
    {
        Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
    }
    */
}

