package barcodemaker;

import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.JButton;

public class BarcodeMakerMainPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3311740127059207876L;
	private JTextField textField;

	/**
	 * Create the panel.
	 */
	public BarcodeMakerMainPanel() {
		setLayout(new GridLayout(1, 2, 0, 0));
		
		textField = new JTextField();
		add(textField);
		textField.setColumns(10);
		
		JButton btnWritesvg = new JButton("WriteSVG");
		btnWritesvg.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Code128Parser parser = new Code128Parser();
				Barcode bc = new Barcode(parser.parse(textField.getText()));
				bc.writeSVG("Barcode.svg");
			}			
		});
		add(btnWritesvg);

	}

}
