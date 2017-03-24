package dkeep.gui;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class OptionsDialogGUI extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtNumOgres;
	private JComboBox<String> cmbGuardPers;
	private JLabel lblGuardPers, lblNumOgres;
	private JButton btnConfirm;
	private static GUI gui=null;
	
	protected int confirmed;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			OptionsDialogGUI dialog = new OptionsDialogGUI(gui);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * @brief Initiates lblUnit
	 */
	private void lblInit(){
		lblNumOgres = new JLabel("Number of Ogres");
		lblNumOgres.setBounds(51, 49, 120, 25);
		getContentPane().add(lblNumOgres);
		
		lblGuardPers = new JLabel("Guard Personality");
		lblGuardPers.setBounds(51, 89, 153, 14);
		getContentPane().add(lblGuardPers);
	}
	/**
	 * @brief Initiates txtOgres, and makes input validation
	 */
	private void txtOgresInit(){
		txtNumOgres = new JTextField();
		txtNumOgres.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				JTextField textField = (JTextField) e.getSource();
				String text = textField.getText();
				boolean validate=true;
				int num=0;
				try{
					if(validate == true){
						num=Integer.parseInt(text);
						if(num>=1 && num<=5)
							validate=true;
						else{
							validate=false;
						}
					}
				}catch (NumberFormatException n){
					validate=false;
				}
				if(validate==true)
					textField.setText(text);
				else
					textField.setText("1");
			}
		});
		txtNumOgres.setText("1");
		txtNumOgres.setHorizontalAlignment(SwingConstants.CENTER);
		txtNumOgres.setColumns(10);
		txtNumOgres.setBounds(247, 49, 125, 20);
		getContentPane().add(txtNumOgres);
	}
	/**
	 * @brief Initiates cmbGuard
	 */
	private void cmbGuardInit(){
		cmbGuardPers = new JComboBox<String>();
		cmbGuardPers.setBounds(247, 89, 125, 20);
		cmbGuardPers.addItem("Rookie");
		cmbGuardPers.addItem("Drunken");
		cmbGuardPers.addItem("Suspicious");
		getContentPane().add(cmbGuardPers);
	}
	/**
	 * @brief Initiates btnConfirm and its ActionListener
	 */
	private void btnConfirmInit(){
		btnConfirm = new JButton("Confirm");
		btnConfirm.setBounds(170, 127, 89, 23);
		getContentPane().add(btnConfirm);
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				
				((PanelGame) gui.panelGame).panelShowGame.requestFocusInWindow();
				gui.panelMenu.setVisible(false);
				gui.panelGame.setVisible(true);
				((PanelGame) gui.panelGame).txtNumOgres.setText(txtNumOgres.getText());
				((PanelGame) gui.panelGame).cmbGuardPers.setSelectedIndex(cmbGuardPers.getSelectedIndex());

			}
		});
	}

	/**
	 * @brief Create the dialog.
	 */
	public OptionsDialogGUI(GUI gui) {
		setTitle("Ogres and Guards specifications");
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		OptionsDialogGUI.gui=gui;
		setBounds(100, 100, 450, 200);
		getContentPane().setLayout(null);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				((PanelGame) gui.panelGame).panelShowGame.requestFocusInWindow();
			}
		});
		
		lblInit();
		txtOgresInit();
		cmbGuardInit();
		btnConfirmInit();

	}
}
