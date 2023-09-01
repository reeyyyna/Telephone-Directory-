package TelephoneFileDirectory;

import javax.swing.*;
import java.awt.*;


public class ScreenLoading extends JWindow{

	//basic info or declarations
		private static final long serialVersionUID = 1L;
		private JProgressBar loading;
		private JPanel panelColor;
		private JLabel label1, label2, label3;

		public ScreenLoading() {
		   initComponents();
		}

		private void initComponents() {
		this.setSize(400, 400);
		setLocationRelativeTo(null);

		        /***PANEL CONFIGURATION***/
		 panelColor = new JPanel();
		 panelColor.setBackground(new Color(135, 206, 235));
		 panelColor.setLayout(new GridBagLayout());
		 this.add(panelColor);

	           /***LABEL 1 CONFIGURATION***/
		 label1 = new JLabel();
		 label1.setFont(new Font("Consolas", Font.BOLD, 25));
		 label1.setForeground(Color.WHITE);
		 label1.setText("TELEPHONE DIRECTORY");

	     		/***LABEL 2 CONFIGURATION***/
		 label2 = new JLabel();
		 label2.setFont(new Font("Consolas", Font.BOLD, 12));
		 label2.setForeground(Color.WHITE);
		 label2.setText("Case Study #03: File Handling");
		        
	     		/***LABEL 3 CONFIGURATION***/
		 label3 = new JLabel();
		 label3.setFont(new Font("Consolas", Font.BOLD, 12));
		 label3.setForeground(Color.WHITE);
		 label3.setText("Programmed By: Aguilar | Miranda | Soberano");

		 GridBagConstraints gridLabel1 = new GridBagConstraints();
		 gridLabel1.gridx = 0;
		 gridLabel1.gridy = 1;
		 gridLabel1.anchor = GridBagConstraints.CENTER;

		 GridBagConstraints gridLabel2 = new GridBagConstraints();
		 gridLabel2.gridx = 0;
		 gridLabel2.gridy = 1;
		 gridLabel2.anchor = GridBagConstraints.CENTER;

		 GridBagConstraints gridLabel3 = new GridBagConstraints();
		 gridLabel3.gridx = 0;
		 gridLabel3.gridy = 2;
		 gridLabel3.anchor = GridBagConstraints.CENTER;

		 Object gridLablel1 = null;
		 panelColor.add(label1, gridLablel1);
		 panelColor.add(label2, gridLabel2);
		 panelColor.add(label3, gridLabel3);

		        /***LOADING***/
		 loading = new JProgressBar();
		 loading.setStringPainted(true);
		 loading.setOrientation(JProgressBar.HORIZONTAL);
		 loading.setPreferredSize(new Dimension(200, 10));
		 loading.setForeground(new Color(135, 220, 250)); //set the foreground color of the loading bar to a lighter shade of blue 

		        
		 GridBagConstraints gridLoading = new GridBagConstraints();
		     gridLoading.gridx = 0;
		     gridLoading.gridy = 3;
		     gridLoading.anchor = GridBagConstraints.CENTER;
		     gridLoading.insets.top = 10;
		     panelColor.add(loading, gridLoading);
		    }
		    
		  public void startLoading(Runnable loadingCompletedCallback) {
		        Thread loadingThread = new Thread(() -> {
		            try {
		                for (int k = 0; k <= 100; k++) {
		                    Thread.sleep(35);
		                    loading.setValue(k);
		                }
		            } catch (InterruptedException e) {
		                e.printStackTrace();
		            }
		            setVisible(false);
		            dispose();

		            //execute the callback to continue with the main GUI after loading is complete
		            loadingCompletedCallback.run();
		        });
		        loadingThread.start();
		    }

}
