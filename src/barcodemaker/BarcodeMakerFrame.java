package barcodemaker;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class BarcodeMakerFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5106873936989880889L;
	private BarcodeMakerMainPanel  contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BarcodeMakerFrame frame = new BarcodeMakerFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public BarcodeMakerFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new BarcodeMakerMainPanel();
		setContentPane(contentPane);
	}

}
